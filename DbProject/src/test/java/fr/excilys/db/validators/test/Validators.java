package fr.excilys.db.validators.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.*;

import fr.excilys.db.mapper.DatesConversion;
public class Validators {
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
		assertEquals(LocalDate.of(2010, 01, 01), DatesConversion.fromStringToLocalDate(stringDate));
		assertEquals(null, DatesConversion.fromStringToLocalDate(stringDateVide));
		assertThrows(DateTimeParseException.class, ()->{DatesConversion.fromStringToLocalDate("gsqdjkmldq");});
	}
	@Test
	public void testConversionToSQL() {
		assertEquals(Date.valueOf(locald), DatesConversion.convertLocalToSql(locald));
		assertEquals(null, DatesConversion.convertLocalToSql(localdNull));
		
	}

}
