package com.db.daoImp;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import com.db.connection.ComputerDBConnection;
import com.db.dao.DaoComputer;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.service.DbService;

public class ComputerDaoImpl implements DaoComputer {

	@Override
	public List<Computer> getAllComputers() {
		List<Computer> computers =new ArrayList<Computer>();
		Connection myConn=ComputerDBConnection.getInstance();
		try {
			Statement stm=myConn.createStatement();
			ResultSet myRes=stm.executeQuery("select * from computer");
			while(myRes.next()) {
				int id = myRes.getInt("id");
				String name = myRes.getString("name");
				Timestamp introduced = myRes.getTimestamp("introduced");		
				Timestamp discounted = myRes.getTimestamp("discontinued");
				int idCompany = myRes.getInt("company_id");
				Computer computer = new Computer(id, name, introduced, discounted, idCompany);
				computers.add(computer);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return computers;
	}

	@Override
	public List<Company> getAllyCompanies() {
		List<Company> companies = new ArrayList<Company>();
		Connection conn=ComputerDBConnection.getInstance();
		try {
			Statement stm=conn.createStatement();
			ResultSet rs = stm.executeQuery("select * from company");
			while(rs.next()) {
				int idComapny = rs.getInt("id");
				String nameCompany = rs.getString("name");
				Company company = new Company(idComapny, nameCompany);
				companies.add(company);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}

	@Override
	public Computer getComputerDetails(int id) {
		Computer computer=null;
		Connection conn=ComputerDBConnection.getInstance();
		try {
			PreparedStatement pst = conn.prepareStatement("select * from computer where id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int idComputer = rs.getInt("id");
				String name=rs.getString("name");
				Timestamp intoduced=rs.getTimestamp("introduced");
				Timestamp discontinued = rs.getTimestamp("discontinued");
				int idCompany = rs.getInt("company_id");
				computer = new Computer(idComputer, name, 
												intoduced, discontinued, idCompany);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computer;
	}

	@Override
	public void createComputer(String name, Date introducedDate, Date discountedDate, int idCompany) throws Exception {
		DbService dbService = new DbService();
		boolean dateIsvalid = dbService.dateIsvalid(introducedDate,discountedDate);
		boolean idExist = dbService.companyIdExist(idCompany);
		if(!dateIsvalid || !idExist) {
			throw new Exception("discounte Date must be greater than introduced Date");
		}else if(!idExist) {
			throw new Exception("The id company doesn't exist in database");
		}
		Connection conn=ComputerDBConnection.getInstance();
		try {
			
			String query = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setString(1, name);
			pstm.setDate(2, introducedDate);
			pstm.setDate(3, discountedDate);
			pstm.setInt(4, idCompany);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateComputer(int id, String name,Date introducedDate, Date discountedDate, int idCompany ) throws Exception {
		Connection conn = ComputerDBConnection.getInstance();
		Computer computer = this.getComputerDetails(id);
		if(computer != null) {
			String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ";
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setString(1, name);
			pstm.setDate(2, introducedDate);
			pstm.setDate(3, discountedDate);
			pstm.setInt(4, idCompany);
			pstm.setInt(5, id);
			ResultSet rs = pstm.executeQuery();
			
		}else {
			throw new Exception("The computer you want to update doesn't exist");
		}


	}

	@Override
	public void deleteComputer(int id) {
		// TODO Auto-generated method stub
		
	}

}
