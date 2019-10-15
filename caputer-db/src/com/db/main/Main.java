package com.db.main;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.db.daoImp.ComputerDaoImpl;
import com.db.exception.ComputerToDeleteNotFound;
import com.db.exception.DatesNotValidException;
import com.db.exception.NoCompanyFound;
import com.db.exception.NoComputerFound;
import com.db.exception.NotFoundCompanyException;
import com.db.exception.PageNotFoundException;
import com.db.mapper.DatesConversion;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.ComputerBuilder;
import com.db.service.impl.*;

public class Main {
	static ComputerDaoImpl computerDAO = ComputerDaoImpl.getInstance();
	static ComputerServiceImpl computerServiceImpl = ComputerServiceImpl.getInstance();
	static int value;

	public static void main(String[] args) {

		showTheMenu();

		Label: while (true) {
			switch (value) {
			case 1:
				List<Computer> computers = manageAllComputers();
				computers.forEach((c) -> System.out.println(c));
				value = showTheMenu();
				break;
			case 2:
				List<Company> companies = manageAllCompanies();
				value = showTheMenu();
				break;
			case 3:
				Computer computer = computerCreate();
				manageCreate(computer);
				value = showTheMenu();
				break;
			case 4:
				System.out.println("Your are about to get computer details :");
				int idComputer = getIdOfComputer();
				Computer myComputer = manageDetails(idComputer);
				System.out.println(myComputer.toString());
				value = showTheMenu();
				break;
			case 5:
				System.out.println("Your are in the update part :");
				Computer updatComputer = getComputerToUpdate();
				manageUpdate(updatComputer);
				value = showTheMenu();
				break;
			case 6:
				System.out.println("Your are in the delete part :");
				int idCom = getComputerToDelet();
				manageDelete(idCom);
				value = showTheMenu();
				break;
			case 7:
				System.out.println("Pagination part :");
				List<Computer> myComputers = computersByPage();
				myComputers.forEach((cp) -> System.out.println(cp.toString()));
				value = showTheMenu();
				break;
			case 8:
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

	public static Computer computerCreate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the name of PC :");
		String name = sc.nextLine();
		System.out.println("Please give the date of introduction :(Ex : yyyy-dd-mm or mm/dd/yyyy )");
		String localDIntroduction = sc.nextLine();
		LocalDate localDateIntro = DatesConversion.fromStringToLocalDate(localDIntroduction);
		System.out.println("Please give the date of discounted :(Ex : yyyy-dd-mm or mm/dd/yyyy )");
		String localDiscounted = sc.nextLine();
		LocalDate localDateDicounted = DatesConversion.fromStringToLocalDate(localDiscounted);
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Company company = computerDao.getCompanyById(idCompany);
		Computer computer = ComputerBuilder.newInstance().setName(name).setIntroducedDate(localDateIntro)
				.setDiscountedDate(localDateDicounted).setCompany(company).build();
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

	public static Computer getComputerToUpdate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		Scanner scn = new Scanner(System.in);
		int idComputer = sc.nextInt();
		System.out.println("Please give the name of PC :");
		String name = scn.nextLine();
		System.out.println("Please give the date of introduction :");
		String localDIntroduction = scn.nextLine();
		LocalDate localDateIntro = DatesConversion.fromStringToLocalDate(localDIntroduction);
		System.out.println("Please give the date of discontinued :");
		String localDiscounted = scn.nextLine();
		LocalDate localDateDicounted = DatesConversion.fromStringToLocalDate(localDiscounted);
		System.out.println("Please give the id of company :");
		int idCompany = scn.nextInt();
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Company company = computerDao.getCompanyById(idCompany);
		Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
				.setIntroducedDate(localDateIntro).setDiscountedDate(localDateDicounted).setCompany(company).build();
		return computer;
	}

	public static List<Computer> computersByPage() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of page");
		int pageNumber = sc.nextInt();
		List<Computer> computers = null;
		try {
			computers = computerDAO.getComputersByPageNumber(pageNumber);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (PageNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return computers;
	}

	public static int showTheMenu() {

		System.out.println("=================================");
		System.out.println("      Excilys: CDB Application   ");
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

	public static void manageCreate(Computer computer) {

		try {
			int exectued = computerServiceImpl.createComputer(computer);

		} catch (DatesNotValidException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundCompanyException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void manageUpdate(Computer computer) {
		try {
			int updated = computerServiceImpl.updateComputer(computer);
		} catch (DatesNotValidException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundCompanyException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void manageDelete(Computer computer) {
		try {
			int deleted = computerServiceImpl.deleteComputer(computer.getId());
		} catch (ComputerToDeleteNotFound e) {

			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static List<Computer> manageAllComputers() {

		List<Computer> computers = null;
		try {
			computers = computerServiceImpl.getAllComputers();
		} catch (NoComputerFound e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return computers;

	}

	public static List<Company> manageAllCompanies() {

		List<Company> computers = null;

		try {
			computers = computerServiceImpl.getAllCompanies();
		} catch (NoCompanyFound e) {

			System.out.println(e.getMessage());
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}

		return computers;

	}

	public static Computer manageDetails(int id) {

		Computer computer = null;
		try {
			computer = computerDAO.getComputerDetails(id);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return computer;
	}

	public static void manageDelete(int idComputer) {

		try {
			computerServiceImpl.deleteComputer(idComputer);
		} catch (ComputerToDeleteNotFound e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}