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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.excilys.db.connection.ComputerDBConnection;
import fr.excilys.db.dao.DaoComputer;
import fr.excilys.db.exception.DatesNotValidException;
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
@Repository
public class ComputerDaoImpl implements DaoComputer {
	private static final String GET_ALL_COMPUTERS = "select computer.id, computer.name, computer.introduced, "
												  + "computer.discontinued, computer.company_id, company.name from computer "
			+ "left join company on computer.company_id=company.id";
	private static final String GET_ALL_COMPANIES = "select * from company";
	private static final String GET_COMPUTERS_DETAILS = "select computer.id, computer.name, computer.introduced, "
													  +"computer.discontinued, computer.company_id, company.name from computer "
													  +"left join company on computer.company_id=company.id where computer.id=?";
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String DELETE_COMPUTER = "delete from computer where id = ?";
	private static final String GET_COMPUTERS_BY_PAGE = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name, computer.company_id"
													  + " from computer left join company on computer.company_id=company.id LIMIT ?, ?";
	private static final String GET_COMPANY_BY_ID = "select * from company where id = ?";
	private static final String GET_COMPUTERS_BY_NAME = "select computer.id, computer.name, computer.introduced,"
			+ " computer.discontinued,computer.company_id "
			+ "from computer left join company on computer.company_id=company.id"
			+ " where computer.name like ? or company.name like ? order by computer.name limit ? offset ?";
	private static final String GET_NUMBER_OF_COMPUTERS_BY_NAME = "select count(*) as number from computer left  join"
			+ " company on computer.company_id = company.id " + "where computer.name like ? or company.name like ? ";
	private static final String DELETE_COMPUTERS_BY_COMPANY = "delete from computer where company_id = ?";
	private static final String DELETE_COMPANY_BY_ID = "delete from company where id = ?";
	private static final String GET_COMPUTERS_BY_ORDER = "select computer.id, computer.name, computer.introduced, computer.discontinued,"
			+ "computer.company_id from computer order by computer.name ";
	private static final String LIMIT = " limit ? offset ?";
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	private Connection connection;
	// private boolean testConnection = false;

	@Autowired
	ComputerDBConnection computerDbConnection;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	PageMappper pageMapper;

	@Override
	public List<Computer> getAllComputers() {
		this.connection = computerDbConnection.getConnection();
		LOGGER.info("The operations get all computers is running");
		List<Computer> computers = null;
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(GET_ALL_COMPUTERS);
			computers = computerMapper.getAllComputerMapper(rs);
			if (computers == null) {
				throw new NoComputerFound("There is no computer in database");
			}
		} catch (SQLException e) {
			LOGGER.error("There is an exception of type SQL" + e.getMessage());
		} catch (NoComputerFound e) {
			LOGGER.error("Computer database is empty !");
		} finally {
			connection = computerDbConnection.closeConnection();
		}
		return computers;
	}

	@Override
	public List<Company> getAllyCompanies() {
		this.connection = computerDbConnection.getConnection();
		LOGGER.info("The operation get all companies is running");
		List<Company> companies = null;
		try (Statement stm = connection.createStatement();) {
			ResultSet rs = stm.executeQuery(GET_ALL_COMPANIES);
			companies = computerMapper.getAllCompaniesMapper(rs);
			if (companies == null || companies.isEmpty()) {
				throw new NoCompanyFound("There is no company in the table");
			}
		} catch (SQLException e) {
			LOGGER.error("There is an exception of type SQL" + e.getMessage());
		} catch (NoCompanyFound ex) {
			LOGGER.error("No company found !");
		} finally {
			connection = computerDbConnection.closeConnection();
		}
		return companies;
	}

	@Override
	public Computer getComputerDetails(int id) {
		this.connection = computerDbConnection.getConnection();
		LOGGER.info("The operation get the details of the id=" + id + " is running");
		Computer computer = null;
		try{
			PreparedStatement pst = connection.prepareStatement(GET_COMPUTERS_DETAILS);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			computer = computerMapper.getComputerDetailsMapper(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			LOGGER.error("Showing the details of computer fails" + e.getMessage());
		} finally {
			connection = computerDbConnection.closeConnection();
		}
		return computer;
	}

	@Override
	public int createComputer(Computer computer) {
		this.connection = computerDbConnection.getConnection();
		LOGGER.info("Creation of the computer" + computer.getName() + " is running");
		int i = 0;
		try (PreparedStatement pstm = connection.prepareStatement(CREATE_COMPUTER);){
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertLocalToSql(computer.getDiscountedDate()));
			if (DatesConversion.datesExisted(computer.getDiscountedDate(), computer.getIntroducedDate())
					&& computer.getDiscountedDate().compareTo(computer.getIntroducedDate()) <= 0) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) 
			if (computer.getCompany() == null || computer.getCompany().getName().equals("")) {
				pstm.setString(4, null);
			} else {
				pstm.setInt(4, computer.getCompany().getId());
			}
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Creation of the computer fails" + e.getMessage());
		} catch (DatesNotValidException e) {
			LOGGER.error("Discounted date must be greater than introduced date" + e.getMessage());
		}  finally {
			connection = computerDbConnection.closeConnection();
		}
		return i;
	}
	
	@Override
	public int updateComputer(Computer computer) {
		this.connection = computerDbConnection.getConnection();
		LOGGER.info("Update computer" + computer.getName() + " is runninng");
		int i = 0;
		try (PreparedStatement pstm = connection.prepareStatement(UPDATE_COMPUTER);) {
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertLocalToSql(computer.getDiscountedDate()));
			if (DatesConversion.datesExisted(computer.getDiscountedDate(), computer.getIntroducedDate())
					&& computer.getDiscountedDate().compareTo(computer.getIntroducedDate()) <= 0) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			}
			if (computer.getCompany() == null || computer.getCompany().getName().equals("")) {
				pstm.setString(4, null);
			} else {
				pstm.setInt(4, computer.getCompany().getId());
			}
			pstm.setInt(5, computer.getId());
			i = pstm.executeUpdate();
		} catch (SQLException exc) {
			LOGGER.error("Update cannot succed :" + exc.getMessage());
		} catch (DatesNotValidException e) {
			LOGGER.error("Update cannot succed :" + e.getMessage());
		} finally {
			connection = computerDbConnection.closeConnection();
		}
		return i;
	}

	@Override
	public int deleteComputer(int id) {
		this.connection = computerDbConnection.getConnection();
		LOGGER.info("Delete computer with the id " + id + " is running");
		int value = 0;
		try (PreparedStatement pstm = this.connection.prepareStatement(DELETE_COMPUTER);) {
			pstm.setInt(1, id);
			value = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("delete computer fails");
		} finally {
			connection = computerDbConnection.closeConnection();
		}
		return value;
	}

	@Override
	public Company getCompanyById(int idCompany) {
		if (connection == null) {
			this.connection = computerDbConnection.getConnection();
		}
		Company company = null;
		try {
			PreparedStatement pst = connection.prepareStatement(GET_COMPANY_BY_ID);
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
		this.connection = computerDbConnection.getConnection();
		LOGGER.info("Pagination is running");
		List<Computer> computers = new ArrayList<Computer>();
		try {
			if (pageId > getNumberOfPages(pageSize)) {
				throw new PageNotFoundException("You have exced the max number of pages");
			}
			PreparedStatement pstm = this.connection.prepareStatement(GET_COMPUTERS_BY_PAGE);
			Page page = new Page(pageSize);
			pstm.setInt(1, page.getPageSize() * (pageId - 1));
			pstm.setInt(2, page.getPageSize());
			ResultSet rs = pstm.executeQuery();
			computers = pageMapper.getComputersByPageNumberMapper(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (PageNotFoundException e) {
			LOGGER.error("You have exced the max number of pages");
		}
		finally {
			connection = computerDbConnection.closeConnection();
		}
		return computers;
}

	@Override
	public int getNumberOfPages(int pageSize) {
		this.connection = computerDbConnection.getConnection();
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
		this.connection = computerDbConnection.getConnection();
		int offset = (numPage - 1) * size + 1;
		List<Computer> computers = new ArrayList<Computer>();
		LOGGER.info("getting computer by name is running");
		try (PreparedStatement pstm = connection.prepareStatement(GET_COMPUTERS_BY_NAME);) {
			pstm.setString(1, "%" + name + "%");
			pstm.setString(2, "%" + name + "%");
			pstm.setInt(3, size);
			pstm.setInt(4, offset);
			ResultSet rs = pstm.executeQuery();
			mapperByName(computers, rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			connection = computerDbConnection.closeConnection();
		}
		return computers;
	}

	private void mapperByName(List<Computer> computers, ResultSet rs) throws SQLException {
		while (rs.next()) {
			int idComputer = rs.getInt("computer.id");
			String nameComputer = rs.getString("computer.name");
			Date introducedDate = rs.getDate("computer.introduced");
			Date discountedDate = rs.getDate("computer.discontinued");
			LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
			LocalDate discontinued = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "discontinued");
			int idCompany = rs.getInt("computer.company_id");
			Company company = getCompanyById(idCompany);
			Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(nameComputer)
					.setIntroducedDate(introduced).setDiscountedDate(discontinued).setCompany(company).build();
			computers.add(computer);
		}
	}

	@Override
	public int getPagesNumberByName(int pageSize, String name) {
		int numberOfPages = 0;
		int numberOflines = 0;
		this.connection = computerDbConnection.getConnection();
		try {
			PreparedStatement pstm = connection.prepareStatement(GET_NUMBER_OF_COMPUTERS_BY_NAME);
			pstm.setString(1, "%" + name + "%");
			pstm.setString(2, "%" + name + "%");
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
		this.connection = computerDbConnection.getConnection();
		int executedQueryCompany = 0;
		try (PreparedStatement pstm = connection.prepareStatement(DELETE_COMPUTERS_BY_COMPANY);) {
			connection.setAutoCommit(false);
			pstm.setInt(1, id);
			PreparedStatement stm = connection.prepareStatement(DELETE_COMPANY_BY_ID);
			stm.setInt(1, id);
			executedQueryCompany = stm.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			} finally {
				connection = computerDbConnection.closeConnection();
			}
		}
		return executedQueryCompany;
	}

	@Override
	public List<Computer> getComputersByOrder(String order, int sizePage, int numPage) {
		this.connection = computerDbConnection.getConnection();
		List<Computer> computers = new ArrayList<Computer>();
		int offset = (numPage - 1) * sizePage + 1;
		try (PreparedStatement pstm = connection.prepareStatement(GET_COMPUTERS_BY_ORDER + order + LIMIT);) {
			pstm.setInt(1, sizePage);
			pstm.setInt(2, offset);
			ResultSet rs = pstm.executeQuery();
			mapperByName(computers, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection = computerDbConnection.closeConnection();
		}
		return computers;
	}
}