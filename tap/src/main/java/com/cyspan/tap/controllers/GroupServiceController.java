package com.cyspan.tap.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.commons.CustomNoDataFoundException;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.group.services.GroupServices;
import com.cyspan.tap.response.UserResponse;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.user.services.UserService;

@RestController
@RequestMapping("/api/group")
public class GroupServiceController {

	@Autowired
	UserService userService;

	@Autowired
	GroupServices groupServices;

	static Logger logger = Logger.getLogger(GroupServiceController.class.getName());

	@RequestMapping(method = RequestMethod.POST)
	public Object saveGroup(@RequestBody GroupsModel groupsModel, HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			if (groupsModel != null) {
				GroupsModel model = groupServices.createGroup(groupsModel, userService.getUserByTokenId(token));
				model.setGroupIcon(null);
				return model;
			} else {
				userResponse.setSuccessMessage(Constants.FAIL_MESSAGE);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Object updateGroup(@RequestBody GroupsModel groupsModel, HttpServletRequest request)
			throws CustomNoDataFoundException {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			if (groupsModel != null) {
				GroupsModel model = null;
				model = groupServices.updateGroup(groupsModel, userService.getUserByTokenId(token));
				model.setGroupIcon(null);
				return model;
			} else {
				userResponse.setSuccessMessage(Constants.FAIL_MESSAGE);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 *
	 * Fetch All Groups Fetch All Groups by UserID Fetch All Group Members By
	 * GroupID
	 * 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Object getAllGroups(@RequestParam Map<String, String> parameters, HttpServletRequest request)
			throws Exception {
		String token = request.getHeader("token");
		UsersModel model = userService.getUserByTokenId(token);
		if (model == null) {
			logger.error("user data not found");
			throw new Exception("invalid token");
		}
		if (userService.checkTokenAvailabe(token)) {
			if (parameters.get("userId") != null) {
				Integer userId = new Integer(model.getUserId());
				logger.info("fetch all group by user ID : " + userId);
				return groupServices.fetchAllGroupModelByUserId(userId);
			} else if (parameters.get("groupId") != null) {
				Integer groupId = new Integer(parameters.get("groupId"));
				logger.info("fetch all group members by user ID : " + groupId);
				return groupServices.fetchGroupMembersByGroupId(groupId);
			} else {
				return groupServices.fetchAllGroup();
			}
		}
		return null;
	}

	@RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
	public Object getGroupByGroupId(@PathVariable Integer groupId, HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			GroupsModel groupsModel = groupServices.fetchGroupByGroupId(groupId);
			return groupsModel;
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/minmal/{groupId}", method = RequestMethod.GET)
	public Object getGroupByGroupIdMinimal(@PathVariable Integer groupId, HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			GroupsModel groupsModel = groupServices.fetchGroupByGroupId(groupId);
			groupsModel.setGroupMembers(null);
			return groupsModel;
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
	public Object removeGroup(@PathVariable Integer groupId, HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			return groupServices.deleteGroupByGroupId(groupId);
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}

	}

	@RequestMapping(value = "/{groupId}/{userId}", method = RequestMethod.DELETE)
	public Object removeGroupMember(@PathVariable Integer groupId, @PathVariable Integer userId,
			HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			return groupServices.deleteGroupMemberByUserId(groupId, userId, request);
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}
}
