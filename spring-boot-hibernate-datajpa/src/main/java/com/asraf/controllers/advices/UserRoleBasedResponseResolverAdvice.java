package com.asraf.controllers.advices;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import com.asraf.constants.ControllerAdviceExecutionOrder;
import com.asraf.constants.UserRole;
import com.asraf.constants.UserRoleResponse;

@ControllerAdvice
@Order(ControllerAdviceExecutionOrder.USER_ROLE_BASED_RESPONSE_RESOLVER_ADVICE)
public class UserRoleBasedResponseResolverAdvice extends AbstractMappingJacksonResponseBodyAdvice {

	private final List<String> IGNORE_PATH_LIST = Arrays.asList("/v2/api-docs", "/swagger-resources",
			"/swagger-resources/configuration/security", "/swagger-resources/configuration/ui");

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return super.supports(returnType, converterType);
	}

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
			MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

		if (IGNORE_PATH_LIST.contains(request.getURI().getPath())) {
			return;
		}

		bodyContainer.setSerializationView(getViewClass());
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