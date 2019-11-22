package fr.excilys.db.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.excilys.db.dao.ComputerDaoImpl;
import fr.excilys.db.dto.ComputerDto;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.model.Computer;
@Service
public class ComputerServiceImpl  {
	
	@Autowired
	private  ComputerDaoImpl computerDaoImpl;
	@Autowired
	private ComputerMapper computerMapper;
	
	public List<ComputerDto> getAllComputers()  {
		List<Computer> computers = computerDaoImpl.getAllComputers();
		return computerMapper.fromListObjecToListString(computers);
	}
	
	public void updateComputer(Computer computer) {
			 computerDaoImpl.updateComputer(computer);
	}
	
	public void createComputer(ComputerDto computerDto) {
		Computer computer=computerMapper.mapToComputerForCreate(computerDto);
		computerDaoImpl.createComputer(computer);
			
	}
	
	public List<ComputerDto> getComputersByPage(int numPage,int pageSize)  {
		List<Computer> computers = computerDaoImpl.getComputersByPageNumber(numPage,pageSize);
		List<ComputerDto> computersDto=computerMapper.fromListObjecToListString(computers);
		return computersDto;
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