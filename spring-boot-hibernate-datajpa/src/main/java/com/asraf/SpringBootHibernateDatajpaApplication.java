package com.asraf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.asraf.properties.AuthProperties;
import com.asraf.properties.AwsProperties;
import com.asraf.properties.FileStorageProperties;
import com.asraf.repositories.persistence.ExtendedQueryDslJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ExtendedQueryDslJpaRepositoryImpl.class)
@EnableConfigurationProperties({ FileStorageProperties.class, AwsProperties.class, AuthProperties.class })
public class SpringBootHibernateDatajpaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHibernateDatajpaApplication.class, args);
	}
}
