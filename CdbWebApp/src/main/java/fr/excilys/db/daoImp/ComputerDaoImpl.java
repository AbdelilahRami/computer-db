package fr.excilys.db.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.db.dialect.H2Dialect;
import fr.excilys.db.connection.ComputerDBConnection;
import fr.excilys.db.connection.H2DataBaseOperations;
import fr.excilys.db.dao.DaoComputer;
import fr.excilys.db.exception.ComputerToDeleteNotFound;
import fr.excilys.db.exception.NoCompanyFound;
import fr.excilys.db.exception.NoComputerFound;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.mapper.DatesConversion;
import fr.excilys.db.mapper.PageMappper;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.Page;

public class ComputerDaoImpl implements DaoComputer {
	private static ComputerDaoImpl computerDaoImpl;
	private static final String GET_ALL_COMPUTERS = "select * from computer ";
	private static final String GET_ALL_COMPANIES = "select * from company";
	private static final String GET_COMPUTERS_DETAILS = "select * from computer where id = ?";
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String DELETE_COMPUTER = "delete from computer where id = ?";
	private static final String GET_COMPUTERS_BY_PAGE = "select * from computer LIMIT ?, ?";
	private static final String GET_COMPANY_BY_ID = "select * from company where id = ?";
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	private Connection connection;
	private boolean testConnection = false;

	private ComputerDaoImpl() {
	}

	public ComputerDaoImpl(boolean testConnection) {
		this.testConnection = testConnection;
	}

	public static ComputerDaoImpl getInstance() {
		if (computerDaoImpl == null) {
			computerDaoImpl = new ComputerDaoImpl();
		}
		return computerDaoImpl;
	}

	@Override
	public List<Computer> getAllComputers() {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection()
				: H2DataBaseOperations.getConnection();
		LOGGER.info("The operations get all computers is running");
		List<Computer> computers = null;
		String query = GET_ALL_COMPUTERS;
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(query);
			computers = ComputerMapper.getInstance().getAllComputerMapper(rs);
			if (computers == null) {
				throw new NoComputerFound("There is no computer in database");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			LOGGER.error("There is an exception of type SQL" + e.getMessage());
		} catch (NoComputerFound e) {
			LOGGER.error("Computer database is empty !");
		} finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection)
					: H2DataBaseOperations.closeConnection();
		}
		return computers;
	}

	@Override
	public List<Company> getAllyCompanies() {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection()
				: H2DataBaseOperations.getConnection();
		LOGGER.info("The operation get all companies is running");
		List<Company> companies = null;
		try (Statement stm = connection.createStatement();) {
			ResultSet rs = stm.executeQuery(GET_ALL_COMPANIES);
			companies = ComputerMapper.getInstance().getAllCompaniesMapper(rs);
			if (companies == null || companies.isEmpty()) {
				throw new NoCompanyFound("There is no company in the table");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("There is an exception of type SQL" + e.getMessage());
		} catch (NoCompanyFound ex) {
			LOGGER.error("No company found !");
		} finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection)
					: H2DataBaseOperations.closeConnection();
		}
		return companies;
	}

	@Override
	public Computer getComputerDetails(int id) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection()
				: H2DataBaseOperations.getConnection();
		LOGGER.info("The operation get the details of the id=" + id + " is running");
		Computer computer = null;
		try (PreparedStatement pst = connection.prepareStatement(GET_COMPUTERS_DETAILS);) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			computer = ComputerMapper.getInstance().getComputerDetailsMapper(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			LOGGER.error("Showing the details of computer fails" + e.getMessage());
		} finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection)
					: H2DataBaseOperations.closeConnection();
		}
		return computer;
	}

	@Override
	public int createComputer(Computer computer) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection()
				: H2DataBaseOperations.getConnection();
		LOGGER.info("Creation of the computer" + computer.getName() + " is running");
		int i = 0;
		String query = CREATE_COMPUTER;
		try {
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertLocalToSql(computer.getDiscountedDate()));
			if (computer.getCompany() == null || computer.getCompany().getName().equals("")) {
				pstm.setString(4, null);
			} else {
				pstm.setInt(4, computer.getCompany().getId());
			}
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Creation of the computer fails" + e.getMessage());
		} finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection)
					: H2DataBaseOperations.closeConnection();
		}
		return i;
	}

	@Override
	public int updateComputer(Computer computer) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection()
				: H2DataBaseOperations.getConnection();
		LOGGER.info("Update computer" + computer.getName() + " is runninng");
		int i = 0;
		String query = UPDATE_COMPUTER;
		try (PreparedStatement pstm = connection.prepareStatement(query);) {
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertLocalToSql(computer.getDiscountedDate()));
			pstm.setInt(4, computer.getCompany().getId());
			pstm.setInt(5, computer.getId());
			i = pstm.executeUpdate();
		} catch (SQLException exc) {
			exc.printStackTrace();
			LOGGER.error("Update cannot fails :" + exc.getMessage());
		} finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection)
					: H2DataBaseOperations.closeConnection();
		}
		return i;
	}

	@Override
	public int deleteComputer(int id) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection()
				: H2DataBaseOperations.getConnection();
		LOGGER.info("Delete computer with the id " + id + " is running");
		int value = 0;
		Computer computer = ComputerDaoImpl.getInstance().getComputerDetails(id);
		try (PreparedStatement pstm = connection.prepareStatement(DELETE_COMPUTER);) {
			if (computer == null) {
				throw new ComputerToDeleteNotFound("This computer doesn't exist :");
			}
			pstm.setInt(1, id);
			value = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("delete computer fails");
		} catch (ComputerToDeleteNotFound e) {

		} finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection)
					: H2DataBaseOperations.closeConnection();
		}
		return value;
	}

	@Override
	public Company getCompanyById(int idCompany) {
		if (connection == null && !testConnection) {
			this.connection = ComputerDBConnection.getInstance().getConnection();
		}
		if (connection == null && testConnection) {
			this.connection = H2DataBaseOperations.getConnection();
		}
		Company company = null;
		try (PreparedStatement pst = connection.prepareStatement(GET_COMPANY_BY_ID);) {
			pst.setInt(1, idCompany);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				company = new Company(idCompany, name);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return company;
	}

	@Override
	public List<Computer> getComputersByPageNumber(int pageId, int pageSize) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection()
				: H2DataBaseOperations.getConnection();
		LOGGER.info("Pagination is running");
		List<Computer> computers = new ArrayList<Computer>();
		try {
			if (pageId > computerDaoImpl.getNumberOfPages(pageSize)) {
				throw new PageNotFoundException("You have exced the max number of pages");
			}
			PreparedStatement pstm = connection.prepareStatement(GET_COMPUTERS_BY_PAGE);
			Page page = new Page(pageSize);
			pstm.setInt(1, page.getPageSize() * (pageId - 1));
			pstm.setInt(2, page.getPageSize());
			ResultSet rs = pstm.executeQuery();
			computers = PageMappper.getComputersByPageNumberMapper(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (PageNotFoundException e) {
			LOGGER.error("You have exced the max number of pages");
		} finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection)
					: H2DataBaseOperations.closeConnection();
		}
		return computers;
	}

	@Override
	public int getNumberOfPages(int pageSize) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
		int numberOfPages = 0;
		int numberOflines = 0;
		try {
			Statement stm = connection.createStatement();
			ResultSet myRes = stm.executeQuery("select count(*) as number from computer");
			if (myRes.next()) {
				numberOflines = myRes.getInt(1);
			}
			numberOfPages = (numberOflines % pageSize == 0) ? numberOflines / pageSize : numberOflines / pageSize + 1;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 

		return numberOfPages;
	}
}