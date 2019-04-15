package com.asraf.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.asraf.properties.AuthProperties;

import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!prod")
public class SwaggerConfig implements WebMvcConfigurer  {
	
	private final String CLIENT_ID;
	private final String CLIENT_SECRET;
	private final String AUTH_SERVER;

	@Autowired
	public SwaggerConfig(AuthProperties authProperties) {
		CLIENT_ID = authProperties.getSwagger().getClientId();
		CLIENT_SECRET = authProperties.getSwagger().getClientSecret();
		AUTH_SERVER = authProperties.getEndpoint();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");
	 
	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
    
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.any())              
          //.apis(RequestHandlerSelectors.basePackage("com.asraf.controllers"))
          //.apis(RequestHandlerSelectors.basePackage("org.springframework.security.oauth2.provider.endpoint"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo())
          .securitySchemes(Arrays.asList(securityScheme()))
          .securityContexts(Arrays.asList(securityContext()));
    }
    
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
            .clientId(CLIENT_ID)
            .clientSecret(CLIENT_SECRET)
            .scopeSeparator(" ")
            .useBasicAuthenticationWithAccessCodeGrant(true)
            .build();
    }
    
    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
            .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/oauth/token", "oauthtoken"))
            .tokenRequestEndpoint(
              new TokenRequestEndpoint(AUTH_SERVER + "/oauth/authorize", CLIENT_ID, CLIENT_ID))
            .build();
     
        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
            .grantTypes(Arrays.asList(grantType))
            .scopes(Arrays.asList(scopes()))
            .build();
        return oauth;
    }
    
    private SecurityContext securityContext() {
        return SecurityContext.builder()
          .securityReferences(
            Arrays.asList(new SecurityReference("spring_oauth", scopes())))
//          .forPaths(PathSelectors.regex("/foos.*"))
          .build();
    }
    
    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = { 
          new AuthorizationScope("read", "for read operations"), 
          new AuthorizationScope("write", "for write operations"), 
          new AuthorizationScope("delete", "for delete operations") };
        return scopes;
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "My REST API",
          "Some custom description of API.", 
          "v1.0",
          "Terms of service URL", 
          new Contact("Asraf Uddin Ahmed (Ratul)", "https://github.com/Asraf-Uddin-Ahmed", "13ratul@gmail.com"), 
          "License of API",
          "API license URL", 
          Collections.emptyList());
    }
   
}
