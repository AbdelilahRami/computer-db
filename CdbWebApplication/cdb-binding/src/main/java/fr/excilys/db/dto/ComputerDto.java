package fr.excilys.db.dto;

public class ComputerDto {
	private String id;
	private String name;
	private String localDateIntroduction;
	private String localDateDiscontinued;
	private String idCompany ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocalDateIntroduction() {
		return localDateIntroduction;
	}
	public void setLocalDateIntroduction(String localDateIntroduction) {
		this.localDateIntroduction = localDateIntroduction;
	}
	public String getLocalDateDiscontinued() {
		return localDateDiscontinued;
	}
	public void setLocalDateDiscontinued(String localDateDiscontinued) {
		this.localDateDiscontinued = localDateDiscontinued;
	}
	public String getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(String idCompany) {
		this.idCompany = idCompany;
	}
	
}
