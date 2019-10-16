package fr.excilys.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.Page;

public interface ComputeService {
	public List<Computer> getAllComputers(Connection conn)throws Exception;
	public List<Company> getAllCompanies(Connection conn)throws Exception;
	public int updateComputer(Computer computer,Connection conn)throws Exception;
	public int deleteComputer(int idComputer,Connection conn)throws Exception;
	public int createComputer(Computer computer,Connection conn)throws Exception;
	public List<Computer> getComputersByPage(int idpage,Connection conn)throws SQLException, PageNotFoundException ;
	public boolean datesExisted(LocalDate d1, LocalDate d2);
}
