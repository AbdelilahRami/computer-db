package fr.excilys.db.servlet;
import java.io.IOException;
import java.time.LocalDate;
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
import fr.excilys.db.mapper.DatesConversion;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.impl.ComputerServiceImpl;
/**
 * Servlet implementation class ServletAddingComputer
 */
@WebServlet("/servletAddingComputer")
@Controller
public class ServletAddingComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Autowired
	ComputerServiceImpl computerService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAddingComputer() {
        super();
    }
    
	@Override
	public void init() throws ServletException {
		super.init();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//int id = Integer.parseInt(request.getParameter("id"));
		//Computer computer=computerService.getComputerDetails(id);
		List<Company> companies=computerService.getAllCompanies();
		List<fr.excilys.db.dto.Company> companiesDto=CompanyMapper.fromListObjectsToListString(companies);
		request.setAttribute("companies", companiesDto);
		//request.setAttribute("computer", computer);
		request.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Company company=null;
		String name = request.getParameter("computerName");
		String introducedDate=request.getParameter("introduced");
		String discontinuedDate=request.getParameter("discontinued");
		fr.excilys.db.dto.Computer dtoComputer=ComputerBuilder.newInstance().setName(name).setLocalDateIntro(introducedDate).setLocaldateDiscontinued(discontinuedDate).build();
		boolean inputIsValid=ServletAddingComputer.valideInputs(dtoComputer);
		if(inputIsValid) {
			if(!request.getParameter("companyId").equals("")) {
				int idCompany=Integer.parseInt(request.getParameter("companyId"));
				company=computerService.getCompanyById(idCompany);
			}
			Computer computer=ComputerMapper.fromStringToObject(dtoComputer);
			computer.setCompany(company);
			computerService.createComputer(computer);
			request.setAttribute("sucess", true);
			request.setAttribute("message", "Success your computer has benn added");
			request.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
		}else {
			request.setAttribute("fails", true);
			request.setAttribute("message", "Dicontinued date is after than introduced date !");
			request.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
		}
	}
	private static boolean valideInputs(fr.excilys.db.dto.Computer computer) {
		boolean inputValid=false;
		String computerName=computer.getName();
		if(computerName.isEmpty()) {
			return inputValid;
		}
		LocalDate lci=DatesConversion.fromStringToLocalDate(computer.getLocalDateIntroduction());
		LocalDate lcd=DatesConversion.fromStringToLocalDate(computer.getLocalDateDiscontinued());
		if((lci ==null || (lci instanceof LocalDate)) &&(lcd ==null ||lcd instanceof LocalDate)) {		
			if(DatesConversion.datesExisted(lci, lcd) && lcd.compareTo(lci)<0) {
				return inputValid;
			}
			else if((DatesConversion.datesExisted(lci, lcd) && lcd.compareTo(lci)>0) ||!DatesConversion.datesExisted(lci, lcd)) {
				return !inputValid;
			}
		}
		return inputValid;
	}
}