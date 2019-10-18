package fr.excilys.service.test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.db.coonection.H2DataBaseOperations;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.service.impl.ComputerServiceImpl;

public class TestService {
	private static final int ID1=1;
	private static final String NAME1="Mac book";
	private static final LocalDate LCI1=LocalDate.of(2019, 01, 01);
	private static final LocalDate LCD1=LocalDate.of(2020, 03, 20);
	private static final Company COMAPNY1= new Company(1, "Apple");
	private static final int ID2=1;
	private static final String NAME2="Mac book";
	private static final LocalDate LCI2=LocalDate.of(2019, 01, 01);
	private static final LocalDate LCD2=LocalDate.of(2020, 03, 20);
	private static final Company COMPANY2= new Company(1, "Apple");
	@Mock private Connection conn;
	private Computer computer1;
	private Computer computer2;
	@Before
	public void beforeServiceAllComputers(){
		computer1=ComputerBuilder.newInstance().setId(ID1).
				setName(NAME1).setIntroducedDate(LCI1).setDiscountedDate(LCD1).setCompany(COMAPNY1).build();
		computer2=ComputerBuilder.newInstance().setId(ID2).
				setName(NAME2).setIntroducedDate(LCI2).setDiscountedDate(LCD2).setCompany(COMPANY2).build();
	conn=H2DataBaseOperations.getConnection().get();
	}
	
	@Test
	public void testGetAllComputerService() {
		ComputerDaoImpl computerService=mock(ComputerDaoImpl.class);
		List<Computer> computers=new ArrayList<Computer>();
		computers.add(computer1);
		computers.add(computer2);
		when(computerService.getAllComputers(conn)).thenReturn(computers);
		ComputerServiceImpl computerservice=ComputerServiceImpl.getInstance();
		List<Computer> myComputers=computerservice.getAllComputers(conn);
		assertEquals(computers, myComputers);
		 
	}
}
