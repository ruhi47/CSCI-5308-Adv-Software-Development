package edu.dal.eauction.userManagement.convertor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;
import edu.dal.eauction.userManagement.entities.Address;
import edu.dal.eauction.userManagement.entities.User;

class UserRegistrationDtoConvertorTest {

	@Test
	void test() {
		UserRegistrationDto dto = new UserRegistrationDto();
		String firstName = "First";
		String lastName = "Last";
		String addressLine1 = "Address line 1";
		String addressLine2 = "Address line 2";
		String city = "City";
		String province = "Province";
		String country = "Country";
		String zipCode = "741258";
		String email = "first@last.com";
		String password = "Qwerty@2";
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.set(1991, 02, 20);
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setDateOfBirth(dateOfBirth.getTime());
		dto.setEmail(email);
		dto.setAddressLine1(addressLine1);
		dto.setAddressLine2(addressLine2);
		dto.setCity(city);
		dto.setCountry(country);
		dto.setProvince(province);
		dto.setZipCode(zipCode);
		dto.setPassword(password);
		IConvertor<UserRegistrationDto, User> convertor = new UserRegistrationDtoConvertor();
		User user = convertor.convert(dto);
		LocalDate userDob = user.getDateOfBirth();
		Address address = user.getAddress();
		assertEquals(firstName, user.getFirstName());
		assertEquals(lastName, user.getLastName());
		assertEquals(email, user.getEmail());
		assertEquals(20, userDob.getDayOfMonth());
		assertEquals(1991, userDob.getYear());
		assertEquals(password, user.getPassword());
		assertEquals(addressLine1, address.getAddressLine1());
		assertEquals(addressLine2, address.getAddressLine2());
		assertEquals(city, address.getCity());
		assertEquals(province, address.getProvince());
		assertEquals(zipCode, address.getZipCode());
		assertEquals(country, address.getCountry());
	}

}
