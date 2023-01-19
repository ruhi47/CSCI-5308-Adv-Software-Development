package edu.dal.eauction.userManagement.validators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

public class AddressValidatorStrategy extends UserRegistrationValidationStrategy {

	@Override
	public List<String> validate(UserRegistrationDto registerDto) {
		String addressLine1 = registerDto.getAddressLine1();
		String city = registerDto.getCity();
		String province = registerDto.getProvince();
		String zipCode = registerDto.getZipCode();
		String country = registerDto.getCountry();
		if (addressLine1.isBlank() || city.isBlank() || province.isBlank() || zipCode.isBlank() || country.isBlank()) {
			addError(RegistrationError.ADDRESS_FIELDS_BLANK);
		}
		validateCity(city);
		validateProvince(province);
		validateCountry(country);
		return errors;
	}

	private void validateCity(String city) {
		if (containsDigit(city)) {
			addError(RegistrationError.CITY_HAS_DIGITS);
		}
	}

	private void validateCountry(String country) {
		if (containsDigit(country)) {
			addError(RegistrationError.COUNTRY_HAS_DIGITS);
		}
	}

	private void validateProvince(String province) {
		if (containsDigit(province)) {
			addError(RegistrationError.PROVINCE_HAS_DIGITS);
		}
	}

	private boolean containsDigit(String password) {
		Pattern digitPattern = Pattern.compile("[0-9]");
		Matcher matcher = digitPattern.matcher(password);
		return matcher.find();
	}

}
