package com.db.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.service.impl.*;
public class Main {
	static ComputerDaoImpl computerDAO = new ComputerDaoImpl();
	static ComputerServiceImpl computerService= new ComputerServiceImpl(computerDAO);
	static int value;
	public static void main(String[] args) throws Exception {
		
			System.out.println("=================================");
			System.out.println("=======User Interface============");
			System.out.println("1-Show the list of computers :");
			System.out.println("2-Show the list of campanies :");
			System.out.println("3-Add a computer to database :");
			System.out.println("4-Find a computer with a specific Id :");
			System.out.println("5-Update a given computer :");
			System.out.println("6-Delete a given computer :");
			System.out.println("7-Quit :");
			value = getOperationNumber();
			
			
	Label:		while(true) {
				switch(value) {
				case 1 :
					computerService.getAllComputers();
					value = getOperationNumber();
					break;
				case 2 :
					computerService.getAllCompanies();
					value = getOperationNumber();
					break;
				case 3 :
					Computer computer=computerCreate();
					computerService.createComputer(computer);
					value = getOperationNumber();
					break;
				case 4 :
					System.out.println("Your are about to get computer details :");
					Computer myComputer =computerDAO.getComputerDetails(getIdOfComputer());
					System.out.println(myComputer.toString());
					break;
				case 5 :
					System.out.println("Your are in the update part :");
					Computer updatComputer = getComputerToUpdate();
					computerService.updateComputer(updatComputer);
					value = getOperationNumber();
					break;
				case 6 :
					System.out.println("Your are in the delete part :");
					Computer computerToDelete=getComputerToDelet();
					break;
				case 7 : 
					break Label;
					//start = false;	
				}

			}	
	}
	public static int getOperationNumber() {
		System.out.println("Choose a number :");
		Scanner sc = new Scanner(System.in);
		value = sc.nextInt();
		
		return value;
	}
	
	public static Computer computerCreate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the name of PC :");
		String name = sc.nextLine();
		System.out.println("Please give the date of introduction :");
		String localDIntroduction = sc.next();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDateIntro = LocalDate.parse(localDIntroduction, formatter);
		System.out.println("Please give the date of discounted :");
		String localDiscounted = sc.next();
		LocalDate localDateDicounted = LocalDate.parse(localDiscounted, formatter);
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		ComputerDaoImpl computerDao = new ComputerDaoImpl();
		Company company=computerDao.getCompanyById(idCompany);
		Computer computer = new Computer(name, localDateIntro, localDateDicounted, company);
		return computer;
	}
	public static int getIdOfComputer() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer");
		int idComputer = sc.nextInt();
		return idComputer;
	}
	public static Computer getComputerToDelet() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		int idComputer = sc.nextInt();
		ComputerDaoImpl computerDao = new ComputerDaoImpl();
		Computer computer= computerDao.getComputerDetails(idComputer);
		return computer;		
	}
	public static Computer getComputerToUpdate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		int idComputer = sc.nextInt();
		System.out.println("Please give the name of PC :");
		String name = sc.next();
		System.out.println("Please give the date of introduction :");
		String localDIntroduction = sc.next();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDateIntro = LocalDate.parse(localDIntroduction, formatter);
		System.out.println("Please give the date of discounted :");
		String localDiscounted = sc.next();
		LocalDate localDateDicounted = LocalDate.parse(localDiscounted, formatter);
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		ComputerDaoImpl computerDao = new ComputerDaoImpl();
		Company company=computerDao.getCompanyById(idCompany);
		Computer computer = new Computer(idComputer, name, localDateIntro, localDateDicounted, company);
		return computer;
	}
}