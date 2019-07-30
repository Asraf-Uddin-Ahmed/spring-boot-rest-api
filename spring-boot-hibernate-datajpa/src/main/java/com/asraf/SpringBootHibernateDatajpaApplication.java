package com.asraf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.asraf.entities.Student;
import com.asraf.entities.User;
import com.asraf.properties.AuthProperties;
import com.asraf.properties.AwsProperties;
import com.asraf.properties.FileStorageProperties;
import com.asraf.repositories.persistence.ExtendedQueryDslJpaRepositoryImpl;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(repositoryBaseClass = ExtendedQueryDslJpaRepositoryImpl.class)
@EnableConfigurationProperties({ FileStorageProperties.class, AwsProperties.class, AuthProperties.class })
@EntityScan(basePackageClasses = { Student.class, User.class })
public class SpringBootHibernateDatajpaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHibernateDatajpaApplication.class, args);
	}
	
}
