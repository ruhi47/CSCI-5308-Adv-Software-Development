package edu.dal.eauction.userManagement.dto;

/*
 * This class is use to hold user Login input and utilize in Controller
 */
public class LoginDto {

	public LoginDto() {
		super();
	}

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
