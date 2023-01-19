package edu.dal.eauction.userManagement.service;

import java.util.List;

import edu.dal.eauction.userManagement.dao.UserDaoImpl;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.entities.UserStatus;

public class SearchUserService {

	public SearchUserService() {
		super();
		this.userDao = new UserDaoImpl();
	}

	private UserDaoImpl userDao;

	public User searchUserByEmail(String email) {
		User user = userDao.readByEmail(email);
		return user;
	}

	public List<User> searchUserByStatus(UserStatus status) {
		List<User> users = userDao.searchByStatus(status);
		return users;
	}

}
