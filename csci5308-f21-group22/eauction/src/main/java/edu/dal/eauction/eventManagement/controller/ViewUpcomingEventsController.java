package edu.dal.eauction.eventManagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.dal.eauction.eventManagement.entities.Event;
import edu.dal.eauction.eventManagement.service.EventManagementService;
import edu.dal.eauction.eventManagement.service.IEventManagementService;

@Controller
public class ViewUpcomingEventsController {

	private static final Logger LOG = LogManager.getLogger();
	IEventManagementService ems = new EventManagementService();

	public ViewUpcomingEventsController() {
		super();
	}

	@RequestMapping(value = { "/upcomingEvents" })
	public String getAllEventByUser(@ModelAttribute(name = "getPastEvents") Event getPastEvents, Model model) {
		LOG.info("Inside view upcoming events controller");
		List<Event> eventList = ems.readUpcomingEvents(getPastEvents);
		model.addAttribute("eventList", eventList);
		return "pastEvents";
	}
}
