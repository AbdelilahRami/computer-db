package fr.excilys.db.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Computer;
@Service
public class ComputerServiceImpl  {
	
	@Autowired
	private  ComputerDaoImpl computerDaoImpl;
		
	
	public List<Computer> getAllComputers()  {
		List<Computer> computers = computerDaoImpl.getAllComputers();
		return computers;
	}
	
	public void updateComputer(Computer computer) {
			 computerDaoImpl.updateComputer(computer);
	}
	
	public void createComputer(Computer computer) {
			 computerDaoImpl.createComputer(computer);
			
	}
	
	public List<Computer> getComputersByPage(int numPage,int pageSize)  {
		List<Computer> computers = computerDaoImpl.getComputersByPageNumber(numPage,pageSize);
		return computers;
	}
	
	public void deleteComputer(int idComputer) {
		computerDaoImpl.deleteComputer(idComputer);
		
	}

	
	public List<Computer> getComputersByName(String name ,int limite, int offset) {
		return computerDaoImpl.getComputersByName(name,limite,offset);
	}
	
	public int getPagesNumberByName(int pageSize, String name) {
		int pageNumbers=computerDaoImpl.getPagesNumberByName(pageSize, name);
		return pageNumbers;
	}
	
	
	public int getNumberOfPages(int pageSize) {
		return computerDaoImpl.getNumberOfPages(pageSize);
	}
	
	public List<Computer> getComputersByOrder(String order, int sizePage, int numPage) {
		
		return computerDaoImpl.getComputersByOrder(order, sizePage, numPage);
	}

	
	public Computer getComputerDetails(int id) {
		
		return computerDaoImpl.getComputerDetails(id);
	}
	public ComputerServiceImpl() {
		super();
	}
	
}