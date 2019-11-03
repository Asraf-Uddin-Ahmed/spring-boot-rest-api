package com.asraf.rsrc.services.persistence;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.asraf.rsrc.config.CustomLocaleResolver;
import com.asraf.rsrc.services.MessageSourceService;

@Component
public class MessageSourceServiceImpl implements MessageSourceService {

	private MessageSource messageSource;
	private CustomLocaleResolver customLocaleResolver;
	
	@Autowired
	public MessageSourceServiceImpl(MessageSource messageSource, CustomLocaleResolver customLocaleResolver) {
		this.messageSource = messageSource;
		this.customLocaleResolver = customLocaleResolver;
	}

    public String getMessage(String propertyKey) {
        return messageSource.getMessage(propertyKey, null, LocaleContextHolder.getLocale());
    }

    public String getMessage(String propertyKey, HttpServletRequest httpServletRequest) {
        return messageSource.getMessage(propertyKey, null, customLocaleResolver.resolveLocale(httpServletRequest));
    }
    
}
