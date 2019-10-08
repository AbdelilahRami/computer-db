package com.db.main;
import java.util.Scanner;

import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Computer;

public class ShowDetails {
	
	public static void main(String[] args) {
		ComputerDaoImpl computerService= new ComputerDaoImpl();
		System.out.println("Merci de laisser un id");
		Scanner sc = new Scanner(System.in);
		int idComputer = sc.nextInt();
		Computer computer=computerService.getComputerDetails(idComputer);
		System.out.println("Details of the computer with the id "+idComputer+" is : ");
		System.out.println(computer.toString());
		
		
		
	}

}
