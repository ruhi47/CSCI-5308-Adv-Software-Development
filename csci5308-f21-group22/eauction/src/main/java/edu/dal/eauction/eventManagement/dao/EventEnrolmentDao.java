package edu.dal.eauction.eventManagement.dao;

import java.sql.SQLException;
import java.util.List;

import edu.dal.eauction.Dao.GenericDao;
import edu.dal.eauction.eventManagement.entities.EventEnrolment;

public interface EventEnrolmentDao extends GenericDao<EventEnrolment, Integer> {
	Integer create(EventEnrolment event);

	// update user
	void update(EventEnrolment event);

	// select user by id
	EventEnrolment read(Integer eventId);

	// select users
	List<EventEnrolment> read();

	// delete user
	void delete(Integer eventId) throws SQLException;

	Integer autoIncrementId(EventEnrolment event);

	List<EventEnrolment> readByUserEmail(String userEmail);

	EventEnrolment readEnrolmentByEventId(Integer eventId);
}
