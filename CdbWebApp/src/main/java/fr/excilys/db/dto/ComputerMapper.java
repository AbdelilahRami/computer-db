package fr.excilys.db.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.db.mapper.DatesConversion;

public class ComputerMapper {

	public static Computer fromObjectToString(fr.excilys.db.model.Computer computer) {
		String id = String.valueOf(computer.getId());
		String name = computer.getName();
		String lci = "";
		if (computer.getIntroducedDate() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
			lci = computer.getIntroducedDate().format(formatter);
		}
		String lcd = "";
		if (computer.getDiscountedDate() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
			lcd = computer.getDiscountedDate().format(formatter);
		}
		String company="";
		if (computer.getCompany()!=null) {	
			company=computer.getCompany().getName();
		}
		return ComputerBuilder.newInstance().
				setId(id).setName(name).setLocalDateIntro(lci).setLocaldateDiscontinued(lcd).setCompany(company).build();
	}
	
	
	//public static fr.excilys.db.model.Computer fromStringToObject(Computer computer){
		//int id=Integer.parseInt(computer.getId());
		//String name=computer.getName();
		//LocalDate lci=DatesConversion.fromStringToLocalDate(computer.getLocalDateIntroduction());
		//LocalDate lcd=DatesConversion.fromStringToLocalDate(computer.getLocalDateDiscontinued());
		
		
	public static List<Computer> fromListObjecToListString(List<fr.excilys.db.model.Computer> list){
		
		List<Computer> computers= new ArrayList<Computer>();
		for(fr.excilys.db.model.Computer computer : list) {
			computers.add(fromObjectToString(computer));
		}
		
		return computers;
	}

}
