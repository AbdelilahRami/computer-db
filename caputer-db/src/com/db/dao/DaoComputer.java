package com.db.dao;


import java.util.Date;
import java.util.List;

import com.db.model.Computer;
import com.db.model.Company;

public interface DaoComputer {
	
	public List<Computer> getAllComputers();
	public List<Company> getAllyCompanies();
	public Computer getComputerDetails(int id);
	public void createComputer(Computer computer) throws Exception;
	public void updateComputer(Computer computer)throws Exception;
	public void deleteComputer(int id);

}
