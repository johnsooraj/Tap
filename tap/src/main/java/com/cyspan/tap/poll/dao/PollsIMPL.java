package com.cyspan.tap.poll.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyspan.tap.commons.LattestPollModel;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.poll.models.ImageOptionModel;
import com.cyspan.tap.poll.models.ImageOptionsResponseModel;
import com.cyspan.tap.poll.models.MultipleOptionModel;
import com.cyspan.tap.poll.models.MultipleOptionsResponseModel;
import com.cyspan.tap.poll.models.PollCommentModel;
import com.cyspan.tap.poll.models.PollGroupModel;
import com.cyspan.tap.poll.models.PollInterestModel;
import com.cyspan.tap.poll.models.PollLikeModel;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.poll.models.RatingOptionModel;
import com.cyspan.tap.poll.models.RatingOptionsResponseModel;
import com.cyspan.tap.poll.models.ReportPoll;
import com.cyspan.tap.poll.models.RsvpResponseModel;
import com.cyspan.tap.poll.models.ThisorthatOptionModel;
import com.cyspan.tap.poll.models.ThisorthatOptionsResponseModel;
import com.cyspan.tap.user.model.BlockedContacts;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.utils.PollStatus;

@Repository
@Transactional
public class PollsIMPL implements PollsDAO {

	@Autowired
	SessionFactory sessionFactory;

	static Logger logger = Logger.getLogger(PollsIMPL.class.getName());

	@Override
	@Transactional
	public PollModel savePoll(PollModel model) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(model);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PollModel> retrieveAllPoll() {
		return sessionFactory.getCurrentSession().createCriteria(PollModel.class).list();
	}

	@Override
	@Transactional
	public PollCommentModel savePollComment(PollCommentModel pollCommentModel) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(pollCommentModel);
		return pollCommentModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PollCommentModel> getPollCommentsByPollId(int pollId, int groupId) {
		PollModel pollModel = new PollModel(pollId);
		return sessionFactory.getCurrentSession().createCriteria(PollCommentModel.class)
				.add(Restrictions.eq("poll", pollModel)).add(Restrictions.eq("groupId", groupId)).list();
	}

	@Override
	@Transactional
	public PollLikeModel savePollLike(PollLikeModel pollLikeModel) {
		sessionFactory.getCurrentSession().save(pollLikeModel);
		return pollLikeModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PollLikeModel> getPollLikesByPollId(int id, int groupId) {
		return sessionFactory.getCurrentSession().createCriteria(PollLikeModel.class, "plm")
				.createAlias("plm.usersModel", "user")
				.setProjection(Projections.projectionList().add(Projections.property("plm.pollLikeId").as("pollLikeId"))
						.add(Projections.property("plm.status").as("status"))
						.add(Projections.property("user.firstName").as("userName"))
						.add(Projections.property("user.lastName").as("lastName"))
						.add(Projections.property("user.userId").as("userId"))
						.add(Projections.property("user.profilePic").as("profilePic")))
				.add(Restrictions.eq("plm.poll.pollId", id)).add(Restrictions.eq("plm.groupId", groupId))
				.setResultTransformer(new AliasToBeanResultTransformer(PollLikeModel.class)).list();
	}

	@Override
	@Transactional
	public Object pollById(int pollId) {

		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(PollModel.class);

		criteria.add(Restrictions.eq("pollId", pollId));

		return (PollModel) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PollModel> pollListByGroupId(int groupId) {

		List<PollModel> pollModels = new ArrayList<PollModel>();
		Session session = sessionFactory.getCurrentSession();

		// Poll CRITRIA...
		Criteria pollModelCriteria = session.createCriteria(PollGroupModel.class);
		pollModelCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		pollModelCriteria.add(Restrictions.eq("groupId", groupId));
		pollModelCriteria.add(Restrictions.eq("deleteStatus", false));

		List<PollGroupModel> groupModels = pollModelCriteria.list();

		for (PollGroupModel groupModel : groupModels) {
			pollModels.add(groupModel.getPoll());
		}

		for (PollModel model : pollModels) {
			if (!model.getPollcomments().isEmpty()) {
				List<PollCommentModel> commentModels = new ArrayList<>();
				for (PollCommentModel model2 : model.getPollcomments()) {
					if (model2.getGroupId() == groupId) {
						commentModels.add(model2);
					}
				}
				model.getPollcomments().clear();
				model.getPollcomments().addAll(commentModels);
			}

			if (!model.getPolllikes().isEmpty()) {
				List<PollLikeModel> likeModels = new ArrayList<>();
				for (PollLikeModel model2 : model.getPolllikes()) {
					if (model2.getGroupId() == groupId) {
						likeModels.add(model2);
					}
				}
				model.getPolllikes().clear();
				model.getPolllikes().addAll(likeModels);
			}
			if (!model.getImageoptions().isEmpty()) {
				for (ImageOptionModel imageOptionModel : model.getImageoptions()) {
					List<ImageOptionsResponseModel> newImageOptionResponse = new ArrayList<>();
					if (!imageOptionModel.getImageoptionsresponses().isEmpty()) {
						for (ImageOptionsResponseModel optionsResponseModel : imageOptionModel
								.getImageoptionsresponses()) {
							if (optionsResponseModel.getGroupId() == groupId) {
								newImageOptionResponse.add(optionsResponseModel);
							}
						}
					}
					imageOptionModel.getImageoptionsresponses().clear();
					imageOptionModel.setImageoptionsresponses(newImageOptionResponse);
				}

			}
			if (!model.getMultipleoptions().isEmpty()) {
				for (MultipleOptionModel multipleOptionModel : model.getMultipleoptions()) {
					List<MultipleOptionsResponseModel> newMultipleOptionResponse = new ArrayList<>();
					if (!multipleOptionModel.getMultipleoptionsresponses().isEmpty()) {
						for (MultipleOptionsResponseModel optionsResponseModel : multipleOptionModel
								.getMultipleoptionsresponses()) {
							if (optionsResponseModel.getGroupId() == groupId) {
								newMultipleOptionResponse.add(optionsResponseModel);
							}
						}
					}
					multipleOptionModel.getMultipleoptionsresponses().clear();
					multipleOptionModel.setMultipleoptionsresponses(newMultipleOptionResponse);
				}

			}
			if (!model.getRatingoptions().isEmpty()) {
				for (RatingOptionModel ratingOptionModel : model.getRatingoptions()) {
					List<RatingOptionsResponseModel> newRatingOptionResponse = new ArrayList<>();
					if (!ratingOptionModel.getRatingoptionsresponses().isEmpty()) {
						for (RatingOptionsResponseModel optionsResponseModel : ratingOptionModel
								.getRatingoptionsresponses()) {
							if (optionsResponseModel.getGroupId() == groupId) {
								newRatingOptionResponse.add(optionsResponseModel);
							}
						}
					}
					ratingOptionModel.getRatingoptionsresponses().clear();
					ratingOptionModel.setRatingoptionsresponses(newRatingOptionResponse);
				}

			}
			if (!model.getThisorthatoptions().isEmpty()) {
				for (ThisorthatOptionModel thisOrThatOptionModel : model.getThisorthatoptions()) {
					List<ThisorthatOptionsResponseModel> newThisOrThatOptionResponse = new ArrayList<>();
					if (!thisOrThatOptionModel.getThisorthatoptionsresponses().isEmpty()) {
						for (ThisorthatOptionsResponseModel optionsResponseModel : thisOrThatOptionModel
								.getThisorthatoptionsresponses()) {
							if (optionsResponseModel.getGroupId() == groupId) {
								newThisOrThatOptionResponse.add(optionsResponseModel);
							}
						}
					}
					thisOrThatOptionModel.getThisorthatoptionsresponses().clear();
					thisOrThatOptionModel.setThisorthatoptionsresponses(newThisOrThatOptionResponse);
				}

			}
		}

		return pollModels;
	}

	@Override
	@Transactional
	public PollModel getPollByPollId(int pollId) {
		return (PollModel) sessionFactory.getCurrentSession().get(PollModel.class, pollId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PollModel> lattestPolls(int start, int pageSize, LattestPollModel lattestPollModel, boolean afterDate,
			boolean safeSerach, Integer userid) {

		Session session = sessionFactory.getCurrentSession();
		List<PollModel> pollModels = null;

		Criteria blockedUserCri = session.createCriteria(BlockedContacts.class);
		blockedUserCri.add(Restrictions.eq("userId", userid));
		blockedUserCri.setProjection(Projections.projectionList().add(Projections.property("blockedId")));
		List<Integer> blockedContacts = blockedUserCri.list();

		// fetch polls before the give date
		if (!afterDate) {
			Criteria criteria1 = session.createCriteria(PollModel.class, "polls");
			criteria1.createAlias("polls.pollGroupModels", "groups");
			criteria1.addOrder(Order.desc("polls.createdDate"));
			criteria1.add(Restrictions.le("polls.createdDate", lattestPollModel.getDate()));
			criteria1.add(Restrictions.eq("groups.groupId", lattestPollModel.getGroupId()));
			criteria1.add(Restrictions.ne("polls.pollOptionType", 5));
			if (!blockedContacts.isEmpty()) {
				criteria1.add(Restrictions.not(Restrictions.in("groups.sharedBy.userId", blockedContacts)));
			}
			criteria1.add(Restrictions.eq("groups.deleteStatus", false));
			if (safeSerach) {
				criteria1.add(Restrictions.ne("polls.pollStatus", PollStatus.ObjectiveContentPoll.getValue()));
			}
			criteria1.setFirstResult(start);
			criteria1.setMaxResults(pageSize);
			pollModels = criteria1.list();
		} else {
			Criteria criteria1 = session.createCriteria(PollModel.class, "polls");
			criteria1.createAlias("polls.pollGroupModels", "groups");
			criteria1.addOrder(Order.desc("polls.createdDate"));
			criteria1.add(Restrictions.gt("polls.createdDate", lattestPollModel.getDate()));
			criteria1.add(Restrictions.eq("groups.groupId", lattestPollModel.getGroupId()));
			if (!blockedContacts.isEmpty()) {
				criteria1.add(Restrictions.not(Restrictions.in("groups.sharedBy.userId", blockedContacts)));
			}
			criteria1.add(Restrictions.ne("polls.pollOptionType", 5));
			criteria1.add(Restrictions.eq("groups.deleteStatus", false));
			if (safeSerach) {
				criteria1.add(Restrictions.ne("polls.pollStatus", PollStatus.ObjectiveContentPoll.getValue()));
			}
			criteria1.setFirstResult(start);
			criteria1.setMaxResults(pageSize);
			pollModels = criteria1.list();
		}

		return pollModels;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PollModel> lattestPollsByQuestion(int start, int pageSize, LattestPollModel lattestPollModel,
			boolean afterDate, boolean safeSerach, Integer userid) {
		Session session = sessionFactory.getCurrentSession();
		List<PollModel> pollModels = null;

		Criteria blockedUserCri = session.createCriteria(BlockedContacts.class);
		blockedUserCri.add(Restrictions.eq("userId", userid));
		blockedUserCri.setProjection(Projections.projectionList().add(Projections.property("blockedId")));
		List<Integer> blockedContacts = blockedUserCri.list();

		Criteria criteria1 = session.createCriteria(PollModel.class, "polls");
		criteria1.createAlias("polls.pollGroupModels", "groups");
		criteria1.add(Restrictions.le("polls.createdDate", lattestPollModel.getDate()));
		criteria1.add(Restrictions.disjunction()
				.add(Restrictions.like("polls.question", lattestPollModel.getQuestion(), MatchMode.ANYWHERE)
						.ignoreCase())
				.add(Restrictions.like("polls.eventName", lattestPollModel.getQuestion(), MatchMode.ANYWHERE)
						.ignoreCase()));
		criteria1.add(Restrictions.eq("groups.groupId", lattestPollModel.getGroupId()));
		if (!blockedContacts.isEmpty()) {
			criteria1.add(Restrictions.not(Restrictions.in("groups.sharedBy.userId", blockedContacts)));
		}
		criteria1.add(Restrictions.eq("groups.deleteStatus", false));
		criteria1.add(Restrictions.ne("polls.pollOptionType", 5));
		if (safeSerach) {
			criteria1.add(Restrictions.ne("polls.pollStatus", PollStatus.ObjectiveContentPoll.getValue()));
		}
		criteria1.addOrder(Order.desc("polls.createdDate"));
		criteria1.setFirstResult(start);
		criteria1.setMaxResults(pageSize);
		pollModels = criteria1.list();

		return pollModels;
	}

	@Override
	@Transactional
	public ImageOptionsResponseModel saveImageResponse(ImageOptionsResponseModel imageOptionsResponseModel) {
		Session session = sessionFactory.getCurrentSession();
		session.save(imageOptionsResponseModel);
		return imageOptionsResponseModel;
	}

	@Override
	@Transactional
	public RatingOptionsResponseModel saveRatingOptionResponse(RatingOptionsResponseModel ratingOptionsResponseModel) {
		Session session = sessionFactory.getCurrentSession();
		session.save(ratingOptionsResponseModel);
		return ratingOptionsResponseModel;
	}

	@Override
	@Transactional
	public MultipleOptionsResponseModel saveMultipleOptionResponse(
			MultipleOptionsResponseModel multipleOptionsResponseModel) {
		Session session = sessionFactory.getCurrentSession();
		session.save(multipleOptionsResponseModel);
		return multipleOptionsResponseModel;
	}

	@Override
	@Transactional
	public ThisorthatOptionsResponseModel saveThisOrThatOptionResponse(
			ThisorthatOptionsResponseModel thisorthatOptionsResponseModel) {
		Session session = sessionFactory.getCurrentSession();
		session.save(thisorthatOptionsResponseModel);
		return thisorthatOptionsResponseModel;
	}

	@Override
	@Transactional
	public ImageOptionModel getImageOptionByImageId(int imgId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ImageOptionModel.class);
		criteria.add(Restrictions.eq("imageOptionsId", imgId));
		return (ImageOptionModel) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public RatingOptionModel getRatingOptionByRatingId(int ratId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(RatingOptionModel.class);
		criteria.add(Restrictions.eq("ratingOptionId", ratId));
		return (RatingOptionModel) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public MultipleOptionModel getMultipleOptionByMulId(int multId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MultipleOptionModel.class);
		criteria.add(Restrictions.eq("mPollOptionsId", multId));
		return (MultipleOptionModel) criteria.uniqueResult();

	}

	@Override
	@Transactional
	public ThisorthatOptionModel getThisOrThatOptions(int thisId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ThisorthatOptionModel.class);
		criteria.add(Restrictions.eq("thisOrThatOptionId", thisId));
		return (ThisorthatOptionModel) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public int getPollLikeCount(int pollId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select count(*) from polllikes where poll_id = :pollId");
		query.setParameter("pollId", pollId);
		Integer count = ((BigInteger) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	@Transactional
	public int getPollCommentCount(int pollId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select count(*) from pollcomments where poll_id = :pollId");
		query.setParameter("pollId", pollId);
		Integer count = ((BigInteger) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	@Transactional
	public Boolean savePollIntrests(List<PollInterestModel> pollIntrestList) {

		Session session = sessionFactory.getCurrentSession();

		for (int i = 0; i < pollIntrestList.size(); i++) {
			String sql = "insert into pollinterests (PollId,InterestId) values (?,?)";

			Query query = session.createSQLQuery(sql);
			query.setParameter(0, pollIntrestList.get(i).getPollId());
			query.setParameter(1, pollIntrestList.get(i).getInterestId());

			query.executeUpdate();
		}

		return Boolean.TRUE;

	}

	@Override
	@Transactional
	public List<PollGroupModel> savePollGroupModels(List<PollGroupModel> pollGroupModel) {
		Session session = sessionFactory.getCurrentSession();
		pollGroupModel.forEach(obj -> {
			session.save(obj);
		});
		return pollGroupModel;
	}

	@Override
	@Transactional
	public PollGroupModel getPollGroupModelByPollAndGroupId(PollModel model, int groupId) {
		return (PollGroupModel) sessionFactory.getCurrentSession().createCriteria(PollGroupModel.class, "group")
				.add(Restrictions.eq("poll.pollId", model.getPollId())).add(Restrictions.eq("groupId", groupId))
				.uniqueResult();
	}

	@Override
	public Object getPollSharedList(int pollId, int groupId) {
		return sessionFactory.getCurrentSession().createCriteria(PollGroupModel.class, "group")
				.add(Restrictions.eq("group.poll.pollId", pollId)).list();
	}

	@Override
	public int getPollShareCount(int pollId, int groupId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select count(*) from group_polls where poll_id = :pollId");
		query.setParameter("pollId", pollId);
		Integer count = ((BigInteger) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	public PollModel getPollByPollIdAndGroupId(int pollId, int groupId) {
		PollGroupModel groupModel = (PollGroupModel) sessionFactory.getCurrentSession()
				.createCriteria(PollGroupModel.class, "pgm").add(Restrictions.eq("pgm.poll.pollId", pollId))
				.add(Restrictions.eq("groupId", groupId)).uniqueResult();
		return groupModel == null ? null : groupModel.getPoll();
	}

	@Override
	public boolean saveRSVPResponse(RsvpResponseModel model) {
		try {
			sessionFactory.getCurrentSession().save(model);
		} catch (Exception e) {
			logger.error("SAVE " + model.toString());
		}
		return model.getRsvpResponseId() != null ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchUserTokensByGroupIdAndPollIdFromPollLike(Integer groupId, Integer pollId) {
		String queryString = "select pol.poll_id, usr.UserId, usr.fcmToken from tap.polls pol \r\n"
				+ "join tap.polllikes pollik on pollik.poll_id = pol.poll_id\r\n"
				+ "join tap.users usr on pollik.user_id = usr.UserId\r\n"
				+ "where pollik.groupId = ? and pol.poll_id = ?;";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(queryString);
		query.setInteger(0, groupId);
		query.setInteger(1, pollId);
		List<Object[]> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteLikeByGroupIdAndPollId(int groupId, PollModel pollModel, UsersModel usersModel) {
		Session session = sessionFactory.getCurrentSession();
		List<PollLikeModel> models = session.createCriteria(PollLikeModel.class)
				.add(Restrictions.eq("groupId", groupId)).add(Restrictions.eq("poll", pollModel))
				.add(Restrictions.eq("usersModel", usersModel)).list();
		if (!models.isEmpty()) {
			for (PollLikeModel model : models) {
				model.setPoll(null);
				model.setUsersModel(null);
				session.delete(model);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String fetchPollOwnerTokenFromPollId(int pollId) {
		String token = null;
		Session session = sessionFactory.getCurrentSession();
		String myQuery = "select usr.fcmToken from tap.users usr join tap.polls pol on usr.UserId = pol.CreatedBy where pol.poll_id = ?";
		Query query = session.createSQLQuery(myQuery);
		query.setInteger(0, pollId);
		try {
			token = (String) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	@Override
	public GroupsModel fetchGroupModelByGroupId(int id) {
		return (GroupsModel) sessionFactory.getCurrentSession().get(GroupsModel.class, id);
	}

	@Override
	public boolean deletePollById(Long groupId, Integer pollId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "update PollGroupModel set deleteStatus=:status, deleteDate=:currentdate where poll_id=:pollid and groupId=:groupId";
		Query query = session.createQuery(hql);
		query.setLong("groupId", groupId);
		query.setBoolean("status", true);
		query.setDate("currentdate", new Date());
		query.setInteger("pollid", pollId);
		int res = query.executeUpdate();
		return res > 0 ? true : false;
	}

	@Override
	public boolean updatePollDateAndResultType(byte type, Date date, Long pollId) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer buffer = new StringBuffer();
		buffer.append("update PollModel set ");
		if (type >= 0) {
			buffer.append("resultDisplayType=" + type + ",");
		}
		if (date != null) {
			buffer.append(" expiresOn='" + new Timestamp(date.getTime()) + "',");
		}
		buffer.replace(0, buffer.length(), buffer.substring(0, buffer.length() - 1));
		buffer.append(" where poll_id=" + pollId);
		Query query = session.createQuery(buffer.toString());
		return query.executeUpdate() > 0 ? true : false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> fetchMinimalUserDataByGroupIds(Long[] ids) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select distinct user.UserId, user.FirstName, user.profile_pic, user.fcmToken from tap.users user join tap.groupmembers grpmem on user.UserId = grpmem.UserId where grpmem.GroupId in (:ids)";
		Query query = session.createSQLQuery(sqlQuery);
		query.setParameterList("ids", ids);
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> fetchMinimalUserDataByUserIds(Long[] ids) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select distinct user.UserId, user.FirstName, user.profile_pic, user.fcmToken from tap.users user where user.UserId in (:ids)";
		Query query = session.createSQLQuery(sqlQuery);
		query.setParameterList("ids", ids);
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PollModel> fetchRsvpPollsByUserId(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(PollModel.class);
		criteria.createAlias("rsvpUsersList", "users");
		criteria.add(Restrictions.eq("users.userId", Long.valueOf(userId)));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PollModel> fetchRsvpPollsByUserIdByPage(Integer userId, Integer count, Integer offset, String event) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(PollModel.class);
		criteria.createAlias("rsvpUsersList", "users");
		criteria.add(Restrictions.eq("users.userId", Long.valueOf(userId)));
		criteria.setFirstResult(offset);
		criteria.setMaxResults(count);
		criteria.addOrder(Order.desc("createdDate"));
		if (event != null) {
			criteria.add(Restrictions.like("eventName", event, MatchMode.ANYWHERE));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public boolean updatePollToObjectiveContentPoll(PollModel model) {
		Session session = sessionFactory.getCurrentSession();
		if (model.getPollId() > 0) {
			Query query = session.createQuery("update PollModel set pollStatus =:status where pollId =:pollId");
			query.setByte("status", PollStatus.ObjectiveContentPoll.getValue());
			query.setInteger("pollId", model.getPollId());
			return query.executeUpdate() > 0 ? true : false;
		}
		return false;
	}

	@Override
	public boolean savePollReport(ReportPoll poll) {
		Session session = sessionFactory.getCurrentSession();
		session.save(poll);
		return poll.getId() != null ? true : false;
	}

	@Override
	public boolean checkUserBlocked(Integer userId, Integer blockedId) {
		Session session = sessionFactory.getCurrentSession();
		BlockedContacts obj = (BlockedContacts) session.createCriteria(BlockedContacts.class)
				.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("blockedId", blockedId)).uniqueResult();
		return obj != null ? true : false;
	}

}
