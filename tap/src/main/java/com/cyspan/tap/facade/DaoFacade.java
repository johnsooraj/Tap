package com.cyspan.tap.facade;

import java.util.List;

import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.user.model.UsersModel;

/**
 * @author sumesh
 *
 */
public interface DaoFacade {

	/**
	 * @param phoneNo
	 * @return true - if phoneNo exists else false
	 */
	public boolean checkPhoneExists(String phoneNo);

	/**
	 * @param phoneNo
	 * @return true - if mail exists else false
	 */
	public boolean checkEmailExists(String email);

	/**
	 * @param users
	 * @return signup user
	 */
	public boolean insertUserInfo(UsersModel users);

	public boolean updateUserProfile(UsersModel users);

	public UsersModel retrieveUserProfile(int userId);

	public UsersModel retrieveUserByUsername(String username);

	public List<UsersModel> retrieveUserInfoAll();

	/**
	 * 
	 * Create group
	 * 
	 * @param groupsModel
	 * @return
	 */
	public GroupsModel createGroup(GroupsModel groupsModel);

	public boolean updateCoverPhotoAlone(byte[] coverPhoto, int userId);
}
