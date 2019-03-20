package com.cyspan.tap.login.dao;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.user.model.UsersModel;

@Repository
public class LoginIMPL implements LoginDAO {

	@Autowired
	SessionFactory sessionFactory;

	static Logger logger = Logger.getLogger(LoginIMPL.class.getName());

	@Override
	@Transactional
	public Boolean authenticateUser(String username, String passwordHash) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UsersModel.class);
		Criterion phone = Restrictions.eq("phone", username.trim());
		Criterion email = Restrictions.eq("email", username.trim());
		criteria.add(Restrictions.eq("passwordHash", passwordHash.trim()));
		criteria.add(Restrictions.eq("status", Constants.USER_ACTIVE_STATUS));
		criteria.add(Restrictions.or(phone, email));
		UsersModel model = (UsersModel) criteria.uniqueResult();
		if (model != null) {
			logger.info("Login success -userId :" + model.getUserId());
			return Boolean.TRUE;
		} else {
			logger.info("Login failed -username :" + username);
			return Boolean.FALSE;
		}
	}

	@Override
	public boolean userExist(String deviceId) {
		boolean status = false;
		Session session = null;
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("deviceId", deviceId));
		UsersModel model = (UsersModel) criteria.uniqueResult();
		if (model != null) {
			status = true;
		}
		return status;
	}

}
