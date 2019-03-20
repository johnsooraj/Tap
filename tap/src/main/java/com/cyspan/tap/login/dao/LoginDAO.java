package com.cyspan.tap.login.dao;

public interface LoginDAO {

	/**
	 * @param user
	 * @return true if user exists in db
	 */
	public Boolean authenticateUser(String username, String passwordHash);
	public boolean userExist(String deviceId);

}
