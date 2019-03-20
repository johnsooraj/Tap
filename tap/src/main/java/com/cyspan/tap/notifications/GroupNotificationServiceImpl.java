package com.cyspan.tap.notifications;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cyspan.tap.appconfig.CustomAsyncExceptionHandler;
import com.cyspan.tap.appconfig.HttpPostNotification;
import com.cyspan.tap.group.model.GroupsModel;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Async
@Service
public class GroupNotificationServiceImpl implements GroupNotificationService {

	static Logger logger = Logger.getLogger(CustomAsyncExceptionHandler.class.getName());

	@Autowired
	HttpPostNotification tapNotification;

	private static Future<String> sendNotification(Message message) {
		return FirebaseMessaging.getInstance().sendAsync(message);
	}

	@Override
	public void newPoll(String userName, String groupName, String toAddress, String icon) throws IOException {
		String body = userName + " has been created a poll in " + groupName;
		Notification notification = new Notification("New Poll", body);
		Message message = Message.builder().setNotification(notification).setTopic(toAddress).build();

		try {
			logger.info(sendNotification(message).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addedToAGroupToTopic(GroupsModel groupsModel) {
		String body = "You Have Added To the group " + groupsModel.getGroupName().toLowerCase();
		Notification notification = new Notification("Added To Group", body);
		Message message = Message.builder().setNotification(notification).setTopic(groupsModel.getGroupUniqueName())
				.build();

		Map<String, String> mes = new HashMap<>();
		mes.put("test1", "test1");
		mes.put("title", "custome stuctre");
		mes.put("body", "body");
		mes.put("test2", "test1");
		mes.put("test3", "test1");

		Message message2 = Message.builder().putAllData(mes).setTopic(groupsModel.getGroupUniqueName()).build();

		try {
			logger.info(sendNotification(message).get());
			logger.info(sendNotification(message2).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addedToAGroupToToken(String fcmToken, String groupName) {
		String body = "You Have Added To the group " + groupName.toLowerCase();
		Notification notification = new Notification("Added To New Group", body);
		Message message = Message.builder().setNotification(notification).setToken(fcmToken).build();

		Map<String, String> mes = new HashMap<>();
		mes.put("test1", "test1");
		mes.put("title", "custome stuctre");
		mes.put("body", "body");
		mes.put("test2", "test1");
		mes.put("test3", "test1");

		Message message2 = Message.builder().putAllData(mes).setToken(fcmToken).build();

		try {
			logger.info(sendNotification(message).get());
			logger.info(sendNotification(message2).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void newUserAddedToYourGroupToToken(String token, String groupName, int count) {
		StringBuffer builder = new StringBuffer();
		builder.append(count == 1 ? "One user" : count + " users");
		builder.append("  has been added to your ");
		Notification notification = new Notification("User Added To Group", builder.toString());
		Message message = Message.builder().setNotification(notification).setToken(token).build();
		try {
			logger.info(sendNotification(message).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void groupNameChangedToToken(String topic, String oldName, String newName) {
		String no_title = "Tap group name changed";
		String no_body = "your tap group " + oldName + " has been change name to " + newName;
		String icon = "https://s3.ap-south-1.amazonaws.com/letztapcyspan/profile/bigIcon/ea2c4f6c-73d4-48a6-b308-e1f9d76257dd4324412411293877221.png";
		String c_action = "https://www.youtube.com/watch?v=qsP3Y4hHyeM";
		String notification_type = "";
		boolean content_available = true;
		tapNotification.buildTopic(topic, no_title, no_body, icon, c_action, notification_type, content_available);
		if(tapNotification.sendNotification()) {
			logger.info(no_body);
		}
	}

	@Override
	public void userRemovedByAdmin(String token, String groupName) {
		StringBuffer builder = new StringBuffer();
		builder.append("You are removed from " + groupName.toLowerCase() + " by admin");
		Notification notification = new Notification("You are removed from " + groupName.toLowerCase(),
				builder.toString());
		Message message = Message.builder().setNotification(notification).setToken(token).build();
		try {
			logger.info(sendNotification(message).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void userLeftGroup(String username, String topic, String groupname) {
		StringBuffer builder = new StringBuffer();
		builder.append(username.toLowerCase() + " left from " + groupname);
		Notification notification = new Notification("User Added To Group", builder.toString());
		Message message = Message.builder().setNotification(notification).setTopic(topic).build();
		try {
			logger.info(sendNotification(message).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unsubscribeNotification(String userToken, String topic) {
		FirebaseMessaging.getInstance().unsubscribeFromTopicAsync(Arrays.asList(userToken), topic);
	}

	@Override
	public void reminderExpirePoll(int count, String question, String topic, String group) {
		StringBuffer builder = new StringBuffer();
		builder.append(count > 1 ? count + " polls" : "One poll ");
		builder.append("has been expired: ");
		builder.append(question.substring(0,
				question.length() < 10 ? question.length() : (question.length() > 15 ? 15 : question.length()))
				+ ".... in : " + group);
		Notification notification = new Notification("Poll Expired", builder.toString());
		Message message = Message.builder().setNotification(notification).setTopic(topic).build();
		try {
			logger.info(sendNotification(message).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reminderHoursToPoll(int hour, String question, String topic, String group) {
		StringBuffer builder = new StringBuffer();
		builder.append("You Have " + hour);
		builder.append(" hour remaining to vote the poll ");
		builder.append(question.substring(0,
				question.length() < 10 ? question.length() : (question.length() > 15 ? 15 : question.length()))
				+ ".... in : ");
		builder.append(group.toLowerCase());
		Notification notification = new Notification("Poll Vote Reminder", builder.toString());
		Message message = Message.builder().setNotification(notification).setTopic(topic).build();
		try {
			logger.info(sendNotification(message).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pollCommentNotification(String userName, String question, String groupName, String userToken) {
		StringBuffer builder = new StringBuffer();
		builder.append(userName.toLowerCase() + " commented on your poll : ");
		builder.append(question.substring(0,
				question.length() < 10 ? question.length() : (question.length() > 15 ? 15 : question.length()))
				+ ".... : in " + groupName);
		Notification notification = new Notification(userName + " commented on your poll", builder.toString());
		Message message = Message.builder().setNotification(notification).setToken(userToken).build();
		try {
			logger.info(sendNotification(message).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pollCommentNotification2(String userName, String question, String userToken) {
		StringBuffer builder = new StringBuffer();
		builder.append(userName.toLowerCase() + " commented on the poll you commented : ");
		builder.append(question.substring(0,
				question.length() < 10 ? question.length() : (question.length() > 15 ? 15 : question.length()))
				+ "....");
		Notification notification = new Notification("commented on poll", builder.toString());
		Message message = Message.builder().setNotification(notification).setToken(userToken).build();
		try {
			logger.info(sendNotification(message).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendCustomNotificationToToken(String title, String message, String token) {
		Notification notification = new Notification(title, message);
		Message message2 = Message.builder().setNotification(notification).setToken(token).build();
		FirebaseMessaging.getInstance().sendAsync(message2);
	}

	@Override
	public void sendCustomNotificationToTopic(String title, String message, String topic) {
		Notification notification = new Notification(title, message);
		Message message2 = Message.builder().setNotification(notification).setTopic(topic).build();
		FirebaseMessaging.getInstance().sendAsync(message2);
	}

	@Override
	public void sendPollResponseNotificationToOwner(String username, String groupname, String question,
			String pollOwnerToken, int totalResponseCount) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(username);
		buffer.append(totalResponseCount > 0 ? " and other " + totalResponseCount : "");
		buffer.append(" voted on your");
		buffer.append(groupname != null ? " poll in : " + groupname + " : " : " public poll : ");
		buffer.append("\'"
				+ question.substring(0,
						question.length() < 10 ? question.length() : (question.length() > 15 ? 15 : question.length()))
				+ "\'");
		System.out.println(buffer.toString());

	}

	@Override
	public void sendPollResponseNotificationToMembers(String username, String groupname, String question,
			String userToken, int totalResponseCount) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(username);
		buffer.append(totalResponseCount > 0 ? " and other " + totalResponseCount : "");
		buffer.append(" voted on ");
		buffer.append(groupname != null ? "poll you attened in : " + groupname + " : " : "public poll you attened : ");
		buffer.append("\'"
				+ question.substring(0,
						question.length() < 10 ? question.length() : (question.length() > 15 ? 15 : question.length()))
				+ "\'");
		System.out.println(buffer.toString());

	}

	@Override
	public void sendPollLikeNotificationToOwner(String username, String groupname, String question,
			String pollOwnerToken, int totalResponseCount) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(username);
		buffer.append(totalResponseCount > 0 ? " and other " + totalResponseCount : "");
		buffer.append(" liked on your");
		buffer.append(groupname != null ? " poll in : " + groupname + " : " : " public poll : ");
		buffer.append("\'"
				+ question.substring(0,
						question.length() < 10 ? question.length() : (question.length() > 15 ? 15 : question.length()))
				+ "\'");
		System.out.println(buffer.toString());

	}

	@Override
	public void sendPollLikeNotificationToMembers(String username, String groupname, String question, String userToken,
			int totalResponseCount) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(username);
		buffer.append(totalResponseCount > 0 ? " and other " + totalResponseCount : "");
		buffer.append(" liked on ");
		buffer.append(groupname != null ? "poll you attened in : " + groupname + " : " : "public poll you attened : ");
		buffer.append("\'"
				+ question.substring(0,
						question.length() < 10 ? question.length() : (question.length() > 15 ? 15 : question.length()))
				+ "\'");
		System.out.println(buffer.toString());

	}

}
