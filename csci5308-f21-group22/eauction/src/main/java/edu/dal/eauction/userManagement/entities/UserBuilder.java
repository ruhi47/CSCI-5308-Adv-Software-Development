package edu.dal.eauction.userManagement.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBuilder {

	private int id;
	private String firstName;
	private String lastName;
	private LocalDateTime createdDate;
	private String password;
	private String email;
	private Address address;
	private LocalDate dateOfBirth;
	private UserStatus status;
	private LocalDateTime lastSigninDate;
	private Float credits;
	private UserRole role;

	public UserBuilder id(int id) {
		this.id = id;
		return this;
	}

	public UserBuilder firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public UserBuilder lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserBuilder createdDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public UserBuilder email(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder password(String password) {
		this.password = password;
		return this;
	}

	public UserBuilder address(Address address) {
		this.address = address;
		return this;
	}

	public UserBuilder dateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public UserBuilder lastSigninDate(LocalDateTime lastSigninDate) {
		this.lastSigninDate = lastSigninDate;
		return this;
	}

	public UserBuilder status(String status) {
		this.status = UserStatus.valueOf(status);
		return this;
	}

	public UserBuilder credits(Float credits) {
		this.credits = credits;
		return this;
	}

	public UserBuilder role(UserRole role) {
		this.role = role;
		return this;
	}

	public User build() {
		return new User(id, firstName, lastName, email, password, dateOfBirth, status, createdDate, lastSigninDate,
				credits, role, address);
	}
}
