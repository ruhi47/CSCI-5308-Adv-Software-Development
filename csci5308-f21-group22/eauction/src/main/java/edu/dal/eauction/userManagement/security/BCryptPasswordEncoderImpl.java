package edu.dal.eauction.userManagement.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderImpl implements IPasswordEncoder {

	private static BCryptPasswordEncoderImpl encoderImpl;

	private static BCryptPasswordEncoder encoder;

	private BCryptPasswordEncoderImpl() {
		encoder = new BCryptPasswordEncoder();
	}

	static {
		try {
			encoderImpl = new BCryptPasswordEncoderImpl();
		} catch (Exception exception) {
			throw new RuntimeException("Exception while creating singleton instance:" + exception.getMessage());
		}
	}

	public static BCryptPasswordEncoderImpl getInstance() {
		return encoderImpl;
	}

	@Override
	public String encodePassword(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	@Override
	public boolean matchPassword(String rawPassword, String encodedPassword) {
		return encoder.matches(rawPassword, encodedPassword);
	}

}
