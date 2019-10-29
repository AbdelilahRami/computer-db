package fr.excilys.db.servlet;
import java.io.IOException;
import java.time.LocalDate;
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
import fr.excilys.db.mapper.DatesConversion;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.impl.ComputerServiceImpl;
/**
 * Servlet implementation class ServletAddingComputer
 */
@WebServlet("/servletAddingComputer")
public class ServletAddingComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAddingComputer() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies=ComputerServiceImpl.getInstance().getAllCompanies();
		List<fr.excilys.db.dto.Company> companiesDto=CompanyMapper.fromListObjectsToListString(companies);
		request.setAttribute("companies", companiesDto);
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
			if(!request.getParameter("companyName").equals("")) {
				int idCompany=Integer.parseInt(request.getParameter("companyName"));
				company=ComputerDaoImpl.getInstance().getCompanyById(idCompany);
			}
			Computer computer=ComputerMapper.fromStringToObject(dtoComputer);
			computer.setCompany(company);
			ComputerDaoImpl.getInstance().createComputer(computer);
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
		ComputerServiceImpl computerService=ComputerServiceImpl.getInstance();
		String computerName=computer.getName();
		if(computerName.isEmpty()) {
			return inputValid;
		}
		LocalDate lci=DatesConversion.fromStringToLocalDate(computer.getLocalDateIntroduction());
		LocalDate lcd=DatesConversion.fromStringToLocalDate(computer.getLocalDateDiscontinued());
		if((lci ==null || (lci instanceof LocalDate)) &&(lcd ==null ||lcd instanceof LocalDate)) {		
			if(computerService.datesExisted(lci, lcd) && lcd.compareTo(lci)<0) {
				return inputValid;
			}
			else if((computerService.datesExisted(lci, lcd) && lcd.compareTo(lci)>0) ||!computerService.datesExisted(lci, lcd)) {
				return !inputValid;
			}
		}
		return inputValid;
	}
}
