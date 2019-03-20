package com.cyspan.tap.login.services;

import com.cyspan.tap.user.model.UsersModel;

public interface LoginServices {
	/**
	 * @param user
	 * @return true if user exists in db
	 */
	public Boolean authenticateUser(String username, String passwordHash);
	public boolean userExist(String deviceId);
	public Boolean checkEmailExist(UsersModel model);
	public void saveFbUser(UsersModel usersModel);
	
}
