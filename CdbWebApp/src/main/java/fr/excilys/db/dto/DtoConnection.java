package fr.excilys.db.dto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DtoConnection {
	private static final String url="jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
	private static final String username="admincdb";
	private static final String password="qwerty1234";
	private DtoConnection() {}
	public static Connection getConnection() {
		Connection conn=null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
}