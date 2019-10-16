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

	@Before
	public void beforeTestAllCompanies() {

		conn = H2DataBaseOperations.getConnection().get();
	}

	@Test
	public void testAllCompanies() {
		List<Company> companies = computerDao.getAllyCompanies(conn);
		assertEquals(9, companies.size());
	}
	@After
	public void afterAllCompanies() {
		H2DataBaseOperations.closeConnection();
	}

	@Before
	public void beforeGettingDetailsTest() {
		conn = H2DataBaseOperations.getConnection().get();
	}

	@Test
	public void detailsTest() {
		Computer realComputer = computerDao.getComputerDetails(1, conn);
		Company company = computerDao.getCompanyById(1, conn);
		Computer expectedComputer = ComputerBuilder.newInstance().setId(1).setName("MacBook Pro 15.4 inch")
				.setIntroducedDate(null).setDiscountedDate(null).setCompany(company).build();
		assertEquals(expectedComputer.toString(), realComputer.toString());
	}
	@After
	public void afterdetails() {
		H2DataBaseOperations.closeConnection();
	}

	@Before
	public void beforeAddComputerTest() {
		conn=H2DataBaseOperations.getConnection().get();		
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
	@After
	public void afterAddComputer() {
		H2DataBaseOperations.closeConnection();
	}
	@Before
	public void beforeUpdateComputerTest() {
		conn=H2DataBaseOperations.getConnection().get();		
	}
	@Test
	public void testUpdateCoputer() {
		Company company = computerDao.getCompanyById(1, conn);
		Computer realComputer=ComputerBuilder.newInstance().setId(1).
							setName("HP EliteBook").setIntroducedDate(null).
							setDiscountedDate(null).setCompany(null).build();
		computerDao.updateComputer(realComputer, conn);
		
	}
}