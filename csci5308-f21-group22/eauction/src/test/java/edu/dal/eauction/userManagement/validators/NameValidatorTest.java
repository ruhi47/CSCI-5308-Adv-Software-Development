package edu.dal.eauction.userManagement.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

class NameValidatorTest {

	String firstName = "First";
	String lastName = "Last";
	String email = "firstlast@example.com";
	
	@Test
	public void testForBlankValues() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName("");
		dto.setLastName("");
		dto.setEmail(email);
		Calendar dateOfBirth = Calendar.getInstance();
		dto.setDateOfBirth(dateOfBirth.getTime());
		IValidationStrategy<UserRegistrationDto> validator = new NameValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(2, errors.size());
		assertEquals(RegistrationError.FIRST_NAME_BLANK.message, errors.get(0));
		assertEquals(RegistrationError.LAST_NAME_BLANK.message, errors.get(1));
	}
	
	@Test
	public void testNameShouldContainOnlyAlphabets() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName("first12");
		dto.setLastName("last634");
		dto.setEmail(email);
		Calendar dateOfBirth = Calendar.getInstance();
		dto.setDateOfBirth(dateOfBirth.getTime());
		IValidationStrategy<UserRegistrationDto> validator = new NameValidator();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(2, errors.size());
		assertEquals(RegistrationError.FIRST_NAME_ONLY_ALPHABETS.message, errors.get(0));
		assertEquals(RegistrationError.LAST_NAME_ONLY_ALPHABETS.message, errors.get(1));
	}
	
	@Test
	public void validNameEntered() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName("first");
		dto.setLastName("last");
		dto.setEmail(email);
		Calendar dateOfBirth = Calendar.getInstance();
		dto.setDateOfBirth(dateOfBirth.getTime());
		IValidationStrategy<UserRegistrationDto> validator = new NameValidator();
		List<String> errors = validator.validate(dto);
		assertEquals(0, errors.size());		
	}

}
