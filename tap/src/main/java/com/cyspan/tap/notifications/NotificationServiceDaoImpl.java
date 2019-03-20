package com.cyspan.tap.notifications;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class NotificationServiceDaoImpl implements NotificationServiceDao {

	static Logger logger = Logger.getLogger(NotificationServiceDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionfactory;

	@SuppressWarnings("unchecked")
	@Override
	public Object fetchNotificationByUserByPage(Integer userid, Integer limit, Integer offset) {
		Session session = sessionfactory.getCurrentSession();
		List<NotificationEntity> list = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		Long count = 0l;
		Long unreadcount = 0l;
		try {
			Criteria criteria = session.createCriteria(NotificationEntity.class);
			criteria.add(Restrictions.eq("userId", userid));
			criteria.setFirstResult(offset);
			criteria.setMaxResults(limit);
			criteria.addOrder(Order.desc("timestamp"));
			list = criteria.list();

			Criteria criteria2 = session.createCriteria(NotificationEntity.class);
			criteria2.add(Restrictions.eq("userId", userid));
			criteria2.setProjection(Projections.rowCount());
			count = (Long) criteria2.uniqueResult();

			Criteria criteria3 = session.createCriteria(NotificationEntity.class);
			criteria3.add(Restrictions.eq("userId", userid));
			criteria3.add(Restrictions.eq("readStatus", false));
			criteria3.setProjection(Projections.rowCount());
			unreadcount = (Long) criteria3.uniqueResult();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		jsonObject.put("count", count);
		jsonObject.put("notification", list);
		jsonObject.put("unreadcount", unreadcount);
		Object object = jsonObject;
		return object;
	}

	@Override
	public void saveNotifiactionByUser(Integer userid, String title, String body) {
		Session session = sessionfactory.getCurrentSession();
		try {
			NotificationEntity entity = new NotificationEntity();
			entity.setBody(body);
			entity.setTitle(title);
			entity.setUserId(userid);
			entity.setReadStatus(false);
			session.save(entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void saveNotifiaction(NotificationEntity entity) {
		Session session = sessionfactory.getCurrentSession();
		session.save(entity);
		logger.info("notification saved" + entity.getId());
	}

	@Override
	public boolean updateNotificationReadStatus(Long id) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "update NotificationEntity set readStatus = " + true + " where id =" + id;
		Query query = session.createQuery(hql);
		return query.executeUpdate() > 0 ? true : false;
	}

	@Override
	public void muteNotificationForPoll(Long userId, Long pollId) {
		Session session = sessionfactory.getCurrentSession();
		NotificationMute mute = new NotificationMute();
		mute.setPollId(pollId);
		mute.setUserId(userId);
		session.save(mute);
		logger.info("notification muter user:" + userId + " for pollid:" + pollId);
	}

	@Override
	public void muteNotificationForGroup(Long userId, Long groupId) {
		Session session = sessionfactory.getCurrentSession();
		NotificationMute mute = new NotificationMute();
		mute.setGroupId(groupId);
		mute.setUserId(userId);
		session.save(mute);
		logger.info("notification muter user:" + userId + " for group:" + groupId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkNotificationMuted(Long userid, Long pollid) {
		Session session = sessionfactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NotificationMute.class);
		criteria.add(Restrictions.eq("userId", userid));
		criteria.add(Restrictions.eq("pollId", pollid));
		List<NotificationMute> list = criteria.list();
		return list.size() > 0 ? true : false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void muteNotificationForPollByGroup(Long userId, Long pollId, Long groupId) {
		Session session = sessionfactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NotificationMute.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("pollId", pollId));
		criteria.add(Restrictions.eq("groupId", groupId));
		List<NotificationMute> list = criteria.list();
		if (list.isEmpty()) { // is not muted then mute this poll (insert new)
			NotificationMute obj = new NotificationMute();
			obj.setUserId(userId);
			obj.setGroupId(groupId);
			obj.setPollId(pollId);
			session.save(obj);
			logger.info(" notification muted for user-" + userId + " group-" + groupId + " poll-" + pollId);
		} else { // is muted the unmute it (delete it)
			NotificationMute obj = list.get(0);
			session.delete(obj);
			logger.info(" notification un muted for user-" + userId + " group-" + groupId + " poll-" + pollId);
		}
	}
}
