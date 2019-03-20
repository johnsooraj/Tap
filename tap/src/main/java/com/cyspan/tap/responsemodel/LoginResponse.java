package com.cyspan.tap.responsemodel;

import java.io.Serializable;

public class LoginResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private String deviceId;


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
