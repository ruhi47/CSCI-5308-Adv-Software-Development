package edu.dal.eauction.userManagement.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dal.eauction.userManagement.dao.UserDaoImpl;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.entities.UserBuilder;
import edu.dal.eauction.userManagement.entities.UserRole;
import edu.dal.eauction.userManagement.entities.UserStatus;

@ExtendWith(MockitoExtension.class)
class SearchUserServiceTest {

	@Mock
	private UserDaoImpl userDao;

	@InjectMocks
	private SearchUserService service;

	@Test
	void testSearchUserByEmail() {
		String username = "tiger@dal.ca";
		String password = "Qwerty@2";
		User user = new UserBuilder().firstName("Dal").lastName("Tiger").email(username).password(password)
				.status(UserStatus.ACTIVE.name()).role(UserRole.ADMIN).build();
		when(userDao.readByEmail(username)).thenReturn(user);
		User result = service.searchUserByEmail(username);
		assertTrue(Objects.nonNull(result));
		assertNotNull(result);
		Mockito.verify(userDao).readByEmail(username);
	}

	@Test
	void testSearchUserByStatus() {
		String username = "tiger@dal.ca";
		String password = "Qwerty@2";
		User user1 = new UserBuilder().firstName("Dal").lastName("Tiger").email(username).password(password)
				.status(UserStatus.ACTIVE.name()).role(UserRole.ADMIN).build();
		User user2 = new UserBuilder().firstName("Truro").lastName("Ram").email(username).password(password)
				.status(UserStatus.ACTIVE.name()).role(UserRole.ADMIN).build();
		when(userDao.searchByStatus(UserStatus.ACTIVE)).thenReturn(Arrays.asList(user1, user2));
		List<User> result = service.searchUserByStatus(UserStatus.ACTIVE);
		assertTrue(Objects.nonNull(result));
		assertNotNull(result);
		Mockito.verify(userDao).searchByStatus(UserStatus.ACTIVE);
	}

}
