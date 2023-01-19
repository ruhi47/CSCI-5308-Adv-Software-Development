package edu.dal.eauction.userManagement.validators;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

public class DateOfBirthValidator extends UserRegistrationValidationStrategy {

	@Override
	public List<String> validate(UserRegistrationDto registerDto) {
		Date dateOfBirth = registerDto.getDateOfBirth();
		if (Objects.isNull(dateOfBirth)) {
			addError(RegistrationError.DATE_OF_BIRTH_BLANK);
		} else if (isLessThan18Years(dateOfBirth)) {
			addError(RegistrationError.USER_ATLEAST_18_YEARS);
		}
		return errors;
	}

	private boolean isLessThan18Years(Date dateOfBirth) {
		Calendar threshold = Calendar.getInstance();
		threshold.add(Calendar.YEAR, -18);
		Date thresholdDate = threshold.getTime();
		return dateOfBirth.after(thresholdDate);
	}

}
