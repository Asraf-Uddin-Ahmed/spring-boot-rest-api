package com.asraf.services.email;

import javax.mail.internet.MimeMessage;

public interface EmailSenderService {

	MimeMessage getMimeMessage();

	void send();

}
