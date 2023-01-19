package edu.dal.eauction.eventManagement.daoTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import edu.dal.eauction.eventManagement.dao.EventEnrolmentDao;
import edu.dal.eauction.eventManagement.entities.EventEnrolment;

@ExtendWith(MockitoExtension.class)
public class EventEnrolmentDaoTest {

	@Mock
	private EventEnrolmentDao mockEventEnrolmentDao;
	@Mock
	private EventEnrolment mockEventEnrolment;

	@Test
	public void checkCreateEvent() {

		when(mockEventEnrolmentDao.create(mockEventEnrolment)).thenReturn(Integer.valueOf(1));
		assertEquals(mockEventEnrolmentDao.create(mockEventEnrolment), Integer.valueOf(1));
	}

	@Test
	public void checkUpdateEvent() {

		when(mockEventEnrolmentDao.read(mockEventEnrolment.getEventId())).thenReturn(mockEventEnrolment);
		assertNotNull(mockEventEnrolmentDao.read(mockEventEnrolment.getEventId()));
	}
	
	@Test
	public void checkReadAllEvent() {
		List<EventEnrolment> enrolments = Arrays.asList(mockEventEnrolment);
		when(mockEventEnrolmentDao.read()).thenReturn(enrolments);
		assertNotNull(mockEventEnrolmentDao.read());
	}
	
	@Test
	public void checkAutoIncrementId() {
		when(mockEventEnrolmentDao.autoIncrementId(mockEventEnrolment)).thenReturn(Integer.valueOf(1));
		assertEquals(mockEventEnrolmentDao.autoIncrementId(mockEventEnrolment), Integer.valueOf(1));
	}
	
	@Test
	public void checkReadByEmail() {
		List<EventEnrolment> events = Arrays.asList(mockEventEnrolment);
		when(mockEventEnrolmentDao.readByUserEmail(mockEventEnrolment.getUserEmail())).thenReturn(events);
		assertNotNull(mockEventEnrolmentDao.readByUserEmail(mockEventEnrolment.getUserEmail()));
	}

}