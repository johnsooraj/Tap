package com.cyspan.tap.signup.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyspan.tap.commons.UserHandler;
import com.cyspan.tap.commons.logging.Logger;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.facade.DaoFacade;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.utils.CryptoUtil;

@Service
public class SignUpServicesImpl implements SignUpServices {

	@Autowired
	DaoFacade daoFacade;

	@Autowired
	CryptoUtil cryptoUtil;

	@Autowired
	UsersDao userDao;

	@Autowired
	Logger logger;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyspan.tap.signup.services.SignUpServices#insertUserInfo(com.cyspan.
	 * tap.signup.model.UsersModel)
	 */
	public String insertUserInfo(UsersModel users) {
		UsersModel userPhone = userDao.isUserExistUserByPhone(users.getPhone());
		if (userPhone != null) {
			// To Avoid null pointer at next if condition
			if (userPhone.getStatus() == null) {
				userPhone.setStatus("");
			}
		}
		if (userPhone == null || userPhone.getStatus().equals(Constants.USER_INACTIVE_STATUS)) {
			users.setUserId(userPhone != null ? userPhone.getUserId() : users.getUserId());
			UsersModel userEmail = userDao.isUserExistUserByEmail(users.getEmail());
			if (userEmail != null) {
				// To Avoid null pointer at next if condition
				if (userEmail.getStatus() == null) {
					userEmail.setStatus("");
				}
			}
			if (userEmail == null || userEmail.getStatus().equals(Constants.USER_INACTIVE_STATUS)) {
				users.setUserId(userEmail != null ? userEmail.getUserId() : users.getUserId());
				users.setStatus(Constants.USER_INACTIVE_STATUS);
				users.setPasswordHash(cryptoUtil.encrypt(Constants.PRIVATE_KEY, users.getPassword()));

				boolean done = daoFacade.insertUserInfo(users);
				if (done) {
					String otp = UserHandler.getOtp(users.getEmail());
					return otp;
				} else {
					return Constants.REGISTRATION_FAILED;
				}
			} else {
				return Constants.EMAIL_EXIST;
			}
		} else {
			return Constants.PHONE_EXIST;
		}
	}

	public boolean updateUserProfile(UsersModel users) {
		
		return daoFacade.updateUserProfile(users);
	}

	public UsersModel retrieveUserInfo(int userId) {
		Integer createdPollCount = userDao.pollCountByUserId(userId);
		Integer publicPollCount = userDao.publicPollResponseCount(userId);
		Integer privatePollCount = userDao.privatePollResponseCount(userId);
		UsersModel usersModel = daoFacade.retrieveUserProfile(userId);
		usersModel.setCreatedPollCount(createdPollCount);
		usersModel.setPublicPollResponseCount(publicPollCount);
		usersModel.setPrivatePollResponseCount(privatePollCount);
		return usersModel;

	}

	@Transactional
	public UsersModel retrieveUserByUsername(String username) {
		return daoFacade.retrieveUserByUsername(username);
	}

	@Transactional
	public List<UsersModel> retrieveUserInfoAll() {
		return daoFacade.retrieveUserInfoAll();
	}

	@Override
	@Transactional
	public boolean updateCoverPhotoAlone(byte[] coverPhoto, int userId) {
		return daoFacade.updateCoverPhotoAlone(coverPhoto, userId);
	}

}
