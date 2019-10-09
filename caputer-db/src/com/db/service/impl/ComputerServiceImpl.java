package com.db.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.db.dao.DaoComputer;
import com.db.exception.DatesNotValidException;
import com.db.exception.NotFoundCompanyException;
import com.db.exception.PageNotFoundException;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.Page;
import com.db.service.ComputeService;

public class ComputerServiceImpl implements ComputeService {

	private DaoComputer daoComputer;

	public ComputerServiceImpl(DaoComputer daoComputer) {

		this.daoComputer = daoComputer;
	}

	@Override
	public List<Computer> getAllComputers() throws Exception {
		List<Computer> computers = daoComputer.getAllComputers();

		if (computers == null || computers.isEmpty()) {
			throw new Exception("There is no computer in the table");
		}
		return computers;
	}

	@Override
	public List<Company> getAllCompanies() throws Exception {
		List<Company> companies = daoComputer.getAllyCompanies();
		try {
			if (companies == null || companies.isEmpty()) {
				throw new Exception("There is no company in the table");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return companies;

	}

	@Override
	public void updateComputer(Computer computer) throws Exception {

		int i = computer.getDiscountedDate().compareTo(computer.getIntroducedDate());
		try {
			if (computer.getDiscountedDate().compareTo(computer.getIntroducedDate()) <= 0) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			daoComputer.updateComputer(computer);
		} catch (NotFoundCompanyException exc) {
			System.out.println("The company doesn't exist");
		} catch (DatesNotValidException exc) {
			System.out.println("Discounted date must be greater than introduced date");
		}

	}

	@Override
	public void createComputer(Computer computer) throws Exception {
		int i = computer.getDiscountedDate().compareTo(computer.getIntroducedDate());
		try {
			if (computer.getDiscountedDate().compareTo(computer.getIntroducedDate()) <= 0) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			daoComputer.createComputer(computer);
		} catch (NotFoundCompanyException exc) {
			System.out.println("The company doesn't exist");
		} catch (DatesNotValidException exc) {
			System.out.println("Discounted date must be greater than introduced date");
		}

	}

	@Override
	public int checkDates(LocalDate d1, LocalDate d2) {
		// TODO Auto-generated method stub
		return d2.compareTo(d1);
	}

	@Override
	public boolean idCompanyExisted(int id) {
		Company company = daoComputer.getCompanyById(id);
		return company != null;
	}

	@Override
	public List<Computer> getComputersByPage(Page page) {
		try {
			if(page.getPageNumber() > getNumberOfPages()) {
				throw new PageNotFoundException("You have exced the max number of pages");
			}
		} catch (PageNotFoundException e) {
			System.out.println("You have exced the max number of pages");
		}
		if(page.getPageNumber() > getNumberOfPages())
		return null;
	}

	@Override
	public int getNumberOfPages() {
		// TODO Auto-generated method stub
		return 0;
	}

}
