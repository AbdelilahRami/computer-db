package fr.excilys.db.service.impl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.exception.DatesNotValidException;
import fr.excilys.db.exception.NotFoundCompanyException;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.ComputeService;
public class ComputerServiceImpl implements ComputeService  {
	private static ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.getInstance();
	private static ComputerServiceImpl computerServiceImpl;
	private  ComputerServiceImpl() {
	}
	public static ComputerServiceImpl getInstance() {
		if (computerServiceImpl == null) {
			computerServiceImpl = new ComputerServiceImpl();
		}
		return computerServiceImpl;
	}
	@Override
	public List<Computer> getAllComputers()  {
		List<Computer> computers = computerDaoImpl.getAllComputers();
		return computers;
	}
	@Override
	public List<Company> getAllCompanies() {
		List<Company> companies = computerDaoImpl.getAllyCompanies();
		return companies;
	}
	@Override
	public int updateComputer(Computer computer) throws DatesNotValidException, NotFoundCompanyException, SQLException {
		LocalDate ds = computer.getDiscountedDate();
		LocalDate di = computer.getIntroducedDate();
			if ((datesExisted(ds, di) && computer.getDiscountedDate().compareTo(computer.getIntroducedDate()) <= 0)) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			int value = computerDaoImpl.updateComputer(computer);
				return value;
	}
	@Override
	public int createComputer(Computer computer) throws NotFoundCompanyException, DatesNotValidException, SQLException {
		LocalDate ds = computer.getDiscountedDate();
		LocalDate di = computer.getIntroducedDate();
			if (datesExisted(ds, di) && ds.compareTo(di) <= 0) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			int k = computerDaoImpl.createComputer(computer);
			return k;
	}
	@Override
	public List<Computer> getComputersByPage(int numPage,int pageSize)  {
		List<Computer> computers = computerDaoImpl.getComputersByPageNumber(numPage,pageSize);
		return computers;
	}
	@Override
	public int deleteComputer(int idComputer) {
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		int k = computerDao.deleteComputer(idComputer);
		return k;
	}
	@Override
	public boolean datesExisted(LocalDate d1, LocalDate d2) {
		return ((d1 != null) && (d2 != null));
	}
	@Override
	public List<Computer> getComputersByName(String name ,int limite, int offset) {
		return ComputerDaoImpl.getInstance().getComputersByName(name,limite,offset);
	}
	@Override
	public int getPagesNumberByName(int pageSize, String name) {
		int pageNumbers=ComputerDaoImpl.getInstance().getPagesNumberByName(pageSize, name);
		return pageNumbers;
	}
	@Override
	public int deleteCompany(int id) {
		return ComputerDaoImpl.getInstance().deleteCompany(id);
	
	}
}
