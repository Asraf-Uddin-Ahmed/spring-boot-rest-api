package com.asraf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.asraf.properties.AwsProperties;

@Configuration
public class AWSConfiguration {

	private final String accessKey;
	private final String secretKey;
	private final String s3Region;

	@Autowired
	public AWSConfiguration(AwsProperties awsProperties) {
		this.accessKey = awsProperties.getAccessKey();
		this.secretKey = awsProperties.getSecretKey();
		this.s3Region = awsProperties.getS3().getRegion();
	}

	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	@Bean
	public AmazonS3 amazonS3(AWSCredentials awsCredentials) {
		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(s3Region).build();
	}
}