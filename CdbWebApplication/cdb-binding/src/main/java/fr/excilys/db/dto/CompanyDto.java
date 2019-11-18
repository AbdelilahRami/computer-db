package fr.excilys.db.dto;

public class CompanyDto {
	private String idCompany;
	private String name;
	public CompanyDto(String idCompany, String name) {
		super();
		this.idCompany = idCompany;
		this.name = name;
	}
	public String getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(String idCompany) {
		this.idCompany = idCompany;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
	
	
}
