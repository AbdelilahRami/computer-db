package com.db.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.db.exception.PageNotFoundException;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.Page;

public interface ComputeService {
	public List<Computer> getAllComputers()throws Exception;
	public List<Company> getAllCompanies()throws Exception;
	public int updateComputer(Computer computer)throws Exception;
	public int deleteComputer(int idComputer)throws Exception;
	public int createComputer(Computer computer)throws Exception;
	public boolean idCompanyExisted(int id)throws SQLException;
	public List<Computer> getComputersByPage(Page page)throws SQLException, PageNotFoundException ;
	public int getNumberOfPages();
	public boolean datesExisted(LocalDate d1, LocalDate d2);
}
