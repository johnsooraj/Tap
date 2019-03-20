package com.cyspan.tap.commons;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.user.model.UsersModel;

/**
 * 
 * An implementation class for global usage
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Repository
public class GlobalClassIMPL implements GlobalClassDAO {
	
	@Autowired
	SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see com.cyspan.tap.commons.GlobalClassDAO#checkUserExistsByUsername(java.lang.String)
	 */
	@Override
	@Transactional
	public boolean checkUserExistsByUsername(String username) {
		Session session = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.setProjection(Projections.rowCount());
		long count = (Long) criteria.uniqueResult();
		session.getTransaction().commit();
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.cyspan.tap.commons.GlobalClassDAO#checkUserExistsByUserId(java.lang.String)
	 */
	@Override
	@Transactional
	public boolean checkUserExistsByUserId(String userId) {
		Session session = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.setProjection(Projections.rowCount());
		long count = (Long) criteria.uniqueResult();
		session.getTransaction().commit();
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.cyspan.tap.commons.GlobalClassDAO#checkPhoneExists(java.lang.String)
	 */
	@Override
	@Transactional
	public boolean checkPhoneExists(String phoneNo) {
		Session session = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("phone", phoneNo));
		criteria.setProjection(Projections.rowCount());
		long count = (Long) criteria.uniqueResult();
		session.getTransaction().commit();
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.cyspan.tap.commons.GlobalClassDAO#checkEmailExists(java.lang.String)
	 */
	@Override
	@Transactional
	public boolean checkEmailExists(String email) {
		Session session = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.setProjection(Projections.rowCount());
		long count = (Long) criteria.uniqueResult();
		session.getTransaction().commit();
		return count > 0;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyspan.tap.commons.GlobalClassDAO#checkUsernameExists(java.lang.String)
	 */
	@Override
	@Transactional
	public boolean checkUsernameExists(String usename) {
		Session session = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("email", usename));
		criteria.setProjection(Projections.rowCount());
		long count = (Long) criteria.uniqueResult();
		session.getTransaction().commit();
		return count > 0;
	}
}
