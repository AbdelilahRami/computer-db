package fr.excilys.db.dto;
import java.util.ArrayList;
import java.util.List;
public class CompanyMapper {
	
	public static Company fromObjectToString(fr.excilys.db.model.Company company) {
		String name = company.getName();
		String idCompany=Integer.toString(company.getId());
		Company companyDto=new Company(idCompany, name);	
		return companyDto;
	}
	public static List<Company> fromListObjectsToListString(List<fr.excilys.db.model.Company> companies){
		List<Company> companiesDto= new ArrayList<Company>();
		for(fr.excilys.db.model.Company company: companies) {
			Company companyDto=CompanyMapper.fromObjectToString(company);
			companiesDto.add(companyDto);
		}
		return companiesDto;
	}
}
