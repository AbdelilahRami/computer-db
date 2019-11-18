package fr.excilys.db.test;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import fr.excilys.db.configuration.SpringConfiguration;
import fr.excilys.db.dao.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.service.CompanyServiceImpl;
import junit.framework.TestCase;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfiguration.class})
public class ComputerDaoTest extends TestCase {
	Computer realComputer;
	@Autowired
	ComputerDaoImpl computerDaoImpl;
	@Autowired
	CompanyServiceImpl companyService;
	@Before
	public void beforeTestAllComputers() {
		Company company = companyService.getCompanyById(1);
		realComputer=ComputerBuilder.newInstance().setId(1).
							setName("MacBook Pro 15.4 inch").setIntroducedDate(null).
							setDiscountedDate(null).setCompany(company).build();
	}
	@After
	public void afterAllComputer() {
		computerDaoImpl=null;
	}
	@Test
	public void testGetAllComputers() {
		List<Computer> computers=computerDaoImpl.getAllComputers();
		assertEquals(13, computers.size());

	}
	@Test
	public void getComputerDetails() {
		Computer computer=computerDaoImpl.getComputerDetails(1);
		assertEquals(computer.getId(), realComputer.getId());
		assertEquals(computer.getName(), realComputer.getName());
		assertEquals(computer.getIntroducedDate(), realComputer.getIntroducedDate());
		assertEquals(computer.getDiscountedDate(), realComputer.getDiscountedDate());
		assertEquals(computer.getCompany().toString(), realComputer.getCompany().toString());
	}
	@Test
	public void testAddComputer() {
		Company company = companyService.getCompanyById(1);
		Computer computer= ComputerBuilder.newInstance().setName("ThinkPad Carbon").setIntroducedDate(null).setDiscountedDate(null).setCompany(company).build();
		boolean inserted=computerDaoImpl.createComputer(computer);
		assertEquals(true, inserted);	
	}
	@Test
	public void testUpdateComputer() {
		boolean updated=computerDaoImpl.updateComputer(realComputer);
		assertEquals(true, updated);	
	}

	@Test
	public void deleteTest() {
		boolean deleted=computerDaoImpl.deleteComputer(10);
		assertEquals(true, deleted);
	}
	
	
}

