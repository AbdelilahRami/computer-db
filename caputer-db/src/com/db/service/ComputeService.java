package com.db.service;

import java.time.LocalDate;
import java.util.List;

import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.Page;

public interface ComputeService {
	public List<Computer> getAllComputers()throws Exception;
	public List<Company> getAllCompanies()throws Exception;
	public void updateComputer(Computer computer)throws Exception;
	public void createComputer(Computer computer)throws Exception;
	public int checkDates(LocalDate d1, LocalDate d2);
	public boolean idCompanyExisted(int id);
	public List<Computer> getComputersByPage(Page page);
	public int getNumberOfPages();
}
