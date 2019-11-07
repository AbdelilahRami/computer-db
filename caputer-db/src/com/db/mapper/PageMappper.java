package com.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.ComputerBuilder;

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
			LocalDate introduced = DateValidator.convertDatetoLocalDate(introducedDate, rs, "introduced");
			LocalDate discontinued = DateValidator.convertDatetoLocalDate(discountedDate, rs, "discontinued");
			int idCompany = rs.getInt("company_id");
			Company company=computerDaoImpl.getCompanyById(idCompany);
			computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
					.setIntroducedDate(introduced).setDiscountedDate(discontinued).setCompany(company).build();

			computers.add(computer);
		}
		return computers;
		
	}

}
