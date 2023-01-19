package edu.dal.eauction.userManagement.convertor;

import edu.dal.eauction.userManagement.dto.UserDto;
import edu.dal.eauction.userManagement.entities.User;

public class UsertoUserDtoConvertor implements IConvertor<User, UserDto> {

	@Override
	public UserDto convert(User user) {
		UserDto userDto = new UserDto();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setDateOfBirth(user.getDateOfBirth());
		userDto.setStatus(user.getStatus());
		userDto.setLastSigninDate(user.getLastSigninDate());
		userDto.setCredits(user.getCredits());
		userDto.setRole(user.getRole());
		return userDto;
	}

}
