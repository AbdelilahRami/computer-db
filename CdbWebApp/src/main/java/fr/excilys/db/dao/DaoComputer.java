package fr.excilys.db.dao;
import java.sql.SQLException;
import java.util.List;
import fr.excilys.db.model.Computer;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.model.Company;
public interface DaoComputer {
	public List<Computer> getAllComputers()throws SQLException;
	public Computer getComputerDetails(int id)throws SQLException ;
	public List<Company> getAllyCompanies() throws SQLException;
	public Company getCompanyById(int idCompany) throws SQLException ;
	public int createComputer(Computer computer) throws Exception;
	public int updateComputer(Computer computer)throws Exception;
	public int deleteComputer(int id)throws SQLException;
	public int getNumberOfPages(int pageSize)throws SQLException;
	public List<Computer> getComputersByPageNumber(int pageId, int pageSize)throws PageNotFoundException,SQLException;
	public List<Computer> getComputersByName(String name, int limit, int offset);
	public int getPagesNumberByName(int pageSize, String name);
	public int deleteCompany(int id);
	public List<Computer> getComputersByOrder(String order, int sizePage, int numPage);
}
