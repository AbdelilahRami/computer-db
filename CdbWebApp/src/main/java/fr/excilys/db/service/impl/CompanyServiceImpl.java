package fr.excilys.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.db.daoImp.CompanyDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.service.CompanyService;
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyDaoImpl companyDaoImpl;
	
	@Override
	public Company getCompanyById(int idCompany) {
		Company companies = companyDaoImpl.getCompanyById(idCompany);
		return companies;
	}

	@Override
	public List<Company> getAllCompanies() {
		List<Company> companies = companyDaoImpl.getAllyCompanies();
		return companies;
	}

}
