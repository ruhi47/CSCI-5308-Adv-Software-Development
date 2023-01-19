package edu.dal.eauction.eventManagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.dal.eauction.eventManagement.dao.EventDao;
import edu.dal.eauction.eventManagement.dao.EventDaoImpl;
import edu.dal.eauction.eventManagement.entities.Event;

@Controller
public class viewEventsController {

	private static final Logger LOG = LogManager.getLogger();

	@RequestMapping(value = { "/viewEvents" })
	public String getAllEventByUser(@ModelAttribute(name = "getEvents") Event getEvents, Model model) {
		LOG.info("Inside view events controller");
		// getting username
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		EventDao edi = new EventDaoImpl();
		List<Event> eventList = edi.read(username);
		model.addAttribute("eventList", eventList);
		return "viewEvents";
	}
}
