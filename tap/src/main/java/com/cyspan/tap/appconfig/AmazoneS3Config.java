package com.cyspan.tap.appconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@PropertySource("classpath:aws.s3.properties")
public class AmazoneS3Config {

	@Autowired
	Environment environment;

	@Bean
	public AmazonS3 amazoneS3Client() throws Exception {

		String accessKeyId = environment.getRequiredProperty("accessKeyId");
		String secretAccessKey = environment.getRequiredProperty("secretAccessKey");

		BasicAWSCredentials creds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
		AmazonS3 amazoneS3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(Regions.AP_SOUTH_1).build();
		return amazoneS3Client;

	}

	@Bean
	@Scope
	HttpPostNotification tapNotification() {
		return new HttpPostNotification();
	}
}
