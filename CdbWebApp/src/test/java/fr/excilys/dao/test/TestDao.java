package fr.excilys.dao.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.List;
import org.junit.*;
import fr.excilys.db.coonection.H2DataBaseOperations;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;

public class TestDao {
	private Connection conn;
	private ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
	private Company company;
	private Computer realComputer;
	private Computer expectedComputer;
	@Before
	public void beforeTestAllComputers() {
		conn = H2DataBaseOperations.getConnection().get();
	}
	@Test
	public void testAllComputers() {

		List<Computer> computers = computerDao.getAllComputers(conn);
		assertEquals(13, computers.size());
	}
	@After
	public void afterAllComputer() {
		H2DataBaseOperations.closeConnection();
	}
	@Test
	public void testAllCompanies() {
		List<Company> companies = computerDao.getAllyCompanies(conn);
		assertEquals(9, companies.size());
	}
	@Test
	public void detailsTest() {
		Computer realComputer = computerDao.getComputerDetails(1, conn);
		Company company = computerDao.getCompanyById(1, conn);
		Computer expectedComputer = ComputerBuilder.newInstance().setId(1).setName("MacBook Pro 15.4 inch")
				.setIntroducedDate(null).setDiscountedDate(null).setCompany(company).build();
		assertEquals(expectedComputer.toString(), realComputer.toString());
	}
	@Test
	public void testAddComputer() {
		Company company = computerDao.getCompanyById(1, conn);
		Computer computer= ComputerBuilder.newInstance().
				setId(44).setName("ThinkPad Carbon").setIntroducedDate(null).setDiscountedDate(null).setCompany(company).build();
		int i=computerDao.createComputer(computer, conn);
		List<Computer> computers=computerDao.getAllComputers(conn);
		assertEquals(14, computers.size());
		assertEquals(1, i);	
	}

	@Before
	public void beforeUpdateComputerTest() {
		conn=H2DataBaseOperations.getConnection().get();	
		company = computerDao.getCompanyById(1, conn);
		realComputer=ComputerBuilder.newInstance().setId(1).
							setName("HP EliteBook").setIntroducedDate(null).
							setDiscountedDate(null).setCompany(company).build();
	}
	@Test
	public void testUpdateComputer() {
		computerDao.updateComputer(realComputer, conn);
		Computer expectedComputer=computerDao.getComputerDetails(1, conn);
		assertEquals(expectedComputer.toString(), realComputer.toString());	
	}
	@Before
	public void beforeDeleteComputerTest() {
		conn=H2DataBaseOperations.getConnection().get();
		expectedComputer=computerDao.getComputerDetails(1, conn);
	}
	
	@Test
	public void deleteTest() {
		computerDao.deleteComputer(1, conn);
		List<Computer> computers=computerDao.getAllComputers(conn);
		assertEquals(12, computers.size());
		
		
	}
	
}