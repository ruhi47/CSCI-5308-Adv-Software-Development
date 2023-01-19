package edu.dal.eauction.userManagement.validators;

import java.util.List;

public interface IValidationStrategy<T extends Object> {

	public List<String> validate(T object);
	
}
