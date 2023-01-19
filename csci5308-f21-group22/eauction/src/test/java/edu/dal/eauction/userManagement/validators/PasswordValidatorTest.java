package edu.dal.eauction.userManagement.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

class PasswordValidatorTest {

	String firstName = "First";
	String lastName = "Last";
	String email = "firstlast@example.com";

	@Test
	void testBlankPasswords() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setPassword("");
		dto.setConfirmedPassword("");
		IValidationStrategy<UserRegistrationDto> validator = new PasswordValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PASSWORDS_BLANK.message, errors.get(0));
	}

	@Test
	void testPasswordsMismatch() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setPassword("Qwerty@1");
		dto.setConfirmedPassword("Qwerty@2");
		IValidationStrategy<UserRegistrationDto> validator = new PasswordValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PASSWORDS_MISMATCH.message, errors.get(0));
	}

	@Test
	void testPasswordsLengthError() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setPassword("Qwe@2");
		dto.setConfirmedPassword("Qwe@2");
		IValidationStrategy<UserRegistrationDto> validator = new PasswordValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PASSWORD_LENGTH_ERROR.message, errors.get(0));
	}

	@Test
	void testPasswordNotHavingUpperCase() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setPassword("qwerty@2");
		dto.setConfirmedPassword("qwerty@2");
		IValidationStrategy<UserRegistrationDto> validator = new PasswordValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PASSWORD_UPPERCASE_ERROR.message, errors.get(0));
	}

	@Test
	void testPasswordNotHavingLowerCase() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setPassword("QWERTY@2");
		dto.setConfirmedPassword("QWERTY@2");
		IValidationStrategy<UserRegistrationDto> validator = new PasswordValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PASSWORS_LOWERCASE_ERROR.message, errors.get(0));
	}

	@Test
	void testPasswordNotHavingDigits() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setPassword("QWERTYy@E");
		dto.setConfirmedPassword("QWERTYy@E");
		IValidationStrategy<UserRegistrationDto> validator = new PasswordValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PASSWORD_DIGITS_ERROR.message, errors.get(0));
	}

	@Test
	void testPasswordNotHavingSpecialCharacters() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setPassword("QWERTY0yE");
		dto.setConfirmedPassword("QWERTY0yE");
		IValidationStrategy<UserRegistrationDto> validator = new PasswordValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PASSWORD_SEPCIAL_CHAR_ERROR.message, errors.get(0));
	}

	@Test
	void testPasswordHavingUsersName() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setPassword("FiRstname@1");
		dto.setConfirmedPassword("FiRstname@1");
		IValidationStrategy<UserRegistrationDto> validator = new PasswordValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PASSWORD_HAS_USERS_NAME.message, errors.get(0));
	}

}
