package fr.excilys.db.mapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DatesConversion {
	    public static Date convertLocalToSql(LocalDate locald) {
	    	if (locald == null){
	    		return null;
	    	}
	        Date sDate = Date.valueOf(locald);
	        return sDate;
	    }
	   public static LocalDate convertDatetoLocalDate(java.util.Date utilDate, ResultSet rs, String s) throws SQLException {
		   LocalDate localDate = null;
		   if (utilDate == null) {
			   return localDate;
		   }   
		   else {
			   localDate = rs.getDate(s).toLocalDate();
			   return localDate;
		   }
	   }
	   public static LocalDate fromStringToLocalDate(String s) throws DateTimeParseException {
		   if (s.equals("")) { return null;
		   }
			if (s.matches("^(?:(?:(?:0?[13578]|1[02])(\\/|-|\\.)31)\\1|(?:(?:0?[1,3-9]|1[0-2])(\\/|-|\\.)(?:29|30)\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:0?2(\\/|-|\\.)29\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:(?:0?[1-9])|(?:1[0-2]))(\\/|-|\\.)(?:0?[1-9]|1\\d|2[0-8])\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				return LocalDate.parse(s, formatter);
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			if (LocalDate.parse(s, formatter) instanceof LocalDate) {
				return LocalDate.parse(s);
			} else {
				throw new DateTimeParseException("Input is not valid", null, (Integer) null, null);
			}
		   }
	   }
	   
