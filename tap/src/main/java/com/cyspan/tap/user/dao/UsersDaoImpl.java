package com.cyspan.tap.user.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyspan.tap.commons.UpdatePassword;
import com.cyspan.tap.commons.UserHandler;
import com.cyspan.tap.commons.logging.Logger;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.user.model.BlockedContacts;
import com.cyspan.tap.user.model.InterestModel;
import com.cyspan.tap.user.model.UserCity;
import com.cyspan.tap.user.model.UserInterest;
import com.cyspan.tap.user.model.UserState;
import com.cyspan.tap.user.model.UsersModel;

@Repository("usersDao")
@Transactional
public class UsersDaoImpl implements UsersDao {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	Logger logger;

	@Autowired
	ServletContext servletContext;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public UsersModel getUsersByMobileNum(String phoneNum) throws DataAccessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("phone", phoneNum));
		List<UsersModel> list = criteria.list();
		return list.isEmpty() ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UsersModel> getContactList(List<String> phoneNums) {
		List<UsersModel> contactData = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		for (String phone : phoneNums) {
			if (phone.length() >= 10) {
				List<UsersModel> model = (List<UsersModel>) session.createCriteria(UsersModel.class)
						.add(Restrictions.like("phone", phone.substring(phone.length() - 10), MatchMode.END)).list();
				if (model != null && !model.isEmpty())
					contactData.add(model.get(0));
			}
		}
		return contactData;
	}

	@Override
	public void insertUserHandler(UserHandler userHandler) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userHandler);
	}

	@Override
	@Transactional
	public Boolean checkGmailExist(UsersModel user) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("email", user.getEmail()));
		if (criteria.list().size() > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

	@Override
	@Transactional
	public void saveFbUser(UsersModel usersModel) {
		Session session = sessionFactory.getCurrentSession();
		session.save(usersModel);
	}

	@Override
	@Transactional
	public int updateToken(String username, String password, String token) {

		Session session = sessionFactory.getCurrentSession();
		int i = 0;
		try {
			/*
			 * Query query = session.createQuery("update UsersModel set token = "+token.
			 * toString()+ " where eamil = "+city+" and phone = "+username);
			 */
			if (password != null) {

				Query query = session.createQuery(
						"update UsersModel set token = :token,tokenUpdate =:tokenUpdate,status =:status where (phone = :phone or email = :email) and (passwordHash = :password)");
				query.setParameter("token", token);
				query.setParameter("tokenUpdate", new Date());
				query.setParameter("phone", username);
				query.setParameter("email", username);
				query.setParameter("password", password);
				query.setParameter("status", Constants.USER_ACTIVE_STATUS);
				i = query.executeUpdate();
			} else {
				Query query = session.createQuery(
						"update UsersModel set token = :token,tokenUpdate =:tokenUpdate,status =:status where  email = :email");
				query.setParameter("token", token);
				query.setParameter("tokenUpdate", new Date());
				// query.setParameter("phone",username);
				query.setParameter("email", username);
				query.setParameter("status", Constants.USER_ACTIVE_STATUS);
				i = query.executeUpdate();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return i;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public UsersModel getUserByTokenId(String token) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UsersModel u where u.token = :token");
		query.setParameter("token", token);
		List<UsersModel> usersModels = query.list();
		if (usersModels.size() > 0) {
			return usersModels.get(0);
		} else {
			return new UsersModel();
		}
	}

	@Override
	@Transactional
	public Integer pollCountByUserId(int userId) {

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select count(*) from polls where CreatedBy = :userId");
		query.setParameter("userId", userId);
		Integer count = ((BigInteger) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	@Transactional
	public Integer publicPollResponseCount(int userId) {

		int pollCount = 0;

		Session session = sessionFactory.getCurrentSession();

		Criteria criteria1 = session.createCriteria(PollModel.class, "poll");
		criteria1.createAlias("poll.imageoptions", "imOpt");
		criteria1.createAlias("imOpt.imageoptionsresponses", "imResp");
		criteria1.add(Restrictions.eq("poll.pollType", Constants.PUBLIC_POLL_TYPE));
		Criterion img = Restrictions.eq("imResp.userId", userId);
		criteria1.add(img);

		pollCount = pollCount + criteria1.list().size();

		Criteria criteria2 = session.createCriteria(PollModel.class, "poll");
		criteria2.createAlias("poll.multipleoptions", "mulOpt");
		criteria2.createAlias("mulOpt.multipleoptionsresponses", "mulResp");
		criteria2.add(Restrictions.eq("poll.pollType", Constants.PUBLIC_POLL_TYPE));
		Criterion mul = Restrictions.eq("mulResp.userId", userId);
		criteria2.add(mul);

		pollCount = pollCount + criteria2.list().size();

		Criteria criteria3 = session.createCriteria(PollModel.class, "poll");
		criteria3.createAlias("poll.ratingoptions", "ratOpt");
		criteria3.createAlias("ratOpt.ratingoptionsresponses", "ratResp");
		criteria3.add(Restrictions.eq("poll.pollType", Constants.PUBLIC_POLL_TYPE));
		Criterion rat = Restrictions.eq("ratResp.userId", userId);
		criteria3.add(rat);

		pollCount = pollCount + criteria3.list().size();

		Criteria criteria4 = session.createCriteria(PollModel.class, "poll");
		criteria4.createAlias("poll.thisorthatoptions", "thisOpt");
		criteria4.createAlias("thisOpt.thisorthatoptionsresponses", "thisResp");
		criteria4.add(Restrictions.eq("poll.pollType", Constants.PUBLIC_POLL_TYPE));
		Criterion ths = Restrictions.eq("thisResp.userId", userId);
		criteria4.add(ths);

		pollCount = pollCount + criteria4.list().size();

		return pollCount;
	}

	@Override
	@Transactional
	public Integer privatePollResponseCount(int userId) {
		int pollCount = 0;

		Session session = sessionFactory.getCurrentSession();

		Criteria criteria1 = session.createCriteria(PollModel.class, "poll");
		criteria1.createAlias("poll.imageoptions", "imOpt");
		criteria1.createAlias("imOpt.imageoptionsresponses", "imResp");
		criteria1.add(Restrictions.eq("poll.pollType", Constants.PRIVATE_POLL_TYPE));
		Criterion img = Restrictions.eq("imResp.userId", userId);
		criteria1.add(img);

		pollCount = pollCount + criteria1.list().size();

		Criteria criteria2 = session.createCriteria(PollModel.class, "poll");
		criteria2.createAlias("poll.multipleoptions", "mulOpt");
		criteria2.createAlias("mulOpt.multipleoptionsresponses", "mulResp");
		criteria2.add(Restrictions.eq("poll.pollType", Constants.PRIVATE_POLL_TYPE));
		Criterion mul = Restrictions.eq("mulResp.userId", userId);
		criteria2.add(mul);

		pollCount = pollCount + criteria2.list().size();

		Criteria criteria3 = session.createCriteria(PollModel.class, "poll");
		criteria3.createAlias("poll.ratingoptions", "ratOpt");
		criteria3.createAlias("ratOpt.ratingoptionsresponses", "ratResp");
		criteria3.add(Restrictions.eq("poll.pollType", Constants.PRIVATE_POLL_TYPE));
		Criterion rat = Restrictions.eq("ratResp.userId", userId);
		criteria3.add(rat);

		pollCount = pollCount + criteria3.list().size();

		Criteria criteria4 = session.createCriteria(PollModel.class, "poll");
		criteria4.createAlias("poll.thisorthatoptions", "thisOpt");
		criteria4.createAlias("thisOpt.thisorthatoptionsresponses", "thisResp");
		criteria4.add(Restrictions.eq("poll.pollType", Constants.PRIVATE_POLL_TYPE));
		Criterion ths = Restrictions.eq("thisResp.userId", userId);
		criteria4.add(ths);

		pollCount = pollCount + criteria4.list().size();

		return pollCount;
	}

	@Override
	@Transactional
	public UsersModel getUserModelById(int userId) {
		return (UsersModel) sessionFactory.getCurrentSession().createCriteria(UsersModel.class)
				.add(Restrictions.eq("userId", userId)).uniqueResult();
	}

	@Override
	@Transactional
	public int logout(String username) {
		Session session = sessionFactory.getCurrentSession();
		int i = 0;
		Query query = session
				.createQuery("update UsersModel set token = :token,tokenUpdate =:tokenUpdate  where  email = :email");
		query.setParameter("token", null);
		query.setParameter("tokenUpdate", new Date());
		// query.setParameter("phone",username);
		query.setParameter("email", username);
		// query.setParameter("status",Constants.USER_INACTIVE_STATUS);
		i = query.executeUpdate();
		return i;

	}

	@Override
	@Transactional
	public InterestModel getIntrestsByIntrestId(int intrestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InterestModel.class);
		criteria.add(Restrictions.eq("intrestId", intrestId));
		return (InterestModel) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public Boolean saveUserIntrest(UserInterest intrestModel) {
		Session session = sessionFactory.getCurrentSession();
		int i = (int) session.save(intrestModel);
		if (i > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<InterestModel> getIntrestModels() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InterestModel.class);
		return criteria.list();
	}

	@Override

	@Transactional
	public Boolean updatePassword(UpdatePassword updatePassword) {
		int i = 0;
		Session session = sessionFactory.getCurrentSession();
		if (updatePassword.getOldPassword() != null) {
			Query query = session.createQuery(
					"update UsersModel set passwordHash = :passwordHash where  email = :email and passwordHash = :oldPassword");
			query.setParameter("passwordHash", updatePassword.getNewPassword());
			query.setParameter("oldPassword", updatePassword.getOldPassword());
			query.setParameter("email", updatePassword.getUsername());
			i = query.executeUpdate();
		} else {
			Query query = session
					.createQuery("update UsersModel set passwordHash = :passwordHash where  email = :email ");
			query.setParameter("passwordHash", updatePassword.getNewPassword());
			// query.setParameter("phone",username);
			query.setParameter("email", updatePassword.getUsername());
			i = query.executeUpdate();
		}
		// query.setParameter("status",Constants.USER_INACTIVE_STATUS);

		System.out.println("i.........value .....after updation ......" + i);
		if (i > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

	@Override
	@Transactional
	public Boolean updateUser(UsersModel usersModel) {
		Session session = sessionFactory.getCurrentSession();
		session.evict(usersModel);
		session.saveOrUpdate(usersModel);
		return Boolean.TRUE;
	}

	@Override
	@Transactional
	public Boolean updateUserStatus(String status, String token) {
		int i = 0;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update UsersModel set status = :status where  token = :token");
		query.setParameter("status", status);
		// query.setParameter("phone",username);
		query.setParameter("token", token);

		// query.setParameter("status",Constants.USER_INACTIVE_STATUS);
		i = query.executeUpdate();
		System.out.println("i.........value .....after updation ......" + i);
		if (i > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public Boolean deleteGroupMember(int userId, int groupId) {
		int i = 0;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("delete from groupmembers where UserId = :userId and GroupId = :groupId");

		query.setParameter("userId", userId);
		query.setParameter("groupId", groupId);

		i = query.executeUpdate();
		if (i > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public UsersModel isUserExistUserByPhone(String phoneNumber) {
		UsersModel usersModel = (UsersModel) sessionFactory.getCurrentSession().createCriteria(UsersModel.class)
				.add(Restrictions.eq("phone", phoneNumber)).uniqueResult();
		return usersModel;
	}

	@Override
	@Transactional
	public UsersModel isUserExistUserByEmail(String userEmail) {
		UsersModel usersModel = (UsersModel) sessionFactory.getCurrentSession().createCriteria(UsersModel.class)
				.add(Restrictions.eq("email", userEmail)).uniqueResult();
		return usersModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Set<UserState> getUserStates() {
		List<UserState> states = sessionFactory.getCurrentSession().createCriteria(UserState.class).list();
		logger.trace("<< getUserStates() - return :" + states.size());
		Set<UserState> newList = states.stream().collect(Collectors.toSet());
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserCity> getUserCitys() {
		List<UserCity> cities = sessionFactory.getCurrentSession().createCriteria(UserCity.class)
				.setProjection(Projections.projectionList().add(Projections.property("cityId"), "cityId")
						.add(Projections.property("cityName"), "cityName"))
				.setResultTransformer(Transformers.aliasToBean(UserCity.class)).list();
		logger.trace("<< getUserCitys() - return :" + cities.size());
		return cities;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserCity> getUserCitesByStateId(Long stateId) {
		List<UserCity> cities = (List<UserCity>) sessionFactory.getCurrentSession()
				.createCriteria(UserCity.class, "userCity").add(Restrictions.eq("userCity.userState.stateId", stateId))
				.setProjection(Projections.projectionList().add(Projections.property("cityId"), "cityId")
						.add(Projections.property("cityName"), "cityName")
						.add(Projections.property("userState"), "userState"))
				.setResultTransformer(Transformers.aliasToBean(UserCity.class)).list();
		logger.trace("<< getUserCitesByStateId() - return :" + cities.size());
		return cities;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<InterestModel> getInterestsByInterestName(String interest, int count) {
		List<InterestModel> models = sessionFactory.getCurrentSession().createCriteria(InterestModel.class)
				.add(Restrictions.ilike("intrest", interest, MatchMode.START)).setMaxResults(count).list();
		return models;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<InterestModel> getAllInterests() {
		return sessionFactory.getCurrentSession().createCriteria(InterestModel.class).list();
	}

	@Override
	@Transactional
	public UsersModel getUserByUserId(Integer userId) {
		return (UsersModel) sessionFactory.getCurrentSession().get(UsersModel.class, userId);
	}

	@Override
	@Transactional
	public UsersModel saveUser(UsersModel user) {
		sessionFactory.getCurrentSession().update(user);
		return user;
	}

	@Override
	@Transactional
	public boolean deleteInterestByUserId(Integer userId) {
		/*
		 * Query q = sessionFactory.getCurrentSession()
		 * .createQuery("DELETE FROM UserInterest ui WHERE ui.usersModel.userId=:userId"
		 * ); q.setParameter("userId", userId); int i = q.executeUpdate();
		 */
		int i = 0;
		Session session = sessionFactory.getCurrentSession();
		/*
		 * List<UserInterest> interestsList = session.createCriteria(UserInterest.class,
		 * "ui") .add(Restrictions.eq("ui.usersModel.userId", userId)).list(); for
		 * (UserInterest interest : interestsList) { session.delete(interest); }
		 */
		String sql = "DELETE FROM UserInterest WHERE userId = :userId";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("userId", userId);
		query.executeUpdate();

		return i == 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> fetchFCMTokenByListOfUserId(List<Integer> userIdList) {
		return sessionFactory.getCurrentSession()
				.createQuery("SELECT fcmToken from UsersModel where userId in (:userIdList)")
				.setParameterList("userIdList", userIdList).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchFCMTokenByGroupId(int groupId) {
		String query = "select grpmem.GroupMemberId, grpmem.GroupId, usr.UserId, usr.fcmToken from tap.users usr\r\n"
				+ "join tap.groupmembers grpmem on grpmem.UserId = usr.UserId\r\n" + "where grpmem.GroupId = ?";
		Session session = sessionFactory.getCurrentSession();
		Query query2 = session.createSQLQuery(query);
		query2.setInteger(0, groupId);
		List<Object[]> list = query2.list();
		return list;
	}

	@Override
	public UsersModel createUser(UsersModel model) {
		sessionFactory.getCurrentSession().save(model);
		return model;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UsersModel> getAllUsersByFirstName(String firstname) {
		Session session = sessionFactory.getCurrentSession();
		List<UsersModel> allList = session.createCriteria(UsersModel.class)
				.add(Restrictions.like("firstName", firstname, MatchMode.START)).add(Restrictions.eq("status", "1"))
				.list();
		return allList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UsersModel> getAllUsersStartWithPhone(String phone) {
		Session session = sessionFactory.getCurrentSession();
		List<UsersModel> allList = session.createCriteria(UsersModel.class)
				.add(Restrictions.like("phone", phone, MatchMode.START)).add(Restrictions.eq("status", "1")).list();
		return allList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UsersModel> getAllUsers() {
		Session session = sessionFactory.getCurrentSession();
		List<UsersModel> allList = session.createCriteria(UsersModel.class).add(Restrictions.eq("status", "1")).list();
		return allList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public UsersModel fetchUserByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Optional<UsersModel> usersModel = session.createCriteria(UsersModel.class).add(Restrictions.eq("email", email))
				.list().stream().findFirst();
		return usersModel.orElse(null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String fetchFCMTokenByUserId(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		List<String> tokens = session.createQuery("select usr.fcmToken from UsersModel usr where usr.userId =:userId")
				.setInteger("userId", userId).list();
		return tokens.isEmpty() ? null : tokens.get(0);
	}

	@Override
	public UsersModel saveOrUpdateUsersModel(UsersModel model) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(model);
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UsersModel fetchUserByEmailOrPhone(String email, String phone) {
		Session session = sessionFactory.getCurrentSession();
		Optional<UsersModel> usersModel = session.createCriteria(UsersModel.class).add(Restrictions.eq("email", email))
				.list().stream().findFirst();
		return usersModel.orElse(null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object[] fetchUserNameAndProfilePicByUserId(Integer userid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UsersModel.class);
		criteria.add(Restrictions.eq("userId", userid));
		ProjectionList properties = Projections.projectionList();
		properties.add(Projections.property("firstName"), "firstName");
		properties.add(Projections.property("lastName"), "lastName");
		properties.add(Projections.property("profilePic"), "profilePic");
		criteria.setProjection(properties);
		List<Object> result = criteria.list();
		if (!result.isEmpty()) {
			return (Object[]) result.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setObjectiveWordsToServletContext() {
		if (servletContext.getAttribute("objectionableWords") == null) {
			try {

				JSONParser parser = new JSONParser();
				InputStream is = new ClassPathResource("/poll-report-reasons.json").getInputStream();
				JSONObject dummyData = (JSONObject) parser.parse(new InputStreamReader(is, "UTF-8"));

				JSONArray pollBlockResons = (JSONArray) dummyData.get("poll-block-reasons");
				JSONArray objectinalWords = (JSONArray) dummyData.get("objectional-words");

				if (servletContext.getAttribute("objectionalWords") == null) {
					servletContext.setAttribute("objectionalWords", new ArrayList<String>(objectinalWords));
				}

				if (servletContext.getAttribute("pollReportResons") == null) {
					servletContext.setAttribute("pollReportResons", new ArrayList<String>(pollBlockResons));
				}
			} catch (IOException | ParseException e) {
				logger.error("objectionable words add error");
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<String> fetchPollReportResons() {
		return null;
	}

	@Override
	public boolean BlockContactByUserId(Integer userId, Integer contactId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			BlockedContacts contact = (BlockedContacts) session.createCriteria(BlockedContacts.class)
					.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("blockedId", contactId)).uniqueResult();
			if (contact != null) {
				Query query = session
						.createQuery("delete from BlockedContacts where userId =:userId and blockedId =:contactId");
				query.setInteger("userId", userId);
				query.setInteger("contactId", contactId);
				int a = query.executeUpdate();
				return a > 0 ? true : false;
			} else {
				BlockedContacts obj = new BlockedContacts();
				obj.setBlockedId(contactId);
				obj.setUserId(userId);
				session.save(obj);
				return obj.getId().intValue() > 0 ? true : false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean toogleFilterSearchByUser(Integer userId, boolean currentStatus) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update UsersModel set isFilterSearch =:filter where userId =:userId");
		query.setBoolean("filter", !currentStatus);
		query.setInteger("userId", userId);
		return query.executeUpdate() > 0 ? true : false;
	}

	@Override
	public boolean checkUserBlocked(Integer loggedinUser, Integer contactId) {
		Session session = sessionFactory.getCurrentSession();
		BlockedContacts contact = (BlockedContacts) session.createCriteria(BlockedContacts.class)
				.add(Restrictions.eq("userId", loggedinUser)).add(Restrictions.eq("blockedId", contactId))
				.uniqueResult();
		return contact != null ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsersModel> fetchBlockedContacts(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> userIds = (List<Integer>) session
				.createQuery("select blockedId from BlockedContacts where userId=:userid").setInteger("userid", userId)
				.list();
		if (!userIds.isEmpty()) {
			return session.createCriteria(UsersModel.class).add(Restrictions.in("userId", userIds)).list();
		}
		return null;
	}

}
