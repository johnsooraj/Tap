package com.cyspan.tap.commons;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cyspan.tap.appconfig.EmailSend;
import com.cyspan.tap.group.services.GroupServices;
import com.cyspan.tap.notifications.PollAsynNotification;
import com.cyspan.tap.poll.service.PollsService;
import com.cyspan.tap.subscription.models.OrganizationModel;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.services.UserService;
import com.cyspan.tap.utils.CryptoUtil;

@EnableAsync
@Configuration
@EnableScheduling
public class CustomScheduledTask {

	/**
	 * All General notification only to group
	 */

	@Autowired
	PollsService pollService;

	@Autowired
	UserService userService;

	@Autowired
	GroupServices groupService;

	@Autowired
	CryptoUtil cryptoUtil;

	@Autowired
	UsersDao userDao;

	@Autowired
	PollAsynNotification pollAsynNotification;

	/**
	 * 1. Poll expired notification -> Poll Owner 2. Final Result is ready to view
	 * -> Group
	 */

	@Async
	@Scheduled(cron = "*/10 * * * * *")
	public void sendExpiredPollNotification() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = LocalDateTime.now().minusSeconds(10);
		List<Object[]> list = groupService.fetchDataForExpireNotification(now, then);
		for (Object[] row : list) {
			Integer groupId = row[0] == null ? 0 : (Integer) row[0];
			String question = row[1] == null ? "" : row[1].toString();
			String uniqueId = row[2] == null ? "" : row[2].toString();
			String groupname = row[3] == null ? "" : row[3].toString();
			Integer pollId = row[4] == null ? 0 : (Integer) row[4];
			String groupicon = row[5] == null ? "" : row[5].toString();
			pollAsynNotification.PollExpireReminderNotification(0, uniqueId, groupname, question, pollId, groupicon,
					groupId);
		}
	}

	/**
	 * Remaining 1 hour
	 */
	@Async
	@Scheduled(cron = "*/10 * * * * *")
	public void reminderNotificationOneHour() {
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime a = currentTime.plusHours(1).minusSeconds(5);
		LocalDateTime b = currentTime.plusHours(1).plusSeconds(5);
		List<Object[]> list = groupService.fetchDataForExpireNotification(b, a);
		for (Object[] row : list) {
			Integer groupId = row[0] == null ? 0 : (Integer) row[0];
			String question = row[1] == null ? "" : row[1].toString();
			String uniqueId = row[2] == null ? "" : row[2].toString();
			String groupname = row[3] == null ? "" : row[3].toString();
			Integer pollId = row[4] == null ? 0 : (Integer) row[4];
			String groupicon = row[5] == null ? "" : row[5].toString();
			pollAsynNotification.PollExpireReminderNotification(0, uniqueId, groupname, question, pollId, groupicon,
					groupId);
		}
	}

	/**
	 * Remaining 12 hour
	 */
	@Async
	@Scheduled(cron = "*/10 * * * * *")
	public void reminderNotificationTwelveHour() {
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime a = currentTime.plusHours(12).minusSeconds(5);
		LocalDateTime b = currentTime.plusHours(12).plusSeconds(5);
		List<Object[]> list = groupService.fetchDataForExpireNotification(b, a);
		for (Object[] row : list) {
			Integer groupId = row[0] == null ? 0 : (Integer) row[0];
			String question = row[1] == null ? "" : row[1].toString();
			String uniqueId = row[2] == null ? "" : row[2].toString();
			String groupname = row[3] == null ? "" : row[3].toString();
			Integer pollId = row[4] == null ? 0 : (Integer) row[4];
			String groupicon = row[5] == null ? "" : row[5].toString();
			pollAsynNotification.PollExpireReminderNotification(0, uniqueId, groupname, question, pollId, groupicon,
					groupId);
		}
	}

	public void sendCredentitalsToAdmin(OrganizationModel model) {

	}

	@Async
	public void sendSubscriptioCredentitals(OrganizationModel model, String password) {
		EmailSend emailSend = new EmailSend();
		if (model.getEmail() != null) {
			try {
				emailSend.subscriptionAdminCredetials(model.getEmail(), model.getOrganizationName(), model.getEmail(),
						password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Async
	public void sendSubscriptioCredentitals2(String email, OrganizationModel model, String password) {
		EmailSend emailSend = new EmailSend();
		if (email != null) {
			try {
				emailSend.subscriptionAdminCredetials(email, model.getOrganizationName(), model.getEmail(), password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Async
	public void emailOrganizationAdmins(String newUserEmail, OrganizationModel model, String username,
			String password) {
		EmailSend emailSend = new EmailSend();
		if (newUserEmail != null) {
			try {
				emailSend.subscriptionAdminCredetials(newUserEmail, model.getOrganizationName(), model.getEmail(),
						password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Async
	public void sendCredentialsToSuperAdmin(String email, String username, String password) {
		EmailSend emailSend = new EmailSend();
		if (email != null) {
			try {
				emailSend.sendCredentialsToSuperAdmin(email, username, password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
