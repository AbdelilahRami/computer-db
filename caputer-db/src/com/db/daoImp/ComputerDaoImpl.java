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
import com.db.mapper.DatesConversion;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.Page;
import com.db.service.impl.ComputerServiceImpl;

public class ComputerDaoImpl implements DaoComputer {
	private final static Logger LOGGER = Logger.getLogger(ComputerServiceImpl.class.getName());

	@Override
	public List<Computer> getAllComputers() throws SQLException {
		List<Computer> computers =new ArrayList<Computer>();
		Connection myConn=null;
		try {
			myConn= ComputerDBConnection.getInstance();
			Statement stm=myConn.createStatement();
			ResultSet myRes=stm.executeQuery("select * from computer");
			while(myRes.next()) {
				int id = myRes.getInt("id");
				String name = myRes.getString("name");
				Date introducedDate = myRes.getDate("introduced");
				Date discountedDate = myRes.getDate("discontinued");
				LocalDate introduced = null;
				LocalDate discounted = null;
				if(introducedDate != null) {
					introduced = myRes.getDate("introduced").toLocalDate();
				}
				if(discountedDate != null) {
					discounted = myRes.getDate("discontinued").toLocalDate();
				}
				int idCompany = myRes.getInt("company_id");
				Computer computer = new Computer(id, name, introduced, discounted, getCompanyById(idCompany));
				computers.add(computer);
			}
			computers.forEach((cp)-> System.out.println(cp));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computers;
	}

	@Override
	public List<Company> getAllyCompanies() throws SQLException {
		List<Company> companies = new ArrayList<Company>();
		Connection conn=null;
		try {
			conn=ComputerDBConnection.getInstance();
			Statement stm=conn.createStatement();
			ResultSet rs = stm.executeQuery("select * from company");
			while(rs.next()) {
				int idComapny = rs.getInt("id");
				String nameCompany = rs.getString("name");
				Company company = new Company(idComapny, nameCompany);
				companies.add(company);
			}
			companies.forEach((cp)->System.out.println(cp));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return companies;
	}

	@Override
	public Computer getComputerDetails(int id) throws SQLException {
		Computer computer=null;
		Connection conn=null;
		try {
			conn=ComputerDBConnection.getInstance();
			PreparedStatement pst = conn.prepareStatement("select * from computer where id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int idComputer = rs.getInt("id");
				String name=rs.getString("name");
				Date introducedDate = rs.getDate("introduced");
				Date discountedDate = rs.getDate("discontinued");
				LocalDate introduced = null;
				LocalDate discounted = null;
				if(introducedDate != null) {
					introduced = rs.getDate("introduced").toLocalDate();
				}
				if(discountedDate != null) {
					discounted = rs.getDate("discontinued").toLocalDate();
				}
				int idCompany = rs.getInt("company_id");
				computer = new Computer(idComputer, name, 
						introduced, discounted, getCompanyById(idCompany));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return computer;
	}

	@Override
	public void createComputer(Computer computer) throws Exception {
		
		Connection conn=null;
		try {
			conn=ComputerDBConnection.getInstance();
			String query = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setString(1, computer.getName());
			pstm.setDate(2, java.sql.Date.valueOf(computer.getIntroducedDate().toString()));
			pstm.setDate(3, java.sql.Date.valueOf(computer.getDiscountedDate().toString()));
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
			String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertUtilToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertUtilToSql(computer.getDiscountedDate()));
			pstm.setInt(4, computer.getCompany().getId());
			pstm.setInt(5, computer.getId());
			int rs = pstm.executeUpdate();
		}catch(SQLException exc) {
			exc.printStackTrace();
		}	
	

	}

	@Override
	public void deleteComputer(int id) throws SQLException {
		Connection conn = null;
		String query="delete from computer where id = ?";
		try {
			conn = ComputerDBConnection.getInstance();
			PreparedStatement pstm =conn.prepareStatement(query);
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
		Connection myConn=null;
		
			try {
				myConn=ComputerDBConnection.getInstance();
				Statement stm=myConn.createStatement();
				PreparedStatement pst = myConn.prepareStatement("select * from company where id = ?");
				pst.setInt(1, idCompany);
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					String name = rs.getString("name");
					company = new Company(idCompany,name);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		return company;
	}

	@Override
	public List<Computer> getComputersByPageNumber(int pageId) throws SQLException {
		List<Computer> computers= new ArrayList<Computer>();
		Computer computer;
		Connection myConn=null;
		try {
			myConn=ComputerDBConnection.getInstance();
			PreparedStatement pstm = myConn.prepareStatement("select * from computer LIMIT ?, ?");
			pstm.setInt(1, Page.getPageSize());
			pstm.setInt(2, Page.getPageSize()*(pageId-1));
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
				int idComputer = rs.getInt("id");
				String name=rs.getString("name");
				Date introducedDate = rs.getDate("introduced");
				Date discountedDate = rs.getDate("discontinued");
				LocalDate introduced = null;
				LocalDate discounted = null;
				if(introducedDate != null) {
					introduced = rs.getDate("introduced").toLocalDate();
				}
				if(discountedDate != null) {
					discounted = rs.getDate("discontinued").toLocalDate();
				}
				int idCompany = rs.getInt("company_id");
				computer = new Computer(idComputer, name, 
						introduced, discounted, getCompanyById(idCompany));
				computers.add(computer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return computers;
	}

	@Override
	public int getNumberOfPages() throws SQLException {
		int pageSize = Page.getPageSize();
		int numberOfPages=0;
		Connection myConn=null;
		try {
			myConn=ComputerDBConnection.getInstance();
			Statement stm = myConn.createStatement();
			ResultSet myRes=stm.executeQuery("select count(*) as number from computer");
			int numberOflines =myRes.getInt("number");
			numberOfPages = numberOflines/pageSize;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return numberOfPages;
	}

}