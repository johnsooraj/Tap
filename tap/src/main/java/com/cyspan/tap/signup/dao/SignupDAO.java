package com.cyspan.tap.signup.dao;

import java.util.List;

import com.cyspan.tap.user.model.UsersModel;

/**
 * @author sumesh
 *
 */
public interface SignupDAO {
	/**
	 * @param users
	 *            pass users model class for insert into DB
	 * @return boolean
	 */
	public boolean insertUserInfo(UsersModel users);

	public List<UsersModel> retrieveUserInfoAll();
	
	public boolean changePassword(String oldPassword,String newPassword,String userId);
}
