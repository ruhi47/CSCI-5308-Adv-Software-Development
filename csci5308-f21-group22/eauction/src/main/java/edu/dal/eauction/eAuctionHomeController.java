package edu.dal.eauction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class eAuctionHomeController {

	private static final String GENERIC_APPLICATION_ERROR = "An error has occured, please login back!";
	private static final Logger log = LogManager.getLogger();

	@GetMapping(value = { "/eAuctionHome", "/" })
	public String goToHomePage(Model model) {
		return "index";
	}

	@GetMapping(value = { "/error" })
	public String error(Model model) {
		log.error("An error has occured");
		model.addAttribute("message", GENERIC_APPLICATION_ERROR);
		return "error";
	}

}
