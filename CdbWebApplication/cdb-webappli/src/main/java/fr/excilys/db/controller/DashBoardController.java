package fr.excilys.db.controller;

import java.time.format.DateTimeParseException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.excilys.db.dto.ComputerDto;
import fr.excilys.db.exception.ComputerToDeleteNotFound;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.CompanyServiceImpl;
import fr.excilys.db.service.ComputerServiceImpl;

@RestController
@RequestMapping("/api")
public class DashBoardController {
	@Autowired
	private ComputerServiceImpl computerService;
	@Autowired
	private CompanyServiceImpl companyService;
	@Autowired
	private ComputerMapper computerMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(DashBoardController.class);

	@GetMapping("/computers")
	public List<ComputerDto> getAllComputers() {
		return computerService.getAllComputers();
	}
	
	
	@GetMapping("/computers/{pageNumber}/{pageSize}")
	public List<ComputerDto> getCOmputersByPage(@PathVariable("pageNumber")int pageNumber,
			@PathVariable("pageSize") int pageSize){
		return computerService.getComputersByPage(pageNumber, pageSize);
	}
	
	
	@GetMapping("/computers/{idComputer}")
	public Computer getById(@PathVariable("idComputer") int idComputer) {
		Computer computer = computerService.getComputerDetails(idComputer);
		return computer;
	}

	@PostMapping("/computers")
	public ComputerDto addComputer(@RequestBody ComputerDto computerdto) {
		computerService.createComputer(computerdto);
		return computerdto;
	}

	@PutMapping("/computers")
	public Computer updateComputer(@RequestBody ComputerDto computerDto) {
		Computer computer = null;
		try {
			computer = computerMapper.mapToComputerForUpdate(computerDto);
			computerService.updateComputer(computer);
		} catch (DateTimeParseException e) {
			LOGGER.error("date parising invalid");
		}
		return computer;
	}

	@DeleteMapping("/computers/{computerId}")
	public String deleteComputer(@PathVariable("computerId") int computerId) throws ComputerToDeleteNotFound {
		Computer computer = computerService.getComputerDetails(computerId);
		if (computer == null) {
			throw new ComputerToDeleteNotFound("Computer id not found " + computerId);
		}
		computerService.deleteComputer(computerId);

		return "Deleted computer Id" + computerId;
	}

	@GetMapping("/companies")
	public List<Company> getAllCompanies() {
		return companyService.getAllCompanies();
	}

}
