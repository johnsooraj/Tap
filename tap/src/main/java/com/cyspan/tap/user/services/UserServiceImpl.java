package com.cyspan.tap.user.services;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.cyspan.tap.commons.FileUploadService;
import com.cyspan.tap.commons.UpdatePassword;
import com.cyspan.tap.commons.logging.Logger;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.facade.DaoFacade;
import com.cyspan.tap.group.dao.GroupsDAO;
import com.cyspan.tap.notifications.GroupNotificationService;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.InterestModel;
import com.cyspan.tap.user.model.UserCity;
import com.cyspan.tap.user.model.UserInterest;
import com.cyspan.tap.user.model.UserState;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.utils.CryptoUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	Logger logger;

	@Autowired
	DaoFacade daoFacade;

	@Autowired
	UsersDao userDao;

	@Autowired
	CryptoUtil cryptoUtil;

	@Autowired
	HttpServletRequest request;

	@Autowired
	FileUploadService uploadImage;

	@Autowired
	GroupNotificationService groupnotificationService;

	@Autowired
	GroupsDAO groupDao;

	@Autowired
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

	@Override
	public UsersModel getUsersByMobileNum(String phoneNum) throws DataAccessException {
		return userDao.getUsersByMobileNum(phoneNum);
	}

	@Override
	public List<UsersModel> getContactList(List<String> phoneNums) {

		List<UsersModel> usersModels = userDao.getContactList(phoneNums);
		/*
		 * if (usersModels != null) { for (UsersModel model : usersModels) {
		 * model.setProfilePic(model.getProfilePic20()); } }
		 */
		return usersModels;
	}

	@Override
	public int updateToken(String username, String password, String token) {

		String passwordHash = null;

		if (password != null) {
			passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, password);
		}

		return userDao.updateToken(username.trim(), passwordHash, token);
	}

	@Override
	public Boolean checkTokenAvailabe(String token) {
		UsersModel usersModel = userDao.getUserByTokenId(token);
		if (usersModel.getTokenUpdate() != null) {
			Date lastModified = usersModel.getTokenUpdate();
			Date currentTime = new Date();
			long duration = lastModified.getTime() - currentTime.getTime();
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			long period = Math.abs(diffInHours);
			if (period > 4380) {
				logger.error("expired token : " + lastModified);
				return Boolean.TRUE;
			} else {
				return Boolean.TRUE;
			}
		} else {
			logger.error("invalid token");
			return Boolean.TRUE;
		}
	}

	@Override
	public UsersModel getUserByTokenId(String token) {
		return userDao.getUserByTokenId(token);
	}

	@Override
	public int logout(String username) {
		return userDao.logout(username);
	}

	@Override
	public InterestModel getIntrestsByIntrestId(int intrestId) {
		return userDao.getIntrestsByIntrestId(intrestId);
	}

	@Override
	public Boolean saveUserIntrest(UserInterest intrestModel) {
		return userDao.saveUserIntrest(intrestModel);
	}

	@Override
	public List<InterestModel> getIntrestModels() {
		return userDao.getIntrestModels();
	}

	@Override
	public Boolean updatePassword(UpdatePassword updatePassword) {

		String newpasswordHash = null;
		String oldpasswordHash = null;

		if (updatePassword.getNewPassword() != null) {
			newpasswordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, updatePassword.getNewPassword());
			updatePassword.setNewPassword(newpasswordHash);
		}
		if (updatePassword.getOldPassword() != null) {
			oldpasswordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, updatePassword.getOldPassword());
			updatePassword.setOldPassword(oldpasswordHash);
		}
		return userDao.updatePassword(updatePassword);
	}

	@Override
	public Object updateProfilePic(UsersModel usersModel) {
		if (usersModel.getProfilePhoto() != null) {
			String bigPictureUrl = uploadImage.uploadPictureBig(usersModel.getProfilePhoto(), "profile");
			String smallPictureUrl = uploadImage.uploadPictureSmall(usersModel.getProfilePhoto(), "profile");
			usersModel.setProfilePic(bigPictureUrl);
			usersModel.setProfilePic20(smallPictureUrl);
		}
		usersModel.setProfilePhoto(null);
		return userDao.saveUser(usersModel);

	}

	@Override
	public Boolean updateCoverPic(UsersModel usersModel) {

		if (usersModel.getProfilePhoto() != null) {
			String bigPictureUrl = uploadImage.uploadPictureBig(usersModel.getProfilePhoto(), "profile");
			String smallPictureUrl = uploadImage.uploadPictureSmall(usersModel.getProfilePhoto(), "profile");
			usersModel.setCoverPic(bigPictureUrl);
			usersModel.setCoverPic20(smallPictureUrl);
		}
		usersModel.setCoverPhoto(null);
		return userDao.updateUser(usersModel);
	}

	@Override
	public Integer pollCountByUserId(int userId) {
		return userDao.pollCountByUserId(userId);
	}

	@Override
	public Integer publicPollResponseCount(int userId) {
		return userDao.publicPollResponseCount(userId);
	}

	@Override
	public Integer privatePollResponseCount(int userId) {
		return userDao.privatePollResponseCount(userId);
	}

	@Override
	public Boolean updateUser(UsersModel usersModel) {
		return userDao.updateUser(usersModel);
	}

	@Override
	public Boolean updateUserStatus(String status, String token) {
		return userDao.updateUserStatus(status, token);
	}

	@Override
	public Boolean deleteGroupMember(int userId, int groupId) {
		return userDao.deleteGroupMember(userId, groupId);
	}

	@Override
	public boolean checkEmailExists(String email) {
		return daoFacade.checkEmailExists(email);
	}

	@Override
	public Set<UserState> getUserStates() {
		return userDao.getUserStates();
	}

	@Override
	public List<UserCity> getUserCitys() {
		return userDao.getUserCitys();
	}

	@Override
	public List<UserCity> getUserCitesByStateId(Long stateId) {
		return userDao.getUserCitesByStateId(stateId);
	}

	@Override
	public List<InterestModel> getInterestsByInterestName(String interest, int count) {
		return userDao.getInterestsByInterestName(interest, count);
	}

	@Override
	public UsersModel saveUserInterests(UsersModel usersModel, Set<UserInterest> interests) {
		interests.forEach(interest -> {
			interest.setUsersModel(usersModel);
		});
		/*
		 * Set<UserInterest> interests2 = new HashSet<>(); interests2.addAll(interests);
		 * if (usersModel.getUserInterest().isEmpty()) {
		 * usersModel.getUserInterest().addAll(interests); } else {
		 * usersModel.getUserInterest().forEach(obj -> { interests.forEach(interest -> {
		 * long newInterest = interest.getInterestId(); long oldInterest =
		 * obj.getInterestId(); if (newInterest == oldInterest) {
		 * interests2.remove(interest); } else {
		 * 
		 * } }); }); }
		 */
		// usersModel.getUserInterest().clear();
		userDao.deleteInterestByUserId(usersModel.getUserId());
		usersModel.getUserInterest().clear();
		usersModel.getUserInterest().addAll(interests);
		userDao.updateUser(usersModel);
		return usersModel;
	}

	@Override
	public List<InterestModel> getAllInterests() {
		return userDao.getAllInterests();
	}

	@Override
	public Object updateUserByCustomCheck(String token, UsersModel usersModel) {
		UsersModel oldUserData = userDao.getUserByTokenId(token);
		if (oldUserData != null && usersModel != null) {
			if (usersModel.getUserId().intValue() != oldUserData.getUserId().intValue()) {
				return Constants.TOKEN_RESPONSE;
			}
			if (usersModel.getFirstName() != null) {
				oldUserData.setFirstName(usersModel.getFirstName());
			}
			if (usersModel.getLastName() != null) {
				oldUserData.setLastName(usersModel.getLastName());
			}
			if (usersModel.getPhone() != null) {
				/*
				 * UsersModel model = userDao.isUserExistUserByPhone(usersModel.getPhone()); if
				 * (model != null) if (model.getUserId() == usersModel.getUserId()) { return
				 * "Can't change phone number"; } else { return Constants.PHONE_EXIST; }
				 */
				oldUserData.setPhone(usersModel.getPhone());

			}
			if (usersModel.getEmail() != null) {
				/*
				 * UsersModel model = userDao.isUserExistUserByEmail(usersModel.getEmail()); if
				 * (model != null) if (model.getUserId() == usersModel.getUserId()) { return
				 * "Can't change Email id"; } else { return Constants.EMAIL_EXIST; }
				 */
				oldUserData.setEmail(usersModel.getEmail());
			}
			if (usersModel.getAge() != null) {
				oldUserData.setAge(usersModel.getAge());
			}
			if (usersModel.getGender() != null) {
				oldUserData.setGender(usersModel.getGender());
			}
			if (usersModel.getAttributeName() != null) {
				oldUserData.setAttributeName(usersModel.getAttributeName());
			}
			if (usersModel.getProfilePic() != null) {
				oldUserData.setProfilePic(usersModel.getProfilePic());
			}
			if (usersModel.getCoverPic() != null) {
				oldUserData.setCoverPic(usersModel.getCoverPic());
			}
			if (usersModel.getAccountType() != null) {
				oldUserData.setAccountType(usersModel.getAccountType());
			}
			if (usersModel.getDateOfBirth() != null) {
				oldUserData.setDateOfBirth(usersModel.getDateOfBirth());
			}
			if (usersModel.getUserAddress() != null) {
				if (oldUserData.getUserAddress() != null) {
					if (usersModel.getUserAddress().getAddress() != null)
						oldUserData.getUserAddress().setAddress(usersModel.getUserAddress().getAddress());
					if (usersModel.getUserAddress().getCountry_id() != null)
						oldUserData.getUserAddress().setCountry_id(usersModel.getUserAddress().getCountry_id());
					if (usersModel.getUserAddress().getZip() != null)
						oldUserData.getUserAddress().setZip(usersModel.getUserAddress().getZip());
					if (usersModel.getUserAddress().getCity_id() != null)
						oldUserData.getUserAddress().setCity_id(usersModel.getUserAddress().getCity_id());
					if (usersModel.getUserAddress().getState_id() != null)
						oldUserData.getUserAddress().setState_id(usersModel.getUserAddress().getState_id());
				} else {
					oldUserData.setUserAddress(usersModel.getUserAddress());
				}
			}
			if (usersModel.getUserInterest() != null) {
				usersModel.getUserInterest().forEach(interest -> {
					interest.setUsersModel(oldUserData);
				});
				/*
				 * Set<UserInterest> interests2 = new HashSet<>();
				 * interests2.addAll(usersModel.getUserInterest()); if
				 * (!usersModel.getUserInterest().isEmpty()) {
				 * oldUserData.getUserInterest().forEach(obj -> {
				 * usersModel.getUserInterest().forEach(interest -> { long newInterest =
				 * interest.getInterestId(); long oldInterest = obj.getInterestId(); if
				 * (newInterest == oldInterest) { interests2.remove(interest); } }); }); }
				 * oldUserData.getUserInterest().clear();
				 */
				userDao.deleteInterestByUserId(usersModel.getUserId());
				oldUserData.getUserInterest().clear();
				oldUserData.getUserInterest().addAll(usersModel.getUserInterest());
			}
			return userDao.updateUser(oldUserData);
		}
		return Constants.USER_NOT_FOUND;
	}

	@Override
	public UsersModel getUserByUserId(Integer userId, Integer loggedInUser) {
		UsersModel model = userDao.getUserByUserId(userId);
		if (userDao.checkUserBlocked(loggedInUser, userId)) {
			model.setBlocked(true);
		}
		return model;
	}

	@Override
	public UsersModel saveUserModel(UsersModel model) {
		return userDao.saveUser(model);
	}

	@Override
	public boolean unsubscribeNotification(String userToken) {
		UsersModel model = userDao.getUserByTokenId(userToken);
		if (model.getUserId() != null) {
			List<String> uniqueIds = groupDao.fetchAllGroupuniqueIdByGroupIds(model.getUserId());
			if (!uniqueIds.isEmpty()) {
				for (String ids : uniqueIds) {
					groupnotificationService.unsubscribeNotification(model.getFcmToken(), ids);
				}
			}
		}
		return true;
	}

	@Override
	public List<UsersModel> getAllUsersByFirstName(String firstname) {
		return userDao.getAllUsersByFirstName(firstname);
	}

	@Override
	public List<UsersModel> getAllUsersStartWithPhone(String phone) {
		return userDao.getAllUsersStartWithPhone(phone);
	}

	@Override
	public List<UsersModel> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public boolean blockUserByUserID(Integer ownerId, Integer blockedId) {
		return userDao.BlockContactByUserId(ownerId, blockedId);
	}

	@Override
	public boolean toogleFilterSearchByUser(Integer userId, boolean currentStatus) {
		return userDao.toogleFilterSearchByUser(userId, currentStatus);
	}

	@Override
	public List<UsersModel> fetchBlockedContacts(Integer userId) {
		return userDao.fetchBlockedContacts(userId);
	}
}
