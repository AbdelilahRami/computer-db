package com.db.connection;
import java.sql.*;
public class ComputerDBConnection {
	private String url ="jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
	private String username="admincdb";
	private String password="qwerty1234";
	private static Connection conn;
	
	private ComputerDBConnection() {
		try {
			System.out.println("Connection...");
			conn=DriverManager.getConnection(url, username, password);
			
		}catch(SQLException exc) {
			exc.printStackTrace();
		}
	}
	public static Connection getInstance() {
		if(conn==null) {
			new ComputerDBConnection();
		}
		return conn;
	}

}
