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

import fr.excilys.db.connection.ComputerDBConnection;
import fr.excilys.db.dao.DaoComputer;
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
	private static final  Logger logger=LoggerFactory.getLogger(ComputerDaoImpl.class);
	private ComputerDaoImpl() {

	}
	public static ComputerDaoImpl getInstance() {
		if (computerDaoImpl == null) {
			computerDaoImpl = new ComputerDaoImpl();
		}
		return computerDaoImpl;
	}

	@Override
	public List<Computer> getAllComputers(Connection conn){
		logger.info("The operations get all computers is running");
		List<Computer> computers = null;
		String query = GET_ALL_COMPUTERS;
		try  {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(query);
			computers = ComputerMapper.getInstance().getAllComputerMapper(rs,conn);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("There is an exception of type SQL"+e.getMessage());
		} finally {
			conn = ComputerDBConnection.closeConnection();
		}
		return computers;
	}

	@Override
	public List<Company> getAllyCompanies(Connection conn){
		logger.info("The operation get all companies is running");
		List<Company> companies = null;
		try (Statement stm = conn.createStatement();) {
			ResultSet rs = stm.executeQuery(GET_ALL_COMPANIES);
			companies = ComputerMapper.getInstance().getAllCompaniesMapper(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("There is an exception of type SQL"+e.getMessage());
		} finally {
			conn = ComputerDBConnection.closeConnection();
		}
		return companies;
	}

	@Override
	public Computer getComputerDetails(int id,Connection conn){
		logger.info("The operation get the details of the id="+id+" is running");
		Computer computer = null;
		try (PreparedStatement pst = conn.prepareStatement(GET_COMPUTERS_DETAILS);) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			computer = ComputerMapper.getInstance().getComputerDetailsMapper(rs,conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			logger.error("Showing the details of computer fails"+e.getMessage());

		} finally {
			conn = ComputerDBConnection.closeConnection();
		}
		return computer;
	}

	@Override
	public int createComputer(Computer computer, Connection conn){
		logger.info("Creation of the computer"+computer.getName()+" is running");
		int i = 0;
		String query = CREATE_COMPUTER;
		try (PreparedStatement pstm = conn.prepareStatement(query);) {
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertLocalToSql(computer.getDiscountedDate()));
			pstm.setInt(4, computer.getCompany().getId());
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Creation of the computer fails"+e.getMessage());
		} finally {
			conn = ComputerDBConnection.closeConnection();
		}
		return i;
	}

	@Override
	public int updateComputer(Computer computer,Connection conn){
		logger.info("Update computer"+computer.getName()+" is runninng");
		int i = 0;
		String query = UPDATE_COMPUTER;
		try (PreparedStatement pstm = conn.prepareStatement(query);) {
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertLocalToSql(computer.getDiscountedDate()));
			pstm.setInt(4, computer.getCompany().getId());
			pstm.setInt(5, computer.getId());
			i = pstm.executeUpdate();
		} catch (SQLException exc) {
			exc.printStackTrace();
			logger.error("Update cannot fails :"+exc.getMessage());
		} finally {
			conn = ComputerDBConnection.closeConnection();
		}
		return i;
	}

	@Override
	public int deleteComputer(int id,Connection conn){
		logger.info("Delete computer with the id "+id+" is running");
		int value = 0;
		String query = DELETE_COMPUTER;
		try (PreparedStatement pstm = conn.prepareStatement(query);) {
			pstm.setInt(1, id);
			value = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("delete computer fails");
		} finally {
			conn = ComputerDBConnection.closeConnection();
		}
		return value;
	}

	@Override
	public Company getCompanyById(int idCompany,Connection conn) {
		Company company = null;
		try (PreparedStatement pst = conn.prepareStatement(GET_COMPANY_BY_ID);) {
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
	public List<Computer> getComputersByPageNumber(int pageId, Connection conn) throws PageNotFoundException {
		logger.info("Pagination is running");
		List<Computer> computers = new ArrayList<Computer>();
		try (PreparedStatement pstm = conn.prepareStatement(GET_COMPUTERS_BY_PAGE);) {
			pstm.setInt(1, Page.getPageSize() * (pageId - 1));
			pstm.setInt(2, Page.getPageSize());
			ResultSet rs = pstm.executeQuery();
			computers = PageMappper.getComputersByPageNumberMapper(rs,conn);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error in pagination part.");
		}finally {
			conn = ComputerDBConnection.closeConnection();
		}
		return computers;
	}

	@Override
	public int getNumberOfPages(Connection conn){
		int pageSize = Page.getPageSize();
		int numberOfPages = 0;
		try (Statement stm = conn.createStatement();) {
			ResultSet myRes = stm.executeQuery("select count(*) as number from computer");
			int numberOflines = myRes.getInt("number");
			numberOfPages = numberOflines / pageSize;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = ComputerDBConnection.closeConnection();
		}

		return numberOfPages;
	}

}