package edu.dal.eauction.emailSender.email.params;

import java.util.ArrayList;
import java.util.List;

public class AuctionEventCancelledParam implements IEmailContentParams {

	public AuctionEventCancelledParam(String userFirstName, String itemName, String eventDate) {
		this.userFirstName = userFirstName;
		this.eventDate = itemName;
		this.eventDate = eventDate;
	}
	
	private String userFirstName;
	private String itemName;
	private String eventDate;

	@Override
	public String[] getEmailContentParams() {
		List<String> params = new ArrayList<>();
		params.add(userFirstName);
		params.add(itemName);
		params.add(eventDate);		
		return params.stream().toArray(String[]::new);
	}

}
