package fr.excilys.db.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.CompanyBuilder;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.validators.DateValidator;
@Component
public class PageMappper {
	@Autowired
	DateValidator dateValidator;
	public List<Computer> getComputersByPageNumberMapper(ResultSet rs) throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();
		Computer computer;
		while (rs.next()) {
			int idComputer = rs.getInt("id");
			String name = rs.getString("name");
			Date introducedDate = rs.getDate("introduced");
			Date discountedDate = rs.getDate("discontinued");
			LocalDate introduced = dateValidator.convertDatetoLocalDate(introducedDate, rs, "introduced");
			LocalDate discontinued = dateValidator.convertDatetoLocalDate(discountedDate, rs, "discontinued");
			int idCompany = rs.getInt("computer.company_id");
			String companyName=rs.getString("company.name");
			
			Company company = CompanyBuilder.newInstance().setIdCompany(idCompany).setNameCompany(companyName).build();
			computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
					.setIntroducedDate(introduced).setDiscountedDate(discontinued).setCompany(company).build();
			computers.add(computer);
		}
		return computers;
	}
}
