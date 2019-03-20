package com.cyspan.tap.notifications;

import java.io.IOException;

import com.cyspan.tap.group.model.GroupsModel;

public interface GroupNotificationService {

	public void newPoll(String userName, String groupName, String toAddress, String icon) throws IOException;

	/**
	 * Send Notification to Topic : After Create Group : You Have Added To a group
	 * <GroupName>
	 * 
	 * @param GroupsModel groupsModel
	 */
	public void addedToAGroupToTopic(GroupsModel groupsModel);

	/**
	 * Send Notification to Token : Single User : You Have Added To a group
	 * <GroupName>
	 * 
	 * @param FCM Token
	 */
	public void addedToAGroupToToken(String fcmToken, String groupName);

	/**
	 * Send Notification to Token : Existing user : New your added to your group
	 * <GroupName>
	 *
	 */
	public void newUserAddedToYourGroupToToken(String token, String groupName, int count);

	/**
	 * Send Notification to Topic : All members : Group name change to <new group
	 * name>
	 */
	public void groupNameChangedToToken(String topic, String oldName, String newName);

	/**
	 * Send Notification to Token : Removed user only : You are removed by admin
	 */
	public void userRemovedByAdmin(String token, String groupName);

	/**
	 * Send Notification to Topic : All members : <username> has left the
	 * <groupname>
	 */
	public void userLeftGroup(String username, String topic, String groupName);

	/**
	 * Unsubscribe all notifications
	 */
	public void unsubscribeNotification(String userToken, String topics);

	/**
	 * Send Notification to Topic :
	 */
	public void reminderExpirePoll(int count, String question, String topic, String group);

	/**
	 * Send Notification to Topic :
	 */
	public void reminderHoursToPoll(int hour, String question, String topic, String group);

	/**
	 * Send Notification to Poll owner about a user commented on his poll
	 * 
	 * @param userName     : commenter name
	 * @param pollQuestion : poll first line
	 * @param userName     : poll owner fcm token
	 */
	public void pollCommentNotification(String commenterName, String pollQuestion, String groupName, String userToken);

	/**
	 * Send Notification to the users those are commented on a poll
	 * 
	 * @param userName     : commenter name
	 * @param pollQuestion : poll first line
	 * @param userName     : poll commenter token
	 */
	public void pollCommentNotification2(String commenterName, String pollQuestion, String userToken);

	public void sendCustomNotificationToToken(String title, String message, String token);

	public void sendCustomNotificationToTopic(String title, String message, String topic);

	public void sendPollResponseNotificationToOwner(String username, String groupname, String question,
			String pollOwnerToken, int totalResponseCount);

	public void sendPollResponseNotificationToMembers(String username, String groupname, String question,
			String userToken, int totalResponseCount);

	public void sendPollLikeNotificationToOwner(String username, String groupname, String question,
			String pollOwnerToken, int totalResponseCount);

	public void sendPollLikeNotificationToMembers(String username, String groupname, String question, String userToken,
			int totalResponseCount);
}
