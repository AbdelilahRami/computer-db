package fr.excilys.db.main;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.service.impl.*;
@Component
public class Main {
	static Connection conn;
	@Autowired
	private ComputerServiceImpl computerServiceImpl;
	static int value;

	public static void main(String[] args) throws ClassNotFoundException {
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
				companies.forEach((cp)->System.out.println(cp));
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
				System.out.println(myComputer.getId()+" "+myComputer.getName()+" "+myComputer.getIntroducedDate()+" "+myComputer.getDiscountedDate()+" "+myComputer.getCompany().getId());
				value = showTheMenu();
				break;
			case 5:
				System.out.println("Your are in the update part :");
				Connection conn5= new ComputerDBConnection().getConnection();
				Computer updatComputer = getComputerToUpdate(conn5);
				manageUpdate(updatComputer,conn5);
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
				List<Computer> myComputers = computersByPage(conn);
				myComputers.forEach((cp) -> System.out.println(cp.toString()));
				value = showTheMenu();
				break;
			case 8 :
				System.out.println("Delete company :");
				deleteCompany();
				value = showTheMenu();
				break;
			case 9:
				System.out.print("Quit");
				System.out.flush();
				System.out.println("Thank you for using our Application.");
				break Label;
			}
		}
	}
	private  void deleteCompany() {
		System.out.println("Choose a company id:");
		Scanner sc = new Scanner(System.in);
		int idCompany = sc.nextInt();
		computerServiceImpl.deleteCompany(idCompany);
		
	}
	public static int getOperationNumber() {
		System.out.println("Choose a number :");
		Scanner sc = new Scanner(System.in);
		value = sc.nextInt();
		return value;
	}
	public  Computer computerCreate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the name of PC :");
		String name = sc.nextLine();
		LocalDate localDateIntro=LocalDateValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted=LocalDateValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		Company company = computerServiceImpl.getCompanyById(idCompany);
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
	public  Computer getComputerToUpdate(Connection conn) {
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
		Company company = computerServiceImpl.getCompanyById(idCompany);
		Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
				.setIntroducedDate(localDateIntro).setDiscountedDate(localDateDicounted).setCompany(company).build();
		return computer;
	}
	public  List<Computer> computersByPage(Connection conn) {
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
		System.out.println("8-Delete company :");
		System.out.println("9-Quit :");
		value = getOperationNumber();
		return value;
	}
	public  void manageCreate(Computer computer) {
			computerServiceImpl.createComputer(computer);
	}
	public  void manageUpdate(Computer computer,Connection conn) {
		
			computerServiceImpl.updateComputer(computer);
		
	}
	public static  List<Computer> manageAllComputers() {
		ComputerServiceImpl computerServiceImpl= new ComputerServiceImpl();
		List<Computer> computers = null;
		computers = computerServiceImpl.getAllComputers();
		return computers;
	}
	public   List<Company> manageAllCompanies() {
		List<Company> companies = null;
		companies = computerServiceImpl.getAllCompanies();
		return companies;
	}
	public  Computer manageDetails(int id) {
		Computer computer = null;
			computer = computerServiceImpl.getComputerDetails(id);
		return computer;
	}
	public  void manageDelete(int idComputer) {
		
			computerServiceImpl.deleteComputer(idComputer);
		
	}
}