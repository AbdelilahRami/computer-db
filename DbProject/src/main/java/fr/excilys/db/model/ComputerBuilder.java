package fr.excilys.db.model;

import java.time.LocalDate;

public class ComputerBuilder {
	private int id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discountedDate;
	private Company company;
	
	public static  ComputerBuilder newInstance() {
		return new ComputerBuilder();
	}
	
	private ComputerBuilder() {
	}
	public Computer build() {
		
		return new Computer(this);
	}

	public int getId() {
		return id;
	}

	public ComputerBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ComputerBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public ComputerBuilder setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
		return this;
	}

	public LocalDate getDiscountedDate() {
		return discountedDate;
	}

	public ComputerBuilder setDiscountedDate(LocalDate discountedDate) {
		this.discountedDate = discountedDate;
		return this;
	}

	public Company getCompany() {
		return company;
	}

	public ComputerBuilder setCompany(Company company) {
		this.company = company;
		return this;
	}
	
	
}
