package com.cyspan.tap.appconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FirebaseAdminConfig {
	
	static Logger logger = Logger.getLogger(FirebaseAdminConfig.class.getName());

	@Bean
	public FirebaseMessaging getFirebaseMessaging() {
		FileInputStream serviceAccount;
		try {
			File file = ResourceUtils.getFile("classpath:firebase-adminsdk.json");
			serviceAccount = new FileInputStream(file);
			FirebaseOptions options;
			options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
			FirebaseApp.initializeApp(options);
			FirebaseMessaging obj = FirebaseMessaging.getInstance();
			logger.info("firebase bean craete success!");
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("firebase bean craetion failed");
			logger.error(e.getMessage());
			return null;
		}
	}
}
