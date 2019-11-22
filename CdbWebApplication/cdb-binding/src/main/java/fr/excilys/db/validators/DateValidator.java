package fr.excilys.db.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;
import fr.excilys.db.exception.DatesNotValidException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DateValidator {
	public Date convertLocalToSql(LocalDate locald) {

		return (locald == null ? null : Date.valueOf(locald));
	}

	public static LocalDate convertDatetoLocalDate(java.util.Date utilDate, ResultSet rs, String s)
			throws SQLException {
		LocalDate localDate = null;
		if (utilDate == null) {
			return localDate;
		} else {
			localDate = rs.getDate(s).toLocalDate();
			return localDate;
		}
	}

	public static LocalDate fromStringToLocalDate(String s) {
		LocalDate localDate = null;
		if (s==null || s.isEmpty()) {
			return localDate;
		}
		if (s.matches(
				"^(?:(?:(?:0?[13578]|1[02])(\\/|-|\\.)31)\\1|(?:(?:0?[1,3-9]|1[0-2])(\\/|-|\\.)(?:29|30)\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:0?2(\\/|-|\\.)29\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:(?:0?[1-9])|(?:1[0-2]))(\\/|-|\\.)(?:0?[1-9]|1\\d|2[0-8])\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			localDate = LocalDate.parse(s, formatter);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (LocalDate.parse(s, formatter) instanceof LocalDate) {
			localDate = LocalDate.parse(s);
		} else {
			throw new DateTimeParseException("Input is not valid", null, (Integer) null, null);
		}
		return localDate;
	}

	public boolean datesAreValid(LocalDate introduced, LocalDate discontinued) throws DatesNotValidException {
		boolean datesAreValid = false;
		if (datesExisted(introduced, discontinued)) {
			if (introduced.compareTo(discontinued) > 0) {
				throw new DatesNotValidException("discontinued must be greater than introduced");
			}
		}
		return !datesAreValid;
	}

	public boolean datesExisted(LocalDate d1, LocalDate d2) {
		return ((d1 != null) && (d2 != null));
	}
}