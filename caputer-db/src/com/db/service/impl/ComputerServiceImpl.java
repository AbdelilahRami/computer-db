package com.db.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.db.daoImp.ComputerDaoImpl;
import com.db.exception.ComputerToDeleteNotFound;
import com.db.exception.DatesNotValidException;
import com.db.exception.NoCompanyFound;
import com.db.exception.NoComputerFound;
import com.db.exception.NotFoundCompanyException;
import com.db.exception.PageNotFoundException;
import com.db.model.Company;
import com.db.model.Computer;
import com.db.model.Page;
import com.db.service.ComputeService;


public class ComputerServiceImpl implements ComputeService  {
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
	public List<Computer> getAllComputers() throws NoComputerFound, SQLException {
		List<Computer> computers = computerDaoImpl.getAllComputers();

		if (computers == null || computers.isEmpty()) {
			throw new NoComputerFound("There is no computer in the table");
		}
		return computers;
	}

	@Override
	public List<Company> getAllCompanies() throws NoCompanyFound, SQLException {
		List<Company> companies = computerDaoImpl.getAllyCompanies();
	
			if (companies == null || companies.isEmpty()) {
				throw new NoCompanyFound("There is no company in the table");
			}
		

		return companies;

	}

	@Override
	public int updateComputer(Computer computer) throws DatesNotValidException, NotFoundCompanyException, SQLException {
		LocalDate ds=computer.getDiscountedDate();
		LocalDate di=computer.getIntroducedDate();
		
			if ((datesExisted(ds,di) && computer.getDiscountedDate().compareTo(computer.getIntroducedDate()) <= 0)) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			int value=computerDaoImpl.updateComputer(computer);
		
				return value;

	}

	@Override
	public int createComputer(Computer computer) throws DatesNotValidException,  NotFoundCompanyException, SQLException{
		LocalDate ds=computer.getDiscountedDate();
		LocalDate di=computer.getIntroducedDate();
	
			if (datesExisted(ds,di) && ds.compareTo(di) <= 0) {
				throw new DatesNotValidException("Discounted date must be greater than introduced date");
			} else if (computer.getCompany() == null) {
				throw new NotFoundCompanyException("The company doesn't exist");
			}
			int k=computerDaoImpl.createComputer(computer);
		 
			return k;
	}

	@Override
	public boolean idCompanyExisted(int id) throws SQLException {
		Company company = computerDaoImpl.getCompanyById(id);
		return company != null;
	}

	@Override
	public List<Computer> getComputersByPage(Page page) throws SQLException, PageNotFoundException {
		List<Computer> computers=null;
			if(page.getPageNumber() > getNumberOfPages()) {
				throw new PageNotFoundException("You have exced the max number of pages");
			}
			computers = computerDaoImpl.getComputersByPageNumber(page.getPageNumber());
		
		return computers;
	}

	@Override
	public int getNumberOfPages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteComputer(int idComputer) throws ComputerToDeleteNotFound, SQLException  {
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Computer  computer=computerDao.getComputerDetails(idComputer);
		
			if(computer == null) {
				throw new ComputerToDeleteNotFound("This computer doesn't exist :");
			}
			int k=computerDao.deleteComputer(idComputer);
		
		return k;
		
	}

	@Override
	public boolean datesExisted(LocalDate d1, LocalDate d2) {
		
		return ((d1!= null)&&(d2!=null));
	}

	

}
