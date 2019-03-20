package com.cyspan.tap.signup.services;

import java.util.List;

import com.cyspan.tap.user.model.UsersModel;

/**
 * @author sumesh Signup Services
 */
public interface SignUpServices {
	public String insertUserInfo(UsersModel users);

	public boolean updateUserProfile(UsersModel users);

	public UsersModel retrieveUserInfo(int userId);

	public UsersModel retrieveUserByUsername(String username);

	public List<UsersModel> retrieveUserInfoAll();

	public boolean updateCoverPhotoAlone(byte[] coverPhoto, int userId);
}
