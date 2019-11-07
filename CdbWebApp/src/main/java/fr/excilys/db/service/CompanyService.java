package fr.excilys.db.service;

import java.util.List;

import fr.excilys.db.model.Company;

public interface CompanyService {
	 public Company getCompanyById(int idCompany);
	 public List<Company> getAllCompanies();

}
