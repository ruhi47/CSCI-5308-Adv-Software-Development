package edu.dal.eauction.userManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.dal.eauction.userManagement.service.IUserManagementService;
import edu.dal.eauction.userManagement.service.UserManagementService;

@Controller
public class ForgotPasswordController extends BaseController {

	public ForgotPasswordController(IUserManagementService userManagementService) {
		super();
		this.userManagementService = new UserManagementService();
	}

	private IUserManagementService userManagementService;

	@GetMapping(value = { "/user/forgotPassword" })
	public String goToForgotPassword() {
		return "forgotPassword";
	}

	@RequestMapping(value = { "/user/recoverCredentials" })
	public String recoverCredentials(@RequestParam(value = "email") String email, Model model) {
		String template = null;
		if (email.isBlank()) {
			model.addAttribute("message", getMessage(ModelResponse.EMAIL_REQUIRED));
			template = "forgotPassword";
		} else {
			template = recoverPassword(email, model);
		}
		return template;
	}

	private String recoverPassword(String email, Model model) {
		String template = "index";
		try {
			userManagementService.recoverPassword(email);
			model.addAttribute("message", getMessage(ModelResponse.RECOVERY_EMAIL_SENT));
		} catch (Exception exception) {
			template = "forgotPassword";
			model.addAttribute("message", exception.getMessage());
		}
		return template;
	}

}
