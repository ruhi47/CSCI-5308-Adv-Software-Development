package edu.dal.eauction.eventManagement.service;

import java.util.List;

import edu.dal.eauction.eventManagement.entities.Event;
import edu.dal.eauction.eventManagement.entities.EventEnrolment;
import edu.dal.eauction.userManagement.exception.UserAlreadyExistsException;

public interface IEventManagementService {

	boolean createEvent(Event event) throws UserAlreadyExistsException;

	EventEnrolment readEnrolment(Event event);

	List<Event> readPastEvents(Event event);

	List<Event> readSubscribedEvents(String username);

	List<Event> readUpcomingEvents(Event event);

	void SendEmail(Integer itemId);

}
