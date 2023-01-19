package edu.dal.eauction.userManagement.validators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

public class PasswordValidator extends UserRegistrationValidationStrategy {

	private static final String ALLOWED_SPECIAL_CHARS = ".*[!@#&()–[{}]:;',?/*~$^+=<>].*";

	private static final Integer PASSWORD_MIN_LENGTH = 8;

	private static final Integer PASSWORD_MAX_LENGTH = 50;

	@Override
	public List<String> validate(UserRegistrationDto registerDto) {
		String password = registerDto.getPassword();
		String confirmPassword = registerDto.getConfirmedPassword();
		boolean arePasswordsBlank = password.isBlank() || confirmPassword.isBlank();
		if (arePasswordsBlank) {
			addError(RegistrationError.PASSWORDS_BLANK);
			return errors;
		}
		boolean doesPasswordsMatch = password.equals(confirmPassword);
		if (doesPasswordsMatch) {
			validatePasswordLength(password);
			containsUpperCaseCharacter(password);
			containsLowerCaseCharacter(password);
			containsDigit(password);
			containsSpecialCharacters(password);
			containsUserName(password, registerDto.getFirstName(), registerDto.getLastName());

		} else {
			addError(RegistrationError.PASSWORDS_MISMATCH);
		}
		return errors;
	}

	public void addError(RegistrationError error) {
		String message = RegistrationError.getErrorMessage(error);
		errors.add(message);
	}

	private void validatePasswordLength(String password) {
		int passwordLength = password.length();
		if (passwordLength < PASSWORD_MIN_LENGTH || passwordLength > PASSWORD_MAX_LENGTH) {
			addError(RegistrationError.PASSWORD_LENGTH_ERROR);
		}
	}

	private void containsUpperCaseCharacter(String password) {
		Pattern upperCasePattern = Pattern.compile("[A-Z]");
		Matcher matcher = upperCasePattern.matcher(password);
		boolean hasUpperCase = matcher.find();
		if (!hasUpperCase) {
			addError(RegistrationError.PASSWORD_UPPERCASE_ERROR);
		}
	}

	private void containsLowerCaseCharacter(String password) {
		Pattern lowerCasePattern = Pattern.compile("[a-z]");
		Matcher matcher = lowerCasePattern.matcher(password);
		boolean hasLowerCase = matcher.find();
		if (!hasLowerCase) {
			addError(RegistrationError.PASSWORS_LOWERCASE_ERROR);
		}
	}

	private void containsDigit(String password) {
		Pattern digitPattern = Pattern.compile("[0-9]");
		Matcher matcher = digitPattern.matcher(password);
		boolean hasDigit = matcher.find();
		if (!hasDigit) {
			addError(RegistrationError.PASSWORD_DIGITS_ERROR);
		}
	}

	private void containsSpecialCharacters(String password) {
		Pattern specialCharPattern = Pattern.compile(ALLOWED_SPECIAL_CHARS);
		Matcher matcher = specialCharPattern.matcher(password);
		boolean hasSpecialChar = matcher.find();
		if (!hasSpecialChar) {
			addError(RegistrationError.PASSWORD_SEPCIAL_CHAR_ERROR);
		}
	}

	private void containsUserName(String password, String firstName, String lastName) {
		boolean hasFirstName =  password.toLowerCase().contains(firstName.toLowerCase());
		boolean hasLastName =  password.toLowerCase().contains(lastName.toLowerCase());
		if (hasFirstName || hasLastName) {
			addError(RegistrationError.PASSWORD_HAS_USERS_NAME);
		}		
	}

}
