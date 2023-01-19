package edu.dal.eauction.userManagement.convertor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.dto.UserDto;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.entities.UserBuilder;
import edu.dal.eauction.userManagement.entities.UserRole;

class UsertoUserDtoConvertorTest {

	@Test
	void testConvertUserToUserDto() {
		String firstName = "First";
		String lastName = "Last";
		String email = "first@last.com";
		String password = "Qwerty@2";
		LocalDate dob = LocalDate.of(1990, 8, 25);
		User user = new UserBuilder().firstName(firstName).lastName(lastName).email(email).password(password)
				.dateOfBirth(dob).role(UserRole.USER).status("ACTIVE").build();
		IConvertor<User, UserDto> convertor = new UsertoUserDtoConvertor();
		UserDto userDto = convertor.convert(user);
		assertEquals(user.getFirstName(), userDto.getFirstName());
		assertEquals(user.getLastName(), userDto.getLastName());
		assertEquals(user.getEmail(), userDto.getEmail());
		assertEquals(user.getLastSigninDate(), userDto.getLastSigninDate());
		assertEquals(user.getStatus(), userDto.getStatus());
		assertEquals(user.getCredits(), userDto.getCredits());
		assertEquals(user.getRole(), userDto.getRole());
		assertEquals(user.getDateOfBirth(), userDto.getDateOfBirth());
	}

}
