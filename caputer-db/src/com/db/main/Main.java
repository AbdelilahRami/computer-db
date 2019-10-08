package com.db.main;

import java.util.Scanner;

import com.db.daoImp.ComputerDaoImpl;

public class Main {

	public static void main(String[] args) {
		ComputerDaoImpl computerService= new ComputerDaoImpl();

			System.out.println("User Interface  :");
			System.out.println("1-Show the list of computers :");
			System.out.println("2-Show the list of campanies :");
			System.out.println("3-Add a computer to database :");
			System.out.println("4-Find a computer with a specific Id :");
			System.out.println("5-Update a given computer :");
			System.out.println("6-Delete a given computer :");
			Scanner sc = new Scanner(System.in);
			int value = sc.nextInt();
			
			boolean start = true;
			while(start) {
				switch(value) {
				case 1 :
					computerService.getAllComputers();
					break;
				case 2 :
					computerService.getAllyCompanies();
					break;
				
				}
				
			}
			

			
	}

}
