package com.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Computer;

public class PageMappper {
	
	
	public static List<Computer> getComputersByPageNumberMapper(ResultSet rs) throws SQLException{
		ComputerDaoImpl computerDaoImpl=ComputerDaoImpl.getInstance();
		List<Computer> computers= new ArrayList<Computer>();
		Computer computer;
		while (rs.next()) {
			int idComputer = rs.getInt("id");
			String name = rs.getString("name");
			Date introducedDate = rs.getDate("introduced");
			Date discountedDate = rs.getDate("discontinued");
			LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
			LocalDate discounted = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "introduced");
			int idCompany = rs.getInt("company_id");
			computer = new Computer(idComputer, name, introduced, discounted, computerDaoImpl.getCompanyById(idCompany));
			computers.add(computer);
		}
		return computers;
		
	}

}
