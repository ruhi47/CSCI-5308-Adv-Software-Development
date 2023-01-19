package edu.dal.eauction.userManagement.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class AddressTest {

	@Test
	void testCreateAddressInstance() {
		User user = new User(1, "First", "Last", "firstname@example.com", "password", 
				LocalDate.of(1991, 12, 12), UserStatus.ACTIVE,
				LocalDateTime.now(), null, 3000f, UserRole.USER, null);
		String addressLine1 = "Address line 1";
		String addressLine2 = "Address line 2";
		String city = "City";
		String province = "Province";
		String country = "Country";;		
		String zipCode = "741258";
		Address address = new Address(user, addressLine1, addressLine2, 
				city, province, country, zipCode);
		assertEquals(addressLine1, address.getAddressLine1());
		assertEquals(addressLine2, address.getAddressLine2());
		assertEquals(city, address.getCity());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
		assertEquals(zipCode, address.getZipCode());
		assertEquals(user, address.getUser());
	}

}
