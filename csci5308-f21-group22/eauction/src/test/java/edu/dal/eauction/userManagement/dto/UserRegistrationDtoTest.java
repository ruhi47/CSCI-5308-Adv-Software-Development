package edu.dal.eauction.userManagement.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.validators.RegistrationError;

class UserRegistrationDtoTest {

	@Test
	void testValidUserRegistrationEntry() {
		UserRegistrationDto dto = new UserRegistrationDto();
		String addressLine1 = "Address line 1";
		String addressLine2 = "Address line 2";
		String city = "City";
		String province = "Province";
		String country = "Country";;
		String zipCode = "741258";
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.add(Calendar.YEAR, -30);
		dto.setFirstName("First");
		dto.setLastName("Last");
		dto.setDateOfBirth(dateOfBirth.getTime());
		dto.setEmail("first@last.com");
		dto.setAddressLine1(addressLine1);
		dto.setAddressLine2(addressLine2);
		dto.setCity(city);
		dto.setCountry(country);
		dto.setProvince(province);
		dto.setZipCode(zipCode);
		dto.setPassword("Qwerty@2");
		dto.setConfirmedPassword("Qwerty@2");
		List<String> errors = dto.validate();
		assertNotNull(errors);
		assertEquals(0, errors.size());
	}

	
	@Test
	void testInValidUserRegistrationEntry() {
		UserRegistrationDto dto = new UserRegistrationDto();
		String addressLine1 = "Address line 1";
		String addressLine2 = "Address line 2";
		String city = "City";
		String province = "Prov2ince";
		String country = "Cou4ntry";;
		String zipCode = "741258";
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.add(Calendar.YEAR, -30);
		dto.setFirstName("First");
		dto.setLastName("");
		dto.setDateOfBirth(dateOfBirth.getTime());
		dto.setEmail("first@.com");
		dto.setAddressLine1(addressLine1);
		dto.setAddressLine2(addressLine2);
		dto.setCity(city);
		dto.setCountry(country);
		dto.setProvince(province);
		dto.setZipCode(zipCode);
		dto.setPassword("Qwerty@21");
		dto.setConfirmedPassword("Qwerty@2");
		List<String> errors = dto.validate();
		assertNotNull(errors);
		assertEquals(5, errors.size());
		assertTrue(errors.contains(RegistrationError.PROVINCE_HAS_DIGITS.message));
		assertTrue(errors.contains(RegistrationError.COUNTRY_HAS_DIGITS.message));
		assertTrue(errors.contains(RegistrationError.LAST_NAME_BLANK.message));
		assertTrue(errors.contains(RegistrationError.PASSWORDS_MISMATCH.message));
		assertTrue(errors.contains(RegistrationError.INVALID_EMAIL.message));
	}
}
