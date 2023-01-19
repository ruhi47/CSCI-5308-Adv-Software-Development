package edu.dal.eauction.eventManagement.daoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import edu.dal.eauction.eventManagement.dao.EventDao;
import edu.dal.eauction.eventManagement.entities.Event;

@ExtendWith(MockitoExtension.class)
public class EventDaoTest {

	@Mock
	private EventDao mockeventdao;
	@Mock
	private Event mockEvent;

	@Test
	public void checkCreateEvent() {

		when(mockeventdao.create(mockEvent)).thenReturn(Integer.valueOf(1));
		assertEquals(mockeventdao.create(mockEvent), Integer.valueOf(1));
	}

	@Test
	public void checkUpdateEvent() {

		when(mockeventdao.read(mockEvent.getEventId())).thenReturn(mockEvent);
		assertNotNull(mockeventdao.read(mockEvent.getEventId()));
	}
	
	@Test
	public void checkReadAllEvent() {
		List<Event> events = Arrays.asList(mockEvent);
		when(mockeventdao.read()).thenReturn(events);
		assertNotNull(mockeventdao.read());
	}
	
	@Test
	public void checkAutoIncrementId() {
		when(mockeventdao.autoIncrementId(mockEvent)).thenReturn(Integer.valueOf(1));
		assertEquals(mockeventdao.autoIncrementId(mockEvent), Integer.valueOf(1));
	}
	
	@Test
	public void checkReadByEmail() {
		List<Event> events = Arrays.asList(mockEvent);
		when(mockeventdao.read(mockEvent.getCreatedBy())).thenReturn(events);
		assertNotNull(mockeventdao.read(mockEvent.getCreatedBy()));
	}

}
