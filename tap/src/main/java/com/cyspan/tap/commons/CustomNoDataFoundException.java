package com.cyspan.tap.commons;

public class CustomNoDataFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public CustomNoDataFoundException(String message) {
		super(message);
	}
}
