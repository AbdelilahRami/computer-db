package fr.excilys.db.coonection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class H2DataBaseOperations {
	
	private static String url ="jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'C:/Users/Utilisateur/Desktop/DB/training-java/config/db/1-SCHEMA.sql'";
	private static String username ="root";
	private static String password ="root";
	private  static Connection conn;
	
	public static Optional<Connection> getConnection(){
		
		try {
			conn=DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.of(conn);
	}
	public static void closeConnection() {
		
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Connection cannot be closed !");
			}
			conn=null;
		}
	}

}
