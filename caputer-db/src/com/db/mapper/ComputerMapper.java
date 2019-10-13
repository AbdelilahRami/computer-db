//w
package com.db.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.db.connection.ComputerDBConnection;
import com.db.daoImp.*;
import com.db.model.Company;
import com.db.model.Computer;

public class ComputerMapper {
	
		private static ComputerMapper computerMapper;
		private ComputerMapper() {
			
		}
		public static ComputerMapper getInstance() {
			if(computerMapper ==null) {
				computerMapper= new ComputerMapper();
			}
			return computerMapper;
		}
	 	 
	public List<Computer> getAllComputerMapper(ResultSet rs){
		List<Computer> computers =new ArrayList<Computer>();
		ComputerDaoImpl computerDaoImpl=ComputerDaoImpl.getInstance();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Date introducedDate = rs.getDate("introduced");
				Date discountedDate = rs.getDate("discontinued");
				LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
				LocalDate discounted = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "introduced");
				int idCompany = rs.getInt("company_id");
				Computer computer = new Computer(id, name, introduced, discounted, computerDaoImpl.getCompanyById(idCompany));
				computers.add(computer);
			}		
		}catch(Exception e) {			
		}
		computers.forEach((cp)->System.out.println(cp));
		return computers;
	}
	public List<Company> getAllCompaniesMapper(ResultSet rs){
		List<Company> companies =new ArrayList<Company>();
		try {
			while(rs.next()) {
				int idComapny = rs.getInt("id");
				String nameCompany = rs.getString("name");
				Company company = new Company(idComapny, nameCompany);
				companies.add(company);
			}		
		}catch(Exception e) {			
		}
		companies.forEach((cp)->System.out.println(cp));
		return companies;
	}
	
	public Computer getComputerDetailsMapper(ResultSet rs) throws SQLException {
		Computer computer=null;
		ComputerDaoImpl computerDaoImpl=ComputerDaoImpl.getInstance();
		while (rs.next()) {
			int idComputer = rs.getInt("id");
			String name = rs.getString("name");
			Date introducedDate = rs.getDate("introduced");
			Date discountedDate = rs.getDate("discontinued");
			LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
			LocalDate discounted = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "introduced");
			int idCompany = rs.getInt("company_id");
			computer = new Computer(idComputer, name, introduced, discounted, computerDaoImpl.getCompanyById(idCompany));
		}
		return computer;
	}
	
	
	
	
	
}
