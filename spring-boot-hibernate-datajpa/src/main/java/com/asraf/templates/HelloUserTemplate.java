package com.asraf.templates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class HelloUserTemplate extends BaseTemplate {

	private SpringTemplateEngine templateEngine;

	@Autowired
	public HelloUserTemplate(SpringTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String createTemplate(String name) {

		Context context = new Context();
		context.setVariable("message", "Enjoy Thymeleaf");
		context.setVariable("name", name);
		context.setVariable("link", "https://www.google.com/");
		String text = templateEngine.process("HelloUser.html", context);

		super.addInlineImage("id101", "images/logo.png");

		return text;
	}

}
