package com.db.main;

import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Computer;

public class CreateComputer {

	public static void main(String[] args) {
		ComputerDaoImpl computerService = new ComputerDaoImpl();
		computerService.createComputer("Apple Inc.", null, null, 1);
		List<Computer> computers =computerService.getAllComputers();
		computers.forEach((cp)->System.out.println(cp));

	}

}
