package edu.dal.eauction.userManagement.dao;

import java.sql.SQLException;
import java.util.List;

import edu.dal.eauction.Dao.GenericDao;
import edu.dal.eauction.userManagement.entities.*;

public interface UserDao extends GenericDao<User, Integer> {
	
	public final String USER_ID = "USER_ID";
	public final String FIRST_NAME = "FIRST_NAME";
	public final String LAST_NAME = "LAST_NAME";
	public final String EMAIL = "EMAIL";
	public final String PASSWORD = "PASSWORD";
	public final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
	public final String STATUS = "STATUS";
	public final String CREATED_DATE = "CREATED_DATE";
	public final String ROLE = "ROLE" ;
	public final String LAST_LOGGED_IN_DATE = "LAST_LOGGED_IN_DATE";
	public final String CREDITS = "CREDITS";
	
	Integer create(User user);
	
	//update user
	void update(User user);
	
	//select user by id
	User read(Integer id);
	
	//select users
	List<User> read();
	
	//delete user
	void delete(Integer id) throws SQLException;
	
	//read user by email
	User readByEmail(String Email);
	
	Integer autoIncrementId(User user);
}
