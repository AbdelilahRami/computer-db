package com.db.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.db.connection.ComputerDBConnection;
import com.db.dao.DaoComputer;
import com.db.mapper.DatesConversion;
import com.db.model.Company;
import com.db.model.Computer;

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
				Date intoduced=rs.getDate("introduced");
				Date discontinued = rs.getDate("discontinued");
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
	public void createComputer(Computer computer) throws Exception {
		
		Connection conn=ComputerDBConnection.getInstance();
		try {
			
			String query = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setString(1, computer.getName());
			pstm.setDate(2, java.sql.Date.valueOf(computer.getIntroducedDate().toString()));
			pstm.setDate(3, java.sql.Date.valueOf(computer.getDiscountedDate().toString()));
			pstm.setInt(4, computer.getIdCompany());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateComputer(Computer computer) throws Exception {
		System.out.println(computer.getIntroducedDate());
		try {
		Connection conn = ComputerDBConnection.getInstance();
			String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setString(1, computer.getName());
			pstm.setDate(2, DatesConversion.convertUtilToSql(computer.getIntroducedDate()));
			pstm.setDate(3, DatesConversion.convertUtilToSql(computer.getDiscountedDate()));
			pstm.setInt(4, computer.getIdCompany());
			pstm.setInt(5, computer.getId());
			int rs = pstm.executeUpdate();
		}catch(SQLException exc) {
			exc.printStackTrace();
		}	
	

	}

	@Override
	public void deleteComputer(int id) {
		// TODO Auto-generated method stub
		
	}

}
