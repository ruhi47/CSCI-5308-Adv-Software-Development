package edu.dal.eauction.emailSender;

import edu.dal.eauction.emailSender.email.Email;

public interface IAsyncEmailSenderService extends Runnable {
	
	public void sendEmail(Email email);

}
