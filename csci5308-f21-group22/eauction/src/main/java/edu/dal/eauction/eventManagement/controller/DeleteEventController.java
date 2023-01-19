package edu.dal.eauction.eventManagement.controller;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.dal.eauction.eventManagement.dao.EventDao;
import edu.dal.eauction.eventManagement.dao.EventDaoImpl;

@Controller
public class DeleteEventController {

	private static final Logger LOG = LogManager.getLogger();

	@RequestMapping(value = "/deleteEvent/{id}", method = RequestMethod.GET)
	public String deleteEvent(@PathVariable Integer id) throws SQLException {
		LOG.info("Inside Delete Controller");
		EventDao edi = new EventDaoImpl();
		edi.delete(id);
		return "viewEvents";
	}
}
