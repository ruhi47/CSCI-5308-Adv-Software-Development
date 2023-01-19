package edu.dal.eauction.emailSender.email.params;

import java.util.ArrayList;
import java.util.List;

public class AuctionWonParams implements IEmailContentParams {

	public AuctionWonParams(String userFirstName, String itemName, String eventDate, String credits) {
		super();
		this.userFirstName = userFirstName;
		this.itemName = itemName;
		this.eventDate = eventDate;
		this.credits = credits;
	}

	private String userFirstName;
	private String eventDate;
	private String itemName;	
	private String itemCost;
	private String credits;

	@Override
	public String[] getEmailContentParams() {
		List<String> params = new ArrayList<>();
		params.add(userFirstName);
		params.add(eventDate);
		params.add(itemName);
		params.add(itemCost);
		params.add(credits);
		return params.stream().toArray(String[]::new);
	}

}
