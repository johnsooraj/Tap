package com.cyspan.tap.signup.dao;

import com.cyspan.tap.user.model.UsersModel;

/**
 * 
 * For edit,retrieve,update user profiles
 * 
 * @author sumesh | Integretz LLC
 * 
 */
public interface ProfileDAO {
	/**
	 * @param users
	 * @return true or false method for updating user profile
	 */
	public boolean updateUserProfile(UsersModel users);

	/**
	 * For retrieve user profile by userId
	 * 
	 * @param userId
	 * @return
	 */
	public UsersModel retrieveUserProfile(int userId);

	/**
	 * 
	 * For retrieve user profile by username
	 * 
	 * @param username
	 * @return
	 */
	public UsersModel retrieveUserByUsername(String username);

	/**
	 * 
	 * Update Profile Photo alone
	 * 
	 * @param profilePhoto
	 * @return
	 */
	public boolean updateProfilePhotoAlone(byte[] profilePhoto,int userId);

	/**
	 * 
	 * Update Cover Photo Alone
	 * 
	 * 
	 * @param profilePhoto
	 * @return
	 */
	public boolean updateCoverPhotoAlone(byte[] coverPhoto,int userId);
}
