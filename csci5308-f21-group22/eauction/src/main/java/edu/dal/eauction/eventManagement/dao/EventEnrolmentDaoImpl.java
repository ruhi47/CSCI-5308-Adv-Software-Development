package edu.dal.eauction.eventManagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.dal.eauction.Dao.DBConnect;
import edu.dal.eauction.eventManagement.entities.EventEnrolment;

public class EventEnrolmentDaoImpl implements EventEnrolmentDao {

	private static final String INSERT_EVENTS = "INSERT INTO EVENT_ENROLMENT VALUES (?,?,?,?,?)";
	private static final String SELECT_EVENT = "SELECT eventId,itemId,userEmail,isActive FROM EVENT_ENROLMENT WHERE enrolmentId = ?";
	private static final String SELECT_ALL_EVENTS = "SELECT enrolmentId,eventId,itemId,userEmail,isActive FROM EVENT_ENROLMENT";
	private static final String DELETE_EVENT = "DELETE FROM EVENT_ENROLMENT WHERE enrolmentId =?";
	private static final String UPDATE_EVENT = "UPDATE EVENT_ENROLMENT SET eventId = ?,itemId = ?,userEmail = ?,isActive = ? WHERE enrolmentId = ?";
	private static final String SELECT_EVENTS_BY_USER = "SELECT enrolmentId,eventId,itemId,userEmail,isActive FROM EVENT_ENROLMENT WHERE userEmail = ?";
	private static final String SELECT_ENROLMENT_BY_EVENTID = "SELECT enrolmentId,eventId,itemId,userEmail,isActive FROM EVENT_ENROLMENT WHERE eventId = ?";

	@Override
	public Integer create(EventEnrolment event) {
		int id = autoIncrementId(event);
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENTS)) {
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, event.getEventId());
			preparedStatement.setInt(3, event.getItemId());
			preparedStatement.setString(4, event.getUserEmail());
			preparedStatement.setBoolean(5, event.isActive());

			preparedStatement.executeUpdate();
			System.out.println("Event Inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(EventEnrolment event) {
		boolean rowUpdated;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EVENT);) {
			preparedStatement.setInt(1, event.getEventId());
			preparedStatement.setInt(2, event.getItemId());
			preparedStatement.setString(3, event.getUserEmail());
			preparedStatement.setBoolean(4, event.isActive());
			preparedStatement.setInt(5, event.getEnrolmentId());
			rowUpdated = preparedStatement.executeUpdate() > 0;
			System.out.print(rowUpdated);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public EventEnrolment read(Integer enrolmentId) {
		EventEnrolment event = null;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT);) {
			preparedStatement.setInt(1, enrolmentId);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Integer eventId = rs.getInt("eventId");
				Integer itemId = rs.getInt("itemId");
				String userEmail = rs.getString("userEmail");
				Boolean isActive = rs.getBoolean("isActive");
				event = new EventEnrolment(eventId, itemId, userEmail, isActive);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return event;
	}

	@Override
	public List<EventEnrolment> read() {
		DBConnect con = DBConnect.getInstance();
		EventEnrolment event = null;
		List<EventEnrolment> events = new ArrayList<>();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS);) {
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Integer enrolmentId = rs.getInt("enrolmentId");
				Integer eventId = rs.getInt("eventId");
				Integer itemId = rs.getInt("itemId");
				String userEmail = rs.getString("userEmail");
				Boolean isActive = rs.getBoolean("isActive");
				event = new EventEnrolment(enrolmentId, eventId, itemId, userEmail, isActive);
				events.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}

	@Override
	public void delete(Integer eventId) throws SQLException {
		boolean rowDeleted;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT);) {
			preparedStatement.setInt(1, eventId);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		System.out.print(rowDeleted);

	}

	@Override
	public Integer autoIncrementId(EventEnrolment event) {
		DBConnect con = DBConnect.getInstance();
		Connection connection = null;
		connection = con.getConnection();
		ResultSet rs;
		int user_id = 0;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select max(enrolmentId)+1 from EVENT_ENROLMENT");
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				user_id = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user_id;
	}

	@Override
	public List<EventEnrolment> readByUserEmail(String userEmail) {
		DBConnect con = DBConnect.getInstance();
		EventEnrolment event = null;
		List<EventEnrolment> events = new ArrayList<>();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENTS_BY_USER);) {
			preparedStatement.setString(1, userEmail);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Integer enrolmentId = rs.getInt("enrolmentId");
				Integer eventId = rs.getInt("eventId");
				Integer itemId = rs.getInt("itemId");
				String userEmail1 = userEmail;
				Boolean isActive = rs.getBoolean("isActive");
				event = new EventEnrolment(enrolmentId, eventId, itemId, userEmail1, isActive);
				events.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	public EventEnrolment readEnrolmentByEventId(Integer eventId) {
		EventEnrolment event = null;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ENROLMENT_BY_EVENTID);) {
			preparedStatement.setInt(1, eventId);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Integer enrolmentId = rs.getInt("enrolmentId");
				Integer eventId1 = rs.getInt("eventId");
				Integer itemId = rs.getInt("itemId");
				String userEmail = rs.getString("userEmail");
				Boolean isActive = rs.getBoolean("isActive");
				event = new EventEnrolment(eventId1, itemId, userEmail, isActive);
				event.setEnrolmentId(enrolmentId);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return event;
	}
}
