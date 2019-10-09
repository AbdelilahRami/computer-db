package com.db.model;

import java.time.LocalDate;
import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discountedDate;
	private Company company;

	public Computer() {
		super();
	}

	public Computer(int id, String name, LocalDate introducedDate, LocalDate discountedDate, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discountedDate = discountedDate;
		this.company = company;
	}
	public Computer(String name, LocalDate introducedDate, LocalDate discountedDate, Company company) {
		super();
		this.name = name;
		this.introducedDate = introducedDate;
		this.discountedDate = discountedDate;
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discountedDate="
				+ discountedDate + ", idCompany=" + company + "]";
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

}