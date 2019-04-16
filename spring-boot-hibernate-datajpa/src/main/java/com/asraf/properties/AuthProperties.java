package com.asraf.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class AuthProperties {
	private String checkTokenEndpoint;
	private String clientId;
	private String clientSecret;
	private Swagger swagger = new Swagger();
	private Jdbc jdbc = new Jdbc();

	@Getter
	@Setter
	public class Swagger {
		private String endpoint;
		private String clientId;
		private String clientSecret;
	}

	@Getter
	@Setter
	public class Jdbc {
		private String driverClassName;
		private String url;
		private String username;
		private String password;
	}
	
}