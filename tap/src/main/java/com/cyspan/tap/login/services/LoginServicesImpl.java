package com.cyspan.tap.login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.login.dao.LoginDAO;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.utils.CryptoUtil;

@Service
public class LoginServicesImpl implements LoginServices {
	@Autowired
	LoginDAO loginDAO;
	@Autowired
	CryptoUtil cryptoUtil;
	
	@Autowired
	UsersDao usersDao;

	public Boolean authenticateUser(String username, String password) {
		String passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, password);
		System.out.println("pass hash "+passwordHash);
		return loginDAO.authenticateUser(username, passwordHash);
	}

	@Override
	public boolean userExist(String deviceId) {
		// TODO Auto-generated method stub
		return loginDAO.userExist(deviceId);
	}

	@Override
	public Boolean checkEmailExist(UsersModel model) {
		// TODO Auto-generated method stub
		return usersDao.checkGmailExist(model);
	}

	@Override
	public void saveFbUser(UsersModel usersModel) {
		// TODO Auto-generated method stub
		 usersDao.saveFbUser(usersModel);
	}

}
