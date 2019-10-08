package com.db.main;
import java.util.Date;
import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Computer;

public class UpdateComputer {

	public static void main(String[] args) throws Exception {
		ComputerDaoImpl computerService = new ComputerDaoImpl();
		Computer computer = new Computer(400, "Nintendo GameCube", new Date(), new Date(), 40);

		computerService.updateComputer(computer);
		List<Computer> computers=computerService.getAllComputers();
		computers.forEach((cp)->System.out.println(cp));

	}

}
