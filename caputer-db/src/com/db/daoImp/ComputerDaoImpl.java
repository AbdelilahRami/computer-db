package com.db.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.db.connection.ComputerDBConnection;
import com.db.dao.DaoComputer;
import com.db.exception.ComputerToDeleteNotFound;
import com.db.mapper.ComputerMapper;
import com.db.mapper.DatesConversion;
import com.db.mapper.PageMappper;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.Page;
import com.db.service.impl.ComputerServiceImpl;

public class ComputerDaoImpl implements DaoComputer {
	private static ComputerDaoImpl computerDaoImpl;
	private static final String GET_ALL_COMPUTERS ="select * from computer"; 
	private static final String GET_ALL_COMPANIES ="select * from company";
	private static final String GET_COMPUTERS_DETAILS ="select * from computer where id = ?";
	private static final String CREATE_COMPUTER ="INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private static final String UPDATE_COMPUTER ="UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String DELETE_COMPUTER ="delete from computer where id = ?";
	private static final String GET_COMPUTERS_BY_PAGE="select * from computer LIMIT ?, ?";
	
	private ComputerDaoImpl() {

	}
	public static ComputerDaoImpl getInstance() {
		if (computerDaoImpl == null) {
			computerDaoImpl = new ComputerDaoImpl();
		}
		return computerDaoImpl;
	}

	@Override
	public List<Computer> getAllComputers() {
		List<Computer> computers=null;
		Connection myConn = ComputerDBConnection.getInstance();
		String query = GET_ALL_COMPUTERS;
		try {
			Statement stm=myConn.createStatement();
			ResultSet rs=stm.executeQuery(query);
			computers = ComputerMapper.getInstance().getAllComputerMapper(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}


	@Override
	public List<Company> getAllyCompanies() throws SQLException {
		List<Company> companies = null;
		Connection conn = ComputerDBConnection.getInstance();
		try {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(GET_ALL_COMPANIES);
			companies=ComputerMapper.getInstance().getAllCompaniesMapper(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}

	@Override
	public Computer getComputerDetails(int id) throws SQLException {
		Computer computer = null;
		Connection conn = null;
		try {
			conn = ComputerDBConnection.getInstance();
			PreparedStatement pst = conn.prepareStatement(GET_COMPUTERS_DETAILS);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			computer=ComputerMapper.getInstance().getComputerDetailsMapper(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computer;
	}

	@Override
	public void createComputer(Computer computer) throws Exception {

		Connection conn = null;
		try {
			conn = ComputerDBConnection.getInstance();
			String query = CREATE_COMPUTER;
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertLocalToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertLocalToSql(computer.getDiscountedDate()));
			pstm.setInt(4, computer.getCompany().getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateComputer(Computer computer) throws Exception {
		Connection conn = null;
		System.out.println(computer.getIntroducedDate());
		try {
			conn = ComputerDBConnection.getInstance();
			String query = UPDATE_COMPUTER;
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setString(1, computer.getName());
			pstm.setDate(2, java.sql.Date.valueOf(computer.getIntroducedDate().toString()));
			pstm.setDate(3, java.sql.Date.valueOf(computer.getDiscountedDate().toString()));
			pstm.setInt(4, computer.getCompany().getId());
			pstm.setInt(5, computer.getId());
			int rs = pstm.executeUpdate();
		} catch (SQLException exc) {
			exc.printStackTrace();
		}

	}

	@Override
	public void deleteComputer(int id) throws SQLException {
		Connection conn = null;
		String query = DELETE_COMPUTER;
		try {
			conn = ComputerDBConnection.getInstance();
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setInt(1, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Company getCompanyById(int idCompany) throws SQLException {
		Company company = null;
		Connection myConn = null;

		try {
			myConn = ComputerDBConnection.getInstance();
			Statement stm = myConn.createStatement();
			PreparedStatement pst = myConn.prepareStatement("select * from company where id = ?");
			pst.setInt(1, idCompany);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				company = new Company(idCompany, name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return company;
	}

	@Override
	public List<Computer> getComputersByPageNumber(int pageId) throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();
		Computer computer;
		Connection myConn = null;
		try {
			myConn = ComputerDBConnection.getInstance();
			PreparedStatement pstm = myConn.prepareStatement(GET_COMPUTERS_BY_PAGE);
			pstm.setInt(2, Page.getPageSize());
			pstm.setInt(1, Page.getPageSize() * (pageId - 1));
			ResultSet rs = pstm.executeQuery();
			computers=PageMappper.getComputersByPageNumberMapper(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computers;
	}

	@Override
	public int getNumberOfPages() throws SQLException {
		int pageSize = Page.getPageSize();
		int numberOfPages = 0;
		Connection myConn = null;
		try {
			myConn = ComputerDBConnection.getInstance();
			Statement stm = myConn.createStatement();
			ResultSet myRes = stm.executeQuery("select count(*) as number from computer");
			int numberOflines = myRes.getInt("number");
			numberOfPages = numberOflines / pageSize;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return numberOfPages;
	}

}