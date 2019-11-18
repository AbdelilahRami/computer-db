package fr.excilys.db.controller;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.excilys.db.dao.ComputerDaoImpl;
import fr.excilys.db.dto.ComputerDto;
import fr.excilys.db.exception.DatesNotValidException;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.CompanyServiceImpl;
import fr.excilys.db.service.ComputerServiceImpl;
@Controller
public class EditingController {
	@Autowired
	private ComputerServiceImpl computerSrvice;
	@Autowired
	private CompanyServiceImpl companyService;
	@Autowired
	private ComputerMapper computerMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@GetMapping("/showEditForm")
	public String showEditForm(@RequestParam("computerId") Integer computerId, Model model) {
		Computer computer=computerSrvice.getComputerDetails(computerId);
		ComputerDto computerDto=computerMapper.fromObjectToString(computer);
		List<Company> companies=companyService.getAllCompanies();
		model.addAttribute("companies", companies);
		model.addAttribute("computer", computerDto);
		
		return "editComputer";
	}
	@PostMapping("/editComputer")
	public String editComputer(@ModelAttribute("computer") ComputerDto computerDto, @RequestParam("computerId") String computerId) {
		Computer computer;
		try {
			Company company=companyService.getCompanyById(Integer.parseInt(computerDto.getIdCompany()));
			computer = computerMapper.fromStringToObject(computerDto);
			computer.setId(Integer.parseInt(computerId));
			computer.setCompany(company);
			computerSrvice.updateComputer(computer);
		} catch (DateTimeParseException e) {
			LOGGER.error("the input is not a date"+e.getMessage());
			return "500";
		} catch (DatesNotValidException e) {
			LOGGER.error("discontinued must be gretaer than introduced"+e.getMessage());
			return "500";
		}
		return "redirect:/getAllComputersByPage";
	}

}
