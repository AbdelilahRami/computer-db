package fr.excilys.db.mapper;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import fr.excilys.db.dto.ComputerDto;
import fr.excilys.db.exception.DatesNotValidException;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.CompanyBuilder;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.validators.DateValidator;
@Component
public class ComputerMapper implements RowMapper<Computer>{

	@Autowired
	private DateValidator dateValidator;

	public  ComputerDto fromObjectToString(Computer computer) {
		ComputerDto computerDto= new ComputerDto();
		computerDto.setId(String.valueOf(computer.getId()));
		computerDto.setName(computer.getName());
		computerDto.setLocalDateIntroduction(computer.getIntroducedDate() == null ? "" : computer.getIntroducedDate().toString());
		computerDto.setLocalDateDiscontinued(computer.getDiscountedDate() == null ? "" : computer.getDiscountedDate().toString());
		String companyName=computer.getCompany()==null ? "":String.valueOf(computer.getCompany().getName());
		computerDto.setIdCompany(companyName);
		return computerDto;
	}
	public  Computer fromStringToObject(ComputerDto computer) throws DatesNotValidException, DateTimeParseException{
		String name=computer.getName();
		LocalDate lci=null,lcd=null;
		lci = dateValidator.fromStringToLocalDate(computer.getLocalDateIntroduction());
		lcd = dateValidator.fromStringToLocalDate(computer.getLocalDateDiscontinued());
		dateValidator.datesAreValid(lci, lcd);
		System.out.println(computer.getIdCompany());
		Company company = (!computer.getIdCompany().isEmpty()) ? CompanyBuilder.newInstance().setIdCompany(Integer.parseInt(computer.getIdCompany())).build() : null;
		Computer objComputer=ComputerBuilder.newInstance().setName(name) 
										.setIntroducedDate(lci).setDiscountedDate(lcd).setCompany(company).build();
		return objComputer;
	}
	
	public  List<ComputerDto> fromListObjecToListString(List<Computer> list){	
		List<ComputerDto> computers= new ArrayList<ComputerDto>();
		for(Computer computer : list) {
			computers.add(fromObjectToString(computer));
		}		
		return computers;
	}

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		int id = rs.getInt("computer.id");
		String name = rs.getString("computer.name");
		Date introducedDate = rs.getDate("computer.introduced");
		Date discountedDate = rs.getDate("computer.discontinued");
		LocalDate introduced = dateValidator.convertDatetoLocalDate(introducedDate, rs, "introduced");
		LocalDate discounted = dateValidator.convertDatetoLocalDate(discountedDate, rs, "discontinued");
		int idCompany = rs.getInt("computer.company_id");
		String companyName=rs.getString("company.name");
		Company company = CompanyBuilder.newInstance().setIdCompany(idCompany).setNameCompany(companyName).build();
		Computer computer = ComputerBuilder.newInstance().setId(id).setName(name).setIntroducedDate(introduced)
				.setDiscountedDate(discounted).setCompany(company).build();
		return computer;
	}

}
