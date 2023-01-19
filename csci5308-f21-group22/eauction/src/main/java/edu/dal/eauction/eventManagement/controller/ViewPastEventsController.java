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
public class ViewPastEventsController {

	private static final Logger LOG = LogManager.getLogger();
	IEventManagementService ems = new EventManagementService();

	public ViewPastEventsController() {
		super();
	}

	@RequestMapping(value = { "/pastEvents" })
	public String getAllEventByUser(@ModelAttribute(name = "getPastEvents") Event getPastEvents, Model model) {
		LOG.info("Inside view past events controller");
		List<Event> eventList = ems.readPastEvents(getPastEvents);
		model.addAttribute("eventList", eventList);
		return "pastEvents";
	}
}
