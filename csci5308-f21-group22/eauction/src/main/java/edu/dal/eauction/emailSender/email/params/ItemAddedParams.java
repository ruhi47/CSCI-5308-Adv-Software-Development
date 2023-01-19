package edu.dal.eauction.emailSender.email.params;

import java.util.ArrayList;
import java.util.List;

public class ItemAddedParams implements IEmailContentParams {
	
	public ItemAddedParams(String userFirstName, String itemName, String itemDescription) {
		this.userFirstName = userFirstName;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
	}

	private String userFirstName;
	private String itemName;
	private String itemDescription;

	@Override
	public String[] getEmailContentParams() {
		List<String> params = new ArrayList<>();
		params.add(userFirstName);
		params.add(itemName);
		params.add(itemDescription);
		return params.stream().toArray(String[]::new);
	}

}
