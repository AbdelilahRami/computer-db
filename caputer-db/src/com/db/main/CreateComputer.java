package com.db.main;


import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Computer;

public class CreateComputer {

	public static void main(String[] args) throws Exception {
		ComputerDaoImpl computerService = new ComputerDaoImpl();
		Computer computer = new Computer();
		computer.setName("");
		List<Computer> computers =computerService.getAllComputers();
		computers.forEach((cp)->System.out.println(cp));

	}

}
