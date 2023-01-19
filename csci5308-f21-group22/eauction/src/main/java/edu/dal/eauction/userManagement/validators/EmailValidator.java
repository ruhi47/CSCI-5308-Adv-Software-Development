package edu.dal.eauction.userManagement.validators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

public class EmailValidator extends UserRegistrationValidationStrategy {

	private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public List<String> validate(UserRegistrationDto registerDto) {
		String email = registerDto.getEmail();
		if (email.isBlank()) {
			addError(RegistrationError.BLANK_EMAIL);
			return errors;
		}
		if (isInvalidEmail(email)) {
			addError(RegistrationError.INVALID_EMAIL);
		}
		return errors;
	}

	public void addError(RegistrationError error) {
		String message = RegistrationError.getErrorMessage(error);
		errors.add(message);
	}

	private boolean isInvalidEmail(String email) {
		Pattern emailPattern = Pattern.compile(EMAIL_REGEX_PATTERN);
		Matcher emailMatcher = emailPattern.matcher(email);
		return emailMatcher.find() == true ? false : true;
	}

}
