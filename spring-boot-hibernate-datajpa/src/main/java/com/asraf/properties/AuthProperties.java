package com.asraf.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class AuthProperties {
	private Swagger swagger = new Swagger();

	@Getter
	@Setter
	public class Swagger {
		private String endpoint;
		private String clientId;
		private String clientSecret;
	}

}