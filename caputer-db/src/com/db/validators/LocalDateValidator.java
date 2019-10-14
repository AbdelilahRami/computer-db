package com.db.validators;

import java.time.LocalDate;

import com.db.mapper.DatesConversion;

public class LocalDateValidator {
	
	
	public static boolean inputIsValid(String input) {
		LocalDate localDate = DatesConversion.fromStringToLocalDate(input);	
		return (localDate instanceof LocalDate || localDate==null);
	}

}
