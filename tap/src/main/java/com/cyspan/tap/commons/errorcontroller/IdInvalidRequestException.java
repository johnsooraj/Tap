package com.cyspan.tap.commons.errorcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * For GET Request with Invalid Id
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Requested Id Not Valid")
public class IdInvalidRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public IdInvalidRequestException(String message) {
		super(message);
	}
}
