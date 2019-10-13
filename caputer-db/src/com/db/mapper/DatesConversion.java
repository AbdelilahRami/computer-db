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
		   if(s.isEmpty()) return null;
			if(s.matches("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				return LocalDate.parse(s, formatter);
			}
			return LocalDate.parse(s);
		   
	   }
	   
	  

}