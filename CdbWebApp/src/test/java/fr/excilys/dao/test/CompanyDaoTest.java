package fr.excilys.dao.test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
public class CompanyDaoTest {
	@Autowired
	ComputerDaoImpl computerDaoImpl;
	@Before
	public void beforeTestAllComputers() {
		
	}
	@After
	public void afterAllComputer() {
		computerDaoImpl=null;
	}
	@Test
	public void testAllCompanies() {
		List<Company> companies=computerDaoImpl.getAllyCompanies();
		assertEquals(9,companies.size());
	}

}
