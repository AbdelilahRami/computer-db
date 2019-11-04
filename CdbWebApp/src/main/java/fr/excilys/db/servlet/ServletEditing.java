package fr.excilys.db.servlet;
import java.io.IOException;
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
@Controller
public class ServletEditing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	ComputerServiceImpl computerservice;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
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
		Computer computer=computerservice.getComputerDetails(idComputer);
		List<Company> companies=computerservice.getAllCompanies();
		List<fr.excilys.db.dto.Company> companiesDto=CompanyMapper.fromListObjectsToListString(companies);
		request.setAttribute("companies", companiesDto);
		request.setAttribute("computer", computer);
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
		Company company = idCompnay.equalsIgnoreCase("") ? null : computerservice.getCompanyById(Integer.parseInt(idCompnay));
		Computer computer= ComputerMapper.fromStringToObject(computerDTO);
		computer.setId(idComp);
		computer.setCompany(company);
		computerservice.updateComputer(computer);
	}

}
