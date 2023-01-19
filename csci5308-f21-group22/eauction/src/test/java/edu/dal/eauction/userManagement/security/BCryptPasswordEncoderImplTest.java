package edu.dal.eauction.userManagement.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class BCryptPasswordEncoderImplTest {

	@Test
	public void testBCryptPasswordEncoderImplInstance() {
		assertThat(BCryptPasswordEncoderImpl.getInstance(), is(notNullValue()));
	}
	
	@Test
	public void testBCryptPasswordEncoderImplForSingleton() {
		BCryptPasswordEncoderImpl instance1 = BCryptPasswordEncoderImpl.getInstance();
		BCryptPasswordEncoderImpl instance2 = BCryptPasswordEncoderImpl.getInstance();
		assertThat(instance1, equalTo(instance2));
	}
	
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoderImpl encoder = BCryptPasswordEncoderImpl.getInstance();
		String rawPassword = "rawPassword";
		String encodedPassword = encoder.encodePassword(rawPassword);
		assertThat(encodedPassword, not(equalTo(rawPassword)));
	}
	
	@Test
	public void testCheckPasswordsMatch() {
		BCryptPasswordEncoderImpl encoder = BCryptPasswordEncoderImpl.getInstance();
		String rawPassword = "rawPassword";
		String encodedPassword = encoder.encodePassword(rawPassword);
		boolean isMatches = encoder.matchPassword(rawPassword, encodedPassword);
		assertThat(isMatches, is(true));
	}
	
	@Test
	public void testCheckPasswordsDoesNotMatch() {
		BCryptPasswordEncoderImpl encoder = BCryptPasswordEncoderImpl.getInstance();
		String rawPassword = "rawPassword";
		String encodedPassword = encoder.encodePassword(rawPassword);
		boolean isMatches = encoder.matchPassword("rawPasswordrawPassword", encodedPassword);
		assertThat(isMatches, is(false));
	}

}
