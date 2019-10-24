package fr.excilys.db.main;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import fr.excilys.db.connection.ComputerDBConnection;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.exception.ComputerToDeleteNotFound;
import fr.excilys.db.exception.DatesNotValidException;
import fr.excilys.db.exception.NoCompanyFound;
import fr.excilys.db.exception.NoComputerFound;
import fr.excilys.db.exception.NotFoundCompanyException;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.service.impl.*;
import fr.excilys.db.validators.LocalDateValidator;
public class Main {
	static Connection conn;
	static ComputerDaoImpl computerDAO = ComputerDaoImpl.getInstance();
	static ComputerServiceImpl computerServiceImpl = ComputerServiceImpl.getInstance();
	static int value;

	public static void main(String[] args) throws ClassNotFoundException {
		showTheMenu();
		Label: while (true) {
			switch (value) {
			case 1:	
				Connection conn1=ComputerDBConnection.getInstance().getConnection();
				List<Computer> computers = manageAllComputers();
				computers.forEach((c) -> System.out.println(c));
				value = showTheMenu();
				break;
			case 2:
				Connection conn2=ComputerDBConnection.getInstance().getConnection();
				List<Company> companies = manageAllCompanies();
				companies.forEach((cp)->System.out.println(cp));
				value = showTheMenu();
				break;
			case 3:
				Connection conn3=ComputerDBConnection.getInstance().getConnection();
				Computer computer = computerCreate();
				manageCreate(computer,conn);
				value = showTheMenu();
				break;
			case 4:
				System.out.println("Your are about to get computer details :");
				int idComputer = getIdOfComputer();
				Connection conn4=ComputerDBConnection.getInstance().getConnection();
				Computer myComputer = manageDetails(idComputer);
				System.out.println(myComputer.getId()+" "+myComputer.getName()+" "+myComputer.getIntroducedDate()+" "+myComputer.getDiscountedDate()+" "+myComputer.getCompany().getId());
				value = showTheMenu();
				break;
			case 5:
				System.out.println("Your are in the update part :");
				Connection conn5=ComputerDBConnection.getInstance().getConnection();
				Computer updatComputer = getComputerToUpdate(conn5);
				manageUpdate(updatComputer,conn5);
				value = showTheMenu();
				break;
			case 6:
				System.out.println("Your are in the delete part :");
				int idCom = getComputerToDelet();
				Connection conn6=ComputerDBConnection.getInstance().getConnection();
				manageDelete(idCom);
				value = showTheMenu();
				break;
			case 7:
				System.out.println("Pagination part :");
				Connection conn7=ComputerDBConnection.getInstance().getConnection();
				List<Computer> myComputers = computersByPage(conn);
				myComputers.forEach((cp) -> System.out.println(cp.toString()));
				value = showTheMenu();
				break;
			case 8:
				System.out.print("Quit");
				System.out.flush();
				System.out.println("Thank you for using our Application.");
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
		LocalDate localDateIntro=LocalDateValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted=LocalDateValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Company company = computerDao.getCompanyById(idCompany);
		Computer computer = ComputerBuilder.newInstance().setName(name).setIntroducedDate(localDateIntro)
				.setDiscountedDate(localDateDicounted).setCompany(company).build();
		return computer;
	}
	public static int getIdOfComputer() {
		System.out.println("Please give the id of the computer");
		Scanner sc = new Scanner(System.in);
		int idComputer = sc.nextInt();
		return idComputer;
	}
	public static int getComputerToDelet() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		int idComputer = sc.nextInt();
		return idComputer;
	}
	public static Computer getComputerToUpdate(Connection conn) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		Scanner scn = new Scanner(System.in);
		int idComputer = sc.nextInt();
		System.out.println("Please give the name of PC :");
		String name = scn.nextLine();
		LocalDate localDateIntro=LocalDateValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted=LocalDateValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = scn.nextInt();
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Company company = computerDao.getCompanyById(idCompany);
		Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
				.setIntroducedDate(localDateIntro).setDiscountedDate(localDateDicounted).setCompany(company).build();
		return computer;
	}
	public static List<Computer> computersByPage(Connection conn) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of page");
		int pageNumber = sc.nextInt();
		List<Computer> computers = null;
		
			computers = computerServiceImpl.getComputersByPage(pageNumber,50);

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
	public static void manageCreate(Computer computer, Connection conn) {
		try {
			computerServiceImpl.createComputer(computer);

		} catch (DatesNotValidException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundCompanyException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void manageUpdate(Computer computer,Connection conn) {
		try {
			computerServiceImpl.updateComputer(computer);
		} catch (DatesNotValidException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundCompanyException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public static List<Computer> manageAllComputers() {
		List<Computer> computers = null;
		computers = computerServiceImpl.getAllComputers();
		return computers;
	}
	public static List<Company> manageAllCompanies() {
		List<Company> computers = null;
		computers = computerServiceImpl.getAllCompanies();
		return computers;
	}
	public static Computer manageDetails(int id) {
		Computer computer = null;
			computer = computerDAO.getComputerDetails(id);
		return computer;
	}
	public static void manageDelete(int idComputer) {
		
			computerServiceImpl.deleteComputer(idComputer);
		
	}
}