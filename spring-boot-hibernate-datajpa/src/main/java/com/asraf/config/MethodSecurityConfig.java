package com.asraf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

import com.asraf.utils.JwtUtils;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

	@Bean
	public JwtUtils jwtUtils() {
		return new JwtUtils();
	}

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		OAuth2MethodSecurityExpressionHandler expressionHandler = new OAuth2MethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(new ClaimPermissionEvaluator(jwtUtils()));
		return expressionHandler;
	}

}
