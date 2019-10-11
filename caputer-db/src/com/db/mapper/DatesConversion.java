package com.db.mapper;


import java.time.LocalDate;

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
	   
	  

}