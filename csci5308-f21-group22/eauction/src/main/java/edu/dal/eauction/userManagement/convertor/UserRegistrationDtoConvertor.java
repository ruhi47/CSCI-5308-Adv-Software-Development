package edu.dal.eauction.userManagement.convertor;

import edu.dal.eauction.userManagement.dto.UserRegistrationDto;
import edu.dal.eauction.userManagement.entities.Address;
import edu.dal.eauction.userManagement.entities.User;

public class UserRegistrationDtoConvertor implements IConvertor<UserRegistrationDto, User> {

	@Override
	public User convert(UserRegistrationDto userRegister) {
		User user = new User();
		user.setFirstName(userRegister.getFirstName());
		user.setLastName(userRegister.getLastName());
		user.setDateOfBirth(userRegister.getLocalDateOfBirth());
		user.setEmail(userRegister.getEmail());
		user.setPassword(userRegister.getPassword());
		Address address = new Address(user, 
									userRegister.getAddressLine1(), 
									userRegister.getAddressLine2(),
									userRegister.getCity(), 
									userRegister.getProvince(), 
									userRegister.getCountry(),
									userRegister.getZipCode());
		user.setAddress(address);
		return user;
	}

}
