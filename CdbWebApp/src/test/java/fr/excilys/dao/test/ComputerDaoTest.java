package fr.excilys.dao.test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.*;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
public class ComputerDaoTest {
	private Company company;
	private Computer realComputer;
	ComputerDaoImpl computerDaoImpl= new ComputerDaoImpl(true);
	@Before
	public void beforeTestAllComputers() {
		company = computerDaoImpl.getCompanyById(1);
		realComputer=ComputerBuilder.newInstance().setId(1).
							setName("HP EliteBook").setIntroducedDate(null).
							setDiscountedDate(null).setCompany(company).build();
	}
	@After
	public void afterAllComputer() {
		computerDaoImpl=null;
		fr.excilys.db.connection.H2DataBaseOperations.closeConnection();
	}
	@Test
	public void testGetAllComputers() {
		List<Computer> computers=computerDaoImpl.getAllComputers();
		assertEquals(13, computers.size());

	}
	@Test
	public void testAddComputer() {
		Company company = computerDaoImpl.getCompanyById(1);
		Computer computer= ComputerBuilder.newInstance().
				setId(44).setName("ThinkPad Carbon").setIntroducedDate(null).setDiscountedDate(null).setCompany(company).build();
		int i=computerDaoImpl.createComputer(computer);
		assertEquals(1, i);	
	}
	@Test
	public void testUpdateComputer() {
		int i=computerDaoImpl.updateComputer(realComputer);
		assertEquals(i, 1);	
	}

	@Test
	public void deleteTest() {
		int i=computerDaoImpl.deleteComputer(10);
		assertEquals(1, i);	
	}
	
	
}
