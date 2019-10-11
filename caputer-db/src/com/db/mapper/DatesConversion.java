package com.db.mapper;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DatesConversion {
	    public static Date convertUtilToSql(LocalDate locald) {
	        Date sDate = Date.valueOf(locald);
	        return sDate;
	    }
	   public static LocalDate convertDatetoLocalDate(java.util.Date utilDate, ResultSet rs, String s) throws SQLException {
		   LocalDate localDate=null;
		   if(utilDate == null) {
			   return localDate;
		   }   
		   else {
			   localDate=rs.getDate(s).toLocalDate();
			   return localDate;
		   }
	   }
	   public static LocalDate fromStringToLocalDate(String s) {
		   String pattern="yyyy-mm-dd";
		   LocalDate localD;
		   if(s==null | s.isEmpty()) {
			   return null;
		   }
		   String regex="^((19|20)\\\\d\\\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
		   if(s.matches(regex)) {
			   DateTimeFormatter formatter=DateTimeFormatter.ofPattern(pattern);
			   localD=LocalDate.parse(s, formatter);
		   }
		   /*else {
			   System.out.println("the format is not a date");
		   }*/
		return null;
		   
	   }
	   
	  

}