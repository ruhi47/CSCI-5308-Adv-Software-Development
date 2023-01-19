package edu.dal.eauction.userManagement.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.dal.eauction.userManagement.entities.UserRole;
import edu.dal.eauction.userManagement.service.IUserManagementService;
import edu.dal.eauction.userManagement.service.UserManagementService;

@Controller
public class AuthenticationController extends BaseController {

	private final static Integer MAX_SESSION_DURATION_SECONDS = 15 * 60;

	public AuthenticationController() {
		super();
		this.userManagementService = new UserManagementService();
	}

	private IUserManagementService userManagementService;

	@GetMapping(value = { "/userLogin" })
	public String goToUserLoginPage() {
		return "login";
	}

	@GetMapping(value = { "/user/loginSuccess" })
	public String goToUserHome(HttpSession session) {
		String template = null;
		session.setMaxInactiveInterval(MAX_SESSION_DURATION_SECONDS);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			String username = authentication.getPrincipal().toString();
			userManagementService.processPostLoginRules(username);
			template = getDashboardTemplateByRole(authentication.getAuthorities());
		}
		return template;
	}

	@GetMapping(value = { "/logout" })
	public String logout(Model model, HttpSession session) {
		model.addAttribute("message", getMessage(ModelResponse.LOGOUT_MESSAGE));
		session.invalidate();
		return "index";
	}

	private String getDashboardTemplateByRole(Collection<? extends GrantedAuthority> userRole) {
		String template = null;
		String roleAdmin = UserRole.ADMIN.name();
		SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(roleAdmin);
		if (userRole.contains(adminAuthority)) {
			template = "adminDashboard";
		} else {
			template = "userDashboard";
		}
		return template;
	}

}
