package com.cyspan.tap.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.commons.LattestPollModel;
import com.cyspan.tap.commons.PollResponseType;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.notifications.PollAsynNotification;
import com.cyspan.tap.poll.models.ImageOptionModel;
import com.cyspan.tap.poll.models.ImageOptionsResponseModel;
import com.cyspan.tap.poll.models.MultipleOptionModel;
import com.cyspan.tap.poll.models.MultipleOptionsResponseModel;
import com.cyspan.tap.poll.models.PollInterestModel;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.poll.models.RatingOptionModel;
import com.cyspan.tap.poll.models.RatingOptionsResponseModel;
import com.cyspan.tap.poll.models.ReportPoll;
import com.cyspan.tap.poll.models.ThisorthatOptionModel;
import com.cyspan.tap.poll.models.ThisorthatOptionsResponseModel;
import com.cyspan.tap.poll.service.PollsService;
import com.cyspan.tap.response.UserResponse;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.user.services.UserService;

/**
 * @author sumesh | Integretz LLC
 * 
 */
@RestController
@RequestMapping("/api")
public class PollsController {

	@Autowired
	PollsService pollsService;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	UserService userService;

	@Autowired
	PollAsynNotification pollAsynNotification;

	@Autowired
	ServletContext servletContext;

	static Logger logger = Logger.getLogger(PollsController.class.getName());

	/**
	 * Retrieve Polls By GroupId
	 * 
	 * @category GET
	 * @param int groupId
	 * @return List polls
	 * 
	 */
	@RequestMapping(value = "/groupPolls/{groupId}", method = RequestMethod.GET)
	public Object getPollsByGroupId(@PathVariable int groupId, HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info("GET polls by { groupId:" + groupId + " }");
			return pollsService.pollListByGroupId(groupId);
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 * Retrieve Polls By PollId
	 * 
	 * @category GET
	 * @param int pollId
	 * @return List polls
	 * 
	 */
	@RequestMapping(value = "/poll/{pollId}", method = RequestMethod.GET)
	public Object getPollsByPollId(@PathVariable int pollId, HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info("GET poll by { pollId:" + pollId + " }");
			PollModel pollModel = pollsService.getPollByPollId(pollId);
			return pollModel;
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 * Retrieve Polls By PollId And GroupId
	 * 
	 * @category GET
	 * @param int pollId
	 * @param int groupId
	 * @return PollModel
	 * 
	 */
	@RequestMapping(value = "/poll/{pollId}/{groupId}", method = RequestMethod.GET)
	public Object getPollByPollIdAndGroupId(@PathVariable int pollId, @PathVariable int groupId,
			HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info("GET poll by { pollId:" + pollId + ", groupId:" + groupId + " }");
			PollModel pollModel = pollsService.getPollByPollIdAndGroupId(pollId, groupId);
			return pollModel;
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 * fetch latest polls
	 * 
	 * @param lattestPollModel   - LattestPollModel object
	 * 
	 * @param httpServletRequest - HTTPRequest
	 * 
	 * @return LattestPollsResponse object
	 */

	@RequestMapping(value = "/lattestPolls", method = RequestMethod.POST)
	public Object getLatestPolls(@RequestBody LattestPollModel lattestPollModel,
			HttpServletRequest httpServletRequest) {

		UserResponse userResponse = new UserResponse();
		String token = httpServletRequest.getHeader("token");
		UsersModel user = userService.getUserByTokenId(token);
		if (userService.checkTokenAvailabe(token)) {
			if (lattestPollModel.getQuestion() != null) {
				logger.info("SEARCH lattest Polls by " + lattestPollModel.toString());
			} else {
				logger.info("GET lattest Polls by " + lattestPollModel.toString());
			}
			return pollsService.lattestPolls(lattestPollModel, false, user.isFilterSearch(), user.getUserId());
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 * fetch latest polls After The Date
	 * 
	 * @param lattestPollModel   - LattestPollModel object
	 * 
	 * @param httpServletRequest - HTTPRequest
	 * 
	 * @return LattestPollsResponse object
	 */
	@RequestMapping(value = "/lattestPollsAfterDate", method = RequestMethod.POST)
	public Object getLatestPollsAfterCreateDate(@RequestBody LattestPollModel lattestPollModel,
			HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		UsersModel user = userService.getUserByTokenId(token);
		if (userService.checkTokenAvailabe(token)) {
			logger.info("GET lattest Polls After CreateDate by " + lattestPollModel.toString());
			return pollsService.lattestPolls(lattestPollModel, true, user.isFilterSearch(), user.getUserId());
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/pollreport", method = RequestMethod.GET)
	public Object getPollReportResons() {
		return servletContext.getAttribute("pollReportResons");
	}

	@RequestMapping(value = "/pollreport/{pollId}/{reportText}", method = RequestMethod.POST)
	public Object savePollReport(@PathVariable Integer pollId, @PathVariable String reportText,
			HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			ReportPoll poll = new ReportPoll();
			poll.setPollId(pollId);
			poll.setReportText(reportText);
			poll.setUserId(usersModel.getUserId());
			return pollsService.savePollReport(poll);
		}
		return false;
	}

	@RequestMapping(value = "/imgResponse/save", method = RequestMethod.POST)
	public Object saveImageOptionsResponse(@RequestBody ImageOptionsResponseModel imageOptionsResponseModel,
			HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				logger.info("SAVE imageOption-response");
				imageOptionsResponseModel.setUserId(usersModel.getUserId());
				ImageOptionModel returnObject = pollsService.saveImageResponse(imageOptionsResponseModel);
				try {
					// asynchronous operation
					pollAsynNotification.PollResponseNotifications(returnObject, PollResponseType.MultipleOptions,
							imageOptionsResponseModel.getGroupId(), usersModel);
				} catch (Exception e) {
					logger.error("notification send failed at saveMultiOptionResponse");
				}
				return returnObject;
			} else {
				userResponse.setLoginResponse(Constants.LOGIN_RESPONSE);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/multiResponse/save", method = RequestMethod.POST)
	public Object saveMultiOptionResponse(@RequestBody MultipleOptionsResponseModel multipleOptionsResponseModel,
			HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				multipleOptionsResponseModel.setUserId(usersModel.getUserId());
				logger.info("SAVE multipleOption-response");
				MultipleOptionModel returnObject = pollsService
						.saveMultipleOptionResponse(multipleOptionsResponseModel);
				try {
					// asynchronous operation
					pollAsynNotification.PollResponseNotifications(returnObject, PollResponseType.MultipleOptions,
							multipleOptionsResponseModel.getGroupId(), usersModel);
				} catch (Exception e) {
					logger.error("notification send failed at saveMultiOptionResponse");
				}
				return returnObject;
			} else {
				userResponse.setLoginResponse(Constants.LOGIN_RESPONSE);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/rateResponse/save", method = RequestMethod.POST)
	public Object saveRatingOptionResponse(@RequestBody RatingOptionsResponseModel ratingOptionsResponseModel,
			HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				ratingOptionsResponseModel.setUserId(usersModel.getUserId());
				logger.info("SAVE ratingOption-response");
				RatingOptionModel returnObject = pollsService.saveRatingOptionResponse(ratingOptionsResponseModel);
				try {
					// asynchronous operation
					pollAsynNotification.PollResponseNotifications(returnObject, PollResponseType.MultipleOptions,
							ratingOptionsResponseModel.getGroupId(), usersModel);
				} catch (Exception e) {
					logger.error("notification send failed at saveMultiOptionResponse");
				}
				return returnObject;
			} else {
				userResponse.setLoginResponse(Constants.LOGIN_RESPONSE);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/thisorThatRespone/save", method = RequestMethod.POST)
	public Object saveThisOrThatOptionResponse(
			@RequestBody ThisorthatOptionsResponseModel thisorthatOptionsResponseModel, HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				thisorthatOptionsResponseModel.setUserId(usersModel.getUserId());
				logger.info("SAVE thisorThatOption-response");
				ThisorthatOptionModel returnObject = pollsService
						.saveThisOrThatOptionResponse(thisorthatOptionsResponseModel);
				try {
					// asynchronous operation
					pollAsynNotification.PollResponseNotifications(returnObject, PollResponseType.MultipleOptions,
							thisorthatOptionsResponseModel.getGroupId(), usersModel);
				} catch (Exception e) {
					logger.error("notification send failed at saveMultiOptionResponse");
				}
				return returnObject;
			} else {
				userResponse.setLoginResponse(Constants.LOGIN_RESPONSE);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/save/pollintrest", method = RequestMethod.POST)
	public Object upadteGroupMembers(@RequestBody List<PollInterestModel> pollIntrestModels,
			HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info("/save/pollintrest");
			return pollsService.savePollIntrests(pollIntrestModels);
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}
}
