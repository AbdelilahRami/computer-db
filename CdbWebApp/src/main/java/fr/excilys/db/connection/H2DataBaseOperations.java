package fr.excilys.db.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class H2DataBaseOperations {
	private static String url ="jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'C:/Users/Utilisateur/Desktop/DB/training-java/config/db/1-SCHEMA.sql'";
	private static String username ="root";
	private static String password ="root";
	private  static Connection connection;
	
	public static Connection getConnection(){
		try {
			connection=DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection closeConnection() {
		if(connection!=null) {
			try {
				connection.close();
				connection=null;
			} catch (SQLException e) {
				System.out.println("Connection cannot be closed !");
			}
			connection=null;
		}
		return connection;
	}
}
