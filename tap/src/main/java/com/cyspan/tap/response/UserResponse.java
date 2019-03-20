package com.cyspan.tap.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
public class UserResponse {

	@JsonInclude(Include.NON_NULL)
	private String token;

	@JsonInclude(Include.NON_NULL)
	private String tokenValidTime;

	@JsonInclude(Include.NON_NULL)
	private String loginResponse;

	@JsonInclude(Include.NON_NULL)
	private String tokenInvalid;

	@JsonInclude(Include.NON_NULL)
	private String registerStatus;

	@JsonInclude(Include.NON_NULL)
	private String successMessage;

	@JsonInclude(Include.NON_NULL)
	private String failMessage;

	@JsonInclude(Include.NON_NULL)
	private String userNotFound;

	@JsonInclude(Include.NON_NULL)
	private String registrationFailed;

	@JsonInclude(Include.NON_NULL)
	private String emailExist;

	@JsonInclude(Include.NON_NULL)
	private String phoneExist;

	@JsonInclude(Include.NON_NULL)
	private String mailNotification;

	@JsonInclude(Include.NON_NULL)
	private String invalidateOtp;

	@JsonInclude(Include.NON_NULL)
	private String status;

	@JsonInclude(Include.NON_NULL)
	private String logoutSucc;

	@JsonInclude(Include.NON_NULL)
	private String logoutFail;

	@JsonInclude(Include.NON_NULL)
	private String intrestNotFound;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenValidTime() {
		return tokenValidTime;
	}

	public void setTokenValidTime(String tokenValidTime) {
		this.tokenValidTime = tokenValidTime;
	}

	public String getLoginResponse() {
		return loginResponse;
	}

	public void setLoginResponse(String loginResponse) {
		this.loginResponse = loginResponse;
	}

	public String getTokenInvalid() {
		return tokenInvalid;
	}

	public void setTokenInvalid(String tokenInvalid) {
		this.tokenInvalid = tokenInvalid;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	public String getUserNotFound() {
		return userNotFound;
	}

	public void setUserNotFound(String userNotFound) {
		this.userNotFound = userNotFound;
	}

	public String getRegistrationFailed() {
		return registrationFailed;
	}

	public void setRegistrationFailed(String registrationFailed) {
		this.registrationFailed = registrationFailed;
	}

	public String getEmailExist() {
		return emailExist;
	}

	public void setEmailExist(String emailExist) {
		this.emailExist = emailExist;
	}

	public String getPhoneExist() {
		return phoneExist;
	}

	public void setPhoneExist(String phoneExist) {
		this.phoneExist = phoneExist;
	}

	public String getMailNotification() {
		return mailNotification;
	}

	public void setMailNotification(String mailNotification) {
		this.mailNotification = mailNotification;
	}

	public String getInvalidateOtp() {
		return invalidateOtp;
	}

	public void setInvalidateOtp(String invalidateOtp) {
		this.invalidateOtp = invalidateOtp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogoutSucc() {
		return logoutSucc;
	}

	public void setLogoutSucc(String logoutSucc) {
		this.logoutSucc = logoutSucc;
	}

	public String getLogoutFail() {
		return logoutFail;
	}

	public void setLogoutFail(String logoutFail) {
		this.logoutFail = logoutFail;
	}

	public String getIntrestNotFound() {
		return intrestNotFound;
	}

	public void setIntrestNotFound(String intrestNotFound) {
		this.intrestNotFound = intrestNotFound;
	}

}
