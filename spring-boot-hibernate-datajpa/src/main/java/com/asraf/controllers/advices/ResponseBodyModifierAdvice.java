package com.asraf.controllers.advices;

import java.io.IOException;
import java.util.Collection;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.asraf.constants.ControllerAdviceExecutionOrder;
import com.asraf.constants.UserRole;
import com.asraf.constants.UserRoleResponse;
import com.asraf.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;

@ControllerAdvice
@Order(ControllerAdviceExecutionOrder.RESPONSE_BODY_MODIFIER_ADVICE)
public class ResponseBodyModifierAdvice implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		// response.getHeaders().add("dummy-header", "dummy-value");

		String fields = ((ServletServerHttpRequest) request).getServletRequest().getParameter("fields");
		if (StringUtils.isNullOrWhitespace(fields)) {
			return body;
		}

		ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), fields);
		try {
			String jsonString = objectMapper.writerWithView(getViewClass()).writeValueAsString(body);
			return objectMapper.readTree(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Class<?> getViewClass() {

		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
			Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
					.getAuthorities();

			if (authorities.stream().anyMatch(o -> o.getAuthority().equals(UserRole.ADMIN.getAuthority()))) {
				return UserRoleResponse.Admin.class;
			}

		}
		return UserRoleResponse.Anonymous.class;
	}
}