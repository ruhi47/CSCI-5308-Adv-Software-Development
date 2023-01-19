package edu.dal.eauction.eventManagement.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import edu.dal.eauction.eventManagement.entities.Event;
import edu.dal.eauction.eventManagement.entities.EventEnrolment;
import edu.dal.eauction.eventManagement.service.IEventManagementService;
import edu.dal.eauction.userManagement.exception.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class EventManagementServiceDaoTest {
	
	@Mock
	private IEventManagementService mockservicedao;
	@Mock
	private Event mockEvent;
	@Mock
	private EventEnrolment mockEnrolment;
	
	@Test
	public void checkCreateEvent() throws UserAlreadyExistsException {
		when(mockservicedao.createEvent(mockEvent)).thenReturn(true);
		assertTrue(mockservicedao.createEvent(mockEvent));
	}
	
	@Test
	public void checkReadEnrolment() {
		when(mockservicedao.readEnrolment(mockEvent)).thenReturn(mockEnrolment);
		assertNotNull(mockservicedao.readEnrolment(mockEvent));
	}
	
	@Test
	public void checkReadPastEvents() {
		List<Event> events = Arrays.asList(mockEvent);
		when(mockservicedao.readPastEvents(mockEvent)).thenReturn(events);
		assertNotNull(mockservicedao.readPastEvents(mockEvent));
	}
	
	@Test
	public void checkReadSubscribedEvents() {
		List<Event> events = Arrays.asList(mockEvent);
		when(mockservicedao.readSubscribedEvents(mockEvent.getCreatedBy())).thenReturn(events);
		assertNotNull(mockservicedao.readSubscribedEvents(mockEvent.getCreatedBy()));
	}
	
	@Test
	public void checkReadUpcomingEvents() {
		List<Event> events = Arrays.asList(mockEvent);
		when(mockservicedao.readUpcomingEvents(mockEvent)).thenReturn(events);
		assertNotNull(mockservicedao.readUpcomingEvents(mockEvent));
	}
	
	
}
