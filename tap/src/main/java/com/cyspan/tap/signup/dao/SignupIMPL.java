package com.cyspan.tap.signup.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyspan.tap.commons.logging.Logger;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.user.model.UsersModel;

/**
 * @author sumesh
 *
 */
@Repository
public class SignupIMPL implements SignupDAO, ProfileDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	Logger logger;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyspan.tap.signup.dao.SignupDAO#insertUserInfo(com.cyspan.tap.signup.
	 * model.Users)
	 */
	@Transactional
	public boolean insertUserInfo(UsersModel users) {
		Session session = sessionFactory.getCurrentSession();
		try {
			if (users.getUserId() == null) {
				users.setStatus(Constants.USER_INACTIVE_STATUS);
				session.save(users);
				logger.info("user created id: " + users.getUserId());
			} else {
				session.update(users);
				logger.info("user updated id: " + users.getUserId());
			}
		} catch (Exception exception) {
			logger.error("user createion failed");
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyspan.tap.signup.dao.SignupDAO#updateUserInfo(com.cyspan.tap.signup.
	 * model.Users)
	 */
	@Transactional
	public boolean updateUserProfile(UsersModel users) {
		boolean isSave = true;
		Session session = null;

		System.out.println("USER ID ==== " + users.getUserId());
		try {
			session = sessionFactory.getCurrentSession();
			if (users.getUserId() > 0) {
				session.update(users);
			} else {
				session.save(users);
			}
		} catch (Exception ex) {
			isSave = false;
			ex.printStackTrace();

		}
		return isSave;
	}

	@Transactional
	public List<UsersModel> retrieveUserInfoAll() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "from Users";
		Query query = session.createQuery(sql);
		@SuppressWarnings("unchecked")
		List<UsersModel> listAllUsers = query.list();
		return listAllUsers;
	}

	@Transactional
	public UsersModel retrieveUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public UsersModel retrieveUserProfile(int userId) {
		Session session = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("userId", userId));
		@SuppressWarnings("unchecked")
		List<UsersModel> listUsers = (List<UsersModel>) criteria.list();
		session.getTransaction().commit();
		if (listUsers.size() > 0) {
			return listUsers.get(0);
		} else {
			return new UsersModel();
		}
	}

	@Override
	@Transactional
	public boolean changePassword(String oldPassword, String newPassword, String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public boolean updateProfilePhotoAlone(byte[] profilePhoto, int userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("updateCoverPhoto");
		query.setBinary("coverPhoto", profilePhoto);
		query.setInteger("userId", new Integer(userId));
		int row = query.executeUpdate();
		return row > 0;
	}

	@Override
	@Transactional
	public boolean updateCoverPhotoAlone(byte[] coverPhoto, int userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("updateCoverPhoto");
		query.setBinary("coverPhoto", coverPhoto);
		query.setInteger("userId", userId);
		int row = query.executeUpdate();
		return row > 0;
	}

}
