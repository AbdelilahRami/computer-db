package fr.excilys.db.mapper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;

public class PageMappper {
	public static List<Computer> getComputersByPageNumberMapper(ResultSet rs, Connection conn) throws SQLException {
		ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.getInstance();
		List<Computer> computers = new ArrayList<Computer>();
		Computer computer;
		while (rs.next()) {
			int idComputer = rs.getInt("id");
			String name = rs.getString("name");
			Date introducedDate = rs.getDate("introduced");
			Date discountedDate = rs.getDate("discontinued");
			LocalDate introduced = DatesConversion.convertDatetoLocalDate(introducedDate, rs, "introduced");
			LocalDate discontinued = DatesConversion.convertDatetoLocalDate(discountedDate, rs, "discontinued");
			int idCompany = rs.getInt("company_id");
			Company company = computerDaoImpl.getCompanyById(idCompany,conn);
			computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
					.setIntroducedDate(introduced).setDiscountedDate(discontinued).setCompany(company).build();
			computers.add(computer);
		}
		return computers;
	}
}
