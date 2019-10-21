package fr.excilys.db.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.dto.ComputerBuilder;
import fr.excilys.db.dto.ComputerMapper;
import fr.excilys.db.dto.DtoConnection;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DtoConnection.getConnection();
		Company company=null;
		String name = request.getParameter("computerName");
		String introducedDate=request.getParameter("introduced");
		String discontinuedDate=request.getParameter("discontinued");
		fr.excilys.db.dto.Computer dtoComputer=ComputerBuilder.newInstance().setName(name).setLocalDateIntro(introducedDate).setLocaldateDiscontinued(discontinuedDate).build();
		if(!request.getParameter("companyName").equals("")) {
			int idCompany=Integer.parseInt(request.getParameter("companyName"));
			company=ComputerDaoImpl.getInstance().getCompanyById(idCompany, conn);
		}
		Computer computer=ComputerMapper.fromStringToObject(dtoComputer);
		computer.setCompany(company);
		ComputerDaoImpl.getInstance().createComputer(computer, conn);
	}

}
