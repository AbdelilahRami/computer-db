package fr.excilys.db.model;

import java.time.LocalDate;

public class Computer {
	private int id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discountedDate;
	private Company company;

	public Computer(ComputerBuilder computerBuilder) {
		this.id = computerBuilder.getId();
		this.name = computerBuilder.getName();
		this.introducedDate = computerBuilder.getIntroducedDate();
		this.discountedDate = computerBuilder.getDiscountedDate();
		this.company = computerBuilder.getCompany();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}

	public LocalDate getDiscountedDate() {
		return discountedDate;
	}

	public void setDiscountedDate(LocalDate discountedDate) {
		this.discountedDate = discountedDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setIdCompany(Company company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discountedDate="
				+ discountedDate + ", idCompany=" + company + "]";
	}

}