package com.cyspan.tap.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.poll.models.RsvpResponseModel;
import com.cyspan.tap.response.UserResponse;
import com.cyspan.tap.rsvppoll.model.RsvpPollModel;
import com.cyspan.tap.rsvppoll.service.RsvpPollService;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.user.services.UserService;

@RestController
@RequestMapping("/api")
public class RsvpPollController {

	@Autowired
	RsvpPollService rsvpPollService;

	@Autowired
	UserService userService;
	/*
	 * To Save Rsvp Poll
	 */

	@RequestMapping(value = "/rsvpPoll/save", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public Object saveRsvpPoll(@RequestBody RsvpPollModel pollModel, HttpServletRequest request) {
		//
		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");


		if (userService.checkTokenAvailabe(token)) {
			
			UsersModel usersModel = userService.getUserByTokenId(token);
			if(usersModel.getUserId()!=null){
				pollModel.setCreatedUser(usersModel.getUserId());
				return rsvpPollService.save(pollModel);
			}
			else{
				userResponse.setLoginResponse(Constants.LOGIN_RESPONSE);
				return userResponse;
			}
			
			
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}
	}

	/*
	 * To Get Rsvp poll List.
	 */

	@RequestMapping(value = "/rsvpPoll/list", method = RequestMethod.GET)
	@ResponseBody
	public Object listRsvpPoll( HttpServletRequest request) {
		//
		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {
			return rsvpPollService.getRsvpPollModelList();
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}
	}

	@RequestMapping(value = "/rsvpPoll/{rsvpId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getRsvpPoll(@PathVariable int rsvpId, HttpServletRequest request) {
		//
		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {
			return rsvpPollService.getRsvpPollById(rsvpId);
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}
	}
	
	@RequestMapping(value = "/rsvpPollResponse/save", method = RequestMethod.POST)
	@ResponseBody
	public Object saveRsvpPollResponse(HttpServletRequest request,@RequestBody RsvpResponseModel rsvpResponseModel ) {
		//
		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");


		if (userService.checkTokenAvailabe(token)) {
			
			UsersModel usersModel = userService.getUserByTokenId(token);
			
			if(usersModel.getUserId()!=null){
				rsvpResponseModel.setUserId(usersModel.getUserId());
				return rsvpPollService.saveRsvpPollResponse(rsvpResponseModel);
			}else{
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
			
			
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}
	}
	
}
