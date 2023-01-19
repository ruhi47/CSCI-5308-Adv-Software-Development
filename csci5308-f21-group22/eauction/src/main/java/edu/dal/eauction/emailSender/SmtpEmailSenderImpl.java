package edu.dal.eauction.emailSender;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.dal.eauction.coreServices.IThreadPoolExecutor;
import edu.dal.eauction.coreServices.SystemServices;
import edu.dal.eauction.emailSender.email.Attachment;
import edu.dal.eauction.emailSender.email.Email;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailSenderImpl implements IAsyncEmailSenderService {

	private static final Logger LOG = LogManager.getLogger();

	public SmtpEmailSenderImpl() {
		super();
		initialise();
	}
	
	private IThreadPoolExecutor executor;
	private Message message;
	private SmtpConfiguration emailConfig;

	private void initialise() {
		SystemServices services = SystemServices.getInstance();
		executor = services.getThreadPoolExecutor();
		emailConfig = new SmtpConfiguration();
	}

	@Override
	public void sendEmail(Email email) {
		Authenticator authenticator = emailConfig.getAuthenticator();
		Properties properties = emailConfig.getConfigProperties();
		Session session = Session.getDefaultInstance(properties, authenticator);
		constructTransportMessage(email, session);
		if (Objects.nonNull(message)) {
			executor.runTask(this);
		}
	}

	private void constructTransportMessage(Email email, Session session) {
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailConfig.getEmailSender()));
			message.addRecipient(RecipientType.TO, new InternetAddress(email.getRecepient()));
			message.setSubject(email.getSubject());
			MimeBodyPart bodyContentPart = new MimeBodyPart();
			bodyContentPart.setContent(email.getContent(), "text/plain; charset=utf-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyContentPart);
			if (email.hasAttachments()) {
				addAttachments(email.getAttachments(), multipart);
			}
			message.setContent(multipart);
		} catch (MessagingException messagingException) {
			LOG.error("Error while constructing the email message for email type {}", email.getEmailType());
			messagingException.printStackTrace();
		} catch (IOException e) {
			LOG.error("Unable to retrieve attachment for email type {}", email.getEmailType());
		}
	}

	private void addAttachments(List<Attachment> attachments, Multipart multipart)
			throws IOException, MessagingException {
		for (Attachment attachment : attachments) {
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			attachmentBodyPart.attachFile(attachment.getAttachment());
			multipart.addBodyPart(attachmentBodyPart);
		}
	}

	@Override
	public void run() {
		try {
			Transport.send(message);
			LOG.info("Email has been sent successfully");
		} catch (MessagingException messagingException) {
			LOG.error("Exception while sending email");
			messagingException.printStackTrace();
		}
	}

}
