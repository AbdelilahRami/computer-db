package fr.excilys.db.service;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
public interface ComputeService {
	 List<Computer> getAllComputers()throws Exception;
	 List<Company> getAllCompanies()throws Exception;
	 int updateComputer(Computer computer)throws Exception;
	 int deleteComputer(int idComputer)throws Exception;
	 int createComputer(Computer computer)throws Exception;
	 List<Computer> getComputersByPage(int idpage,int pageSize)throws SQLException, PageNotFoundException;
	 boolean datesExisted(LocalDate d1, LocalDate d2);
}
