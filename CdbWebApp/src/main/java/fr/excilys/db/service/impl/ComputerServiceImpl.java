package fr.excilys.db.service.impl;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.exception.ComputerToDeleteNotFound;
import fr.excilys.db.exception.DatesNotValidException;
import fr.excilys.db.exception.NoCompanyFound;
import fr.excilys.db.exception.NoComputerFound;
import fr.excilys.db.exception.NotFoundCompanyException;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.ComputeService;
public class ComputerServiceImpl implements ComputeService  {
	private static ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.getInstance();
	private static ComputerServiceImpl computerServiceImpl;
	/**
	 * Constructor ComputerServiceImpl
	 */
	private  ComputerServiceImpl() {
	}
	/**
	 * @return ComputerServiceImpl
	 */
	public static ComputerServiceImpl getInstance() {
		if (computerServiceImpl == null) {
			computerServiceImpl = new ComputerServiceImpl();
		}
		return computerServiceImpl;
	}
	@Override
	public List<Computer> getAllComputers(Connection conn)  {
		List<Computer> computers = computerDaoImpl.getAllComputers(conn);
		try {
		if (computers == null || computers.isEmpty()) {
			throw new NoComputerFound("There is no computer in the table");
		}
		} catch (NoComputerFound e) {
			System.out.println(e.getMessage());
		}
		return computers;
	}
	@Override
	public List<Company> getAllCompanies(Connection conn) throws NoCompanyFound, SQLException {
		List<Company> companies = computerDaoImpl.getAllyCompanies(conn);
			if (companies == null || companies.isEmpty()) {
				throw new NoCompanyFound("There is no company in the table");
			}
		return companies;
	}
	@Override
	public int updateComputer(Computer computer, Connection conn) throws DatesNotValidException, NotFoundCompanyException, SQLException {
		LocalDate ds = computer.getDiscountedDate();
		LocalDate di = computer.getIntroducedDate();
			if ((datesExisted(ds, di) && computer.getDiscountedDate().compareTo(computer.getIntroducedDate()) <= 0)) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			int value = computerDaoImpl.updateComputer(computer, conn);
				return value;
	}
	@Override
	public int createComputer(Computer computer, Connection conn) throws NotFoundCompanyException, DatesNotValidException, SQLException {
		LocalDate ds = computer.getDiscountedDate();
		LocalDate di = computer.getIntroducedDate();
			if (datesExisted(ds, di) && ds.compareTo(di) <= 0) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			int k = computerDaoImpl.createComputer(computer, conn);
			return k;
	}
	@Override
	public List<Computer> getComputersByPage(int numPage, Connection conn) throws SQLException, PageNotFoundException {
		List<Computer> computers = null;
			if (numPage > computerDaoImpl.getNumberOfPages(conn)) {
				throw new PageNotFoundException("You have exced the max number of pages");
			}
			computers = computerDaoImpl.getComputersByPageNumber(numPage, conn);
		return computers;
	}
	@Override
	public int deleteComputer(int idComputer, Connection conn) throws ComputerToDeleteNotFound, SQLException  {
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Computer  computer = computerDao.getComputerDetails(idComputer, conn);
			if (computer == null) {
				throw new ComputerToDeleteNotFound("This computer doesn't exist :");
			}
			int k = computerDao.deleteComputer(idComputer, conn);
		return k;
	}
	@Override
	public boolean datesExisted(LocalDate d1, LocalDate d2) {
		return ((d1 != null) && (d2 != null));
	}
}
