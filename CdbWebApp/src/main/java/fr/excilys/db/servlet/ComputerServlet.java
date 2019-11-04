package fr.excilys.db.servlet;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import fr.excilys.db.dto.ComputerMapper;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.impl.ComputerServiceImpl;

/**
 * Servlet implementation class FirsTServlet
 */
@WebServlet("/computerServlet")
@Controller
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	ComputerServiceImpl computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ComputerServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<fr.excilys.db.dto.Computer> computersDTO;
		int numberOfPages;
		String begin = request.getParameter("beginPage");
		int beginPage = (begin != null) ? Integer.parseInt(begin) : 1;
		String size = request.getParameter("size");
		int sizePage = (size != null) ? Integer.parseInt(size) : 10;
		String end = request.getParameter("endPage");
		int endPage = (end != null) ? Integer.parseInt(end) : 5;
		String name = request.getParameter("search");
		String order = request.getParameter("ordre");
		if (order != null && !order.isEmpty()) {
			numberOfPages = computerService.getNumberOfPages(sizePage);
			List<Computer> computers = computerService.getComputersByOrder(order, sizePage, beginPage);
			computersDTO = ComputerMapper.fromListObjecToListString(computers);
		} else if (name != null && !name.isEmpty()) {
			numberOfPages = computerService.getPagesNumberByName(sizePage, name);
			List<Computer> computers = computerService.getComputersByName(name, sizePage, beginPage);
			computersDTO = ComputerMapper.fromListObjecToListString(computers);
			while (endPage > numberOfPages) {
				endPage--;
			}
		} else {
			numberOfPages = computerService.getNumberOfPages(sizePage);
			List<Computer> computers = computerService.getComputersByPage(beginPage, sizePage);
			computersDTO = ComputerMapper.fromListObjecToListString(computers);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = request.getParameter("selection");
		List<String> list = Arrays.asList(s.split(","));
		for (String str : list) {
			int idComputer = Integer.parseInt(str);
			computerService.deleteComputer(idComputer);
		}
		doGet(request, response);
	}
}
