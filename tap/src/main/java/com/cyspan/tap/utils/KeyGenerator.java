package com.cyspan.tap.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {
	private static SecureRandom random = new SecureRandom();

	public static String nextSessionId() {
		return new BigInteger(130, random).toString(16);
	}

	public static String getKey() {
		return "" + System.currentTimeMillis();
	}

	public static void main(String args[]) {
		System.out.println(System.currentTimeMillis());
	}
}