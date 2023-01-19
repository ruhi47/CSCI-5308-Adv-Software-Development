package edu.dal.eauction.userManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.dal.eauction.userManagement.convertor.IConvertor;
import edu.dal.eauction.userManagement.convertor.UsertoUserDtoConvertor;
import edu.dal.eauction.userManagement.dto.UserDto;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.entities.UserStatus;
import edu.dal.eauction.userManagement.service.SearchUserService;

@Controller
public class AdminDashboardController {

	public AdminDashboardController() {
		super();
		this.searchUserService = new SearchUserService();
	}

	private SearchUserService searchUserService;

	@GetMapping(value = { "/admin/users" })
	public String getUsersByStatus(@RequestParam String status, Model model) {
		List<User> users = searchUserService.searchUserByStatus(UserStatus.valueOf(status));
		List<UserDto> userDtos = new ArrayList<>();
		IConvertor<User, UserDto> convertor = new UsertoUserDtoConvertor();
		for (User user : users) {
			userDtos.add(convertor.convert(user));
		}
		model.addAttribute("users", userDtos);
		return "adminDashboard";
	}

	@RequestMapping(value = { "/admin/searchUser" })
	public String searchUser(@RequestParam(value = "email", required = true) String email, Model model) {
		User user = searchUserService.searchUserByEmail(email);
		IConvertor<User, UserDto> convertor = new UsertoUserDtoConvertor();
		UserDto userDto = convertor.convert(user);
		model.addAttribute("users", userDto);
		return "adminDashboard";
	}
	
	@RequestMapping(value = { "/user/viewDetails" })
	public String viewUserDetails(@RequestParam(value = "email", required = true) String email, Model model) {
		User user = searchUserService.searchUserByEmail(email);
		IConvertor<User, UserDto> convertor = new UsertoUserDtoConvertor();
		UserDto userDto = convertor.convert(user);
		model.addAttribute("user", userDto);
		return "userProfile";
	}

}
