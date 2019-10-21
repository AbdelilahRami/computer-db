package fr.excilys.db.service;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
/**
 * @author Abdelilah Rami
 *Interface computerService
 */
public interface ComputeService {
	 List<Computer> getAllComputers(Connection conn)throws Exception;
	 List<Company> getAllCompanies(Connection conn)throws Exception;
	 int updateComputer(Computer computer, Connection conn)throws Exception;
	 int deleteComputer(int idComputer, Connection conn)throws Exception;
	 int createComputer(Computer computer, Connection conn)throws Exception;
	 List<Computer> getComputersByPage(int idpage, Connection conn,int pageSize)throws SQLException, PageNotFoundException;
	 boolean datesExisted(LocalDate d1, LocalDate d2);
}
