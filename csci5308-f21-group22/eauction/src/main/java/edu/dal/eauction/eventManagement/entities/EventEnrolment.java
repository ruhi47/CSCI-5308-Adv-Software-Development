package edu.dal.eauction.eventManagement.entities;

public class EventEnrolment {

	public EventEnrolment() {
		super();
	}

	public EventEnrolment(Integer enrolmentId, Integer eventId, Integer itemId, String userEmail, boolean isActive) {
		this.enrolmentId = enrolmentId;
		this.eventId = eventId;
		this.itemId = itemId;
		this.userEmail = userEmail;
		this.isActive = isActive;
	}

	public EventEnrolment(Integer eventId, Integer itemId, String userEmail, boolean isActive) {
		this.eventId = eventId;
		this.itemId = itemId;
		this.userEmail = userEmail;
		this.isActive = isActive;
	}

	private int enrolmentId;
	private int eventId;
	private int itemId;
	private String userEmail;
	private boolean isActive;

	public int getEnrolmentId() {
		return enrolmentId;
	}

	public void setEnrolmentId(int enrolmentId) {
		this.enrolmentId = enrolmentId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getUserId() {
		return userEmail;
	}

	public void setUserId(String userEmail) {
		this.userEmail = userEmail;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "Event_Enrolment [enrolmentId=" + enrolmentId + ", eventId=" + eventId + ", itemId=" + itemId
				+ ", userEmail=" + userEmail + ", isActive=" + isActive + "]";
	}

}
