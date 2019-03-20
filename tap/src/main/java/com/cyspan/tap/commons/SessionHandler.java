package com.cyspan.tap.commons;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.login.services.LoginServices;
import com.cyspan.tap.responsemodel.LoginResponse;
import com.cyspan.tap.user.model.UsersModel;

@Repository
public class SessionHandler {

	@Autowired
	LoginServices loginService;
	
	static HashMap<String, String> sessionMap = new HashMap<String, String>();

	public Object userDetails(UsersModel model) {
		LoginResponse response = new LoginResponse();
		return response;
	}

	public String getDeviceId(String deviceId) {
		return sessionMap.get(deviceId);
	}

	public boolean removeDevice(String deviceId) {
		if (sessionMap.containsKey(deviceId)) {
			sessionMap.remove(deviceId);
		}

		return true;
	}

	public boolean userExist(String deviceId) {
		return loginService.userExist(deviceId);
	}

}
