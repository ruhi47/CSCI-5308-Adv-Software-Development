package edu.dal.eauction.emailSender.email.params;

import java.util.ArrayList;
import java.util.List;

public class EventSubscriptionParams implements IEmailContentParams {

	public EventSubscriptionParams(String userFirstName, String itemName, String eventStartTime, String eventEndTime,
			String credits) {
		super();
		this.userFirstName = userFirstName;
		this.itemName = itemName;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.credits = credits;
	}

	private String userFirstName;
	private String itemName;
	private String eventStartTime;
	private String eventEndTime;
	private String credits;

	@Override
	public String[] getEmailContentParams() {
		List<String> params = new ArrayList<>();
		params.add(userFirstName);
		params.add(itemName);
		params.add(eventStartTime);
		params.add(eventEndTime);
		params.add(credits);
		return params.stream().toArray(String[]::new);
	}
}
