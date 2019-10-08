package com.db.model;

import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Date introducedDate;
	private Date discountedDate;
	private int idCompany;

	public Computer() {
		super();
	}

	public Computer(int id, String name, Date introducedDate, Date discountedDate, int idCompany) {
		super();
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discountedDate = discountedDate;
		this.idCompany = idCompany;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discountedDate="
				+ discountedDate + ", idCompany=" + idCompany + "]";
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

	public Date getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(Date introducedDate) {
		this.introducedDate = introducedDate;
	}

	public Date getDiscountedDate() {
		return discountedDate;
	}

	public void setDiscountedDate(Date discountedDate) {
		this.discountedDate = discountedDate;
	}

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

}
