package com.asraf.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
public class AwsProperties {
	private String accessKey;
	private String secretKey;
	private S3 s3 = new S3();

	@Getter
	@Setter
	public class S3 {
		private String region;
		private String bucket;
		private Presigned presigned = new Presigned();

		@Getter
		@Setter
		public class Presigned {
			private int expiration;
		}
	}
}