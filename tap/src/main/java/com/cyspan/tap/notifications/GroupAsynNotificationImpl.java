package com.cyspan.tap.notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cyspan.tap.appconfig.HttpPostNotification;
import com.cyspan.tap.group.model.GroupmembersModel;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.user.dao.UsersDao;
import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;

@Async
@Service
public class GroupAsynNotificationImpl implements GroupAsynNotification {

	static Logger logger = Logger.getLogger(GroupAsynNotificationImpl.class.getName());

	@Autowired
	HttpPostNotification tapNotification;

	@Autowired
	UsersDao userDao;

	@Autowired
	NotificationServiceDao notidao;

	@Override
	public void subscribeUserToGroup(GroupsModel group, Integer createrId) {
		if (!group.getGroupMembers().isEmpty()) {
			try {
				List<String> usertokens = new ArrayList<>();
				for (GroupmembersModel model : group.getGroupMembers()) {
					String token = userDao.fetchFCMTokenByUserId(model.getUserId());
					model.setUserToken(token);
					if (token != null) {
						usertokens.add(token);
					}
				}
				ApiFuture<TopicManagementResponse> response = FirebaseMessaging.getInstance()
						.subscribeToTopicAsync(usertokens, group.getGroupUniqueName());
				if (response.get().getSuccessCount() != 0) {
					logger.info(response.get().getSuccessCount() + "(" + usertokens.size() + ") users in "
							+ group.getGroupName() + " group subscribed");
					// this.groupCreateNotification(group);

					// send notification except owner
					logger.info("notification skiped for user with id " + createrId);
					for (GroupmembersModel model : group.getGroupMembers()) {
						if (createrId != model.getUserId()) {
							this.groupCreateNotificationToTokens(group, model.getUserToken(), model.getUserId());
							notidao.saveNotifiaction(new NotificationEntity("Added To Group",
									"You are Added to Tap Group: " + group.getGroupName(),
									"group/" + group.getGroupId(), group.getGroupIconUrl(), model.getUserId()));
						}
					}

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private void groupCreateNotificationToTokens(GroupsModel model, String token, Integer userId) {
		String no_title = "Tap Group Created";
		String no_body = "You have been added to the " + model.getGroupName().toUpperCase() + " Group";
		String icon = model.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + model.getGroupId();
		;
		boolean content_available = true;
		tapNotification.buildToken(token, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("create group notification send to " + model.getGroupName());
		}
	}

	@Override
	public void groupCreateNotification(GroupsModel model) {
		String no_title = "Tap Group Created";
		String no_body = "You have added to the " + model.getGroupName().toUpperCase() + " Group";
		String icon = model.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + model.getGroupId();
		;
		boolean content_available = true;
		tapNotification.buildTopic(model.getGroupUniqueName(), no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("create group notification send to " + model.getGroupName());
			model.getGroupMembers().forEach(obj -> {
				notidao.saveNotifiaction(
						new NotificationEntity(no_title, no_body, c_action, notification_type, icon, obj.getUserId()));
			});
		}
	}

	@Override
	public void groupNameChangeNotification(GroupsModel model, String oldName, String username) {
		String no_title = "Tap Group Name Changed";
		String no_body = username + " has changed the Group name from " + oldName + " to " + model.getGroupName();
		String icon = model.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + model.getGroupId();
		;
		boolean content_available = true;
		tapNotification.buildTopic(model.getGroupUniqueName(), no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("group name change notification send to " + model.getGroupName());
			model.getGroupMembers().forEach(obj -> {
				notidao.saveNotifiaction(
						new NotificationEntity(no_title, no_body, c_action, notification_type, icon, obj.getUserId()));
			});
		}

	}

	@Override
	public void groupIconChangeNotification(GroupsModel model, String username) {
		String no_title = "Tap Group Icon Changed";
		String no_body = username + " has changed the Group icon";
		String icon = model.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + model.getGroupId();
		;
		boolean content_available = true;
		tapNotification.buildTopic(model.getGroupUniqueName(), no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("group icon change notification send to " + model.getGroupName());
			model.getGroupMembers().forEach(obj -> {
				notidao.saveNotifiaction(
						new NotificationEntity(no_title, no_body, c_action, notification_type, icon, obj.getUserId()));
			});
		}

	}

	@Override
	public void groupNewUserAddedToOthers(GroupsModel model, String newUsername) {
		String no_title = "New member added";
		String no_body = newUsername + " added to the tap " + model.getGroupName() + " group";
		String icon = model.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + model.getGroupId();
		;
		boolean content_available = true;
		tapNotification.buildTopic(model.getGroupUniqueName(), no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("new user added to your group notification send to " + model.getGroupName());
			model.getGroupMembers().forEach(obj -> {
				notidao.saveNotifiaction(
						new NotificationEntity(no_title, no_body, c_action, notification_type, icon, obj.getUserId()));
			});
		}
	}

	@Override
	public void groupNewUserAddedToNewuser(GroupsModel model, String token, Integer userid) {
		String no_title = "Tap Group Name Changed";
		String no_body = "You have added to tap group " + model.getGroupName();
		String icon = model.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + model.getGroupId();
		;
		boolean content_available = true;
		tapNotification.buildToken(token, no_title, no_body, icon, c_action, notification_type, content_available);
		if (tapNotification.sendNotification()) {
			logger.info("added to group for single user notification send to " + model.getGroupName());
			notidao.saveNotifiaction(
					new NotificationEntity(no_title, no_body, c_action, notification_type, icon, userid));
		}

	}

	@Override
	public void groupMemberRemovedBy(GroupsModel model, String removedUsername, String removerUsername) {
		String no_title = "Member removed";
		String no_body = removedUsername + " has removed by " + removerUsername + " in group " + model.getGroupName();
		String icon = model.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + model.getGroupId();
		;
		boolean content_available = true;
		tapNotification.buildTopic(model.getGroupUniqueName(), no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("user removed from group notification send to " + model.getGroupName());
			model.getGroupMembers().forEach(obj -> {
				notidao.saveNotifiaction(
						new NotificationEntity(no_title, no_body, c_action, notification_type, icon, obj.getUserId()));
			});
		}

	}

	@Override
	public void groupMemberLeftfromGroup(GroupsModel model, String removedUsername) {
		String no_title = "Member Left group";
		String no_body = removedUsername + " left the group " + model.getGroupName();
		String icon = model.getGroupIconUrl();
		String c_action = null;
		String notification_type = "group/" + model.getGroupId();
		;
		boolean content_available = true;
		tapNotification.buildTopic(model.getGroupUniqueName(), no_title, no_body, icon, c_action, notification_type,
				content_available);
		if (tapNotification.sendNotification()) {
			logger.info("your left group notification send to " + model.getGroupName());
			model.getGroupMembers().forEach(obj -> {
				notidao.saveNotifiaction(
						new NotificationEntity(no_title, no_body, c_action, notification_type, icon, obj.getUserId()));
			});
		}

	}

}
