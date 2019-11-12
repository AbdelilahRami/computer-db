package fr.excilys.db.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.excilys.db.dto.ComputerDto;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.impl.ComputerServiceImpl;

@Controller()
public class DashBoardController {
	@Autowired
	private ComputerServiceImpl computerService;
	@Autowired
	private ComputerMapper computerMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(DashBoardController.class);

	@RequestMapping("/getAllComputersByPage")
	public String getComputers(@RequestParam(required = false, defaultValue = "") String search,
			@RequestParam(required = false, defaultValue = "") String order,
			@RequestParam(required = false, defaultValue = "1") Integer beginPage,
			@RequestParam(required = false, defaultValue = "5") Integer endPage,
			@RequestParam(required = false, defaultValue = "10") Integer size, Model model) {
		List<ComputerDto> computersDto = null;
		int numberOfpages = 0;
		if (!order.isEmpty()) {
			numberOfpages = computerService.getNumberOfPages(size);
			computersDto = computerMapper
					.fromListObjecToListString(computerService.getComputersByOrder(order, size, beginPage));
		} else if (search != null && !search.isEmpty()) {
			numberOfpages = computerService.getPagesNumberByName(size, search);
			List<Computer> computers = computerService.getComputersByName(search, size, beginPage);
			computersDto = computerMapper.fromListObjecToListString(computers);
			while (endPage > numberOfpages) {
				endPage--;
			}
		} else {
			computersDto = computerMapper
					.fromListObjecToListString(computerService.getComputersByPage(beginPage, size));
		}
		model.addAttribute("computers", computersDto);
		model.addAttribute("numberOfpages", numberOfpages);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("size", size);
		model.addAttribute("endPage", endPage);
		return "dashboard";
	}
	@PostMapping("/getAllComputersByPage")
	public String deleteComputers(HttpServletRequest request, HttpServletResponse response) {
		String computersToDelete= request.getParameter("selection");
		String[] listComputers=computersToDelete.split(",");
		for(String idComputer :listComputers) {
			try {
				int id=Integer.parseInt(idComputer);
				computerService.deleteComputer(id);
			}catch(NumberFormatException e){
				LOGGER.error("your input is not a number"+e.getMessage());
				return "500";
			}
		}
		return "redirect:/getAllComputersByPage";
	}

}
