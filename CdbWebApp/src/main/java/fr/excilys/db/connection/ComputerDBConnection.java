package fr.excilys.db.connection;
import java.sql.*;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
public class ComputerDBConnection {
	private static Connection conn;
	private static HikariConfig cfg=new HikariConfig("src/main/ressources/hikari.properties");
	private static HikariDataSource ds= new HikariDataSource(cfg);
	private static final Logger logger=LoggerFactory.getLogger(ComputerDBConnection.class);	
	private ComputerDBConnection() {}
	
	public static Connection getConnection(){
		conn=null; 
		try {
			conn=ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return conn;
	}	
	public static Connection closeConnection() {	
		if(conn!=null) {
			try {
				conn.close();
				logger.error("Cannot close the connection");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn=null;
		}
		return conn;
	}
}
