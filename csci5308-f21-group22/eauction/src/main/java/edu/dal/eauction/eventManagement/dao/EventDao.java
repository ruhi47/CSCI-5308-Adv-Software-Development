package edu.dal.eauction.eventManagement.dao;

import java.sql.SQLException;
import java.util.List;

import edu.dal.eauction.Dao.DBConnect;
import edu.dal.eauction.Dao.GenericDao;
import edu.dal.eauction.eventManagement.entities.Event;

public interface EventDao extends GenericDao<Event, Integer> {
	Integer create(Event event);

	// update user
	void update(Event event);

	// select user by id
	Event read(Integer eventId);

	// select users
	List<Event> read();

	// delete user
	void delete(Integer eventId) throws SQLException;

	// auto incrementing event id for creating a new event
	Integer autoIncrementId(Event event);

	// Getting list of events created by the user
	List<Event> read(String userEmail);

	Event readByItemId(Integer itemId);

}
