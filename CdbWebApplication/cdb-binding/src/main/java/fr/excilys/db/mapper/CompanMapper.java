package fr.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.excilys.db.dto.CompanyDto;
import fr.excilys.db.model.Company;
@Component
public class CompanMapper implements RowMapper<Company> {

	
	public  CompanyDto fromObjectToString(Company company) {
		String name = company.getName();
		String idCompany=Integer.toString(company.getIdCompany());
		CompanyDto companyDto=new CompanyDto(idCompany, name);	
		return companyDto;
	}
	public List<CompanyDto> fromListObjectsToListString(List<Company> companies){
		List<CompanyDto> companiesDto= new ArrayList<CompanyDto>();
		for(fr.excilys.db.model.Company company: companies) {
			CompanyDto companyDto=fromObjectToString(company);
			companiesDto.add(companyDto);
		}
		return companiesDto;
	}
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		int idComapny = rs.getInt("id");
		String nameCompany = rs.getString("name");
		Company company = new Company(idComapny, nameCompany);
		return company;
	}

}
