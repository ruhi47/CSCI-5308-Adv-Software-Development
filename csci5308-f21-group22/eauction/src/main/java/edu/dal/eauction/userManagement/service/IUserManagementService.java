package edu.dal.eauction.userManagement.service;

import edu.dal.eauction.userManagement.entities.User;

public interface IUserManagementService {

	boolean registerUser(User user) throws Exception;

	void processPostLoginRules(String email);

	void recoverPassword(String emailAddress) throws Exception;

}