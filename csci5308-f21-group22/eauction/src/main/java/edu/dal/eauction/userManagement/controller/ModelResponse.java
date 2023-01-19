package edu.dal.eauction.userManagement.controller;

public enum ModelResponse {

	USER_REGISTRATION_SUCCESS("User created successfully"),

	LOGOUT_MESSAGE("You have been successfully logged out"), 
	EMAIL_REQUIRED("Please provide an email ID"),

	RECOVERY_EMAIL_SENT("You will receive an email if the provided email ID is registered with us");

	ModelResponse(String message) {
		this.message = message;
	}

	private String message;

	public static String getMessage(ModelResponse response) {
		return response.message;
	}
	
}
