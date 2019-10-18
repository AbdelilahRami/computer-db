package fr.excilys.db.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DtoConnection {
	
	private static final String url="jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
	private static final String username="admincdb";
	private static final String password="qwerty1234";
	
	private DtoConnection() {}
	
	public static Connection getConnection() throws ClassNotFoundException {
		
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
