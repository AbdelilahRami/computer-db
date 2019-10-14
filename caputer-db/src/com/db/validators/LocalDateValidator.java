package com.db.validators;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.db.mapper.DatesConversion;

public class LocalDateValidator {

	public static LocalDate inputIsValidForIntroduction() {
		LocalDate localDateIntro = null;
		Scanner scn = new Scanner(System.in);
		boolean inputIsNotValid = true;
		while (inputIsNotValid) {
		System.out.println("Please give the date of introduction : :(Exemple : yyyy-dd-mm or mm/dd/yyyy )");
		String localDIntroduction = scn.nextLine();
		try {
			localDateIntro = DatesConversion.fromStringToLocalDate(localDIntroduction);
			inputIsNotValid = false;
		} catch (DateTimeParseException exc) {
			System.out.println(exc.getParsedString() + " Is not a date Could you please retry !");
		}
		}
		return localDateIntro;

	}

	public static LocalDate inputIsValidForDiscontinued() {
		LocalDate localDateDicounted = null;
		Scanner scn = new Scanner(System.in);
		boolean inputIsNotValid = true;
		while (inputIsNotValid) {
			System.out.println("Please give the date of discontinued : :(Exemple : yyyy-dd-mm or mm/dd/yyyy )");
			String localDiscounted = scn.nextLine();
			try {
				localDateDicounted = DatesConversion.fromStringToLocalDate(localDiscounted);
				inputIsNotValid = false;
			} catch (DateTimeParseException exc) {
				System.out.println(exc.getParsedString() + " Is not date Could you please retry !");
			}

		}
		return localDateDicounted;
	}
}
