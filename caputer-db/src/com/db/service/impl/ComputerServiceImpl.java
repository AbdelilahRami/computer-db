package com.db.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.exception.ComputerToDeleteNotFound;
import com.db.exception.DatesNotValidException;
import com.db.exception.NotFoundCompanyException;
import com.db.exception.PageNotFoundException;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.Page;
import com.db.service.ComputeService;


public class ComputerServiceImpl implements ComputeService {

	private static ComputerDaoImpl computerDaoImpl=ComputerDaoImpl.getInstance();
	private static ComputerServiceImpl computerServiceImpl;
	private  ComputerServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static ComputerServiceImpl getInstance() {
		if(computerServiceImpl==null)
			computerServiceImpl= new ComputerServiceImpl();
		return computerServiceImpl;
	}

	@Override
	public List<Computer> getAllComputers() throws Exception {
		List<Computer> computers = computerDaoImpl.getAllComputers();

		if (computers == null || computers.isEmpty()) {
			throw new Exception("There is no computer in the table");
		}
		return computers;
	}

	@Override
	public List<Company> getAllCompanies() throws Exception {
		List<Company> companies = computerDaoImpl.getAllyCompanies();
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

		try {
			if ((computer.getDiscountedDate().compareTo(computer.getIntroducedDate()) <= 0)) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			computerDaoImpl.updateComputer(computer);
		} catch (NotFoundCompanyException exc) {
			System.out.println("The company doesn't exist");
		} catch (DatesNotValidException exc) {
			System.out.println("Discounted date must be greater than introduced date");
		}

	}

	@Override
	public void createComputer(Computer computer) throws Exception {
		LocalDate ds=computer.getDiscountedDate();
		LocalDate di=computer.getIntroducedDate();
		try {
			if (datesExisted(ds,di) && ds.compareTo(di) <= 0) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			computerDaoImpl.createComputer(computer);
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
	public boolean idCompanyExisted(int id) throws SQLException {
		Company company = computerDaoImpl.getCompanyById(id);
		return company != null;
	}

	@Override
	public List<Computer> getComputersByPage(Page page) throws SQLException {
		List<Computer> computers=null;
		try {
			if(page.getPageNumber() > getNumberOfPages()) {
				throw new PageNotFoundException("You have exced the max number of pages");
			}
			computers = computerDaoImpl.getComputersByPageNumber(page.getPageNumber());
		} catch (PageNotFoundException e) {
			System.out.println("You have exced the max number of pages");
		}
		return computers;
	}

	@Override
	public int getNumberOfPages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteComputer(int idComputer) throws Exception {
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Computer  computer=computerDao.getComputerDetails(idComputer);
		try {
			if(computer == null) {
				throw new ComputerToDeleteNotFound("This computer doesn't exist :");
			}
			computerDao.deleteComputer(idComputer);
		}catch(ComputerToDeleteNotFound ex) {
			System.out.println("This computer doesn't exist :");
		}
		
		
	}

	@Override
	public boolean datesExisted(LocalDate d1, LocalDate d2) {
		
		return ((d1!= null)&&(d2!=null));
	}

}
