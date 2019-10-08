package com.db.main;

import java.util.Scanner;

import com.db.daoImp.ComputerDaoImpl;

public class UpdateComputer {

	public static void main(String[] args) {
		ComputerDaoImpl computerService = new ComputerDaoImpl();
		System.out.println("Merci de laisser un id");
		Scanner sc = new Scanner(System.in);
		int idComputer = sc.nextInt();
		String name = sc.nextLine();

	}

}
