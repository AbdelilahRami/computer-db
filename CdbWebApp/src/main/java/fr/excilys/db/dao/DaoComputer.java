package fr.excilys.db.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import fr.excilys.db.model.Computer;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.model.Company;

public interface DaoComputer {
	
	public List<Computer> getAllComputers(Connection conn)throws SQLException;
	public Computer getComputerDetails(int id,Connection conn)throws SQLException ;
	public List<Company> getAllyCompanies(Connection conn) throws SQLException;
	public Company getCompanyById(int idCompany,Connection conn) throws SQLException ;
	public int createComputer(Computer computer, Connection conn) throws Exception;
	public int updateComputer(Computer computer,Connection conn)throws Exception;
	public int deleteComputer(int id,Connection conn)throws SQLException;
	public int getNumberOfPages(Connection conn)throws SQLException;
	public List<Computer> getComputersByPageNumber(int pageId,Connection conn)throws PageNotFoundException,SQLException;
	
}
