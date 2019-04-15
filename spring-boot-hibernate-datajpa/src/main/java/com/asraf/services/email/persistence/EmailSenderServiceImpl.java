package com.asraf.services.email.persistence;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.services.email.EmailSenderService;

@Service
@Transactional
public class EmailSenderServiceImpl implements EmailSenderService {

	private JavaMailSender javaMailSender;
	private MimeMessage mimeMessage;

	@Autowired
	public EmailSenderServiceImpl(JavaMailSender javaMailSender) throws MessagingException {
		this.javaMailSender = javaMailSender;
		mimeMessage = javaMailSender.createMimeMessage();
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	public void send() {
		javaMailSender.send(mimeMessage);
	}

}
