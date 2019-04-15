package com.asraf.templates;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;

import com.asraf.services.email.EmailMessageBuilder;

public class BaseTemplate {

	private Map<String, ClassPathResource> classPathResourceMapper = new HashMap<>();

	public void loadInlineImages(EmailMessageBuilder emailMessageBuilder) throws MessagingException {
		for (Map.Entry<String, ClassPathResource> entry : classPathResourceMapper.entrySet()) {
			emailMessageBuilder.addInline(entry.getKey(), entry.getValue());
		}
	}

	protected BaseTemplate addInlineImage(String contentId, String resourecePath) {
		ClassPathResource cpResource = new ClassPathResource(resourecePath);
		classPathResourceMapper.put(contentId, cpResource);
		return this;
	}

}
