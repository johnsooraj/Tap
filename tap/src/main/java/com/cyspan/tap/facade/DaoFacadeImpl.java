package com.cyspan.tap.facade;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.commons.FileUploadService;
import com.cyspan.tap.commons.GlobalClassDAO;
import com.cyspan.tap.group.dao.GroupsDAO;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.signup.dao.ProfileDAO;
import com.cyspan.tap.signup.dao.SignupDAO;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UsersModel;

@Repository
public class DaoFacadeImpl implements DaoFacade {
	@Autowired
	UsersDao usersDao;
	@Autowired
	SignupDAO SignupDAO;
	@Autowired
	ProfileDAO profileDAO;
	@Autowired
	GroupsDAO groupsDAO;
	@Autowired
	GlobalClassDAO globalClassDAO;
	@Autowired
	HttpServletRequest request;
	@Autowired
	FileUploadService uploadImage;

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public boolean checkPhoneExists(String phoneNo) {

		return globalClassDAO.checkPhoneExists(phoneNo);
	}

	public boolean checkEmailExists(String email) {

		return globalClassDAO.checkEmailExists(email);
	}

	public boolean insertUserInfo(UsersModel users) {
		if (users.getProfilePhoto() != null) {
			String bigPictureUrl = uploadImage.uploadPictureBig(users.getProfilePhoto(), "profile");
			String smallPictureUrl = uploadImage.uploadPictureSmall(users.getProfilePhoto(), "profile");
			users.setProfilePic(bigPictureUrl);
			users.setProfilePic20(smallPictureUrl);
		}
		return SignupDAO.insertUserInfo(users);
	}

	public boolean updateUserProfile(UsersModel users) {
		return profileDAO.updateUserProfile(users);
	}

	public UsersModel retrieveUserProfile(int userId) {
		return profileDAO.retrieveUserProfile(userId);
	}

	public UsersModel retrieveUserByUsername(String username) {
		return profileDAO.retrieveUserByUsername(username);
	}

	public List<UsersModel> retrieveUserInfoAll() {
		return SignupDAO.retrieveUserInfoAll();
	}

	public GroupsModel createGroup(GroupsModel groupsModel) {
		return groupsDAO.createGroup(groupsModel);
	}

	@Override
	public boolean updateCoverPhotoAlone(byte[] coverPhoto, int userId) {
		return profileDAO.updateCoverPhotoAlone(coverPhoto, userId);
	}

}
