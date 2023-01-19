package edu.dal.eauction.eventManagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.dal.eauction.Dao.DBConnect;
import edu.dal.eauction.eventManagement.entities.Event;

public class EventDaoImpl implements EventDao {

	private static final String INSERT_EVENTS = "INSERT INTO EVENT VALUES (?,?,?,?,?,?,?)";
	private static final String SELECT_EVENT = "SELECT itemId, startTime, endTime, createdBy, winner FROM EVENT WHERE eventId = ?";
	private static final String SELECT_ALL_EVENTS = "SELECT itemId, startTime, endTime, createdBy, winner FROM EVENT";
	private static final String DELETE_EVENT = "DELETE FROM EVENT WHERE eventId =?";
	private static final String UPDATE_EVENT = "UPDATE EVENT SET itemId = ?, startTime = ?, endTime = ?, createdBy = ? , winner = ? WHERE eventId = ?";
	private static final String SELECT_EVENTS_BY_USER = "SELECT eventID, itemId, minAmount, startTime, endTime, createdBy, winner FROM EVENT WHERE createdBy = ?";
	private static final String SELECT_EVENTS_BY_ITEM = "SELECT eventID, itemId, minAmount, startTime, endTime, createdBy, winner FROM EVENT WHERE itemId = ?";

	@Override
	public Integer create(Event event) {
		//int id = autoIncrementId(event);
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENTS)) {
			preparedStatement.setInt(1, event.getEventId());
			preparedStatement.setInt(2, event.getItemId());
			preparedStatement.setString(3, event.getStartTime().toString());
			preparedStatement.setString(4, event.getEndTime().toString());
			preparedStatement.setFloat(5, event.getMinAmount());
			preparedStatement.setString(6, event.getCreatedBy());
			preparedStatement.setString(7, event.getWinner());

			preparedStatement.executeUpdate();
			System.out.println("Event Inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Event event) {
		boolean rowUpdated;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EVENT);) {
			preparedStatement.setInt(1, event.getEventId());
			preparedStatement.setInt(2, event.getItemId());
			preparedStatement.setString(3, event.getStartTime().toString());
			preparedStatement.setString(4, event.getEndTime().toString());
			preparedStatement.setFloat(5, event.getMinAmount());
			preparedStatement.setString(6, event.getCreatedBy());
			preparedStatement.setString(7, event.getWinner());

			rowUpdated = preparedStatement.executeUpdate() > 0;
			System.out.print(rowUpdated);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Event read(Integer eventId) {
		Event event = null;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT);) {
			preparedStatement.setInt(1, eventId);
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

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return event;
	}

	@Override
	public List<Event> read() {
		DBConnect con = DBConnect.getInstance();
		Event event = null;
		List<Event> events = new ArrayList<>();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS);) {
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
	public Integer autoIncrementId(Event event) {
		DBConnect con = DBConnect.getInstance();
		Connection connection = con.getConnection();
		ResultSet rs;
		int user_id = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select max(eventId)+1 from EVENT");
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
	public List<Event> read(String userEmail) {
		DBConnect con = DBConnect.getInstance();
		Event event = null;
		List<Event> events = new ArrayList<>();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENTS_BY_USER);) {
			preparedStatement.setString(1, userEmail);
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
	public Event readByItemId(Integer itemId) {
		DBConnect con = DBConnect.getInstance();
		Event event = null;
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENTS_BY_ITEM);) {
			preparedStatement.setInt(1, itemId);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Integer itemId1 = rs.getInt("itemId");
				LocalDateTime startTime = (LocalDateTime) rs.getObject("startTime");
				LocalDateTime endTime = (LocalDateTime) rs.getObject("endTime");
				Integer minAmount = rs.getInt("minAmount");
				String createdBy = rs.getString("createdBy");
				String winner = rs.getString("winner");
				event = new Event(itemId1, startTime, endTime, minAmount, createdBy, winner);
				event.setEventId(rs.getInt("eventId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return event;
	}

}
