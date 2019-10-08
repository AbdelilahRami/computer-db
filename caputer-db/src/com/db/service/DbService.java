package com.db.service;
import java.util.Date;
import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.model.Company;
import com.db.model.Computer;

public class DbService {
	
	
	public boolean dateIsvalid(Date introducedDate, Date discountedDate) {

		return (discountedDate.compareTo(introducedDate)> 0);
	}
	
	public boolean companyIdExist(int id) {
		ComputerDaoImpl computerDao = new ComputerDaoImpl();
		List<Company> companies=computerDao.getAllyCompanies();
		for(Company company : companies) {
			if(id==company.getId())
				return true;
		}
		return false;
		
	}

}
