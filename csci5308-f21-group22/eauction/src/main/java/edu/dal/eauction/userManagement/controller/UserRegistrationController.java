package edu.dal.eauction.userManagement.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.dal.eauction.userManagement.convertor.IConvertor;
import edu.dal.eauction.userManagement.convertor.UserRegistrationDtoConvertor;
import edu.dal.eauction.userManagement.dto.UserRegistrationDto;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.service.IUserManagementService;

@Controller
public class UserRegistrationController extends BaseController {

	public static final String FORMAT_DOB = "yyyy-MM-dd";

	public UserRegistrationController(IUserManagementService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}

	private IUserManagementService userRegistrationService;

	@InitBinder
	public void binderRegisterUserFormData(WebDataBinder binder) {
		SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat(FORMAT_DOB);
		binder.registerCustomEditor(Date.class, "dateOfBirth", new CustomDateEditor(dateOfBirthFormat, true));
	}

	@GetMapping(value = { "/userRegistration" })
	public String goToUserRegistration() {
		return "userRegistration";
	}

	@PostMapping(value = { "/user/register" })
	public String registerUser(@ModelAttribute("newUser") UserRegistrationDto newUser, Model model) {
		String template = null;
		List<String> errors = newUser.validate();
		if (errors.isEmpty()) {
			IConvertor<UserRegistrationDto, User> convertor = new UserRegistrationDtoConvertor();
			template = processUserRegistration(convertor.convert(newUser), model);
		} else {
			model.addAttribute("message", errors);
			template = "userRegistration";
		}
		return template;
	}

	private String processUserRegistration(User user, Model model) {
		String template = "index";
		try {
			userRegistrationService.registerUser(user);
			model.addAttribute("message", getMessage(ModelResponse.USER_REGISTRATION_SUCCESS));
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			template = "userRegistration";
		}
		return template;
	}

}
