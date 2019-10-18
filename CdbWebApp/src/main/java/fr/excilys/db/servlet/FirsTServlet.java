package fr.excilys.db.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.dto.ComputerMapper;
import fr.excilys.db.dto.DtoConnection;
import fr.excilys.db.model.Computer;

/**
 * Servlet implementation class FirsTServlet
 */
@WebServlet("/firstServlet")
public class FirsTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirsTServlet() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn=null;
		try {
			conn = DtoConnection.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		List<Computer> computers=ComputerDaoImpl.getInstance().getAllComputers(conn);
		List<fr.excilys.db.dto.Computer> computersDTO=ComputerMapper.fromListObjecToListString(computers);
		request.setAttribute("computers", computersDTO);
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
