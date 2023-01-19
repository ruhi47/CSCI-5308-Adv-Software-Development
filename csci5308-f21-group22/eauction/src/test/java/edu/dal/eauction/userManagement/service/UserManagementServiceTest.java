package edu.dal.eauction.userManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dal.eauction.emailSender.IAsyncEmailSenderService;
import edu.dal.eauction.emailSender.email.Email;
import edu.dal.eauction.userManagement.dao.UserDaoImpl;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.entities.UserBuilder;
import edu.dal.eauction.userManagement.entities.UserRole;
import edu.dal.eauction.userManagement.entities.UserStatus;
import edu.dal.eauction.userManagement.exception.UserAlreadyExistsException;
import edu.dal.eauction.userManagement.exception.UserCreationException;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

	@Mock
	private UserDaoImpl userDao;
	@Mock
	private SearchUserService searchUserService;
	@Mock
	private IAsyncEmailSenderService emailService;

	@InjectMocks
	private UserManagementService userManagementService;

	@BeforeAll
	public static void setUp() {
		System.setProperty("KEY_DEFAULT_PASSWORD", "password");
	}

	@AfterAll
	public static void tearDown() {
		System.clearProperty("KEY_DEFAULT_PASSWORD");
	}

	@Test
	void testRegisterUserAlreadyExists() {
		String email = "firstname@example.com";
		String encodedPassword = "$2a$10$e5cmLWz6ZYfq39C1nBER1eELQDF3rVdCEMPem.R3BskAT3JlO0fZu";
		User user = new UserBuilder().firstName("Dal").lastName("Tiger").email(email).password(encodedPassword).build();
		when(searchUserService.searchUserByEmail(email)).thenReturn(user);
		assertThrows(UserAlreadyExistsException.class, () -> userManagementService.registerUser(user));
	}

	@Test
	void testRegisterUserCreationExceptionInDB() {
		String email = "firstname@example.com";
		String encodedPassword = "$2a$10$e5cmLWz6ZYfq39C1nBER1eELQDF3rVdCEMPem.R3BskAT3JlO0fZu";
		User user = new UserBuilder().firstName("Dal").lastName("Tiger").email(email).password(encodedPassword).build();
		when(searchUserService.searchUserByEmail(email)).thenReturn(null);
		when(userDao.create(user)).thenReturn(null);
		assertThrows(UserCreationException.class, () -> userManagementService.registerUser(user));
	}

	@Test
	void testRegisterUserSuccessfully() throws Exception {
		String email = "firstname@example.com";
		String password = "Qwerty@2";
		User user = new UserBuilder().firstName("Dal").lastName("Tiger").email(email).password(password).build();
		when(searchUserService.searchUserByEmail(email)).thenReturn(null);
		when(userDao.create(user)).thenReturn(1);
		assertTrue(userManagementService.registerUser(user));
		assertNotNull(user.getCreatedDate());
		assertEquals(UserStatus.ACTIVE, user.getStatus());
		assertEquals(UserRole.USER, user.getRole());
		assertNotEquals(password, user.getPassword());
	}

	@Test
	void testPostLoginRules() {
		String email = "firstname@example.com";
		String password = "Qwerty@2";
		User user = new UserBuilder().firstName("Dal").lastName("Tiger").email(email).password(password).build();
		when(searchUserService.searchUserByEmail(email)).thenReturn(user);
		Mockito.doNothing().when(userDao).update(user);
		userManagementService.processPostLoginRules(email);
		assertNotNull(user.getLastSigninDate());
	}

	@Test
	void recoverPasswordForInActiveUser() throws Exception {
		String email = "firstname@example.com";
		String password = "Qwerty@2";
		User user = new UserBuilder().firstName("first").lastName("last").email(email).password(password)
				.status("FRAUD").role(UserRole.USER).build();
		when(searchUserService.searchUserByEmail(email)).thenReturn(user);
		assertThrows(Exception.class, () -> userManagementService.recoverPassword(email));
		Mockito.verify(emailService, times(0)).sendEmail(Mockito.isA(Email.class));
		Mockito.verify(userDao, times(0)).update(user);
	}

}
