package com.cyspan.tap.notifications;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cyspan.tap.appconfig.HttpPostNotification;
import com.cyspan.tap.commons.PollResponseType;
import com.cyspan.tap.group.dao.GroupsDAO;
import com.cyspan.tap.group.model.GroupmembersModel;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.group.services.GroupServices;
import com.cyspan.tap.poll.dao.PollsDAO;
import com.cyspan.tap.poll.models.ImageOptionModel;
import com.cyspan.tap.poll.models.ImageOptionsResponseModel;
import com.cyspan.tap.poll.models.MultipleOptionModel;
import com.cyspan.tap.poll.models.MultipleOptionsResponseModel;
import com.cyspan.tap.poll.models.PollCommentModel;
import com.cyspan.tap.poll.models.PollLikeModel;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.poll.models.RatingOptionModel;
import com.cyspan.tap.poll.models.RatingOptionsResponseModel;
import com.cyspan.tap.poll.models.RsvpUserModel;
import com.cyspan.tap.poll.models.ThisorthatOptionModel;
import com.cyspan.tap.poll.models.ThisorthatOptionsResponseModel;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UsersModel;

@Async
@Service
public class PollAsynNotificationImpl implements PollAsynNotification {

	static Logger logger = Logger.getLogger(PollAsynNotificationImpl.class.getName());

	@Autowired
	HttpPostNotification tapNotification;

	@Autowired
	UsersDao userDao;

	@Autowired
	NotificationServiceDao notidao;

	@Autowired
	PollsDAO pollsDAO;

	@Autowired
	GroupServices groupService;

	@Autowired
	NotificationServiceDao notiDao;

	@Autowired
	GroupsDAO groupDao;

	@Override
	public void pollResponseNotifications(PollModel pollModel, GroupsModel groupsModel, String currentUsername,
			Map<Integer, String> otherFcmTokens, Integer totalResponseCount) {

		String pollownerName = pollModel.getUsersModel().getFirstName();
		String groupName = groupsModel.getGroupName();
		String pollQuestion = pollModel.getQuestion();
		Date pollcreateDate = pollModel.getCreatedDate();
		LocalDateTime pollCreatedLocaldateTime = null;
		if (pollcreateDate != null) {
			pollCreatedLocaldateTime = LocalDateTime.ofInstant(pollcreateDate.toInstant(), ZoneId.systemDefault());
		}
		Duration duration = Duration.between(pollCreatedLocaldateTime, LocalDateTime.now());
		// case 1 : before 2 hours of poll create
		if (duration.toHours() < 2) {
			logger.info("poll create difference below 2");
			if (pollModel.getUsersModel().getFcmToken() != null) {
				this.sendToPollOwner1(pollModel.getUsersModel().getUserId(), pollModel.getUsersModel().getFcmToken(),
						groupsModel.getGroupId(), groupName, pollQuestion, currentUsername,
						groupsModel.getGroupIconUrl(), pollModel.getPollId());
			}
			otherFcmTokens.forEach((key, value) -> {
				this.sendToOtherGroupMembers1(key, value, groupsModel.getGroupId(), groupName, pollQuestion,
						pollownerName, currentUsername, groupsModel.getGroupName(), pollModel.getPollId());
			});
		}

		// case 2 : after 2 hour of poll create
		if (duration.toHours() >= 2) {
			logger.info("poll create difference above 2");
			if (pollModel.getUsersModel().getFcmToken() != null) {
				this.sendToPollOwner2(pollModel.getUsersModel().getUserId(), pollModel.getUsersModel().getFcmToken(),
						groupsModel.getGroupId(), groupName, pollQuestion, currentUsername,
						groupsModel.getGroupIconUrl(), pollModel.getPollId(), totalResponseCount);
			}
			otherFcmTokens.forEach((key, value) -> {
				this.sendToOtherGroupMembers2(key, value, groupsModel.getGroupId(), groupName, pollQuestion,
						pollownerName, currentUsername, groupsModel.getGroupName(), pollModel.getPollId(),
						totalResponseCount);
			});
		}
	}

	private void sendToPollOwner1(Integer userId, String ownerToken, Integer groupId, String groupname,
			String pollQuestion, String username, String iconurl, Integer pollId) {
		String no_title = "Poll response";
		String no_body = username + " voted your poll on " + groupname.toUpperCase() + ": " + pollQuestion;
		String icon = iconurl;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(ownerToken, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("sendToPollOwner");
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, userId));
		}
	}

	private void sendToOtherGroupMembers1(Integer userId, String memberToken, Integer groupId, String groupname,
			String pollQuestion, String pollownerName, String username, String iconurl, Integer pollId) {
		String no_title = "Poll response";
		String no_body = username + " voted on your attended poll by" + pollownerName + " in " + groupname.toUpperCase()
				+ ": " + pollQuestion;
		String icon = iconurl;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(memberToken, no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("sendToOtherGroupMembers");
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, userId));
		}
	}

	private void sendToPollOwner2(Integer userId, String ownerToken, Integer groupId, String groupname,
			String pollQuestion, String username, String iconurl, Integer pollId, Integer count) {
		String no_title = "Poll response";
		String no_body = username + " and other " + count + " voted your poll on " + groupname.toUpperCase() + ": "
				+ pollQuestion;
		String icon = iconurl;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(ownerToken, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("sendToPollOwner");
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, userId));
		}
	}

	private void sendToOtherGroupMembers2(Integer userId, String memberToken, Integer groupId, String groupname,
			String pollQuestion, String pollownerName, String username, String iconurl, Integer pollId, Integer count) {
		String no_title = "Poll response";
		String no_body = username + " and other " + count + " voted on your attended poll by" + pollownerName + " in "
				+ groupname.toUpperCase() + ": " + pollQuestion;
		String icon = iconurl;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(memberToken, no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("sendToOtherGroupMembers");
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, userId));
		}
	}

	@Override
	public void pollLikeNotification(PollLikeModel likeModel) {
		if (likeModel.getPoll() != null && likeModel.getUsersModel() != null) {

			PollModel pollModel = likeModel.getPoll();
			UsersModel usersModel = likeModel.getUsersModel();
			GroupsModel groupModel = pollsDAO.fetchGroupModelByGroupId(likeModel.getGroupId());
			String userName = usersModel.getFirstName().toUpperCase();
			String groupName = groupModel != null ? groupModel.getGroupName().toUpperCase() : null;
			String pollQuestion = pollModel.getQuestion();
			String ownerToken = pollModel.getUsersModel().getFcmToken();
			List<PollLikeModel> pollLikeModels = pollModel.getPolllikes().stream()
					.filter(obj -> obj.getGroupId() == likeModel.getGroupId()).collect(Collectors.toList());
			Integer totalPollLikeCount = pollLikeModels.size();
			LocalDateTime pollCreatedLocaldateTime = null;
			Set<String> fcmTokens = new HashSet<>();
			Date pollCreatedDate = pollModel.getCreatedDate();

			if (pollCreatedDate != null) {
				pollCreatedLocaldateTime = LocalDateTime.ofInstant(pollCreatedDate.toInstant(), ZoneId.systemDefault());
			} else {
				pollCreatedLocaldateTime = LocalDateTime.now();
			}
			Duration duration = Duration.between(pollCreatedLocaldateTime, LocalDateTime.now());
			if (likeModel.getGroupId() > 0) {
				List<Object[]> groupMembers = userDao.fetchFCMTokenByGroupId(likeModel.getGroupId());
				for (Object[] obj : groupMembers) {
					String token = obj[3] != null ? (String) obj[3] : "dummyToken";
					// Integer userid = obj[2] != null ? (Integer) obj[2] : null;
					fcmTokens.add(token);
				}
				fcmTokens.remove(ownerToken); // remove poll owner
				fcmTokens.remove(usersModel.getFcmToken()); // reomve vote creater
			} else {

			}
			if (duration.toHours() < 12) {
				this.pollLikeNotificationToOwnerBeforeTwoHour(usersModel.getUserId(), userName, groupModel.getGroupId(),
						groupName, pollQuestion, groupModel.getGroupIconBigUrl(), pollModel.getPollId(), ownerToken);

				for (String userToken : fcmTokens) {
					this.pollLikeNotificationToOtherMembersBeforeTwoHour(userName, groupModel.getGroupId(), groupName,
							pollQuestion, groupModel.getGroupIconBigUrl(), pollModel.getPollId(), userToken);
				}
			} else {
				this.pollLikeNotificationToOwnerAfterTwoHour(usersModel.getUserId(), userName, groupModel.getGroupId(),
						groupName, pollQuestion, groupModel.getGroupIconBigUrl(), pollModel.getPollId(), ownerToken,
						(totalPollLikeCount - 1));
				for (String userToken : fcmTokens) {
					this.pollLikeNotificationToOtherMembersAfterTwoHour(userName, groupModel.getGroupId(), groupName,
							pollQuestion, groupModel.getGroupIconBigUrl(), pollModel.getPollId(), userToken,
							(totalPollLikeCount - 1));
				}
			}
		}
	}

	private void pollLikeNotificationToOwnerBeforeTwoHour(Integer userId, String likedUserName, Integer groupId,
			String groupName, String pollQuestion, String groupIcon, int pollId, String ownerToken) {
		String no_title = "Poll Likes";
		String no_body = likedUserName + " liked your poll in " + groupName + " : " + pollQuestion;
		String icon = groupIcon;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(ownerToken, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("poll like notification to Poll Owner Success!");
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, userId));
		}
	}

	private void pollLikeNotificationToOtherMembersBeforeTwoHour(String likedUserName, Integer groupId,
			String groupName, String pollQuestion, String groupIcon, int pollId, String ownerToken) {
		String no_title = "Poll Likes";
		String no_body = likedUserName + " liked your attended poll in " + groupName + " : " + pollQuestion;
		String icon = groupIcon;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(ownerToken, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("poll like notification to Poll Attended Success!");
		}
	}

	private void pollLikeNotificationToOwnerAfterTwoHour(Integer userId, String likedUserName, Integer groupId,
			String groupName, String pollQuestion, String groupIcon, int pollId, String ownerToken, int count) {
		String no_title = "Poll Likes";
		String no_body = likedUserName + " and other " + count + " liked your poll in " + groupName + " : "
				+ pollQuestion;
		String icon = groupIcon;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(ownerToken, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("poll like notification to Poll Attended Success!");
		}
	}

	private void pollLikeNotificationToOtherMembersAfterTwoHour(String likedUserName, Integer groupId, String groupName,
			String pollQuestion, String groupIcon, int pollId, String ownerToken, int count) {
		String no_title = "Poll Likes";
		String no_body = likedUserName + " and other " + count + " liked your attended poll in " + groupName + " : "
				+ pollQuestion;
		String icon = groupIcon;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(ownerToken, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("poll like notification to Poll Attended Success!");
		}
	}

	@Override
	public void PollCommentNotification(PollCommentModel commentModel) {
		GroupsModel groupModel = null;
		if (commentModel.getPollCommentId() > 0) {
			if (commentModel.getGroupId() > 0) {
				groupModel = groupService.fetchGroupByGroupId(Integer.valueOf(commentModel.getGroupId()));
			}
			if (commentModel.getPoll().getUsersModel() != null) {
				this.pollCommentNotificationToOwner(commentModel.getPoll().getUsersModel().getUserId(),
						commentModel.getUsersModel().getFirstName(), groupModel.getGroupId(), groupModel.getGroupName(),
						commentModel.getPoll().getQuestion(), groupModel.getGroupIconBigUrl(),
						commentModel.getPoll().getPollId(), commentModel.getPoll().getUsersModel().getFcmToken());
			}
			if (!commentModel.getPoll().getPollcomments().isEmpty()) {
				List<PollCommentModel> previousCommenters = commentModel.getPoll().getPollcomments();
				Map<String, String> maper = previousCommenters.stream().map(PollCommentModel::getUsersModel)
						.filter(obj -> obj.getFcmToken() != null).distinct()
						.collect(Collectors.toMap(UsersModel::getFirstName, UsersModel::getFcmToken));
				String grpname = groupModel.getGroupName();
				String grpicon = groupModel.getGroupIconBigUrl();
				Integer groupid = groupModel.getGroupId();
				maper.forEach((k, v) -> {
					this.pollCommentNotificationToOtherMembers(commentModel.getUsersModel().getFirstName(), groupid,
							grpname, commentModel.getPoll().getQuestion(), grpicon, commentModel.getPoll().getPollId(),
							v);
				});

			}
		}
	}

	private void pollCommentNotificationToOwner(Integer userId, String commentedUser, Integer groupId, String groupName,
			String pollQuestion, String groupIcon, int pollId, String ownerToken) {
		String no_title = "Poll Comment";
		String no_body = commentedUser + " commented on your poll on " + groupName + " : " + pollQuestion;
		String icon = groupIcon;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(ownerToken, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("poll comment notification to Poll Owner Success!");
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, userId));
		}
	}

	private void pollCommentNotificationToOtherMembers(String commentedUser, Integer groupId, String groupName,
			String pollQuestion, String groupIcon, int pollId, String memberToken) {
		String no_title = "Poll Likes";
		String no_body = commentedUser + " commented on your attended poll on " + groupName + " : " + pollQuestion;
		String icon = groupIcon;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(memberToken, no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("poll Comment notification to Poll Group Members Success!");
		}
	}

	@Override
	public void PollExpireReminderNotification(int remianHour, String topic, String groupName, String pollQuestion,
			Integer pollId, String groupIcon, Integer groupId) {
		String no_title = "Poll Reminder";
		String no_body = "You have " + remianHour + " hours to vote the poll in " + groupName + ": " + pollQuestion;
		String icon = groupIcon;
		String c_action = null;
		String notification_type = "group/" + groupId + "/poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildTopic(topic, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("poll Comment notification to Poll Group Members Success!");
		}

	}

	@Override
	public void RsvpCreateNotification(PollModel model) {
		if (!model.getRsvpUsersList().isEmpty()) {
			for (RsvpUserModel rsvpUser : model.getRsvpUsersList()) {
				if (rsvpUser.getUserToken() != null) {
					this.sendRsvpCreateNotification(model.getUsersModel().getFirstName(), model.getEventName(),
							rsvpUser.getUserToken(), model.getPollId(), rsvpUser.getUserId().intValue());
				}
			}
		}
	}

	private void sendRsvpCreateNotification(String pollOwnerName, String eventName, String userToken, Integer pollId,
			Integer userId) {
		String no_title = "New Rsvp Poll";
		String no_body = pollOwnerName + " created a new rsvp poll :" + eventName;
		String icon = "";
		String c_action = null;
		String notification_type = "poll/" + pollId;
		boolean content_available = true;
		tapNotification.buildToken(userToken, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("rsvp poll created - notification to " + userId);
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, userId));
		}
	}

	@Override
	public void PollResponseNotifications(Object responsModel, PollResponseType type, int groupId, UsersModel user) {

		if (groupId == 0) {
			return;
		}

		GroupsModel groupModel = groupDao.fetchGroupByGroupId(groupId);
		String userName = user.getFirstName().toUpperCase();
		String ownerToken = null;
		Integer totalResponseCount = 0;
		Map<Integer, String> userIdFcmMap = new HashMap<>();
		PollModel pollModel = null;

		switch (type) {
		case ImageOptions: {
			ImageOptionModel model = (ImageOptionModel) responsModel;
			pollModel = model.getPoll();
			ownerToken = model.getPoll().getUsersModel().getFcmToken();
			totalResponseCount = model.getImageoptionsresponses().size();
			for (ImageOptionsResponseModel obj : model.getImageoptionsresponses()) {
				if (obj.getUserId() > 0 && obj.getGroupId() == groupId) {
					String fcm = userDao.fetchFCMTokenByUserId(obj.getUserId());
					if (fcm != null) {
						if (!fcm.equals(ownerToken)) { // check for poll owner token
							if (!fcm.equals(user.getFcmToken())) { // check for poll voter token
								userIdFcmMap.put(obj.getUserId(), fcm);
							}
						}
					}
				}
			}
		}
			break;
		case MultipleOptions: {
			MultipleOptionModel model = (MultipleOptionModel) responsModel;
			pollModel = model.getPoll();
			ownerToken = model.getPoll().getUsersModel().getFcmToken();
			totalResponseCount = model.getMultipleoptionsresponses().size();
			for (MultipleOptionsResponseModel obj : model.getMultipleoptionsresponses()) {
				if (obj.getUserId() > 0 && obj.getGroupId() == groupId) {
					String fcm = userDao.fetchFCMTokenByUserId(obj.getUserId());
					if (fcm != null) {
						if (!fcm.equals(ownerToken)) { // check for poll owner token
							if (!fcm.equals(user.getFcmToken())) { // check for poll voter token
								userIdFcmMap.put(obj.getUserId(), fcm);
							}
						}
					}
				}
			}
		}
			break;
		case RatingOptions: {
			RatingOptionModel model = (RatingOptionModel) responsModel;
			pollModel = model.getPoll();
			ownerToken = model.getPoll().getUsersModel().getFcmToken();
			totalResponseCount = model.getRatingoptionsresponses().size();
			for (RatingOptionsResponseModel obj : model.getRatingoptionsresponses()) {
				if (obj.getUserId() > 0 && obj.getGroupId() == groupId) {
					String fcm = userDao.fetchFCMTokenByUserId(obj.getUserId());
					if (fcm != null) {
						if (!fcm.equals(ownerToken)) { // check for poll owner token
							if (!fcm.equals(user.getFcmToken())) { // check for poll voter token
								userIdFcmMap.put(obj.getUserId(), fcm);
							}
						}
					}
				}
			}
		}
			break;
		case ThisOrThatOptions: {
			ThisorthatOptionModel model = (ThisorthatOptionModel) responsModel;
			pollModel = model.getPoll();
			ownerToken = model.getPoll().getUsersModel().getFcmToken();
			totalResponseCount = model.getThisorthatoptionsresponses().size();
			for (ThisorthatOptionsResponseModel obj : model.getThisorthatoptionsresponses()) {
				if (obj.getUserId() > 0 && obj.getGroupId() == groupId) {
					String fcm = userDao.fetchFCMTokenByUserId(obj.getUserId());
					if (fcm != null) {
						if (!fcm.equals(ownerToken)) { // check for poll owner token
							if (!fcm.equals(user.getFcmToken())) { // check for poll voter token
								userIdFcmMap.put(obj.getUserId(), fcm);
							}
						}
					}
				}
			}
		}
			break;
		default:
			break;
		}

		/**
		 * Some Changes made, All responded users token list is fetch inside the switch
		 * case, so below code is for checking of notification mute or not, if muted
		 * service return true, it (token) will remove from it. The web service
		 * userDao.fetchFCMTokenByGroupId(groupId) is for fetch all group members with
		 * token and check for mute state.
		 * 
		 * As per new requirement Public Poll Not Send Vote Notifications / Only Groups
		 */
		if (groupId > 0) {
			Map<Integer, String> finalTokens = new HashMap<>();
			Integer pollId = pollModel.getPollId();
			userIdFcmMap.forEach((key, value) -> {
				if (!notiDao.checkNotificationMuted(Long.valueOf(key), Long.valueOf(pollId))) {
					finalTokens.put(key, value);
				}
			});
			this.pollResponseNotifications(pollModel, groupModel, userName, finalTokens, totalResponseCount);
		}
	}

	public void PollCreateToGroupMembers(PollModel poll, GroupsModel group, String pollOwnerName) {
		for (GroupmembersModel model : group.getGroupMembers()) {
			if (!poll.getUsersModel().getUserId().equals(model.getUserId())) { // skip poll owner
				String token = userDao.fetchFCMTokenByUserId(model.getUserId());
				if (token != null) {
					this.pollCreatedInGroupToMembers(token, poll, group, pollOwnerName, model.getUserId());
				}
			}
		}
	}

	private void pollCreatedInGroupToMembers(String token, PollModel pollModel, GroupsModel groupsModel,
			String pollOwner, Integer memeberId) {
		String no_title = "New Poll";
		String no_body = pollOwner + " has created poll in " + groupsModel.getGroupName();
		String icon = groupsModel.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + groupsModel.getGroupId() + "/poll/" + pollModel.getPollId();
		boolean content_available = true;
		tapNotification.buildToken(token, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("poll create in group notification send to " + groupsModel.getGroupName());
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, memeberId));
		}

	}
}
