package com.db.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.db.connection.ComputerDBConnection;
import com.db.dao.DaoComputer;
import com.db.exception.PageNotFoundException;
import com.db.mapper.ComputerMapper;
import com.db.mapper.DateValidator;
import com.db.mapper.PageMappper;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.Page;

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
	private Connection conn;

	private ComputerDaoImpl() {

	}
	public static ComputerDaoImpl getInstance() {
		if (computerDaoImpl == null) {
			computerDaoImpl = new ComputerDaoImpl();
		}
		return computerDaoImpl;
	}

	@Override
	public List<Computer> getAllComputers(){
		List<Computer> computers = null;
		conn = ComputerDBConnection.getInstance().getConnection();
		String query = GET_ALL_COMPUTERS;
		try  {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(query);
			computers = ComputerMapper.getInstance().getAllComputerMapper(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = ComputerDBConnection.closeConnection();
		}
		return computers;
	}

	@Override
	public List<Company> getAllyCompanies(){
		List<Company> companies = null;
		this.conn = ComputerDBConnection.getInstance().getConnection();
		try (Statement stm = conn.createStatement();) {
			ResultSet rs = stm.executeQuery(GET_ALL_COMPANIES);
			companies = ComputerMapper.getInstance().getAllCompaniesMapper(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = ComputerDBConnection.closeConnection();
		}
		return companies;
	}

	@Override
	public Computer getComputerDetails(int id){
		Computer computer = null;
		this.conn = ComputerDBConnection.getInstance().getConnection();
		try (PreparedStatement pst = conn.prepareStatement(GET_COMPUTERS_DETAILS);) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			computer = ComputerMapper.getInstance().getComputerDetailsMapper(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.conn = ComputerDBConnection.closeConnection();
		}
		return computer;
	}

	@Override
	public int createComputer(Computer computer){
		int i = 0;
		this.conn = ComputerDBConnection.getInstance().getConnection();
		String query = CREATE_COMPUTER;
		try (PreparedStatement pstm = conn.prepareStatement(query);) {
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DateValidator.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DateValidator.convertLocalToSql(computer.getDiscountedDate()));
			pstm.setInt(4, computer.getCompany().getId());
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = ComputerDBConnection.closeConnection();
		}
		return i;
	}

	@Override
	public int updateComputer(Computer computer){
		int i = 0;
		this.conn = ComputerDBConnection.getInstance().getConnection();
		String query = UPDATE_COMPUTER;
		try (PreparedStatement pstm = conn.prepareStatement(query);) {
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DateValidator.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DateValidator.convertLocalToSql(computer.getDiscountedDate()));
			pstm.setInt(4, computer.getCompany().getId());
			pstm.setInt(5, computer.getId());
			i = pstm.executeUpdate();
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			this.conn = ComputerDBConnection.closeConnection();
		}
		return i;
	}

	@Override
	public int deleteComputer(int id) throws SQLException {
		int value = 0;
		this.conn = ComputerDBConnection.getInstance().getConnection();
		String query = DELETE_COMPUTER;
		try (PreparedStatement pstm = conn.prepareStatement(query);) {
			pstm.setInt(1, id);
			value = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = ComputerDBConnection.closeConnection();
		}
		return value;
	}

	@Override
	public Company getCompanyById(int idCompany) {
		Company company = null;
		if(conn==null) {
			conn=ComputerDBConnection.getInstance().getConnection();
		}
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
	public List<Computer> getComputersByPageNumber(int pageId) throws PageNotFoundException {
		List<Computer> computers = new ArrayList<Computer>();
		conn = ComputerDBConnection.getInstance().getConnection();
		try (PreparedStatement pstm = conn.prepareStatement(GET_COMPUTERS_BY_PAGE);) {
			pstm.setInt(1, Page.getPageSize() * (pageId - 1));
			pstm.setInt(2, Page.getPageSize());
			ResultSet rs = pstm.executeQuery();
			computers = PageMappper.getComputersByPageNumberMapper(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.conn = ComputerDBConnection.closeConnection();
		}
		return computers;
	}

	@Override
	public int getNumberOfPages(){
		int pageSize = Page.getPageSize();
		int numberOfPages = 0;
		conn = ComputerDBConnection.getInstance().getConnection();
		try (Statement stm = conn.createStatement();) {
			ResultSet myRes = stm.executeQuery("select count(*) as number from computer");
			int numberOflines = myRes.getInt("number");
			numberOfPages = numberOflines / pageSize;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = ComputerDBConnection.closeConnection();
		}

		return numberOfPages;
	}

}