package fr.excilys.dao.test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.excilys.db.configuration.SpringConfiguration;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.service.impl.CompanyServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
public class ComputerDaoTest {
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
