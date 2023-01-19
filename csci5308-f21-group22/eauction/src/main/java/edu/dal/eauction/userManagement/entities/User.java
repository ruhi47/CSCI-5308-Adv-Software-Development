package edu.dal.eauction.userManagement.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import edu.dal.eauction.coreServices.SystemServices;
import edu.dal.eauction.userManagement.security.IPasswordEncoder;

public class User {
	
	private static final float DEFAULT_INITIAL_CREDITS = 4000f;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String password, LocalDate dateOfBirth,
			UserStatus status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.status = status;
	}
	
	public User(int id, String firstName, String lastName, String email, String password, LocalDate dateOfBirth,
			UserStatus status, LocalDateTime createdDate, LocalDateTime lastSigninDate, Float credits, UserRole role, Address address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.status = status;
		this.createdDate = createdDate;
		this.lastSigninDate = lastSigninDate;
		this.credits = credits;
		this.role = role;
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDateTime getLastSigninDate() {
		return lastSigninDate;
	}

	public void setLastSigninDate(LocalDateTime lastSigninDate) {
		this.lastSigninDate = lastSigninDate;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = UserStatus.valueOf(status);
	}

	public void setActiveStatus() {
		this.status = UserStatus.ACTIVE;
	}

	public boolean isActiveUser() {
		return UserStatus.ACTIVE.equals(status);
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

	public String getRoleAsString() {
		return role.name();
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setDefaultUserRole() {
		this.role = UserRole.USER;
	}

	public void hashPassword() {
		SystemServices services = SystemServices.getInstance();
		IPasswordEncoder passwordEncoder = services.getPasswordEncoder();
		password = passwordEncoder.encodePassword(password);
	}

	public void setDefaultsForNewUser() {
		createdDate = LocalDateTime.now();
		credits = DEFAULT_INITIAL_CREDITS;
		hashPassword();
		setDefaultUserRole();
		setActiveStatus();
	}

}
