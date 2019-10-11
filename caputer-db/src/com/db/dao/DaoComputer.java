package com.db.dao;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.db.model.Computer;
import com.db.model.Company;

public interface DaoComputer {
	
	public List<Computer> getAllComputers()throws SQLException;
	public List<Company> getAllyCompanies()throws SQLException;
	public Computer getComputerDetails(int id)throws SQLException ;
	public void createComputer(Computer computer) throws Exception;
	public void updateComputer(Computer computer)throws Exception;
	public void deleteComputer(int id)throws SQLException;
	public Company getCompanyById(int idCompany)throws SQLException;
	public int getNumberOfPages()throws SQLException;
	public List<Computer> getComputersByPageNumber(int pageId)throws SQLException;
	
}
