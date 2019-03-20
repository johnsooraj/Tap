package com.cyspan.tap.appconfig;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class MailConfig {

	@Autowired
	Environment environment;

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(environment.getRequiredProperty("host"));
		javaMailSender.setPort(Integer.parseInt(environment.getRequiredProperty("port")));
		javaMailSender.setUsername(environment.getRequiredProperty("username"));
		javaMailSender.setPassword(environment.getRequiredProperty("password"));

		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", environment.getRequiredProperty("mail.transport.protocol"));
		properties.put("mail.smtp.auth", environment.getRequiredProperty("mail.smtp.auth"));
		properties.put("mail.smtp.starttls.enable", environment.getRequiredProperty("mail.smtp.starttls.enable"));
		properties.put("mail.debug", environment.getRequiredProperty("mail.debug"));
		return properties;
	}
}
