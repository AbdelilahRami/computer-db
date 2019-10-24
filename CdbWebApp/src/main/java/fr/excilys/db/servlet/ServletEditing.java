package fr.excilys.db.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.dto.ComputerMapper;
import fr.excilys.db.model.Computer;

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
		request.setAttribute("computer", dtoComputer);
		request.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
