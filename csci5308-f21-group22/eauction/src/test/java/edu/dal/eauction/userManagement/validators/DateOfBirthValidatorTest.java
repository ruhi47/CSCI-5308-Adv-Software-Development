package edu.dal.eauction.userManagement.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

class DateOfBirthValidatorTest {

	String firstName = "First";
	String lastName = "Last";
	String email = "firstlast@example.com";

	@Test
	void testForNoValueProvided() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		dto.setDateOfBirth(null);
		IValidationStrategy<UserRegistrationDto> validator = new DateOfBirthValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.DATE_OF_BIRTH_BLANK.message, errors.get(0));
	}

	@Test
	void testUserNot18Years() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		Calendar dateOfBirth = Calendar.getInstance();
		dto.setDateOfBirth(dateOfBirth.getTime());
		IValidationStrategy<UserRegistrationDto> validator = new DateOfBirthValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertTrue(errors.contains(RegistrationError.USER_ATLEAST_18_YEARS.message));
	}

	@Test
	void testForValidDateOfBirth() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setEmail(email);
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.add(Calendar.YEAR, -30);
		dto.setDateOfBirth(dateOfBirth.getTime());
		IValidationStrategy<UserRegistrationDto> validator = new DateOfBirthValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(errors.size() == 0);
	}

}
