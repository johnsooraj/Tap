package com.cyspan.tap.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.commons.errorcontroller.IdInvalidRequestException;
import com.cyspan.tap.commons.logging.Logger;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.poll.models.PollCommentModel;
import com.cyspan.tap.poll.models.PollGroupModel;
import com.cyspan.tap.poll.models.PollLikeModel;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.poll.service.PollsService;
import com.cyspan.tap.response.UserResponse;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.user.services.UserService;

@RestController
@RequestMapping("/api/poll")
public class PollServiceController {

	@Autowired
	PollsService pollsService;

	@Autowired
	UserService userService;

	@Autowired
	Logger logger;

	/**
	 * 
	 * Web service for save or update poll details
	 * 
	 * @param pollModel
	 * @return
	 * @throws IOException
	 */

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
	public Object savePoll(@RequestBody PollModel pollModel, HttpServletRequest request) throws IOException {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				pollModel.setCreatedUser(usersModel.getUserId());
				pollModel.setUsersModel(usersModel);
				return pollsService.savePoll(pollModel);
			} else {
				userResponse.setLoginResponse(Constants.LOGIN_RESPONSE);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{groupId}/{pollId}", method = RequestMethod.DELETE)
	public boolean deletePoll(@PathVariable("groupId") Long groupId, @PathVariable("pollId") Integer pollId) {
		return pollsService.deletePollById(groupId, pollId);
	}

	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Object savePollComment(@RequestBody PollCommentModel commentModel, HttpServletRequest request)
			throws IOException {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				commentModel.setUsersModel(usersModel);
				return pollsService.savePollComment(commentModel);
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/like", method = RequestMethod.POST)
	public @ResponseBody Object savePolllikes(@RequestBody PollLikeModel likeResponse, HttpServletRequest request)
			throws IOException {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				likeResponse.setUsersModel(usersModel);
				likeResponse.setUserId(usersModel.getUserId());
				return pollsService.savePollLike(likeResponse);
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/like/{groupId}/{pollId}", method = RequestMethod.DELETE)
	public @ResponseBody Object deletePollLikeByGroupIdAndPollId(@PathVariable int groupId, @PathVariable int pollId,
			HttpServletRequest request) throws IOException {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				return pollsService.deleteLikeByGroupIdAndPollId(groupId, pollId, usersModel.getUserId());
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object getAllPolls(HttpServletRequest request) throws IOException {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			return pollsService.retrieveAllPoll();
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/comments/{pollid}/{groupId}", method = RequestMethod.GET)
	public Object getPollCommentsByPollId(@PathVariable int pollid, @PathVariable int groupId,
			HttpServletRequest request) throws IdInvalidRequestException {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			if (pollid > 0) {
				return pollsService.getPollCommentsByPollId(pollid, groupId);
			} else {
				throw new IdInvalidRequestException("No Poll Found with id :" + pollid);
			}

		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/likes/{pollid}/{groupId}", method = RequestMethod.GET)
	public Object getPolllikesByPollId(@PathVariable int pollid, @PathVariable int groupId,
			HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			return pollsService.getPollLikesByPollId(pollid, groupId);
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 * Get list of groups by poll id that shared
	 * 
	 * @param int poll id
	 * 
	 * @return List PollGroups
	 */
	@RequestMapping(value = { "/shares/{pollId}/{groupId}" })
	public Object getPollSharedGroups(@PathVariable int pollId, @PathVariable int groupId, HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info("valid token");
			return pollsService.getPollSharedList(pollId, groupId);
		} else {
			logger.info("invalid token");
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 * Share a Poll to Other Groups
	 * 
	 * @param      int poll id
	 * @param List PollGroups ids
	 * @return List PollGroups
	 */
	@RequestMapping(value = { "/share/{pollId}" }, method = RequestMethod.POST)
	public Object updatePollForPollShare(@PathVariable int pollId, @RequestBody List<PollGroupModel> groupsModels,
			HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info("valid token");
			return pollsService.sharePollByGroupList(pollId, groupsModels);
		} else {
			logger.info("invalid token");
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = { "/rsvp/{pollId}/{groupId}/{responseId}" }, method = RequestMethod.POST)
	public Object saveRsvpResponse(@PathVariable int pollId, @PathVariable int groupId, @PathVariable int responseId,
			HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			return pollsService.saveRSVPResponse(pollId, groupId, usersModel.getUserId(), responseId);
		} else {
			logger.info("invalid token");
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}

	}

	@RequestMapping(value = "/dateandtype/{pollId}", method = RequestMethod.PUT)
	public Object updatePollDateAndDisplayType(@PathVariable Long pollId, @PathParam("type") byte type,
			@PathParam("date") Long date) {
		return pollsService.updatePollDateAndResultType(type, new Date(date), pollId);
	}

	/**
	 * For Get RSVP and Search By Event Name
	 * @throws Exception 
	 */
	@RequestMapping(value = "/rsvp", method = RequestMethod.POST)
	public Object fetchAllRsvpPollByUserIds(HttpServletRequest request, @RequestBody Map<String, String> body) throws Exception {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if(usersModel == null)
				throw new Exception("invalid user");
			body.put("userId", usersModel.getUserId().toString());
			return pollsService.fetchRsvpPollsByUserId(body);
		} else {
			return userResponse;
		}
	}

}
