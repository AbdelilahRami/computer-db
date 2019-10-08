package com.db.dao;

import java.sql.Date;
import java.util.List;

import com.db.model.Computer;
import com.db.model.Company;

public interface DaoComputer {
	
	public List<Computer> getAllComputers();
	public List<Company> getAllyCompanies();
	public Computer getComputerDetails(int id);
	public void createComputer(String name, Date introduced, Date discountedDate, int idCompany) throws Exception;
	public void updateComputer(int id,String name,Date introducedDate, Date discountedDate, int idCompany)throws Exception;
	public void deleteComputer(int id);

}
