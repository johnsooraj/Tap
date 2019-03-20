package com.cyspan.tap.subscription.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.subscription.models.FeedbackFreeComment;
import com.cyspan.tap.subscription.models.MultipleChoiceOptions;
import com.cyspan.tap.subscription.models.MultipleChoiceResponse;
import com.cyspan.tap.subscription.models.SubscriptionClearFlag;
import com.cyspan.tap.subscription.models.SubscriptionFeedback;
import com.cyspan.tap.subscription.models.SubscriptionFeedbackGroup;
import com.cyspan.tap.subscription.models.SubscriptionImageOptionResponse;
import com.cyspan.tap.subscription.models.SubscriptionNotice;
import com.cyspan.tap.subscription.models.SubscriptionPoll;
import com.cyspan.tap.subscription.models.SubscriptionPollImages;
import com.cyspan.tap.subscription.models.SubscriptionRating;
import com.cyspan.tap.subscription.models.SubscriptionResponses;
import com.cyspan.tap.user.dao.UsersDao;

@Repository
@Transactional
public class SubscriptionToolsDaoImpl implements SubscriptionToolsDao {

	static Logger logger = Logger.getLogger(SubscriptionToolsDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	UsersDao userDao;

	@Override
	public SubscriptionFeedback fechFeedbackById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionFeedback feedback = (SubscriptionFeedback) session.get(SubscriptionFeedback.class, id);
		return feedback;
	}

	@Override
	public SubscriptionPoll fechPollById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionPoll poll = (SubscriptionPoll) session.get(SubscriptionPoll.class, id);
		return poll;
	}

	@Override
	public SubscriptionNotice fetchNoticeById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionNotice notice = (SubscriptionNotice) session.get(SubscriptionNotice.class, id);
		return notice;
	}

	@Override
	public SubscriptionFeedback saveSubscriptionFeedback(SubscriptionFeedback feedback) {
		Session session = sessionFactory.getCurrentSession();
		session.save(feedback);
		return feedback;
	}

	@Override
	public SubscriptionPoll saveSubscriptionPoll(SubscriptionPoll poll) {
		Session session = sessionFactory.getCurrentSession();
		session.save(poll);
		return poll;
	}

	@Override
	public SubscriptionNotice saveSubscriptionNotice(SubscriptionNotice notice) {
		Session session = sessionFactory.getCurrentSession();
		session.save(notice);
		return notice;
	}

	@Override
	public SubscriptionFeedback updateSubscriptionFeedback(SubscriptionFeedback feedback) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(feedback);
		return feedback;
	}

	@Override
	public boolean deleteFeedbackById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionFeedback feedback = (SubscriptionFeedback) session.get(SubscriptionFeedback.class, id);
		session.delete(feedback);
		return true;
	}

	@Override
	public boolean deletePollById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionPoll poll = (SubscriptionPoll) session.get(SubscriptionPoll.class, id);
		if (poll != null) {
			session.delete(poll);
		}
		return true;
	}

	@Override
	public boolean deleteNoticeById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionNotice notice = (SubscriptionNotice) session.get(SubscriptionNotice.class, id);
		if (notice != null) {
			session.delete(notice);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> fetchfeedbacksAndPollsByOrganizationIdOrderByCreateDate(Long orgId, Integer limit,
			Integer offset, LocalDate upperLimit, LocalDate lowerLimit) {

		StringBuffer feedBuffer = new StringBuffer();
		StringBuffer pollBuffer = new StringBuffer();
		StringBuffer resultBuffer = new StringBuffer();

		// create feedback query to return data
		feedBuffer.append("SELECT sfeed.feedbackid AS fid, NULL AS pid, sfeed.createdate AS cd "
				+ "FROM tap.subscriptionfeedback sfeed");
		if (upperLimit != null && lowerLimit != null) {
			feedBuffer.append(" WHERE ( sfeed.createdate BETWEEN '" + upperLimit + "' AND '" + lowerLimit
					+ "' ) AND sfeed.organization_organizationId = " + orgId);
		}

		// create poll query to return data
		pollBuffer.append("SELECT NULL AS fid, spoll.pollid AS pid, spoll.createdate AS cd "
				+ "FROM tap.subscriptionpoll spoll ");
		if (upperLimit != null && lowerLimit != null) {
			pollBuffer.append("WHERE ( spoll.createdate BETWEEN '" + upperLimit + "' AND '" + lowerLimit
					+ "' ) AND spoll.organization_organizationId = " + orgId);
		}

		// combine two query for UNION operation
		resultBuffer.append("SELECT * FROM (");
		resultBuffer.append(feedBuffer);
		resultBuffer.append(" UNION ");
		resultBuffer.append(pollBuffer);
		resultBuffer.append(") AS res ");
		resultBuffer.append("ORDER BY res.cd DESC ");
		resultBuffer.append("LIMIT " + limit + "," + offset + ";");

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(resultBuffer.toString());

		String countQuery = "SELECT count(*) \r\n" + "FROM   (SELECT sfeed.feedbackid AS fid, \r\n"
				+ "               NULL             AS pid, \r\n" + "               sfeed.createdate AS cd \r\n"
				+ "        FROM   tap.subscriptionfeedback sfeed \r\n"
				+ "        where sfeed.organization_organizationId = " + orgId + "\r\n" + "        UNION \r\n"
				+ "        SELECT NULL             AS fid, \r\n" + "               spoll.pollid     AS pid, \r\n"
				+ "               spoll.createdate AS cd \r\n" + "        FROM   tap.subscriptionpoll spoll \r\n"
				+ "        where spoll.organization_organizationId = " + orgId + "\r\n" + "	) AS res \r\n"
				+ "ORDER  BY res.cd DESC;";
		Query query2 = session.createSQLQuery(countQuery);
		BigInteger count = (BigInteger) query2.uniqueResult();

		List<Object[]> list = query.list();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("count", count);

		List<Object> results = new ArrayList<>();
		results.add(jsonObject);

		for (Object[] obj : list) {
			Long feedbackId = obj[0] != null ? new Long(obj[0].toString()) : null;
			Long pollId = obj[1] != null ? new Long(obj[1].toString()) : null;
			if (feedbackId != null) {
				// results.add(session.get(SubscriptionFeedback.class, feedbackId));
				results.add(this.fetchFeedbackByIdAndJoinUserModel(feedbackId));
			}
			if (pollId != null) {
				// results.add(session.get(SubscriptionPoll.class, pollId));
				results.add(this.fetchPollByIdAndJoinUserModel(pollId));
			}
		}
		return results;
	}

	@Override
	public SubscriptionFeedback fetchFeedbackByIdAndJoinUserModel(Long feedId) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SubscriptionFeedback.class);
		criteria.add(Restrictions.eq("feedbackId", feedId));
		SubscriptionFeedback feedback = (SubscriptionFeedback) criteria.uniqueResult();
		if (feedback != null) {
			Object[] owner = userDao.fetchUserNameAndProfilePicByUserId(feedback.getFeedbackCreaterId().intValue());
			if (owner != null) {
				String usename = (owner[0] != null ? (String) owner[0] : "") + " "
						+ (owner[1] != null ? (String) owner[1] : "");
				String profile = (owner[2] != null ? (String) owner[2] : "");
				feedback.setFeedbackCreaterName(usename);
				feedback.setFeedbackCreaterProfilePic(profile);

				if (!feedback.getQuestions().isEmpty()) {
					for (MultipleChoiceOptions data : feedback.getQuestions()) {
						data.getResponses().forEach(obj -> {
							Object[] user = userDao.fetchUserNameAndProfilePicByUserId(obj.getResponderId().intValue());
							if (user != null) {
								String usenameRes = (owner[0] != null ? (String) owner[0] : "") + " "
										+ (owner[1] != null ? (String) owner[1] : "");
								String profileRes = (owner[2] != null ? (String) owner[2] : "");
								obj.setResponderName(usenameRes);
								obj.setResponderProfilePic(profileRes);
							}
						});
					}
				}

				if (!feedback.getRatings().isEmpty()) {
					for (SubscriptionRating rating : feedback.getRatings()) {
						Object[] user = userDao.fetchUserNameAndProfilePicByUserId(rating.getResponderId().intValue());
						if (user != null) {
							String usenameRes = (owner[0] != null ? (String) owner[0] : "") + " "
									+ (owner[1] != null ? (String) owner[1] : "");
							String profileRes = (owner[2] != null ? (String) owner[2] : "");
							rating.setResponderName(usenameRes);
							rating.setResponderProfilePic(profileRes);
						}
					}
				}

				if (!feedback.getFreeComments().isEmpty()) {
					for (FeedbackFreeComment comment : feedback.getFreeComments()) {
						Object[] user = userDao.fetchUserNameAndProfilePicByUserId(comment.getResponderId().intValue());
						if (user != null) {
							String usenameRes = (owner[0] != null ? (String) owner[0] : "") + " "
									+ (owner[1] != null ? (String) owner[1] : "");
							String profileRes = (owner[2] != null ? (String) owner[2] : "");
							comment.setResponderName(usenameRes);
							comment.setResponderProfilePic(profileRes);
						}
					}
				}

			}
		}

		return feedback;
	}

	@Override
	public SubscriptionPoll fetchPollByIdAndJoinUserModel(Long pollId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SubscriptionPoll.class);
		criteria.add(Restrictions.eq("pollId", pollId));
		SubscriptionPoll poll = (SubscriptionPoll) criteria.uniqueResult();
		if (poll != null) {

			try {
				Object[] polluser = userDao.fetchUserNameAndProfilePicByUserId(poll.getCreatedBy().intValue());
				String pollusenameRes = (polluser[0] != null ? (String) polluser[0] : "") + " "
						+ (polluser[1] != null ? (String) polluser[1] : "");
				String pollprofileRes = (polluser[2] != null ? (String) polluser[2] : "");
				poll.setCreatedUserName(pollusenameRes);
				poll.setCreatedUserProfilePic(pollprofileRes);
			} catch (Exception e) {
				logger.error("fetch user data for poll by id failed");
			}

			if (!poll.getQuestions().isEmpty()) {
				for (MultipleChoiceOptions data : poll.getQuestions()) {
					data.getResponses().forEach(obj -> {
						Object[] user = userDao.fetchUserNameAndProfilePicByUserId(obj.getResponderId().intValue());
						if (user != null) {
							String usenameRes = (user[0] != null ? (String) user[0] : "") + " "
									+ (user[1] != null ? (String) user[1] : "");
							String profileRes = (user[2] != null ? (String) user[2] : "");
							obj.setResponderName(usenameRes);
							obj.setResponderProfilePic(profileRes);
						}
					});
				}
			}

			if (!poll.getRatings().isEmpty()) {
				for (SubscriptionRating rating : poll.getRatings()) {
					Object[] owner = userDao.fetchUserNameAndProfilePicByUserId(rating.getResponderId().intValue());
					if (owner != null) {
						String usenameRes = (owner[0] != null ? (String) owner[0] : "") + " "
								+ (owner[1] != null ? (String) owner[1] : "");
						String profileRes = (owner[2] != null ? (String) owner[2] : "");
						rating.setResponderName(usenameRes);
						rating.setResponderProfilePic(profileRes);
					}
				}
			}

			if (!poll.getPollImages().isEmpty()) {
				for (SubscriptionPollImages image : poll.getPollImages()) {
					image.getImageRespons().forEach(obj -> {
						Object[] owner = userDao.fetchUserNameAndProfilePicByUserId(obj.getResponderId().intValue());
						if (owner != null) {
							String usenameRes = (owner[0] != null ? (String) owner[0] : "") + " "
									+ (owner[1] != null ? (String) owner[1] : "");
							String profileRes = (owner[2] != null ? (String) owner[2] : "");
							obj.setResponderName(usenameRes);
							obj.setResponderProfilePic(profileRes);
						}
					});
				}
			}
		}
		return poll;
	}

	@Override
	public SubscriptionFeedbackGroup fetchFeedbackGroupById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionFeedbackGroup group = (SubscriptionFeedbackGroup) session.get(SubscriptionFeedbackGroup.class, id);
		return group;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubscriptionFeedbackGroup> fechFeedbackGroupByOrganizationId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SubscriptionFeedbackGroup.class);
		criteria.add(Restrictions.eq("organization.organizationId", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("createDate"));
		return criteria.list();
	}

	@Override
	public boolean deleteFeedbackGroupById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionFeedbackGroup obj = (SubscriptionFeedbackGroup) session.get(SubscriptionFeedbackGroup.class, id);
		if (obj != null) {
			session.delete(obj);
			return true;
		}
		return false;
	}

	@Override
	public SubscriptionFeedbackGroup saveSubscriptionFeedbackGroup(SubscriptionFeedbackGroup model) {
		Session session = sessionFactory.getCurrentSession();
		session.save(model);
		return model;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> fetchfeedbackGroupsAndPollsByOrganizationIdOrderByCreateDate(Long orgId, Integer limit,
			Integer offset, LocalDate upperLimit, LocalDate lowerLimit) {
		StringBuffer feedBuffer = new StringBuffer();
		StringBuffer pollBuffer = new StringBuffer();
		StringBuffer resultBuffer = new StringBuffer();

		// create feedback query to return data
		feedBuffer.append("SELECT sfeed.id AS fid, NULL AS pid, sfeed.createdate AS cd "
				+ "FROM SubscriptionFeedbackGroup sfeed");
		feedBuffer.append(" WHERE sfeed.status = 1 AND sfeed.organization_organizationId = " + orgId);
		if (upperLimit != null && lowerLimit != null) {
			feedBuffer.append(" sfeed.createdate BETWEEN '" + upperLimit + "' AND '" + lowerLimit);
		}

		// create poll query to return data
		pollBuffer.append(
				"SELECT NULL AS fid, spoll.pollid AS pid, spoll.createdate AS cd " + "FROM SubscriptionPoll spoll ");
		pollBuffer.append(" WHERE spoll.organization_organizationId = " + orgId);
		if (upperLimit != null && lowerLimit != null) {
			pollBuffer.append(" AND spoll.createdate BETWEEN '" + upperLimit + "' AND '" + lowerLimit);
		}

		// combine two query for UNION operation
		resultBuffer.append("SELECT * FROM (");
		resultBuffer.append(feedBuffer);
		resultBuffer.append(" UNION ");
		resultBuffer.append(pollBuffer);
		resultBuffer.append(") AS res ");
		resultBuffer.append("ORDER BY res.cd DESC ");
		resultBuffer.append("LIMIT " + limit + "," + offset + ";");

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(resultBuffer.toString());

		String countQuery = "SELECT count(*) \r\n" + "FROM   (SELECT sfeed.id AS fid, \r\n"
				+ "               NULL             AS pid, \r\n" + "               sfeed.createdate AS cd \r\n"
				+ "        FROM   tap.subscriptionfeedbackgroup sfeed \r\n"
				+ "        where sfeed.organization_organizationId = " + orgId + "\r\n"
				+ "        and sfeed.status = 1\r\n" + "        UNION \r\n"
				+ "        SELECT NULL             AS fid, \r\n" + "               spoll.pollid     AS pid, \r\n"
				+ "               spoll.createdate AS cd \r\n" + "        FROM   tap.subscriptionpoll spoll \r\n"
				+ "        where spoll.organization_organizationId = " + orgId + "\r\n" + "	) AS res \r\n"
				+ "ORDER  BY res.cd DESC;";
		Query query2 = session.createSQLQuery(countQuery);
		BigInteger count = (BigInteger) query2.uniqueResult();

		List<Object[]> list = query.list();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("count", count);

		List<Object> results = new ArrayList<>();
		results.add(jsonObject);

		for (Object[] obj : list) {
			Long feedbackId = obj[0] != null ? new Long(obj[0].toString()) : null;
			Long pollId = obj[1] != null ? new Long(obj[1].toString()) : null;
			if (feedbackId != null) {
				// results.add(session.get(SubscriptionFeedback.class, feedbackId));
				// results.add(this.fetchFeedbackByIdAndJoinUserModel(feedbackId));
				SubscriptionFeedbackGroup feedGroup = this.fetchFeedbackGroupById(feedbackId);
				Set<SubscriptionFeedback> updatedList = new HashSet<>();
				for (SubscriptionFeedback localFeed : feedGroup.getFeedbacks()) {
					SubscriptionFeedback updateFeed = this.fetchFeedbackByIdAndJoinUserModel(localFeed.getFeedbackId());
					updatedList.add(updateFeed);
				}
				feedGroup.getFeedbacks().clear();
				feedGroup.getFeedbacks().addAll(updatedList);
				results.add(feedGroup);
			}
			if (pollId != null) {
				// results.add(session.get(SubscriptionPoll.class, pollId));
				results.add(this.fetchPollByIdAndJoinUserModel(pollId));
			}
		}
		return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object fetchSubscriptionToolsByOrgId(Long orgid, int start, int end, Integer userId, String text) {
		// total subscription count
		// subscriptions by page number

		Session session = sessionFactory.getCurrentSession();
		JSONObject jsonObject = new JSONObject();
		List<Object> results = new ArrayList<>();

		StringBuilder countFetchQuery = new StringBuilder();
		countFetchQuery.append(" SELECT count(*) FROM ( ");
		countFetchQuery.append(" SELECT sfeed.id AS fid, NULL AS pid, NULL AS nid, sfeed.createdate AS cd ");
		countFetchQuery.append(" FROM	tap.SubscriptionFeedbackGroup sfeed ");
		countFetchQuery.append(" LEFT JOIN tap.SubscriptionClearFlag scflag ");
		countFetchQuery.append(" ON sfeed.organization_organizationid = scflag.organizationid");
		countFetchQuery.append(" WHERE sfeed.organization_organizationid = " + orgid);
		countFetchQuery.append(" AND sfeed.status = 1 ");
		if (text != null) {
			countFetchQuery.append(" AND sfeed.feedbackFormName like '%" + text + "%' ");
		}
		countFetchQuery.append(" AND CASE WHEN scflag.userid = " + userId
				+ " THEN sfeed.createdate > scflag.timestamp ELSE true END ");
		countFetchQuery.append(" \n UNION \n ");
		countFetchQuery.append(" SELECT NULL AS fid, spoll.pollid AS pid, NULL AS nid, spoll.createdate AS cd ");
		countFetchQuery.append(" FROM      tap.SubscriptionPoll spoll ");
		countFetchQuery.append(" LEFT JOIN tap.SubscriptionClearFlag scflag ");
		countFetchQuery.append(" ON        spoll.organization_organizationid = scflag.organizationid ");
		countFetchQuery.append(" WHERE     spoll.organization_organizationid = " + orgid);
		if (text != null) {
			countFetchQuery.append(" AND       spoll.pollText like '%" + text + "%'");
		}
		countFetchQuery.append(
				" AND CASE WHEN scflag.userid = " + userId + " THEN spoll.createdate > scflag.timestamp ELSE true END");
		countFetchQuery.append(" \n UNION \n ");
		countFetchQuery.append(" SELECT NULL AS fid, NULL AS pid, snote.noticeid AS nid, snote.createdate AS cd ");
		countFetchQuery.append(" FROM      tap.SubscriptionNotice snote");
		countFetchQuery.append(" LEFT JOIN tap.SubscriptionClearFlag scflag ");
		countFetchQuery.append(" ON        snote.organization_organizationid = scflag.organizationid ");
		countFetchQuery.append(" WHERE     snote.organization_organizationid = " + orgid);
		if (text != null) {
			countFetchQuery.append(" AND       snote.noticeText like '%" + text + "%' ");
		}
		countFetchQuery.append(
				" AND CASE WHEN scflag.userid = " + userId + " THEN snote.createdate > scflag.timestamp ELSE true END");
		countFetchQuery.append(" ) AS res ORDER BY res.cd DESC ");

		Query countQuery = session.createSQLQuery(countFetchQuery.toString());
		BigInteger count = (BigInteger) countQuery.uniqueResult();

		StringBuilder dataFetchQuery = new StringBuilder();

		dataFetchQuery.append(" SELECT * FROM ( ");
		dataFetchQuery.append(" SELECT sfeed.id AS fid, NULL AS pid, NULL AS nid, sfeed.createdate AS cd ");
		dataFetchQuery.append(" FROM	tap.SubscriptionFeedbackGroup sfeed ");
		dataFetchQuery.append(" LEFT JOIN tap.SubscriptionClearFlag scflag ");
		dataFetchQuery.append(" ON sfeed.organization_organizationid = scflag.organizationid");
		dataFetchQuery.append(" WHERE sfeed.organization_organizationid = " + orgid);
		dataFetchQuery.append(" AND sfeed.status = 1 ");
		if (text != null) {
			dataFetchQuery.append(" AND sfeed.feedbackFormName like '%" + text + "%' ");
		}
		dataFetchQuery.append(" AND CASE WHEN scflag.userid = " + userId
				+ " THEN sfeed.createdate > scflag.timestamp ELSE true END ");
		dataFetchQuery.append(" \n UNION \n ");
		dataFetchQuery.append(" SELECT NULL AS fid, spoll.pollid AS pid, NULL AS nid, spoll.createdate AS cd ");
		dataFetchQuery.append(" FROM      tap.SubscriptionPoll spoll ");
		dataFetchQuery.append(" LEFT JOIN tap.SubscriptionClearFlag scflag ");
		dataFetchQuery.append(" ON        spoll.organization_organizationid = scflag.organizationid ");
		dataFetchQuery.append(" WHERE     spoll.organization_organizationid = " + orgid);
		if (text != null) {
			dataFetchQuery.append(" AND       spoll.pollText like '%" + text + "%'");
		}
		dataFetchQuery.append(
				" AND CASE WHEN scflag.userid = " + userId + " THEN spoll.createdate > scflag.timestamp ELSE true END");
		dataFetchQuery.append(" \n UNION \n ");
		dataFetchQuery.append(" SELECT NULL AS fid, NULL AS pid, snote.noticeid AS nid, snote.createdate AS cd ");
		dataFetchQuery.append(" FROM      tap.SubscriptionNotice snote");
		dataFetchQuery.append(" LEFT JOIN tap.SubscriptionClearFlag scflag ");
		dataFetchQuery.append(" ON        snote.organization_organizationid = scflag.organizationid ");
		dataFetchQuery.append(" WHERE     snote.organization_organizationid = " + orgid);
		if (text != null) {
			dataFetchQuery.append(" AND       snote.noticeText like '%" + text + "%' ");
		}
		dataFetchQuery.append(
				" AND CASE WHEN scflag.userid = " + userId + " THEN snote.createdate > scflag.timestamp ELSE true END");
		dataFetchQuery.append(" ) AS res ORDER BY res.cd DESC limit " + start + ", " + end);

		Query dataQuery = session.createSQLQuery(dataFetchQuery.toString());
		List<Object[]> data = dataQuery.list();

		for (Object[] object : data) {
			Long feedbackId = object[0] != null ? new Long(object[0].toString()) : null;
			Long pollId = object[1] != null ? new Long(object[1].toString()) : null;
			Long noticeId = object[2] != null ? new Long(object[2].toString()) : null;
			if (feedbackId != null) {
				SubscriptionFeedbackGroup feedGroup = this.fetchFeedbackGroupById(feedbackId);
				Set<SubscriptionFeedback> updatedList = new HashSet<>();
				for (SubscriptionFeedback localFeed : feedGroup.getFeedbacks()) {
					// check if current user responded or not
					SubscriptionFeedback updateFeed = this.fetchFeedbackByIdAndJoinUserModel(localFeed.getFeedbackId());
					updateFeed.setRespondStatus(this.checkIsUserRespondedOrNot(userId, localFeed.getFeedbackId()));
					updatedList.add(updateFeed);
				}
				feedGroup.getFeedbacks().clear();
				feedGroup.getFeedbacks().addAll(updatedList);
				results.add(feedGroup);
			}
			if (pollId != null) {
				SubscriptionPoll pollObj = this.fetchPollByIdAndJoinUserModel(pollId);
				pollObj.setRespondStatus(this.checkIsUserRespondedOrNot(userId, pollObj.getPollId()));
				results.add(pollObj);
			}
			if (noticeId != null) {
				results.add(this.fetchSubscriptionNoticeWithUserDataById(noticeId));
			}
		}

		jsonObject.put("count", count);
		results.add(jsonObject);

		return results;
	}

	@Override
	public SubscriptionNotice fetchSubscriptionNoticeWithUserDataById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionNotice notice = (SubscriptionNotice) session.get(SubscriptionNotice.class, id);
		if (notice != null) {
			if (notice.getCreatedBy() != null) {
				Object[] owner = userDao.fetchUserNameAndProfilePicByUserId(notice.getCreatedBy().intValue());
				String usename = (owner[0] != null ? (String) owner[0] : "") + " "
						+ (owner[1] != null ? (String) owner[1] : "");
				String profile = (owner[2] != null ? (String) owner[2] : "");
				if (usename != null)
					notice.setCreatedUserName(usename);
				if (profile != null)
					notice.setCreateUserProfilePic(profile);
			}
		}
		return notice;
	}

	@Override
	public MultipleChoiceResponse saveMultipleChoiceResponse(MultipleChoiceResponse response) {
		Session session = sessionFactory.getCurrentSession();
		session.save(response);
		return response;
	}

	@Override
	public SubscriptionRating saveRatingResponse(SubscriptionRating response) {
		Session session = sessionFactory.getCurrentSession();
		session.save(response);
		return response;
	}

	@Override
	public SubscriptionImageOptionResponse saveImageResponse(SubscriptionImageOptionResponse response) {
		Session session = sessionFactory.getCurrentSession();
		session.save(response);
		return response;
	}

	@Override
	public FeedbackFreeComment saveFreeCommentResponse(FeedbackFreeComment response) {
		Session session = sessionFactory.getCurrentSession();
		session.save(response);
		return response;
	}

	@Override
	public boolean saveClearDateByUserIdAndOranizationId(Integer userId, Long orgId) {
		Session session = sessionFactory.getCurrentSession();
		SubscriptionClearFlag clearFlag = new SubscriptionClearFlag();
		clearFlag.setOrganizationId(orgId);
		clearFlag.setUserId(userId);
		clearFlag.setTimestamp(new Timestamp(System.currentTimeMillis()));
		session.save(clearFlag);
		return clearFlag.getId() != null ? true : false;
	}

	@Override
	public boolean updateSubscriptionFeedbackGroup(SubscriptionFeedbackGroup feedback) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"update SubscriptionFeedbackGroup set feedbackFormName =:fgn, status =:status, exprieDate =:exprieDate where id=:id");
		query.setString("fgn", feedback.getFeedbackFormName());
		query.setByte("status", feedback.getStatus());
		query.setTimestamp("exprieDate", feedback.getExprieDate());
		query.setLong("id", feedback.getId());
		int a = query.executeUpdate();
		return a > 0 ? true : false;
	}

	@Override
	public boolean checkIsUserRespondedOrNot(Integer userId, Long toolId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SubscriptionResponses.class);

		Criterion c1 = Restrictions.eq("userId", userId);
		Criterion c2 = Restrictions.eq("feedbackId", toolId);
		Criterion c3 = Restrictions.eq("pollId", toolId);
		Criterion c4 = Restrictions.eq("feedbackGroupId", toolId);
		Criterion c5 = Restrictions.and(c1, Restrictions.or(c2, c3, c4));
		criteria.add(c5);

		SubscriptionResponses responses = (SubscriptionResponses) criteria.uniqueResult();

		return responses != null ? true : false;
	}

	@Override
	public boolean saveUserRespondData(SubscriptionResponses responses) {
		Session session = sessionFactory.getCurrentSession();
		session.save(responses);
		return responses.getId() != null ? true : false;
	}

}
