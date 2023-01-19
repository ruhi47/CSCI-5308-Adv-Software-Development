package edu.dal.eauction.userManagement.security;

public interface IPasswordEncoder {

	public String encodePassword(String rawPassword);

	public boolean matchPassword(String rawPassword, String encodedPassword);

}
