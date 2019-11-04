package fr.excilys.db.service;
import java.time.LocalDate;
import java.util.List;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
public interface ComputeService {
	 public List<Computer> getAllComputers();
	 public List<Company> getAllCompanies();
	 public int updateComputer(Computer computer);
	 public int deleteComputer(int idComputer);
	 public int createComputer(Computer computer);
	 public List<Computer> getComputersByPage(int idpage,int pageSize);
	 public List<Computer> getComputersByName(String name ,int limite, int offset);
	 public int getPagesNumberByName(int pageSize, String name);
	 public int deleteCompany(int id);
	 public boolean datesExisted(LocalDate d1, LocalDate d2);
	 public int getNumberOfPages(int pageSize);
	 public List<Computer> getComputersByOrder(String order, int sizePage, int numPage);
	 public Computer getComputerDetails(int id);
	 public Company getCompanyById(int idCompany);
}
