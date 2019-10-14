package com.db.connection;
import java.sql.*;
public class ComputerDBConnection {
	private String url ="jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
	private String username="admincdb";
	private String password="qwerty1234";
	private static Connection conn;
	private static ComputerDBConnection computerDbInstance;

	private ComputerDBConnection() {}
	
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
	public static ComputerDBConnection getInstance() {
		if(computerDbInstance==null) {
			computerDbInstance=new ComputerDBConnection();
		}
		return computerDbInstance;
	}
	
	public static Connection closeConnection() throws SQLException {
		
		if(conn!=null) {
			conn.close();
			conn=null;
		}
		return conn;
	}

}
