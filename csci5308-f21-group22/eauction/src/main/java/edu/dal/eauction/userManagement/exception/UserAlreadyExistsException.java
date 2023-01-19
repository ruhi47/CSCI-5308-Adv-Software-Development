package edu.dal.eauction.userManagement.exception;

/**
 * This exception is thrown while registering a new user and the user already
 * exists in the system
 */
public class UserAlreadyExistsException extends Exception {

	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
