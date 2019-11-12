package fr.excilys.db.dao;
import java.sql.SQLException;
import java.util.List;
import fr.excilys.db.model.Computer;
import fr.excilys.db.exception.PageNotFoundException;
public interface DaoComputer {
	public List<Computer> getAllComputers()throws SQLException;
	public Computer getComputerDetails(int id)throws SQLException ;
	public void createComputer(Computer computer) throws Exception;
	public int updateComputer(Computer computer)throws Exception;
	public int deleteComputer(int id)throws SQLException;
	public int getNumberOfPages(int pageSize)throws SQLException;
	public List<Computer> getComputersByPageNumber(int pageId, int pageSize)throws PageNotFoundException,SQLException;
	public List<Computer> getComputersByName(String name, int limit, int offset);
	public int getPagesNumberByName(int pageSize, String name);
	public List<Computer> getComputersByOrder(String order, int sizePage, int numPage);
}
