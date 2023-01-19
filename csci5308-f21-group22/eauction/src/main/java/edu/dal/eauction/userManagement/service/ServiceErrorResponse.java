package edu.dal.eauction.userManagement.service;

public enum ServiceErrorResponse {
	
	ACCOUNT_DEACTIVATED("Your account is deactivated. Please contact eAuction support"),
	
	ACCOUNT_ALREADY_EXISTS("User already exists with the same email ID"),
	
	USER_CREATION_ERROR("Error while creating user");

	ServiceErrorResponse(String message) {
		this.message = message;
	}
	
	private String message;
	
	public static String getErrorMessage(ServiceErrorResponse error) {
		return error.message;
	}

}
