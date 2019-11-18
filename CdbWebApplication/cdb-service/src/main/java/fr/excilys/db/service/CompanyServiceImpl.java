package fr.excilys.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.db.dao.CompanyDaoImpl;
import fr.excilys.db.model.Company;
@Service
public class CompanyServiceImpl  {

	@Autowired
	CompanyDaoImpl companyDaoImpl;
	

	public Company getCompanyById(int idCompany) {
		Company companies = companyDaoImpl.getCompanyById(idCompany);
		return companies;
	}


	public List<Company> getAllCompanies() {
		List<Company> companies = companyDaoImpl.getAllyCompanies();
		return companies;
	}

}
