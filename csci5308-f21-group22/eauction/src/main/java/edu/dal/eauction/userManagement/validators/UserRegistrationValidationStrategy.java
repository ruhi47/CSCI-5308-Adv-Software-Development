package edu.dal.eauction.userManagement.validators;

import java.util.ArrayList;
import java.util.List;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

public abstract class UserRegistrationValidationStrategy implements IValidationStrategy<UserRegistrationDto> {

	public UserRegistrationValidationStrategy() {
		this.errors = new ArrayList<>();
	}

	protected List<String> errors;
	
	public void addError(RegistrationError error) {
		String message = RegistrationError.getErrorMessage(error);
		errors.add(message);
	}
	
	public abstract List<String> validate(UserRegistrationDto registerDto); 

}
