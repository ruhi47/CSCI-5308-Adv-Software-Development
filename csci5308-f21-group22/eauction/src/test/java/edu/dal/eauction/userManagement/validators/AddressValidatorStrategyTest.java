package edu.dal.eauction.userManagement.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

class AddressValidatorStrategyTest {

	String addressLine1 = "Address line 1";
	String addressLine2 = "Address line 2";
	String city = "City";
	String province = "Province";
	String country = "Country";;
	String zipCode = "741258";

	@Test
	void testValidateMandatoryFields() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setAddressLine1("");
		dto.setAddressLine2("");
		dto.setCity("");
		dto.setCountry("");
		dto.setProvince("");
		dto.setZipCode("");
		AddressValidatorStrategy validator = new AddressValidatorStrategy();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.ADDRESS_FIELDS_BLANK.message, errors.get(0));
	}

	@Test
	void testValidateCity() {
		city = "12City";
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setAddressLine1(addressLine1);
		dto.setAddressLine2(addressLine2);
		dto.setCity(city);
		dto.setCountry(country);
		dto.setProvince(province);
		dto.setZipCode(zipCode);
		AddressValidatorStrategy validator = new AddressValidatorStrategy();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.CITY_HAS_DIGITS.message, errors.get(0));
	}

	@Test
	void testValidateCountry() {
		country = "Country2";
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setAddressLine1(addressLine1);
		dto.setAddressLine2(addressLine2);
		dto.setCity(city);
		dto.setCountry(country);
		dto.setProvince(province);
		dto.setZipCode(zipCode);
		AddressValidatorStrategy validator = new AddressValidatorStrategy();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.COUNTRY_HAS_DIGITS.message, errors.get(0));
	}

	@Test
	void testValidateProvince() {
		province = "1Province2";
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setAddressLine1(addressLine1);
		dto.setAddressLine2(addressLine2);
		dto.setCity(city);
		dto.setCountry(country);
		dto.setProvince(province);
		dto.setZipCode(zipCode);
		AddressValidatorStrategy validator = new AddressValidatorStrategy();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(1, errors.size());
		assertEquals(RegistrationError.PROVINCE_HAS_DIGITS.message, errors.get(0));
	}
	
	@Test
	void testValidAddress() {		
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setAddressLine1(addressLine1);
		dto.setAddressLine2(null);
		dto.setCity(city);
		dto.setCountry(country);
		dto.setProvince(province);
		dto.setZipCode(zipCode);
		AddressValidatorStrategy validator = new AddressValidatorStrategy();
		List<String> errors = validator.validate(dto);
		assertTrue(Objects.nonNull(errors));
		assertEquals(0, errors.size());
	}

}
