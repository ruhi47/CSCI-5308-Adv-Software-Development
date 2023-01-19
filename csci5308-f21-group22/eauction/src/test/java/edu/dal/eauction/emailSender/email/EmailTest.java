package edu.dal.eauction.emailSender.email;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.emailSender.email.params.ForgotPasswordParams;
import edu.dal.eauction.emailSender.email.params.IEmailContentParams;
import edu.dal.eauction.emailSender.email.params.ItemAddedParams;

class EmailTest {

	@Test
	void testContructForgotPasswordEmail() {
		String userFirstName = "Akanksha";
		String defaultPassword = "password";
		IEmailContentParams forgotPasswordParams = new ForgotPasswordParams(userFirstName, defaultPassword);
		Email email = new Email(EmailType.FORGOT_PASSWORD_EMAIL, "akanksha@example.com");
		email.constructEmailContent(forgotPasswordParams);
		assertNotNull(email.getContent());
		assertTrue(email.getContent().contains(userFirstName));
		assertTrue(email.getContent().contains(defaultPassword));
	}

	@Test
	void testContructItemAddedEmail() {
		String userFirstName = "Akanksha";
		String itemName = "Jwellery";
		String itemDescription = "This is a jwellery";
		IEmailContentParams forgotPasswordParams = new ItemAddedParams(userFirstName, itemName, itemDescription);
		Email email = new Email(EmailType.ITEM_ADDED, "akanksha@example.com");
		email.constructEmailContent(forgotPasswordParams);
		assertNotNull(email.getContent());
		assertTrue(email.getContent().contains(userFirstName));
		assertTrue(email.getContent().contains(itemName));
		assertTrue(email.getContent().contains(itemDescription));
	}

}
