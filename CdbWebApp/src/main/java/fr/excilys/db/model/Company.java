package fr.excilys.db.model;
public class Company {
	private int idCompany;
	private String name;
	public Company(CompanyBuilder companyBuilder) {
			this.idCompany=companyBuilder.getIdCompany();
			this.name=companyBuilder.getNameCompany();
	}
	public int getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Company [id=" + idCompany + ", name=" + name +  "]";
	}
	
}
