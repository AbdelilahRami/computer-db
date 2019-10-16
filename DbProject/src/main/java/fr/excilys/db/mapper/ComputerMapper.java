
package fr.excilys.db.mapper;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import fr.excilys.db.daoImp.*;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;

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
	 	 
	public List<Computer> getAllComputerMapper(ResultSet rs,Connection conn){
		List<Computer> computers =new ArrayList<Computer>();
		ComputerDaoImpl computerDaoImpl=ComputerDaoImpl.getInstance();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Date introducedDate = rs.getDate("introduced");
				Date discountedDate = rs.getDate("discontinued");
				LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
				LocalDate discounted = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "discontinued");
				int idCompany = rs.getInt("company_id");
				Company company=computerDaoImpl.getCompanyById(idCompany,conn);
				Computer computer=ComputerBuilder.newInstance().
													setId(id).
													setName(name). 
													setIntroducedDate(introduced).
													setDiscountedDate(discounted).
													setCompany(company).build();											
				computers.add(computer);
			}		
		}catch(Exception e) {			
		}
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
		return companies;
	}
	
	public Computer getComputerDetailsMapper(ResultSet rs,Connection conn) throws SQLException {
		Computer computer=null;
		ComputerDaoImpl computerDaoImpl=ComputerDaoImpl.getInstance();
		while (rs.next()) {
			int idComputer = rs.getInt("id");
			String name = rs.getString("name");
			Date introducedDate = rs.getDate("introduced");
			Date discountedDate = rs.getDate("discontinued");
			LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
			LocalDate discounted = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "discontinued");
			int idCompany = rs.getInt("company_id");
			Company company=computerDaoImpl.getCompanyById(idCompany,conn);
			computer=ComputerBuilder.newInstance().
					setId(idComputer).
					setName(name). 
					setIntroducedDate(introduced).
					setDiscountedDate(discounted).
					setCompany(company).build();
		}
		return computer;
	}
	
	
	
	
	
}
