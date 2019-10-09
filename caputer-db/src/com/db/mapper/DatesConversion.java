package com.db.mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
public class DatesConversion {
	    public static Date convertUtilToSql(LocalDate locald) {
	        Date sDate = Date.valueOf(locald);
	        return sDate;
	    }
	    public static LocalDate asLocalDate(java.util.Date date, ZoneId zone) {
	        if (date == null)
	            return null;

	        if (date instanceof java.sql.Date)
	            return ((java.sql.Date) date).toLocalDate();
	        else
	            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
	    }

}