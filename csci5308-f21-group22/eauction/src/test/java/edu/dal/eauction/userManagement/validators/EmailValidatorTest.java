package edu.dal.eauction.userManagement.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

class EmailValidatorTest {

	String firstName = "First";
	String lastName = "Last";

	@Test
	void testValidEmail() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail("firstname@dal.ca");
		IValidationStrategy<UserRegistrationDto> validator = new EmailValidator();
		List<String> errors = validator.validate(dto);	
		assertEquals(0, errors.size());
	}
	
	@Test
	void testBlankInputForEmail() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail("");
		IValidationStrategy<UserRegistrationDto> validator = new EmailValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertTrue(errors.contains(RegistrationError.BLANK_EMAIL.message));
	}

	@Test
	void testInvalidEmail() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail("firstname@dal");
		IValidationStrategy<UserRegistrationDto> validator = new EmailValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertTrue(errors.contains(RegistrationError.INVALID_EMAIL.message));		
	}
}
