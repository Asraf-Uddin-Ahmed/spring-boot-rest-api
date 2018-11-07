package com.asraf.controllers.advices;

import java.io.IOException;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.asraf.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;

@ControllerAdvice
public class ResponseBodyModifierAdvice implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		String fields = ((ServletServerHttpRequest) request).getServletRequest().getParameter("fields");
		System.out.println(body);
		// response.getHeaders().add("dummy-header", "dummy-value");
		if (StringUtils.isNullOrWhitespace(fields)) {
			return body;
		}

		ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), fields);
		String jsonString = SquigglyUtils.stringify(objectMapper, body);
		try {
			return objectMapper.readTree(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
			return jsonString;
		}
	}
}