package edu.dal.eauction.userManagement.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.dal.eauction.userManagement.validators.AddressValidatorStrategy;
import edu.dal.eauction.userManagement.validators.DateOfBirthValidator;
import edu.dal.eauction.userManagement.validators.EmailValidator;
import edu.dal.eauction.userManagement.validators.IValidationStrategy;
import edu.dal.eauction.userManagement.validators.NameValidator;
import edu.dal.eauction.userManagement.validators.PasswordValidator;

/*
 * This class is use to hold user registration input and utilize in Controller
 */
@Component
public class UserRegistrationDto {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String confirmedPassword;
	private Date dateOfBirth;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String province;
	private String country;
	private String zipCode;

	public UserRegistrationDto() {
		super();
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

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public LocalDate getLocalDateOfBirth() {
		Instant dobInstant = dateOfBirth.toInstant();
		ZonedDateTime zonedDob = dobInstant.atZone(ZoneId.systemDefault());
		return zonedDob.toLocalDate();
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<String> validate() {
		List<String> errors = new ArrayList<>();
		List<IValidationStrategy<UserRegistrationDto>> validator = new ArrayList<>();
		validator.add(new EmailValidator());
		validator.add(new PasswordValidator());
		validator.add(new NameValidator());
		validator.add(new DateOfBirthValidator());
		validator.add(new AddressValidatorStrategy());
		for (IValidationStrategy<UserRegistrationDto> strategy : validator) {
			errors.addAll(strategy.validate(this));
		}
		return errors;
	}

}
