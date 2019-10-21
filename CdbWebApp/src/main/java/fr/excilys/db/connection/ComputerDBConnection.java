package fr.excilys.db.connection;
import java.sql.*;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
public class ComputerDBConnection {
	private String url;
	private String username;
	private String password;
	private static Connection conn;
	private static ComputerDBConnection computerDbInstance;
	private static final Logger logger=LoggerFactory.getLogger(ComputerDBConnection.class);
	
	public ComputerDBConnection(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public Connection getConnection() throws ClassNotFoundException {
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url, username, password);
			logger.info("Connection is established successfully");
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
				logger.error("Cannot close the connection");
			} catch (SQLException e) {
				System.out.println("Connection cannot be closed !");
			}
			conn=null;
		}
		return conn;
	}

}
