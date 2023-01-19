package edu.dal.eauction.userManagement.security;

public enum SecurityErrorResponse {
	
	ACCOUNT_DEACTIVATED("Your account is deactivated"),
	INVALID_CREDENTIALS("Invalid login credentials"),
	LOGIN_CREDENTIALS_REQUIRED("Please enter login credentials");
	
	SecurityErrorResponse(String message) {
		this.message = message;
	}
	
	private String message;
	
	public static String getErrorMessage(SecurityErrorResponse error) {
		return error.message;
	}

}
