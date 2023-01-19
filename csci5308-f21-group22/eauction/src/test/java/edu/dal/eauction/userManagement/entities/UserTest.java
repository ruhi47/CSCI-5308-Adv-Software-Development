package edu.dal.eauction.userManagement.entities;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	void testCreateNewUserInstance() {
		LocalDate dob = LocalDate.of(1992, 01, 01);
		LocalDateTime createdDate = LocalDateTime.now();
		LocalDateTime lastSignInDate = LocalDateTime.now();
		Address address = new Address();
		address.setAddressLine1("Address Line1");
		address.setAddressLine2("Address line 2");
		address.setCity("city");
		address.setProvince("Province");
		address.setZipCode("123456");
		User user = new User(1, "First", "Last", "firstname@example.com", "password", dob, UserStatus.ACTIVE,
				createdDate, lastSignInDate, 3000f, UserRole.USER, address);
		assertEquals(1, user.getId());
		assertEquals("First", user.getFirstName());
		assertEquals("Last", user.getLastName());
		assertEquals("firstname@example.com", user.getEmail());
		assertEquals("password", user.getPassword());
		assertEquals(dob, user.getDateOfBirth());
		assertEquals(UserStatus.ACTIVE, user.getStatus());
		assertEquals(createdDate, user.getCreatedDate());
		assertEquals(lastSignInDate, user.getLastSigninDate());
		assertEquals(3000f, user.getCredits());
		assertEquals("USER", user.getRoleAsString());
	}

	@Test
	void testHashPassword() {
		LocalDate dob = LocalDate.of(1992, 01, 01);
		LocalDateTime createdDate = LocalDateTime.now();
		LocalDateTime lastSignInDate = LocalDateTime.now();
		String rawPassword = "password";
		User user = new User(1, "First", "Last", "firstname@example.com", rawPassword, dob, UserStatus.ACTIVE,
				createdDate, lastSignInDate, 3000f, UserRole.USER, null);
		user.hashPassword();
		assertNotEquals(rawPassword, user.getPassword());
		MatcherAssert.assertThat(user.getPassword().length(), greaterThan(rawPassword.length()));
	}

	@Test
	void testDefaultValuesForNewUser() {
		String rawPassword = "password";
		User user = new User();
		user.setFirstName("First");
		user.setLastName("Last");
		user.setEmail("firstname@example.com");
		user.setPassword(rawPassword);
		user.setDateOfBirth(LocalDate.of(1990, 10, 31));
		user.setDefaultsForNewUser();
		MatcherAssert.assertThat(user.getPassword().length(), greaterThan(rawPassword.length()));
		assertEquals(UserStatus.ACTIVE, user.getStatus());
		assertEquals(UserRole.USER, user.getRole());
		assertNotNull(user.getCreatedDate());
		assertTrue(user.getCredits() > 0);
	}

}
