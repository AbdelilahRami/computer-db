package fr.excilys.db.console;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.service.*;
import fr.excilys.db.validators.ConsoleValidator;
@Component
public class Main {
	static Connection conn;
	@Autowired
	private ComputerServiceImpl computerServiceImpl;
	@Autowired
	private CompanyServiceImpl companyService;
	private static ApplicationContext context;
	static int value;

	public static void main(String[] args) throws ClassNotFoundException {
		context= new AnnotationConfigApplicationContext(ConsoleConfiguration.class);
		Main main =context.getBean(Main.class);
		showTheMenu();
		Label: while (true) {
			switch (value) {
			case 1:	
				List<Computer> computers=main.manageAllComputers();
				computers.forEach((c) -> System.out.println(c));
				value = showTheMenu();
				break;
			case 2:
				List<Company> companies = main.manageAllCompanies();
				companies.forEach((cp)->System.out.println(cp));
				value = showTheMenu();
				break;
			case 3:
				main.computerCreate();
				value = showTheMenu();
				break;
			case 4:
				System.out.println("Your are about to get computer details :");
				int idComputer = getIdOfComputer();
				Computer myComputer = main.manageDetails(idComputer);
				System.out.println(myComputer.getId()+" "+myComputer.getName()+" "+myComputer.getIntroducedDate()+" "+myComputer.getDiscountedDate()+" "+myComputer.getCompany().getIdCompany());
				value = showTheMenu();
				break;
			case 5:
				System.out.println("Your are in the update part :");
				main.getComputerToUpdate();
				value = showTheMenu();
				break;
			case 6:
				System.out.println("Your are in the delete part :");
				main.getComputerToDelet();
				value = showTheMenu();
				break;
			case 7:
				System.out.println("Pagination part :");
				List<Computer> myComputers = main.computersByPage();
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

	public void computerCreate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the name of PC :");
		String name = sc.nextLine();
		LocalDate localDateIntro = ConsoleValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted = ConsoleValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		Company company = companyService.getCompanyById(idCompany);
		Computer computer = ComputerBuilder.newInstance().setName(name).setIntroducedDate(localDateIntro)
				.setDiscountedDate(localDateDicounted).setCompany(company).build();
		computerServiceImpl.createComputer(computer);
	}

	public static int getIdOfComputer() {
		System.out.println("Please give the id of the computer");
		Scanner sc = new Scanner(System.in);
		int idComputer = sc.nextInt();
		return idComputer;
	}

	public  int getComputerToDelet() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		int idComputer = sc.nextInt();
		computerServiceImpl.deleteComputer(idComputer);

		return idComputer;
	}

	public void getComputerToUpdate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		Scanner scn = new Scanner(System.in);
		int idComputer = sc.nextInt();
		System.out.println("Please give the name of PC :");
		String name = scn.nextLine();
		LocalDate localDateIntro = ConsoleValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted = ConsoleValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = scn.nextInt();
		Company company = companyService.getCompanyById(idCompany);
		Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
				.setIntroducedDate(localDateIntro).setDiscountedDate(localDateDicounted).setCompany(company).build();
		computerServiceImpl.updateComputer(computer);
	}

	public List<Computer> computersByPage() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of page");
		int pageNumber = sc.nextInt();
		System.out.println("Please enter the size of page");
		int size = sc.nextInt();
		List<Computer> computers = null;

		computers = computerServiceImpl.getComputersByPage(pageNumber, size);

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

	

	public  List<Computer> manageAllComputers() {
		List<Computer> computers = computerServiceImpl.getAllComputers();
		return computers;
	}

	public List<Company> manageAllCompanies() {
		List<Company> companies = null;
		companies = companyService.getAllCompanies();
		return companies;
	}

	public Computer manageDetails(int id) {
		Computer computer = null;
		computer = computerServiceImpl.getComputerDetails(id);
		return computer;
	}

	public void manageDelete(int idComputer) {

		computerServiceImpl.deleteComputer(idComputer);

	}
}