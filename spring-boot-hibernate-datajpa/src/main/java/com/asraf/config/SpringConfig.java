package com.asraf.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SpringConfig {
	@Bean
	@Scope(value = "prototype")
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
