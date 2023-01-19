package edu.dal.eauction.eventManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.dal.eauction.eventManagement.dao.EventDao;
import edu.dal.eauction.eventManagement.dao.EventDaoImpl;
import edu.dal.eauction.eventManagement.dao.EventEnrolmentDaoImpl;
import edu.dal.eauction.eventManagement.dao.EventEnrolmentDao;
import edu.dal.eauction.eventManagement.entities.Event;
import edu.dal.eauction.eventManagement.entities.EventEnrolment;
import edu.dal.eauction.eventManagement.service.EventManagementService;
import edu.dal.eauction.eventManagement.service.IEventManagementService;

@Controller
public class EventRegistrationController {

	private static final Logger LOG = LogManager.getLogger();

	@RequestMapping(value = "/eventRegistration/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String eventCreation(EventEnrolment newEnrolment, @PathVariable int id, Model model) {
		String Template = null;
		List<String> errors = new ArrayList<String>();
		EventEnrolmentDao eedi = new EventEnrolmentDaoImpl();
		EventDao edi = new EventDaoImpl();
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		Event eventTosubscribe = edi.readByItemId(id);
		// checking if the user is already registered for the event
		List<EventEnrolment> enrolments = eedi.readByUserEmail(username);
		if(enrolments!=null) {
		for (EventEnrolment enrolment : enrolments) {
			if (enrolment.getItemId() == id) {
				//errors.add("Already registered for the event");
				model.addAttribute("message", "Already registered for the event");
				Template = "eventRegistration";
				}
			}
		}
			if(eventTosubscribe == null) {
				//errors.add("No event available for this item! Please wait for the seller to create an event!");
				model.addAttribute("message", "No event available for this item! Please wait for the seller to create an event!");
				Template = "eventRegistration";
			}else {
				newEnrolment.setItemId(id); // Get from items
				Event event = edi.readByItemId(id); // get event from itemId
				newEnrolment.setEventId(event.getEventId());
				newEnrolment.setUserEmail(username);
				newEnrolment.setActive(true);
				LOG.info("Creating enrolment : " + newEnrolment.toString());
				eedi.create(newEnrolment);
				//IEventManagementService ems = new EventManagementService();
				//ems.SendEmail(id);
				model.addAttribute("message", "Registered for the event! Please check your email");
				Template = "eventRegistration";
			}
		
		return "eventRegistration";
	}
	
}
