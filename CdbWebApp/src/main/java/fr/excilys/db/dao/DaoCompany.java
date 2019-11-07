package fr.excilys.db.dao;

import java.util.List;

import fr.excilys.db.model.Company;

public interface DaoCompany {
	public  List<Company> getAllyCompanies();
	public Company getCompanyById(int idCompany);
}
