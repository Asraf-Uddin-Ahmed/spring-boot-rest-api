package com.asraf.rsrc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.asraf.auth.entities.User;
import com.asraf.rsrc.entities.Student;
import com.asraf.rsrc.properties.AuthProperties;
import com.asraf.rsrc.properties.AwsProperties;
import com.asraf.rsrc.properties.FileStorageProperties;
import com.asraf.rsrc.repositories.persistence.ExtendedQueryDslJpaRepositoryImpl;

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
