package fr.excilys.dao.test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.excilys.db.connection.H2DataBaseOperations;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
public class CompanyDaoTest {
	ComputerDaoImpl computerDao = new ComputerDaoImpl(true);
	@Before
	public void beforeTestAllComputers() {
		
	}
	@After
	public void afterAllComputer() {
		H2DataBaseOperations.closeConnection();
		computerDao=null;
	}
	@Test
	public void testAllCompanies() {
		List<Company> companies=computerDao.getAllyCompanies();
		assertEquals(9,companies.size());
	}

}
