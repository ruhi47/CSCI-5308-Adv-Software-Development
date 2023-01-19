package edu.dal.eauction.emailSender.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.dal.eauction.emailSender.email.params.IEmailContentParams;

public class Email {

	private static final Logger LOG = LogManager.getLogger();
	private static final String EMAIL_TEMPLATE_FOLDER = "emailTemplates/";

	public Email(EmailType emailType, String recepient) {
		super();
		this.recepient = recepient;
		this.emailType = emailType;
	}

	private String recepient;
	private String subject;
	private String content;
	private EmailType emailType;
	private List<Attachment> attachments;

	public String getRecepient() {
		return recepient;
	}

	public void setRecepient(String recepient) {
		this.recepient = recepient;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public EmailType getEmailType() {
		return emailType;
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}

	public boolean hasAttachments() {
		boolean hasAttachment = false;
		if (Objects.nonNull(attachments)) {
			hasAttachment = attachments.isEmpty() ? false : true;
		}
		return hasAttachment;
	}

	/*
	 * Reads the email template and substitutes the params in the email template
	 */
	public void constructEmailContent(IEmailContentParams params) {
		String templateFileName = EmailType.getTemplateFileName(emailType);
		String templateFilePath = EMAIL_TEMPLATE_FOLDER + templateFileName;
		InputStream templateStream = getEmailTemplate(templateFilePath);
		if (Objects.isNull(templateStream)) {
			LOG.error("Error while reading the email template {}", emailType.toString());
			return;
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(templateStream))) {
			String rawLines = reader.lines().collect(Collectors.joining(System.lineSeparator()));
			parseTemplateContent(rawLines, params.getEmailContentParams());
			templateStream.close();
		} catch (IOException ioException) {
			LOG.error("Exception while reading the email temaplate {}", emailType.name());
		}
	}

	private void parseTemplateContent(String rawLines, Object[] params) {
		String formatContent = String.format(rawLines, (Object[]) params);
		String lines[] = formatContent.split(System.lineSeparator());
		subject = lines[0];
		StringBuilder builder = new StringBuilder();
		for (int line = 1; line < lines.length; line++) {
			builder.append(lines[line]).append(System.lineSeparator());
		}
		content = builder.toString();
	}

	private static InputStream getEmailTemplate(String filePath) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream templateContent = classloader.getResourceAsStream(filePath);
		return templateContent;
	}

}
