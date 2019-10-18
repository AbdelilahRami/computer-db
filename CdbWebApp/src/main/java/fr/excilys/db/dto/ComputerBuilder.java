package fr.excilys.db.dto;

public class ComputerBuilder {

	private String id;
	private String name;
	private String localDateIntro;
	private String localdateDiscontinued;
	private String company;
	
	public static ComputerBuilder newInstance() {
		return  new ComputerBuilder();
	}
	public ComputerBuilder setId(String id) {
		this.id = id;
		return this;
	}
	public Computer build() {
		return new Computer(this);
	}

	public ComputerBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ComputerBuilder setLocalDateIntro(String localDateIntro) {
		this.localDateIntro = localDateIntro;
		return this;
	}

	public ComputerBuilder setLocaldateDiscontinued(String localdateDiscontinued) {
		this.localdateDiscontinued = localdateDiscontinued;
		return this;
	}

	public ComputerBuilder setCompany(String company) {
		this.company = company;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocalDateIntro() {
		return localDateIntro;
	}

	public String getLocaldateDiscontinued() {
		return localdateDiscontinued;
	}

	public String getCompany() {
		return company;
	}
	
	
	
}
