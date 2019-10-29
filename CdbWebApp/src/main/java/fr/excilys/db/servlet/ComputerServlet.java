package fr.excilys.db.servlet;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.dto.ComputerMapper;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.impl.ComputerServiceImpl;

/**
 * Servlet implementation class FirsTServlet
 */
@WebServlet("/computerServlet")
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerServlet() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDaoImpl computerDaoImpl= ComputerDaoImpl.getInstance();
		List<fr.excilys.db.dto.Computer> computersDTO;
		int numberOfPages;
		String begin =request.getParameter("beginPage");
		int beginPage= (begin != null) ? Integer.parseInt(begin) : 1;
		String size=request.getParameter("size");
		int sizePage = (size != null) ? Integer.parseInt(size) : 10;
		String end =request.getParameter("endPage");
		int endPage= (end != null) ? Integer.parseInt(end) : 5;
		String name=request.getParameter("search");
		String order=request.getParameter("ordre");
		if(order !=null && !order.isEmpty()) {
			numberOfPages=computerDaoImpl.getNumberOfPages(sizePage);
			List<Computer> computers=ComputerDaoImpl.getInstance().getComputersByOrder(order,sizePage,beginPage);
			computersDTO=ComputerMapper.fromListObjecToListString(computers);
		}
		else if(name != null && !name.isEmpty()) {
			numberOfPages=computerDaoImpl.getPagesNumberByName(sizePage, name);
			List<Computer> computers=ComputerServiceImpl.getInstance().getComputersByName(name, sizePage, beginPage);
			computersDTO=ComputerMapper.fromListObjecToListString(computers);
			while(endPage>numberOfPages) {
				endPage--;
			}
		}else{
		numberOfPages=computerDaoImpl.getNumberOfPages(sizePage);
		List<Computer> computers=ComputerServiceImpl.getInstance().getComputersByPage(beginPage,sizePage);
		computersDTO=ComputerMapper.fromListObjecToListString(computers);
		}
		request.setAttribute("order", order);
		request.setAttribute("search", name);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("size", sizePage);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("computers", computersDTO);		
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] s=request.getParameterValues("selection");
		List<String> list=Arrays.asList(s[0].split(","));
		for(String str: list) {
			int idComputer=Integer.parseInt(str);
			ComputerServiceImpl computerService=ComputerServiceImpl.getInstance();
			computerService.deleteComputer(idComputer);
			System.out.println("I've deleted computer number"+idComputer);
		}
		doGet(request, response);
	}
}
