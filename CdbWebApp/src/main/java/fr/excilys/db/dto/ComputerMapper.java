package fr.excilys.db.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.mapper.DatesConversion;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.CompanyBuilder;

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
	
	
	public static fr.excilys.db.model.Computer fromStringToObject(Computer computer){
		String name=computer.getName();
		LocalDate lci=DatesConversion.fromStringToLocalDate(computer.getLocalDateIntroduction());
		LocalDate lcd=DatesConversion.fromStringToLocalDate(computer.getLocalDateDiscontinued());
		fr.excilys.db.model.Computer objComputer=fr.excilys.db.model.ComputerBuilder.newInstance().setName(name) 
										.setIntroducedDate(lci).setDiscountedDate(lcd).build();
		
		return objComputer;
	}
		
	public static List<Computer> fromListObjecToListString(List<fr.excilys.db.model.Computer> list){
		
		List<Computer> computers= new ArrayList<Computer>();
		for(fr.excilys.db.model.Computer computer : list) {
			computers.add(fromObjectToString(computer));
		}
		
		return computers;
	}
	

}
