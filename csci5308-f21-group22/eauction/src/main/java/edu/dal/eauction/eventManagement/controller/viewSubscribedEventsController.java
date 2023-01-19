package edu.dal.eauction.eventManagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.dal.eauction.eventManagement.entities.Event;
import edu.dal.eauction.eventManagement.service.EventManagementService;
import edu.dal.eauction.eventManagement.service.IEventManagementService;

@Controller
public class viewSubscribedEventsController {

	private static final Logger LOG = LogManager.getLogger();
	IEventManagementService ems = new EventManagementService();

	@RequestMapping(value = { "/subscribedEvents" })
	public String getAllEventByUser(Model model) {
		LOG.info("Inside view subscribed events controller");
		// getting username
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		// Fetchig list of events subscribed by the user
		List<Event> eventList = ems.readSubscribedEvents(username);
		model.addAttribute("eventList", eventList);
		return "subscribedEvents";
	}

}
