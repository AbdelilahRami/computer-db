package fr.excilys.db.connection;
import java.sql.*;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ComputerDBConnection {
	private static Connection connection;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDBConnection.class);
	@Autowired
	 DataSource dataSource;
	
	public  Connection getConnection() {
		try {
			if (connection == null) {
				connection = dataSource.getConnection();
				return connection;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	public  Connection closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				LOGGER.info("connection closed successufully");
			} catch (SQLException e) {
				LOGGER.error("Cannot close the connection !");
				System.out.println(e.getMessage());
			}
		}
		return connection;
	}
}
