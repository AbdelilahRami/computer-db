package fr.excilys.dao.test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.servlet.SpringConfiguration;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
public class ComputerDaoTest {
	Computer realComputer;
	@Autowired
	ComputerDaoImpl computerDaoImpl;
	
	@Before
	public void beforeTestAllComputers() {
		Company company = computerDaoImpl.getCompanyById(1);
		realComputer=ComputerBuilder.newInstance().setId(1).
							setName("HP EliteBook").setIntroducedDate(null).
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
		assertEquals(1, i);	
	}

	@Test
	public void deleteTest() {
		int i=computerDaoImpl.deleteComputer(10);
		assertEquals(1, i);	
	}
	
	
}
