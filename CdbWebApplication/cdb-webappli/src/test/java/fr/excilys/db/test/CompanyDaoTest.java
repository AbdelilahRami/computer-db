package fr.excilys.db.test;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.excilys.db.configuration.SpringConfiguration;
import fr.excilys.db.dao.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.service.CompanyServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfiguration.class})
public class CompanyDaoTest {
	@Autowired
	ComputerDaoImpl computerDaoImpl;
	@Autowired
	CompanyServiceImpl companyService;
	@Before
	public void beforeTestAllComputers() {
		
	}
	@After
	public void afterAllComputer() {
		computerDaoImpl=null;
	}
	@Test
	public void testAllCompanies() {
		List<Company> companies=companyService.getAllCompanies();
		assertEquals(9,companies.size());
	}

}
