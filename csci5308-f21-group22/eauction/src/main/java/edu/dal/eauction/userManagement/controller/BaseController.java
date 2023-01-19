package edu.dal.eauction.userManagement.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.dal.eauction.userManagement.dto.LoginDto;
import edu.dal.eauction.userManagement.dto.UserRegistrationDto;

@ControllerAdvice
public class BaseController {
	
	protected String getMessage(ModelResponse reponse) {
		return ModelResponse.getMessage(reponse);
	}
	
	@ModelAttribute("newUser")
	public UserRegistrationDto userRegistrationModelAttribute() {
		return new UserRegistrationDto();
	}

	@ModelAttribute("login")
	public LoginDto userLoginModelAttribute() {
		return new LoginDto();
	}

}
