package edu.dal.eauction.userManagement.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.entities.UserBuilder;
import edu.dal.eauction.userManagement.entities.UserRole;
import edu.dal.eauction.userManagement.entities.UserStatus;
import edu.dal.eauction.userManagement.service.SearchUserService;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationProviderTest {

	@InjectMocks
	private CustomAuthenticationProvider provider;

	@Mock
	private SearchUserService searchUserService;

	@Test
	void testWithValidCredentials() {
		String username = "tiger@dal.ca";
		String password = "Qwerty@2";
		String encodedPassword = "$2a$10$e5cmLWz6ZYfq39C1nBER1eELQDF3rVdCEMPem.R3BskAT3JlO0fZu";
		User user = new UserBuilder().firstName("Dal").lastName("Tiger").email(username).password(encodedPassword)
				.status(UserStatus.ACTIVE.name()).role(UserRole.ADMIN).build();
		Authentication token = new UsernamePasswordAuthenticationToken(username, password);
		when(searchUserService.searchUserByEmail(username)).thenReturn(user);
		provider.authenticate(token);
	}

	@Test
	void testForDeactivatedAccount() {
		String username = "tiger@dal.ca";
		String password = "Qwerty@2";
		String encodedPassword = "$2a$10$e5cmLWz6ZYfq39C1nBER1eELQDF3rVdCEMPem.R3BskAT3JlO0fZu";
		User user = new UserBuilder().firstName("Dal").lastName("Tiger").email(username).password(encodedPassword)
				.status(UserStatus.FRAUD.name()).role(UserRole.ADMIN).build();
		Authentication token = new UsernamePasswordAuthenticationToken(username, password);
		when(searchUserService.searchUserByEmail(username)).thenReturn(user);
		Exception exception = assertThrows(BadCredentialsException.class, () -> provider.authenticate(token));
		assertEquals(SecurityErrorResponse.getErrorMessage(SecurityErrorResponse.ACCOUNT_DEACTIVATED),
				exception.getMessage());
	}

	@Test
	void testWithBlankPassword() {
		String username = "tiger@dal.ca";
		String password = "";
		Authentication token = new UsernamePasswordAuthenticationToken(username, password);
		Exception exception = assertThrows(BadCredentialsException.class, () -> provider.authenticate(token));
		assertEquals(SecurityErrorResponse.getErrorMessage(SecurityErrorResponse.LOGIN_CREDENTIALS_REQUIRED),
				exception.getMessage());
	}

	@Test
	void testWithBlankUsername() {
		String username = "";
		String password = "Qwerty@2";
		Authentication token = new UsernamePasswordAuthenticationToken(username, password);
		Exception exception = assertThrows(BadCredentialsException.class, () -> provider.authenticate(token));
		assertEquals(SecurityErrorResponse.getErrorMessage(SecurityErrorResponse.LOGIN_CREDENTIALS_REQUIRED),
				exception.getMessage());
	}

	@Test
	void testInvalidCredentials() {
		String username = "tiger@dal.ca";
		String password = "Qwerty@21";
		String encodedPassword = "$2a$10$e5cmLWz6ZYfq39C1nBER1eELQDF3rVdCEMPem.R3BskAT3JlO0fZu";
		User user = new UserBuilder().firstName("Dal").lastName("Tiger").email(username).password(encodedPassword)
				.status(UserStatus.ACTIVE.name()).role(UserRole.ADMIN).build();
		Authentication token = new UsernamePasswordAuthenticationToken(username, password);
		when(searchUserService.searchUserByEmail(username)).thenReturn(user);
		Exception exception = assertThrows(BadCredentialsException.class, () -> provider.authenticate(token));
		assertEquals(SecurityErrorResponse.getErrorMessage(SecurityErrorResponse.INVALID_CREDENTIALS),
				exception.getMessage());
	}

}
