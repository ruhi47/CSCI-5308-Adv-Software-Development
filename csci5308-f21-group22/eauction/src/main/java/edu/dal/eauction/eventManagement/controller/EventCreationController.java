package edu.dal.eauction.eventManagement.controller;

import java.beans.PropertyEditorSupport;
import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.dal.eauction.eventManagement.dao.EventDao;
import edu.dal.eauction.eventManagement.dao.EventDaoImpl;
import edu.dal.eauction.eventManagement.dto.EventCreationDto;
import edu.dal.eauction.eventManagement.entities.Event;
import edu.dal.eauction.eventManagement.service.EventManagementService;
import edu.dal.eauction.eventManagement.service.IEventManagementService;
import edu.dal.eauction.eventManagement.validations.EventCreationDtoValidator;
import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.dao.ItemDaoImplementation;
import edu.dal.eauction.itemManagement.entities.Item;

@Controller
public class EventCreationController {

	private static final Logger LOG = LogManager.getLogger();
	public static final String FORMAT_TYPE = "yyyy-MM-dd'T'HH:mm";
	private IEventManagementService eventManagementService = new EventManagementService();
	private int ITEM_ID = 0;

	public EventCreationController() {
		super();
	}

	@ControllerAdvice
	public class ControllerAdviceInitBinder {

		private class Editor<T> extends PropertyEditorSupport {
			private final Function<String, T> parser;
			private final Format format;

			public Editor(Function<String, T> parser, Format format) {
				this.parser = parser;
				this.format = format;
			}

			public void setAsText(String text) {
				setValue(this.parser.apply(text));
			}

			public String getAsText() {
				return format.format((T) getValue());
			}
		}

		@InitBinder
		public void initBinder(WebDataBinder webDataBinder) {
			webDataBinder.registerCustomEditor(LocalDateTime.class,
					new Editor<>(text -> LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
							DateTimeFormatter.ISO_LOCAL_DATE_TIME.toFormat()));
		}
	}

	@GetMapping(value = { "/createEvent/{id}" })
	public String goToEventsPage(@PathVariable int id) {
		this.ITEM_ID = id;
		LOG.info("Routing to event Creation Page");
		return "createEvent";
	}

	@PostMapping("/event/create/")
	public String eventCreation(@ModelAttribute("newEvent") EventCreationDto newEvent,
			Model model) {
		LOG.info("Creating event : " + newEvent.toString());
		EventCreationDtoValidator ecdv = new EventCreationDtoValidator();
		EventDao edi = new EventDaoImpl();
		String template = null;
		// Validating the fields entered by the user
		List<String> errors = ecdv.validateStartTime(newEvent.getStartTime());
		errors.addAll(ecdv.validateEndTime(newEvent.getEndTime()));
		if (errors.size() == 0) {
			Event event = edi.readByItemId(this.ITEM_ID);
			if (event == null) {
				Event eventToInsert = new Event(newEvent);
				eventToInsert.setItemId(this.ITEM_ID);
				// get item by passing id
				ItemDao idi = new ItemDaoImplementation();
				Item item = idi.read(this.ITEM_ID);
				// pass minimumAmount from item to eventToInsert
				eventToInsert.setMinAmount(item.getMinPrice());				
				template = processEventCreation(eventToInsert, model);;
			}
		} else {
			model.addAttribute("message", errors);
			template = "createEvent";
		}
		return template;
	}

	private String processEventCreation(Event event, Model model) {
		try {
			eventManagementService.createEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "Event created successfully");
		return "createEvent";
	}

}
