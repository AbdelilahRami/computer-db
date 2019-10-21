package fr.excilys.db.model;

public class CompanyBuilder {
	private int idCompany;
	private String nameCompany;
	
	public CompanyBuilder() {
	}
	public static CompanyBuilder newInstance() {
		return new CompanyBuilder();
	}
	public Company build() {
		return new Company(this);
	}
	public int getIdCompany() {
		return idCompany;
	}
	public CompanyBuilder setIdCompany(int idCompany) {
		this.idCompany = idCompany;
		return this;
	}
	public String getNameCompany() {
		return nameCompany;
	}
	public CompanyBuilder setNameCompany(String nameCompany) {
		this.nameCompany = nameCompany;
		return this;
	}
	

}
