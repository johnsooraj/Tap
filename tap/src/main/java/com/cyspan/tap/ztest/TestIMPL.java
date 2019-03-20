package com.cyspan.tap.ztest;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.user.model.UserCity;
import com.cyspan.tap.user.model.UserState;
import com.cyspan.tap.user.model.UsersModel;

@Repository
@Transactional
public class TestIMPL implements TestDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean insertPoll(PollModel model) {
		boolean isSave = true;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			if (model.getPollId() != 0) {
				session.update(model);
			} else {
				session.save(model);
			}
		} catch (Exception ex) {
			isSave = false;
			ex.printStackTrace();

		}
		return isSave;
	}

	@Override
	@Transactional
	public List<PollModel> retrieveAllPoll() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "from polls";
		Query query = session.createQuery(sql);
		@SuppressWarnings("unchecked")
		List<PollModel> listAllUsers = query.list();
		return listAllUsers;
	}

	@Override
	public UsersModel saveUserModel(UsersModel usersModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsersModel getUserModelById(Integer id) {
		return (UsersModel) sessionFactory.getCurrentSession().get(UsersModel.class, id);
	}

	@Override
	public UserCity saveUserCity(UserCity userCity) {
		sessionFactory.getCurrentSession().save(userCity);
		return userCity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserCity> getAllUserCitys() {
		return sessionFactory.getCurrentSession().createCriteria(UserCity.class).list();
	}

	@Override
	public UserState saveUserState(UserState userState) {
		sessionFactory.getCurrentSession().save(userState);
		return userState;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserState> getAllUserState() {
		return sessionFactory.getCurrentSession().createCriteria(UserState.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserCity> getCityByStateId(Long id) {
		return (List<UserCity>) sessionFactory.getCurrentSession()
				.createQuery("select uc.cityId, uc.cityName from UserCity uc where uc.userState.stateId = :stateId")
				.setParameter("stateId", id).list();
	}

	@Override
	public UserCity getUserCityById(Long id) {
		return (UserCity) sessionFactory.getCurrentSession().get(UserCity.class, id);
	}

}
