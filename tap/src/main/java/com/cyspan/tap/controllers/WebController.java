package com.cyspan.tap.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.poll.service.PollsService;
import com.cyspan.tap.response.UserResponse;
import com.cyspan.tap.subscription.service.MemberCredentialService;
import com.cyspan.tap.user.services.UserService;
import com.google.firebase.database.FirebaseDatabase;

@Controller
public class WebController {

	@Autowired
	PollsService pollsService;

	@Autowired
	UserService userService;

	@Autowired
	MemberCredentialService service;

	static Logger logger = Logger.getLogger(WebController.class.getName());

	@RequestMapping(value = { "/" })
	public ModelAndView viewGraph() {
		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
	}

	@RequestMapping("/poll/graph/{id}/{groupId}")
	public ModelAndView messages(@PathVariable int id, @PathVariable int groupId, HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();
		String token = "59699f3e-591a-43df-a6d0-a749abc20d86";
		// String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			ModelAndView mav = new ModelAndView("pollGraph/pollGraph");
			mav.addObject("token", token);
			mav.addObject("pollId", id);
			mav.addObject("groupId", groupId);
			logger.info("graph request for {pollId : " + id + ", groupId :" + groupId + "}");
			return mav;
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return null;
		}
	}
}
