package edu.dal.eauction.userManagement.validators;

public enum RegistrationError {

	BLANK_EMAIL("Please enter the Email address"),
	INVALID_EMAIL("Please enter a valid Email address"),
	
	PASSWORDS_BLANK("Please enter both Password and Confirm Password fields"),
	PASSWORDS_MISMATCH("Both Password and Confirm Password fields should match"),
	PASSWORD_LENGTH_ERROR("Password should be atleast 8 characters long"),
	PASSWORD_UPPERCASE_ERROR("Password should contain atleast 1 upper case character"),
	PASSWORS_LOWERCASE_ERROR("Password should contain atleast 1 lower case character"),
	PASSWORD_DIGITS_ERROR("Password should contain atleast 1 digit between 0 to 9"),
	PASSWORD_SEPCIAL_CHAR_ERROR("Password should contain atleast one special character"),
	PASSWORD_HAS_USERS_NAME("Password should not contain your name"),
	
	FIRST_NAME_BLANK("Please enter the First Name"),
	FIRST_NAME_ONLY_ALPHABETS("First Name should contain only alphabets"),
	
	LAST_NAME_BLANK("Please enter the Last Name"),
	LAST_NAME_ONLY_ALPHABETS("Last Name should contain only alphabets"),
	
	DATE_OF_BIRTH_BLANK("Please enter the date of birth"),
	USER_ATLEAST_18_YEARS("You must be atleast 18 years old to register"),
	
	ADDRESS_FIELDS_BLANK("Please enter the mandatory address fields"),
	CITY_HAS_DIGITS("City should not contain digits"),
	PROVINCE_HAS_DIGITS("Province should not contain digits"),
	COUNTRY_HAS_DIGITS("Country should not contain digits");

	public final String message;

	RegistrationError(String message) {
		this.message = message;
	}

	public static String getErrorMessage(RegistrationError errorType) {
		return errorType.message;
	}

}
