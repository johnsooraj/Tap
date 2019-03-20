package com.cyspan.tap.commons;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class UserHandler {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static Map<String, String> otpMap = new HashMap<String, String>();

	public static String getToken() {
		String token = UUID.randomUUID().toString();
		return token;
	}

	public static String tokenValidDate() {
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, 1);
		Date tokenValidDate = c.getTime();
		return dateFormat.format(tokenValidDate);
	}

	public static String getOtp(String username) {
		String s = "";
		String numbers = "0123456789";
		Random rndm_method = new Random();
		char[] otp = new char[4];
		for (int i = 0; i < 4; i++) {
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
			System.out.println(otp[i]);
			s = s + otp[i];
		}
		System.out.println("OTP = " + s);
		otpMap.put(username.trim(), s);
		return s;
	}

	public static Boolean chekOtpValidate(String email, String otpPassed) {
		String otp = otpMap.get(email);
		if (otp != null) {
			if (otp.trim().equals(otpPassed.trim())) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else {
			return Boolean.FALSE;
		}
	}

	public static String generateGroupUniqueId(String groupName) {
		groupName = groupName.replaceAll("\\s", "").toUpperCase();
		String uniqueID = UUID.randomUUID().toString();
		return groupName + uniqueID;
	}

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public static String subscriptionAdminCreadentials() {
		int len = 6;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}
