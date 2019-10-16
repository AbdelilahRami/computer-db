package fr.excilys.db.connection;
import java.sql.*;
public class ComputerDBConnection {
	private String url;
	private String username;
	private String password;
	private static Connection conn;
	private static ComputerDBConnection computerDbInstance;

	
	public ComputerDBConnection(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public Connection getConnection() {
		try {			
			conn=DriverManager.getConnection(url, username, password);
			
		}catch(SQLException exc) {
			for(Throwable e : exc) {
				System.err.println("Connection problem " + e);
			}
		}
		return conn;
	}

	
	public static Connection closeConnection() {
		
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Connection cannot be closed !");
			}
			conn=null;
		}
		return conn;
	}

}
