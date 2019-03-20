package com.cyspan.tap.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cyspan.tap.commons.logging.Logger;

@Controller
@Scope("session")
@RequestMapping("/tiles")
public class WebLoginController {

	@Autowired
	Logger logger;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView index() {
		logger.info("inside controller");
		ModelAndView model = new ModelAndView("login");
		return model;
	}

	@RequestMapping("/login")
	public ModelAndView loadHomepage1(@RequestParam(required = false) String authfailed, String logout, String denied,
			String sessionout1) {
		String message = "";

		// Commented due to not using now

		/*
		 * System.out.println("Login success..........."); if (authfailed !=
		 * null) { message = "Invalid username or password, try again !";
		 * System.out.println(message); } else if (logout != null) { message =
		 * "Logged Out successfully, login again to continue !";
		 * System.out.println(message); } else if (denied != null) { message =
		 * "Access denied for this user !"; System.out.println(message); } else
		 * if (sessionout1 != null) {
		 * System.out.println("SESSION EXPIRED... SRY!"); }
		 */

		ModelAndView modelAndView = new ModelAndView("login", "message", message);
		return modelAndView;
	}

	@RequestMapping("/redirect")
	public ModelAndView defaultAfterLogin(HttpServletRequest request, Principal principal, SessionStatus status,
			HttpSession session) {

		ModelAndView modelAndView = new ModelAndView("login?denied");

		// Commented due to not using now

		/*
		 * ServletContext context = request.getSession().getServletContext();
		 * String appPath = context.getRealPath("");
		 * System.out.println("appPath = " + appPath); if
		 * (request.isUserInRole("ROLE_ADMIN")) {
		 * modelAndView.setViewName("redirect:/sample"); } if
		 * (request.isUserInRole("ROLE_USER")) { //
		 * mailMail.sendMail("jinsndkm@gmail.com", "jinsndkm@gmail.com", //
		 * "jinsndkm@gmail.com", "jinsndkm@gmail.com");
		 * System.out.println("Username :: " + principal.getName()); //
		 * Constant.USER_ID = commonDao.selectUserId(principal.getName());
		 * modelAndView.setViewName("redirect:/user");
		 * session.setAttribute("loggedUser", principal.getName()); }
		 */
		return modelAndView;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("login");

		// Commented due to not using now

		/*
		 * System.out.print("Hi Logout!!"); Authentication auth =
		 * SecurityContextHolder.getContext().getAuthentication(); if (auth !=
		 * null) { new SecurityContextLogoutHandler().logout(request, response,
		 * auth); }
		 */
		return modelAndView;
	}

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public ModelAndView loadSamplePage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("sample");
		return modelAndView;
	}

}
