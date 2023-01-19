package edu.dal.eauction.eventManagement.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.dal.eauction.Dao.DBConnect;
import edu.dal.eauction.emailSender.IAsyncEmailSenderService;
import edu.dal.eauction.emailSender.SmtpEmailSenderImpl;
import edu.dal.eauction.emailSender.email.Email;
import edu.dal.eauction.emailSender.email.EmailType;
import edu.dal.eauction.emailSender.email.params.EventSubscriptionParams;
import edu.dal.eauction.emailSender.email.params.IEmailContentParams;
import edu.dal.eauction.eventManagement.dao.EventDaoImpl;
import edu.dal.eauction.eventManagement.entities.Event;
import edu.dal.eauction.eventManagement.entities.EventEnrolment;
import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.dao.ItemDaoImplementation;
import edu.dal.eauction.itemManagement.entities.Item;
import edu.dal.eauction.userManagement.dao.UserDao;
import edu.dal.eauction.userManagement.dao.UserDaoImpl;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.exception.UserAlreadyExistsException;

public class EventManagementService implements IEventManagementService {

	private static final Logger log = LogManager.getLogger();
	private IAsyncEmailSenderService emailService;

	public EventManagementService() {
		super();
		emailService = new SmtpEmailSenderImpl();
	}

	@Override
	public boolean createEvent(Event event) throws UserAlreadyExistsException {
		log.info("Creating an event");
		// getting username
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		event.setCreatedBy(username);
		EventDaoImpl edi = new EventDaoImpl();
		edi.create(event);
		return true;
	}

	@Override
	// Fetching event enrolment with event id and username
	public EventEnrolment readEnrolment(Event event) {
		EventEnrolment enrolment = null;
		DBConnect con = DBConnect.getInstance();
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM EVENT_ENROLMENT WHERE EVENTID = ? AND USEREMAIL = ?");) {
			preparedStatement.setInt(1, event.getEventId());
			preparedStatement.setString(2, username);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer eventId = rs.getInt("eventId");
				Integer itemId = rs.getInt("itemId");
				String userEmail = rs.getString("userEmail");
				Boolean isActive = rs.getBoolean("isActive");
				enrolment = new EventEnrolment(eventId, itemId, userEmail, isActive);
				enrolment.setEnrolmentId(rs.getInt("enrolmentId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrolment;
	}

	@Override
	// Fetching the list of past events
	public List<Event> readPastEvents(Event event) {
		DBConnect con = DBConnect.getInstance();
		Event returnevent = null;
		List<Event> events = new ArrayList<>();
		// getting username
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT distinct e.eventId,e.itemId, e.startTime, e.endTime, e.minAmount,e.createdBy,e.winner FROM EVENT e inner join EVENT_ENROLMENT ee on e.eventId = ee.eventId where ee.userEmail = ? and e.startTime<(select now())");) {
			preparedStatement.setString(1, username);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer itemId = rs.getInt("itemId");
				LocalDateTime startTime = (LocalDateTime) rs.getObject("startTime");
				LocalDateTime endTime = (LocalDateTime) rs.getObject("endTime");
				Integer minAmount = rs.getInt("minAmount");
				String createdBy = rs.getString("createdBy");
				String winner = rs.getString("winner");
				returnevent = new Event(itemId, startTime, endTime, minAmount, createdBy, winner);
				returnevent.setEventId(rs.getInt("eventId"));
				events.add(returnevent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}

	@Override
	// Fetching list of events subscribed by the user
	public List<Event> readSubscribedEvents(String username) {
		DBConnect con = DBConnect.getInstance();
		Event event = null;
		List<Event> events = new ArrayList<>();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT distinct e.eventId,e.itemId, e.startTime, e.endTime, e.minAmount,e.createdBy,e.winner from EVENT e inner join EVENT_ENROLMENT ee on e.eventId = ee.eventId where ee.userEmail = ?");) {
			preparedStatement.setString(1, username);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer itemId = rs.getInt("itemId");
				LocalDateTime startTime = (LocalDateTime) rs.getObject("startTime");
				LocalDateTime endTime = (LocalDateTime) rs.getObject("endTime");
				Integer minAmount = rs.getInt("minAmount");
				String createdBy = rs.getString("createdBy");
				String winner = rs.getString("winner");
				event = new Event(itemId, startTime, endTime, minAmount, createdBy, winner);
				event.setEventId(rs.getInt("eventId"));
				events.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}

	@Override
	// Fetching upcoming events subscribed by the user
	public List<Event> readUpcomingEvents(Event event) {
		DBConnect con = DBConnect.getInstance();
		Event returnevent = null;
		List<Event> events = new ArrayList<>();
		// getting username
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT distinct e.eventId,e.itemId, e.startTime, e.endTime, e.minAmount,e.createdBy,e.winner from EVENT e inner join EVENT_ENROLMENT ee on e.eventId = ee.eventId where ee.userEmail = ? and e.startTime>=(select now())");) {
			preparedStatement.setString(1, username);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer itemId = rs.getInt("itemId");
				LocalDateTime startTime = (LocalDateTime) rs.getObject("startTime");
				LocalDateTime endTime = (LocalDateTime) rs.getObject("endTime");
				Integer minAmount = rs.getInt("minAmount");
				String createdBy = rs.getString("createdBy");
				String winner = rs.getString("winner");
				returnevent = new Event(itemId, startTime, endTime, minAmount, createdBy, winner);
				returnevent.setEventId(rs.getInt("eventId"));
				events.add(returnevent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}

	@Override
	// Generating params and sending email
	public void SendEmail(Integer itemId) {
		// Find Item by itemId
		ItemDao idi = new ItemDaoImplementation();
		Item item = idi.read(itemId);
		UserDao udi = new UserDaoImpl();
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		User user = udi.readByEmail(username);
		EventDaoImpl edi = new EventDaoImpl();
		Event event = edi.readByItemId(itemId);
		IEmailContentParams params = new EventSubscriptionParams(user.getFirstName(), item.getItemName(),
				event.getStartTime().toString(), event.getEndTime().toString(),"credit_score");
		Email email = new Email(EmailType.EVENT_SUBSCRIPTION, username);
		email.constructEmailContent(params);
		emailService.sendEmail(email);

	}
}
