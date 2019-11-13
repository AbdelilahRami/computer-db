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
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.dto.CompanyDto;
import fr.excilys.db.dto.ComputerDto;
import fr.excilys.db.exception.DatesNotValidException;
import fr.excilys.db.mapper.CompanMapper;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.impl.CompanyServiceImpl;
import fr.excilys.db.service.impl.ComputerServiceImpl;

@Controller
public class AddingController {
	@Autowired
	private ComputerServiceImpl computerServiceImpl;
	@Autowired
	private CompanyServiceImpl companyServiceImpl;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private CompanMapper companyMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@GetMapping("/showAddingForm")
	public String showAddingForm(Model model) {
		List<Company> companies=companyServiceImpl.getAllCompanies();
		List<CompanyDto> companiesDto=companyMapper.fromListObjectsToListString(companies);
		model.addAttribute("companies",companiesDto);
		ComputerDto computerDTO= new ComputerDto();
		model.addAttribute("computer", computerDTO);
		return "addComputer";
	}

	@PostMapping("/addComputer")
	public String addComputer(@ModelAttribute("computer") ComputerDto computerDto) {
		Computer computer=null;
			try {
				computer = computerMapper.fromStringToObject(computerDto);
				computerServiceImpl.createComputer(computer);
			} catch (DateTimeParseException e) {
				LOGGER.error("the input is not a date"+e.getMessage());
			} catch (DatesNotValidException e) {
				LOGGER.error("discontinued must be gretaer than introduced"+e.getMessage());
			}
		
				
		return "redirect:/getAllComputersByPage";		
	}
}
