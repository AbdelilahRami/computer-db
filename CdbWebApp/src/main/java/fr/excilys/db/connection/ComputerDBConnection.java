package fr.excilys.db.connection;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
public class ComputerDBConnection {
	private Connection connection;
	private static HikariConfig cfg = new HikariConfig("/hikari.properties");
	private static HikariDataSource ds = new HikariDataSource(cfg);
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDBConnection.class);
	private static ComputerDBConnection computerDb;
	private ComputerDBConnection() {
	}
	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = ds.getConnection();
				return connection;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}
	public static ComputerDBConnection getInstance() {
		if (computerDb == null) {
			computerDb = new ComputerDBConnection();
		}
		return computerDb;
	}

	public static Connection closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				LOGGER.info("connection closed successufully");
			} catch (SQLException e) {
				LOGGER.error("Cannot close the connection !");
				System.out.println(e.getMessage());
			}
		}
		return conn;
	}
}
