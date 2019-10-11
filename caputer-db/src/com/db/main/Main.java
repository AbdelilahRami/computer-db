package com.db.main;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.db.daoImp.ComputerDaoImpl;
import com.db.mapper.DatesConversion;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.service.impl.*;
import com.sun.jdi.ByteValue;


public class Main {
	static ComputerDaoImpl computerDAO = ComputerDaoImpl.getInstance();
	static ComputerServiceImpl computerServiceImpl =ComputerServiceImpl.getInstance();
	static int value;

	public static void main(String[] args) throws Exception {

		showTheMenu();

		Label: while (true) {
			switch (value) {
			case 1:
				computerServiceImpl.getAllComputers();
				value = showTheMenu();
				break;
			case 2:
				computerServiceImpl.getAllCompanies();
				value = showTheMenu();
				break;
			case 3:
				Computer computer = computerCreate();
				computerServiceImpl.createComputer(computer);
				value = showTheMenu();
				break;
			case 4:
				System.out.println("Your are about to get computer details :");
				Computer myComputer = computerDAO.getComputerDetails(getIdOfComputer());
				System.out.println(myComputer.toString());
				value=showTheMenu();
				break;
			case 5:
				System.out.println("Your are in the update part :");
				Computer updatComputer = getComputerToUpdate();
				computerServiceImpl.updateComputer(updatComputer);
				value = showTheMenu();
				break;
			case 6:
				System.out.println("Your are in the delete part :");
				int idComputer = getComputerToDelet();
				computerServiceImpl.deleteComputer(idComputer);
				value=showTheMenu();
				break;
			case 7:
				System.out.println("Pagination part :");
				List<Computer> myComputers = computersByPage();
				myComputers.forEach((cp) -> System.out.println(cp.toString()));
				value =showTheMenu();
				break;
			case 8 :
				System.out.print("Quit");				
				System.out.flush();
				break Label;
			}

		}
	}

	public static int getOperationNumber() {
		System.out.println("Choose a number :");
		Scanner sc = new Scanner(System.in);
		value = sc.nextInt();

		return value;
	}

	public static Computer computerCreate() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the name of PC :");
		String name = sc.nextLine();
		System.out.println("Please give the date of introduction :");
		String localDIntroduction = sc.nextLine().trim();
		LocalDate localDateIntro = DatesConversion.fromStringToLocalDate(localDIntroduction);
		System.out.println("Please give the date of discounted :");
		String localDiscounted = sc.next();
		LocalDate localDateDicounted = DatesConversion.fromStringToLocalDate(localDiscounted);
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Company company = computerDao.getCompanyById(idCompany);
		Computer computer = new Computer(name, localDateIntro, localDateDicounted, company);
		return computer;
	}

	public static int getIdOfComputer() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer");
		int idComputer = sc.nextInt();
		return idComputer;
	}

	public static int getComputerToDelet() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		int idComputer = sc.nextInt();
		return idComputer;
	}

	public static Computer getComputerToUpdate() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		int idComputer = sc.nextInt();
		System.out.println("Please give the name of PC :");
		String name = sc.next();
		System.out.println("Please give the date of introduction :");
		String localDIntroduction = sc.next();
		LocalDate localDateIntro = DatesConversion.fromStringToLocalDate(localDIntroduction);
		String localDiscounted = sc.next();
		LocalDate localDateDicounted = DatesConversion.fromStringToLocalDate(localDiscounted);
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Company company = computerDao.getCompanyById(idCompany);
		Computer computer = new Computer(idComputer, name, localDateIntro, localDateDicounted, company);
		return computer;
	}

	public static List<Computer> computersByPage() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of page");
		int pageNumber = sc.nextInt();
		List<Computer> computers = computerDAO.getComputersByPageNumber(pageNumber);
		return computers;
	}

	public static int showTheMenu() {

		System.out.println("=================================");
		System.out.println("=======User Interface============");
		System.out.println("=================================");
		System.out.println("1-Show the list of computers :");
		System.out.println("2-Show the list of campanies :");
		System.out.println("3-Add a computer to database :");
		System.out.println("4-Find a computer with a specific Id :");
		System.out.println("5-Update a given computer :");
		System.out.println("6-Delete a given computer :");
		System.out.println("7-Pagination functionalities :");
		System.out.println("8-Quit :");
		value = getOperationNumber();
		return value;
	}
}