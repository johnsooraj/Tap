package com.cyspan.tap.commons.errorcontroller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.commons.logging.Logger;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
@RestController
public class ExceptionControllerAdvice {

	@Autowired
	Logger logger;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponsePOJO> exceptionHandler(Exception e, HttpServletRequest request) {
		logger.error(e.getMessage());
		e.printStackTrace();
		ErrorResponsePOJO errorResponsePOJO = new ErrorResponsePOJO();
		errorResponsePOJO.setErrorCode("" + HttpStatus.PRECONDITION_FAILED.value());
		errorResponsePOJO.setDeveloperMessage(e.getMessage());
		errorResponsePOJO.setUserMessage(e.getMessage());
		errorResponsePOJO.setUrl("" + request.getRequestURL());
		return new ResponseEntity<ErrorResponsePOJO>(errorResponsePOJO, HttpStatus.OK);
	}

	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<ErrorResponsePOJO> jsonEexceptionHandler(JsonMappingException e, HttpServletRequest request) {
		logger.error(e.getMessage());
		e.printStackTrace();
		ErrorResponsePOJO errorResponsePOJO = new ErrorResponsePOJO();
		errorResponsePOJO.setErrorCode("" + HttpStatus.PRECONDITION_FAILED.value());
		errorResponsePOJO.setDeveloperMessage(e.getMessage());
		errorResponsePOJO.setUserMessage(e.getMessage());
		errorResponsePOJO.setUrl("" + request.getRequestURL());
		return new ResponseEntity<ErrorResponsePOJO>(errorResponsePOJO, HttpStatus.OK);
	}

	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<ErrorResponsePOJO> jsonParseEexceptionHandler(JsonParseException e,
			HttpServletRequest request) {
		logger.error(e.getMessage());
		e.printStackTrace();
		ErrorResponsePOJO errorResponsePOJO = new ErrorResponsePOJO();
		errorResponsePOJO.setErrorCode("" + HttpStatus.PRECONDITION_FAILED.value());
		errorResponsePOJO.setDeveloperMessage(e.getMessage());
		errorResponsePOJO.setUserMessage(e.getMessage());
		errorResponsePOJO.setUrl("" + request.getRequestURL());
		return new ResponseEntity<ErrorResponsePOJO>(errorResponsePOJO, HttpStatus.OK);
	}

	@ExceptionHandler(SQLException.class)
	public String sqlError(SQLException exception) {
		logger.error(exception.getMessage());
		exception.printStackTrace();
		return "error";
	}

	@ExceptionHandler(Throwable.class)
	public String handleAnyException(Throwable ex, HttpServletRequest request) {
		return "404";
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponsePOJO> hibernateError(DataIntegrityViolationException exception,
			HttpServletRequest request) {
		logger.error(exception.getMessage());
		exception.printStackTrace();
		ErrorResponsePOJO errorResponsePOJO = new ErrorResponsePOJO();
		errorResponsePOJO.setErrorCode("" + HttpStatus.PRECONDITION_FAILED.value());
		errorResponsePOJO.setDeveloperMessage(exception.getMessage());
		errorResponsePOJO.setUserMessage(exception.getRootCause().getMessage());
		errorResponsePOJO.setUrl(request.getRequestURL().toString());
		return new ResponseEntity<ErrorResponsePOJO>(errorResponsePOJO, HttpStatus.OK);
	}

	@ExceptionHandler(HibernateException.class)
	public ResponseEntity<ErrorResponsePOJO> hibernateError(HibernateException exception, HttpServletRequest request) {
		ErrorResponsePOJO errorResponsePOJO = new ErrorResponsePOJO();
		errorResponsePOJO.setErrorCode("" + HttpStatus.PRECONDITION_FAILED.value());
		errorResponsePOJO.setDeveloperMessage(exception.getMessage());
		errorResponsePOJO.setUserMessage(exception.getMessage());
		errorResponsePOJO.setUrl(request.getRequestURL().toString());
		return new ResponseEntity<ErrorResponsePOJO>(errorResponsePOJO, HttpStatus.OK);
	}

	@ExceptionHandler(IdInvalidRequestException.class)
	public ResponseEntity<ErrorResponsePOJO> idInvalidExceptionHandler(IdInvalidRequestException e,
			HttpServletRequest request) {
		logger.error(e.getMessage());
		ErrorResponsePOJO errorResponsePOJO = new ErrorResponsePOJO();
		errorResponsePOJO.setErrorCode("" + HttpStatus.NOT_FOUND.value());
		errorResponsePOJO.setDeveloperMessage(e.getMessage());
		errorResponsePOJO.setUserMessage(e.getMessage());
		errorResponsePOJO.setUrl("" + request.getRequestURL());
		return new ResponseEntity<ErrorResponsePOJO>(errorResponsePOJO, HttpStatus.OK);
	}

}
