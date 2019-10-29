package fr.excilys.db.servlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.dto.CompanyMapper;
import fr.excilys.db.dto.ComputerBuilder;
import fr.excilys.db.dto.ComputerMapper;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.impl.ComputerServiceImpl;
/**
 * Servlet implementation class ServletEditing
 */
@WebServlet("/servletEditing")
public class ServletEditing extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEditing() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("id");
		int idComputer=Integer.parseInt(id);
		Computer computer=ComputerDaoImpl.getInstance().getComputerDetails(idComputer);
		fr.excilys.db.dto.Computer dtoComputer=ComputerMapper.fromObjectToString(computer);
		List<Company> companies=ComputerServiceImpl.getInstance().getAllCompanies();
		List<fr.excilys.db.dto.Company> companiesDto=CompanyMapper.fromListObjectsToListString(companies);
		request.setAttribute("companies", companiesDto);
		request.setAttribute("computer", dtoComputer);
		request.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idComputer=request.getParameter("id");
		int idComp= Integer.parseInt(idComputer);
		String name = request.getParameter("name");
		String introducedDate=request.getParameter("introduced");
		String discontinuedDate=request.getParameter("discontinued");
		String idCompnay=request.getParameter("companyId");
		fr.excilys.db.dto.Computer computerDTO= ComputerBuilder.newInstance().setId(idComputer).setName(name).setLocalDateIntro(introducedDate).setLocaldateDiscontinued(discontinuedDate).build();
		Company company = idCompnay.equalsIgnoreCase("") ? null : ComputerDaoImpl.getInstance().getCompanyById(Integer.parseInt(idCompnay));
		Computer computer= ComputerMapper.fromStringToObject(computerDTO);
		computer.setId(idComp);
		computer.setCompany(company);
		ComputerDaoImpl computerDao= ComputerDaoImpl.getInstance();
		computerDao.updateComputer(computer);
	}

}
