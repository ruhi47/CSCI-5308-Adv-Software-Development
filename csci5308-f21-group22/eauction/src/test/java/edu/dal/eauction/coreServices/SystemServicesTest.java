package edu.dal.eauction.coreServices;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.userManagement.security.BCryptPasswordEncoderImpl;
import edu.dal.eauction.userManagement.security.IPasswordEncoder;

class SystemServicesTest {

	@Test
	void testForSingletonInstance() {
		SystemServices instance1 = SystemServices.getInstance();
		SystemServices instance2 = SystemServices.getInstance();
		assertThat(instance1, equalTo(instance2));
	}

	@Test
	void testGetPasswordEncoderService() {
		SystemServices instance = SystemServices.getInstance();
		assertNotNull(instance);
		IPasswordEncoder encoder = (BCryptPasswordEncoderImpl) instance.getPasswordEncoder();
		String rawPassword = "rawPassword";
		String encodedPassword = encoder.encodePassword(rawPassword);
		assertThat(encodedPassword, not(equalTo(rawPassword)));
	}

	@Test
	void testGetThreadPoolExecutorService() {
		SystemServices instance = SystemServices.getInstance();
		assertNotNull(instance);
		IThreadPoolExecutor executor = instance.getThreadPoolExecutor();
		assertNotNull(executor);
	}

}
