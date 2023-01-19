package edu.dal.eauction.userManagement.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import edu.dal.eauction.coreServices.SystemServices;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.service.SearchUserService;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOG = LogManager.getLogger();

	public CustomAuthenticationProvider() {
		super();
		this.searchUserService = new SearchUserService();
	}

	private SearchUserService searchUserService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		validateLoginRequest(email, password);
		User user = searchUserService.searchUserByEmail(email);
		validateUser(user);

		if (user.isActiveUser()) {
			return validateCredentials(authentication, password, user);
		} else {
			throw new BadCredentialsException(getErrorMessage(SecurityErrorResponse.ACCOUNT_DEACTIVATED));
		}
	}

	private void validateUser(User user) {
		if (Optional.ofNullable(user).isEmpty()) {
			throw new BadCredentialsException(getErrorMessage(SecurityErrorResponse.INVALID_CREDENTIALS));
		}
	}

	private void validateLoginRequest(String email, String password) {
		if (email.isBlank() || password.isBlank()) {
			throw new BadCredentialsException(getErrorMessage(SecurityErrorResponse.LOGIN_CREDENTIALS_REQUIRED));
		}
	}

	private Authentication validateCredentials(Authentication authentication, String password, User user) {
		SystemServices services = SystemServices.getInstance();
		IPasswordEncoder passwordEncoder = services.getPasswordEncoder();
		if (passwordEncoder.matchPassword(password, user.getPassword())) {
			List<GrantedAuthority> userRoles = new ArrayList<>();
			userRoles.add(new SimpleGrantedAuthority(user.getRoleAsString()));
			Authentication token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
					authentication.getCredentials(), userRoles);
			LOG.debug("Authentication successful");
			return token;
		} else {
			throw new BadCredentialsException(getErrorMessage(SecurityErrorResponse.INVALID_CREDENTIALS));
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}
	
	private String getErrorMessage(SecurityErrorResponse error) {
		return SecurityErrorResponse.getErrorMessage(error);
	}

}
