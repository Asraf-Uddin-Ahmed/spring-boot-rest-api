package com.asraf.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.asraf.constants.ScopeTypes;
import com.asraf.properties.AuthProperties;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	@Autowired
	private AuthProperties authProperties;

	private static final String RESOURCE_ID = null;
	private static final String SECURED_PATTERN = "/**";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).tokenServices(tokenServices());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
			.antMatchers(SECURED_PATTERN).and().authorizeRequests()
			// TODO: To enabling security, remove SECURED_PATTERN from below line
			.antMatchers(SECURED_PATTERN, "/v2/api-docs", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll()
			.antMatchers(HttpMethod.POST, SECURED_PATTERN).access(ScopeTypes.WRITE)
			.antMatchers(HttpMethod.PUT, SECURED_PATTERN).access(ScopeTypes.WRITE)
			.antMatchers(HttpMethod.DELETE, SECURED_PATTERN).access(ScopeTypes.DELETE)
			.antMatchers(HttpMethod.GET, SECURED_PATTERN).access(ScopeTypes.READ)
			;
	}

	/* For resource server configuration with auth server */
//    @Primary
//	@Bean
//	public RemoteTokenServices tokenServices() {
//		final RemoteTokenServices tokenService = new RemoteTokenServices();
//		tokenService.setCheckTokenEndpointUrl(authProperties.getCheckTokenEndpoint());
//		tokenService.setClientId(authProperties.getClientId());
//		tokenService.setClientSecret(authProperties.getClientSecret());
//		return tokenService;
//	}

	/* For resource server configuration without auth server */
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

	/* JDBC token store configuration */

	@Bean
	public DataSource authDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(authProperties.getJdbc().getDriverClassName());
		dataSource.setUrl(authProperties.getJdbc().getUrl());
		dataSource.setUsername(authProperties.getJdbc().getUsername());
		dataSource.setPassword(authProperties.getJdbc().getPassword());
		return dataSource;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(authDataSource());
	}

}