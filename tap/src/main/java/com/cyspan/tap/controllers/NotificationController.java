package com.cyspan.tap.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.notifications.GroupNotificationService;
import com.cyspan.tap.notifications.NotificationService;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.user.services.UserService;

@RestController
@RequestMapping(value = "/api/fcm")
public class NotificationController {

	@Autowired
	UserService userService;

	@Autowired
	GroupNotificationService notificationService;

	@Autowired
	NotificationService notiService;

	/**
	 * Get fcm token by user token id
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String getUserToken(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel model = userService.getUserByTokenId(token);
			return model.getFcmToken();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/token/{fcmToken}", method = RequestMethod.POST)
	public Object saveUserToken(@PathVariable("fcmToken") String fcmToken, HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel model = userService.getUserByTokenId(token);
			model.setFcmToken(fcmToken);
			userService.saveUserModel(model);
			return model;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/token/{fcmToken}", method = RequestMethod.PUT)
	public Object updateUserToken(@PathVariable("fcmToken") String fcmToken, HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel model = userService.getUserByTokenId(token);
			model.setFcmToken(fcmToken);
			userService.saveUserModel(model);
			return model;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/token", method = RequestMethod.DELETE)
	public Boolean deleteUserToken(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel model = userService.getUserByTokenId(token);
			model.setFcmToken(null);
			userService.saveUserModel(model);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@RequestMapping(value = "/unsubscribe", method = RequestMethod.DELETE)
	public Object unsubscribeNotification(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			userService.unsubscribeNotification(token);
			return null;
		} else {
			return Boolean.FALSE;
		}
	}

	@RequestMapping(value = "/notification/{pageno}")
	public Object getNotificationByUserIdPaged(HttpServletRequest request, @PathVariable("pageno") int pageno) {
		String token = request.getHeader("token");
		UsersModel userByToken = userService.getUserByTokenId(token);
		return notiService.fetchNotificationByUserByPage(userByToken.getUserId(), pageno);
	}

	@RequestMapping(value = "/notification/readstatus", method = RequestMethod.POST)
	public boolean updateReadStatus(@RequestBody List<Long> id) {
		return notiService.updateNotificationReadStatus(id);
	}

	@RequestMapping(value = "/notification/mute")
	public Object muteNotification(HttpServletRequest request, @PathParam("pollId") Long pollId,
			@PathParam("groupId") Long groupId) {
		String token = request.getHeader("token");
		UsersModel user = userService.getUserByTokenId(token);

		if (user != null && pollId != null && groupId != null) {
			notiService.muteNotificationForPollByGroup(Long.valueOf(user.getUserId()), pollId, groupId);
		}
		if (pollId != null) {
			notiService.muteNotificationForPoll(Long.valueOf(user.getUserId()), pollId);
		}
		if (groupId != null) {
			notiService.muteNotificationForGroup(Long.valueOf(user.getUserId()), groupId);
		}
		return true;
	}

}
