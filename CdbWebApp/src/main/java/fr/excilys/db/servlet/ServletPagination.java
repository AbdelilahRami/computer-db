package fr.excilys.db.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.dto.DtoConnection;
import fr.excilys.db.model.Page;

/**
 * Servlet implementation class ServletPagination
 */
@WebServlet("/servletPagination")
public class ServletPagination extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPagination() {
        super();
        // TODO Auto-generated constructor stub
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
		int pageSize=Integer.parseInt(request.getParameter("id"));
		int numberOfPages=ComputerDaoImpl.getInstance().getNumberOfPages(conn, pageSize);
		request.setAttribute("numberOfPages", numberOfPages);
		request.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
