package edu.dal.eauction.emailSender.email.params;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordParams implements IEmailContentParams {

	public ForgotPasswordParams(String userFirstName, String password) {
		this.userFirstName = userFirstName;
		this.password = password;
	}

	private String userFirstName;
	private String password;

	@Override
	public String[] getEmailContentParams() {
		List<String> params = new ArrayList<>();
		params.add(userFirstName);
		params.add(password);
		return params.stream().toArray(String[]::new);
	}
	
}
