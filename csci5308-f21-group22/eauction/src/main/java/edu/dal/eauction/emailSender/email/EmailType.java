package edu.dal.eauction.emailSender.email;

public enum EmailType {
	
	USER_REGISTRATION("userRegistration.txt"),
	FORGOT_PASSWORD_EMAIL("forgotPassword.txt"),
	ITEM_ADDED("itemAdded.txt"),
	EVENT_SUBSCRIPTION("eventSubscription.txt"),
	AUCTION_WON("auctionWon.txt"),
	AUCTION_EVENT_CANCELLATION("auctionEventCancellation.txt");

	public final String templateFileName;
	
	EmailType(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public static String getTemplateFileName(EmailType emailType) {
		return emailType.templateFileName;
	}
	
}
