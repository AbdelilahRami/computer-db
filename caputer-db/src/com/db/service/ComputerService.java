package com.db.service;

import java.util.List;

import com.db.dao.DaoComputer;
import com.db.model.Company;
import com.db.model.Computer;

public class ComputerService {
	
	private DaoComputer daoComputer;

	public ComputerService(DaoComputer daoComputer) {

		this.daoComputer = daoComputer;
	}
	
	public List<Computer> getAllComputers() throws Exception{
		List<Computer> computers=daoComputer.getAllComputers();
		
		if(computers == null || computers.isEmpty()) {
			throw new Exception("There is no computer in the table");
		}
		return computers;
	}
	
	public List<Company> getAllCompanies() throws Exception{
		List<Company> companies=daoComputer.getAllyCompanies();
		if(companies ==null || companies.isEmpty()) {
			throw new Exception("There is no company in the table");
		}
		
		return companies;
		
	}
	

}
