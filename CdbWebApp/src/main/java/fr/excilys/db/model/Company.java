package fr.excilys.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int idCompany;
	@Column(name = "name")
	private String name;

	public Company() {
		super();
	}

	public Company(int idCompany, String name) {
		this.idCompany = idCompany;
		this.name = name;
	}

	public Company(CompanyBuilder companyBuilder) {
		this.idCompany = companyBuilder.getIdCompany();
		this.name = companyBuilder.getNameCompany();
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
		return "Company [id=" + idCompany + ", name=" + name + "]";
	}
}
