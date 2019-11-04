package fr.excilys.db.mapper;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.CompanyBuilder;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
@Component
public class ComputerMapper {

	public List<Computer> getAllComputerMapper(ResultSet rs) {
		List<Computer> computers = new ArrayList<Computer>();
		try {
			while (rs.next()) {
				int id = rs.getInt("computer.id");
				String name = rs.getString("computer.name");
				Date introducedDate = rs.getDate("computer.introduced");
				Date discountedDate = rs.getDate("computer.discontinued");
				LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
				LocalDate discounted = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "discontinued");
				int idCompany = rs.getInt("computer.company_id");
				String companyName=rs.getString("company.name");
				Company company = CompanyBuilder.newInstance().setIdCompany(idCompany).setNameCompany(companyName).build();
				Computer computer = ComputerBuilder.newInstance().setId(id).setName(name).setIntroducedDate(introduced)
						.setDiscountedDate(discounted).setCompany(company).build();
				computers.add(computer);
			}
		} catch (Exception e) {
 		}
		return computers;
	}

	public List<Company> getAllCompaniesMapper(ResultSet rs) {
		List<Company> companies = new ArrayList<Company>();
		try {
			while (rs.next()) {
				int idComapny = rs.getInt("id");
				String nameCompany = rs.getString("name");
				Company company = new Company(idComapny, nameCompany);
				companies.add(company);
			}
		} catch (Exception e) {
		}
		return companies;
	}

	public Computer getComputerDetailsMapper(ResultSet rs) throws SQLException {
		Computer computer = null;
		while (rs.next()) {
			int idComputer = rs.getInt("computer.id");
			String name = rs.getString("computer.name");
			Date introducedDate = rs.getDate("computer.introduced");
			Date discountedDate = rs.getDate("computer.discontinued");
			LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
			LocalDate discounted = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "discontinued");
			int idCompany = rs.getInt("computer.company_id");
			String companyName=rs.getString("company.name");
			Company company = CompanyBuilder.newInstance().setIdCompany(idCompany).setNameCompany(companyName).build();
			computer = ComputerBuilder.newInstance().setId(idComputer).setName(name).setIntroducedDate(introduced)
					.setDiscountedDate(discounted).setCompany(company).build();
		}
		return computer;
	}

}
