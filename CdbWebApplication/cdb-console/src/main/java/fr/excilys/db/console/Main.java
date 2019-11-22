package fr.excilys.db.console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;
import fr.excilys.db.client.RestClient;
import fr.excilys.db.dto.ComputerDto;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.CompanyBuilder;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.validators.ConsoleValidator;
@Component
public class Main {
	
	static int value;

	public static void main(String[] args) throws ClassNotFoundException {
		
		showTheMenu();
		Label: while (true) {
			switch (value) {
			case 1:	
				RestClient client1= new RestClient();
				List<ComputerDto> computers=client1.getAllComputers();
				computers.forEach((c) -> System.out.println(c));
				value = showTheMenu();
				break;
			case 2:
				RestClient client2= new RestClient();
				List<Company> companies = client2.getAllCompanies();
				companies.forEach((cp)->System.out.println(cp));
				value = showTheMenu();
				break;
			case 3:
				Computer computer=getComputerToCreate();
				RestClient client3= new RestClient();
				client3.registerComputer(computer);
				value = showTheMenu();
				break;
			case 4:
				System.out.println("Your are about to get computer details :");
				int idComputer = getIdOfComputer();
				RestClient client4= new RestClient();
				System.out.println(client4.getJsonComputer(idComputer));
				value = showTheMenu();
				break;
			case 5:
				System.out.println("Your are in the update part :");
				Computer computerToUpdate=getComputerToUpdate();
				RestClient client5= new RestClient();
				client5.updateComputer(computerToUpdate);
				value = showTheMenu();
				break;
			case 6:
				System.out.println("Your are in the delete part :");
				RestClient client6= new RestClient();
				int idComputerToDelete=getComputerToDelet();
				client6.deleteComputer(idComputerToDelete);
				value = showTheMenu();
				break;
			case 7:
				System.out.println("Pagination part :");
				List<Integer> paginationInputs = paginationInputs();
				RestClient client7= new RestClient();
				List<ComputerDto> computersPerPage=client7.getAllComputersByPage(paginationInputs.get(0), paginationInputs.get(1));
				computersPerPage.forEach((cp) -> System.out.println(cp.toString()));
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

	public static Computer getComputerToCreate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the name of PC :");
		String name = sc.nextLine();
		LocalDate localDateIntro = ConsoleValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted = ConsoleValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		Company company=CompanyBuilder.newInstance().setIdCompany(idCompany).build();
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

	public static  int getComputerToDelet() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to delete");
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
		LocalDate localDateIntro = ConsoleValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted = ConsoleValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = scn.nextInt();
		Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
				.setIntroducedDate(localDateIntro).setDiscountedDate(localDateDicounted).setCompany(CompanyBuilder.newInstance().setIdCompany(idCompany).build()).build();
		return computer;
	}

	public static List<Integer> paginationInputs() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of page");
		int pageNumber = sc.nextInt();
		System.out.println("Please enter the size of page");
		int size = sc.nextInt();
		List<Integer> inputs = new ArrayList<Integer>();
		inputs.add(pageNumber);
		inputs.add(size);
		return inputs;
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

	

//	public  List<ComputerDto> manageAllComputers() {
//		List<ComputerDto> computers = computerServiceImpl.getAllComputers();
//		return computers;
//	}
//
//	
//
//	public Computer manageDetails(int id) {
//		Computer computer = null;
//		computer = computerServiceImpl.getComputerDetails(id);
//		return computer;
//	}
//
//	public void manageDelete(int idComputer) {
//
//		computerServiceImpl.deleteComputer(idComputer);
//
//	}
}