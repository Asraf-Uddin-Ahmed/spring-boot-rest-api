package com.asraf.services.email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import lombok.Builder;

public class EmailMessageBuilder {

	private List<InternetAddress> emailTos;

	private InternetAddress emailFrom;

	private InternetAddress emailReplyTo;

	private String emailBody;

	private String emailSubject;

	private boolean isHtml;

	private MimeMessageHelper mimeMessageHelper;

	@Builder
	public EmailMessageBuilder(List<InternetAddress> emailTos, InternetAddress emailFrom, InternetAddress emailReplyTo,
			String emailBody, String emailSubject, boolean isHtml) {
		this.emailTos = emailTos == null ? new ArrayList<>() : emailTos;
		this.emailFrom = emailFrom;
		this.emailReplyTo = emailReplyTo;
		this.emailBody = emailBody;
		this.emailSubject = emailSubject;
		this.isHtml = isHtml;
	}

	public EmailMessageBuilder addEmailTo(String emailAddress, String name) throws UnsupportedEncodingException {
		this.emailTos.add(new InternetAddress(emailAddress, name));
		return this;
	}

	public InternetAddress[] getEmailTos() {
		return emailTos.toArray(new InternetAddress[emailTos.size()]);
	}

	public EmailMessageBuilder buildMail(MimeMessage mimeMessage) throws MessagingException {
		mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		mimeMessageHelper.setTo(this.getEmailTos());
		mimeMessageHelper.setReplyTo(this.emailReplyTo);
		mimeMessageHelper.setText(this.emailBody, this.isHtml);
		mimeMessageHelper.setSubject(this.emailSubject);
		if (this.emailFrom != null) {
			mimeMessageHelper.setFrom(this.emailFrom);
		}
		return this;
	}

	public EmailMessageBuilder addInline(String contentId, File file) throws MessagingException {
		mimeMessageHelper.addInline(contentId, file);
		return this;
	}

	public EmailMessageBuilder addInline(String contentId, ClassPathResource resource) throws MessagingException {
		mimeMessageHelper.addInline(contentId, resource);
		return this;
	}

	public EmailMessageBuilder addAttachment(String attachmentFilename, File file) throws MessagingException {
		mimeMessageHelper.addAttachment(attachmentFilename, file);
		return this;
	}

}
