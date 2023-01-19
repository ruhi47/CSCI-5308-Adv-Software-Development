package edu.dal.eauction.userManagement.validators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

public class NameValidator extends UserRegistrationValidationStrategy {

	@Override
	public List<String> validate(UserRegistrationDto registerDto) {
		validateFirstName(registerDto.getFirstName());
		validateLastName(registerDto.getLastName());
		return errors;
	}

	private void validateFirstName(String firstName) {
		if (firstName.isBlank()) {
			addError(RegistrationError.FIRST_NAME_BLANK);
		} else if (isStringContainSpecialCharacters(firstName)) {
			addError(RegistrationError.FIRST_NAME_ONLY_ALPHABETS);
		}
	}

	private void validateLastName(String lastName) {
		if (lastName.isBlank()) {
			addError(RegistrationError.LAST_NAME_BLANK);
		} else if (isStringContainSpecialCharacters(lastName)) {
			addError(RegistrationError.LAST_NAME_ONLY_ALPHABETS);
		}
	}

	private boolean isStringContainSpecialCharacters(String fieldValue) {
		Pattern specialCharPattern = Pattern.compile("[^A-Za-z]");
		Matcher matcher = specialCharPattern.matcher(fieldValue);
		return matcher.find();
	}

}
