package edu.dal.eauction.emailSender;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public final class SmtpConfiguration extends EmailConfiguration {

	private static final String SMTP_PORT = "587";	
	private static final String SMTP_HOST = "smtp.gmail.com";	
	private static final String ENV_USERNAME_KEY = "ENV_USERNAME_KEY";	
	private static final String ENV_PASSWORD_KEY = "ENV_PASSWORD_KEY";

	public SmtpConfiguration() {
		super();
	}

	@Override
	protected Properties getConfigProperties() {
		Properties properties = System.getProperties();		
		properties.put("mail.smtp.host", SMTP_HOST);
		properties.put("mail.smtp.port", SMTP_PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		return properties;
	}

	private String getPassword() {
		return System.getenv(ENV_PASSWORD_KEY);
	}

	String getEmailSender() {
		return System.getenv(ENV_USERNAME_KEY);
	}

	public Authenticator getAuthenticator() {
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getEmailSender(), getPassword());
			}
		};
		return authenticator;
	}

}
