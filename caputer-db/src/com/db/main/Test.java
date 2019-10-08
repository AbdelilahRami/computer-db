package com.db.main;
import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Computer;
public class Test {

	public static void main(String[] args) {
		
		System.out.println("List of Computers");
		ComputerDaoImpl computerService=new ComputerDaoImpl();
		
		List<Computer> computers= computerService.getAllComputers();
		for(Computer computer : computers) {
			System.out.println(computer.getId()+" "+computer.getName()+" "+computer.getIntroducedDate()
			+" "+computer.getDiscountedDate()+" "+computer.getIdCompany());
		}
		
	}

}
