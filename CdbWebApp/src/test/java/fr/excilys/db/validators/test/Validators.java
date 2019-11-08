package fr.excilys.db.validators.test;
import static org.junit.Assert.assertEquals;
import java.sql.Date;
import java.time.LocalDate;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.excilys.db.configuration.SpringConfiguration;
import fr.excilys.db.validators.DateValidator;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
public class Validators {
	@Autowired
	DateValidator dateValidator;
	String  stringDate;
	String stringDateVide;
	LocalDate locald=LocalDate.of(2013, 05, 25);
	LocalDate localdNull=null;
	@Before
	public void beforeValidateDate() {
		stringDate="2010-01-01";
		stringDateVide="";
	}
	@Test
	public void testValiDate() {
		assertEquals(LocalDate.of(2010, 01, 01), dateValidator.fromStringToLocalDate(stringDate));
		assertEquals(null, dateValidator.fromStringToLocalDate(stringDateVide));
	}
	@Test
	public void testConversionToSQL() {
		assertEquals(Date.valueOf(locald), dateValidator.convertLocalToSql(locald));
		assertEquals(null, dateValidator.convertLocalToSql(localdNull));
	}
}
