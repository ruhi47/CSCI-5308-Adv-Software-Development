package edu.dal.eauction.userManagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import edu.dal.eauction.userManagement.entities.UserRole;
import edu.dal.eauction.userManagement.entities.UserStatus;

/*
 * This class is use to send selected User data to UI for display
 */
public class UserDto {

	public UserDto() {
		super();
	}

	private String firstName;
	private String lastName;
	private String email;
	private LocalDate dateOfBirth;
	private UserStatus status;
	private LocalDateTime lastSigninDate;
	private Float credits;
	private UserRole role;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public LocalDateTime getLastSigninDate() {
		return lastSigninDate;
	}

	public void setLastSigninDate(LocalDateTime lastSigninDate) {
		this.lastSigninDate = lastSigninDate;
	}

	public Float getCredits() {
		return credits;
	}

	public void setCredits(Float credits) {
		this.credits = credits;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
