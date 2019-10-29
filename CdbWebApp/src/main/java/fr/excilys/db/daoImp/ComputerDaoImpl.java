package fr.excilys.db.daoImp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.excilys.db.connection.ComputerDBConnection;
import fr.excilys.db.connection.H2DataBaseOperations;
import fr.excilys.db.dao.DaoComputer;
import fr.excilys.db.exception.NoCompanyFound;
import fr.excilys.db.exception.NoComputerFound;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.mapper.DatesConversion;
import fr.excilys.db.mapper.PageMappper;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
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
	private static final String GET_COMPUTERS_BY_NAME="select computer.id, computer.name, computer.introduced,"
			+ " computer.discontinued,computer.company_id from computer left join company on computer.company_id=company.id"
			+ " where computer.name like ? or company.name like ? order by computer.name limit ? offset ?";
	private static final String GET_NUMBER_OF_COMPUTERS_BY_NAME="select count(*) as number from computer left  join"
			+ " company on computer.company_id = company.id where computer.name like ? or company.name like ? ";
	private static final String DELETE_COMPUTERS_BY_COMPANY="delete from computer where company_id = ?";
	private static final String DELETE_COMPANY_BY_ID="delete from company where id = ?";
	private static final String GET_COMPUTERS_BY_ORDER="select computer.id, computer.name, computer.introduced, computer.discontinued,"
			+ "computer.company_id from computer order by computer.name ";
	private static final String LIMIT = " limit ? offset ?";
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
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
		LOGGER.info("The operations get all computers is running");
		List<Computer> computers = null;
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(GET_ALL_COMPUTERS);
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
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection) : H2DataBaseOperations.closeConnection();
		}
		return computers;
	}

	@Override
	public List<Company> getAllyCompanies() {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
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
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
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
		try {
			PreparedStatement pstm = connection.prepareStatement(CREATE_COMPUTER);
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
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
		LOGGER.info("Update computer" + computer.getName() + " is runninng");
		int i = 0;
		try (PreparedStatement pstm = connection.prepareStatement(UPDATE_COMPUTER);) {
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertLocalToSql(computer.getDiscountedDate()));
			if (computer.getCompany() == null || computer.getCompany().getName().equals("")) {pstm.setString(4, null);} 
			else { pstm.setInt(4, computer.getCompany().getId());}
			pstm.setInt(5, computer.getId());
			i = pstm.executeUpdate();
		} catch (SQLException exc) {
			exc.printStackTrace();
			LOGGER.error("Update cannot succed :" + exc.getMessage());
		} finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection) : H2DataBaseOperations.closeConnection();
		}
		return i;
	}
	@Override
	public int deleteComputer(int id) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
		LOGGER.info("Delete computer with the id " + id + " is running");
		int value = 0;
		try (PreparedStatement pstm = this.connection.prepareStatement(DELETE_COMPUTER);) {
			pstm.setInt(1, id);
			value = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("delete computer fails");
		}  finally {
			connection = !testConnection ? ComputerDBConnection.closeConnection(connection) : H2DataBaseOperations.closeConnection();
		}
		return value;
	}
	@Override
	public Company getCompanyById(int idCompany) {
		if (connection == null && !testConnection) {this.connection = ComputerDBConnection.getInstance().getConnection();}
		if (connection == null && testConnection) {this.connection = H2DataBaseOperations.getConnection();}
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
	@Override
	public List<Computer> getComputersByName(String name, int size, int numPage) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
		int offset=(numPage-1)*size+1;
		List<Computer> computers = new ArrayList<Computer>();
		LOGGER.info("getting computer by name is running");
		try {
			PreparedStatement pstm= connection.prepareStatement(GET_COMPUTERS_BY_NAME);
			pstm.setString(1, "%"+name+"%");
			pstm.setString(2, "%"+name+"%");
			pstm.setInt(3, size);
			pstm.setInt(4,offset);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				int idComputer = rs.getInt("computer.id");
				String nameComputer = rs.getString("computer.name");
				Date introducedDate = rs.getDate("computer.introduced");
				Date discountedDate = rs.getDate("computer.discontinued");
				LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
				LocalDate discontinued = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "discontinued");
				int idCompany = rs.getInt("computer.company_id");
				Company company = computerDaoImpl.getCompanyById(idCompany);
				Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(nameComputer)
						.setIntroducedDate(introduced).setDiscountedDate(discontinued).setCompany(company).build();
				computers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}
	@Override
	public int getPagesNumberByName(int pageSize, String name) {
		int numberOfPages = 0;
		int numberOflines = 0;
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
		try {
			PreparedStatement pstm = connection.prepareStatement(GET_NUMBER_OF_COMPUTERS_BY_NAME);
			pstm.setString(1, "%"+name+"%");
			pstm.setString(2,"%"+name+"%");
			ResultSet myRes = pstm.executeQuery();
			if (myRes.next()) {
				numberOflines = myRes.getInt(1);
			}
			numberOfPages = (numberOflines % pageSize == 0) ? numberOflines / pageSize : numberOflines / pageSize + 1;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return numberOfPages;
	}
	@Override
	public int deleteCompany(int id) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
		int executedQueryCompany=0;
		try {
			connection.setAutoCommit(false);
			PreparedStatement pstm=connection.prepareStatement(DELETE_COMPUTERS_BY_COMPANY);
			pstm.setInt(1, id);
			int executedQueryComputer=pstm.executeUpdate();
			PreparedStatement stm=connection.prepareStatement(DELETE_COMPANY_BY_ID);
			stm.setInt(1, id);
			executedQueryCompany= stm.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return executedQueryCompany;
	}
	@Override
	public List<Computer> getComputersByOrder(String order, int sizePage, int numPage) {
		this.connection = !testConnection ? ComputerDBConnection.getInstance().getConnection() : H2DataBaseOperations.getConnection();
		List<Computer> computers=new ArrayList<Computer>();
		int offset=(numPage-1)*sizePage+1;
		try {
			PreparedStatement pstm=connection.prepareStatement(GET_COMPUTERS_BY_ORDER+order+LIMIT);
			pstm.setInt(1, sizePage);
			pstm.setInt(2, offset);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				int idComputer = rs.getInt("computer.id");
				String nameComputer = rs.getString("computer.name");
				Date introducedDate = rs.getDate("computer.introduced");
				Date discountedDate = rs.getDate("computer.discontinued");
				LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
				LocalDate discontinued = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "discontinued");
				int idCompany = rs.getInt("computer.company_id");
				Company company = computerDaoImpl.getCompanyById(idCompany);
				Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(nameComputer)
						.setIntroducedDate(introduced).setDiscountedDate(discontinued).setCompany(company).build();
				computers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}	
}