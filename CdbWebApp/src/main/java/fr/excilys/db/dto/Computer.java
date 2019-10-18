package fr.excilys.db.dto;

public class Computer {
	private String id;
	private String name;
	private String localDateIntroduction;
	private String localDateDiscontinued;
	private String company ;
	
	public Computer(ComputerBuilder builder) {
		
		this.id=builder.getId();
		this.name=builder.getName();
		this.localDateIntroduction=builder.getLocalDateIntro();
		this.localDateDiscontinued=builder.getLocaldateDiscontinued();
		this.company=builder.getCompany();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocalDateIntroduction() {
		return localDateIntroduction;
	}

	public String getLocalDateDiscontinued() {
		return localDateDiscontinued;
	}

	public String getCompany() {
		return company;
	}
	
	
	
}
