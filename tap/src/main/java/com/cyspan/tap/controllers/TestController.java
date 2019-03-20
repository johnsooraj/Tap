package com.cyspan.tap.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyspan.tap.appconfig.HttpPostNotification;
import com.cyspan.tap.subscription.service.MemberCredentialService;
import com.cyspan.tap.subscription.service.OrganizationMemberService;
import com.cyspan.tap.subscription.service.OrganizationService;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.ztest.TestDAO;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	HttpPostNotification tapNotification;

	@Autowired
	TestDAO testDAO;

	@Autowired
	UsersDao usersDao;

	@Autowired
	MemberCredentialService service;

	@Autowired
	OrganizationService orgService;

	@Autowired
	OrganizationMemberService memberService;

	@Autowired
	SessionFactory fac;

	@RequestMapping("/fcm/{token}")
	@ResponseBody
	public Object sendNotification(@PathVariable String token) {
		tapNotification.setToken(token);
		tapNotification.setTitle("test");
		tapNotification.setBody("test");
		return tapNotification.sendNotification();
	}

}
