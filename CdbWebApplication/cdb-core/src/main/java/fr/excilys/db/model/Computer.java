package fr.excilys.db.model;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="computer")
public class Computer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "introduced")
	private LocalDate introducedDate;
	@Column(name = "discontinued")
	private LocalDate discountedDate;
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	public Computer() {
		super();
	}
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
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discountedDate="
				+ discountedDate + ", idCompany=" + company + "]";
	}
}