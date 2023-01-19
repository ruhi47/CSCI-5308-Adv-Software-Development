package edu.dal.eauction.eventManagement.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.dal.eauction.eventManagement.dao.EventEnrolmentDaoImpl;
import edu.dal.eauction.eventManagement.entities.Event;
import edu.dal.eauction.eventManagement.entities.EventEnrolment;
import edu.dal.eauction.eventManagement.service.EventManagementService;
import edu.dal.eauction.eventManagement.service.IEventManagementService;

@Controller
public class UnsubscribeEventController {

	public UnsubscribeEventController() {
		super();
	}

	private static final Logger LOG = LogManager.getLogger();
	private IEventManagementService ems;
	private EventEnrolmentDaoImpl eedi = new EventEnrolmentDaoImpl();

	@RequestMapping(value = "/deleteSubscription/{id}", method = RequestMethod.GET)
	public String deleteEvent(@ModelAttribute("deleteSubscription") Event deleteSubscription, @PathVariable Integer id,
			Model model) throws SQLException {

		List<String> errors = new ArrayList<String>();
		LOG.info("Inside Delete Controller");
		EventEnrolment enrolment = eedi.readEnrolmentByEventId(id);

		if (enrolment != null) {
			eedi.delete(enrolment.getEnrolmentId());
		} else {
			errors.add("The enrolment does not exist");
			model.addAttribute("message", errors);
		}
		return "subscribedEvents";
	}
}
