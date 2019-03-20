package com.cyspan.tap.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.appconfig.EmailSend;
import com.cyspan.tap.commons.Email;
import com.cyspan.tap.commons.OTPModel;
import com.cyspan.tap.commons.Token;
import com.cyspan.tap.commons.UpdatePassword;
import com.cyspan.tap.commons.UserHandler;
import com.cyspan.tap.commons.logging.Logger;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.login.services.LoginServices;
import com.cyspan.tap.response.UserResponse;
import com.cyspan.tap.signup.services.SignUpServices;
import com.cyspan.tap.user.model.InterestModel;
import com.cyspan.tap.user.model.UserInterest;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.user.services.UserService;

@RestController
@RequestMapping("/api")
public class UserServiceController {

	@Autowired
	UserService userService;

	@Autowired
	SignUpServices SignUpServices;

	@Autowired
	Logger logger;

	@Autowired
	LoginServices loginServices;

	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "/clear-cache", method = RequestMethod.GET)
	public @ResponseBody String getCSRFToken(HttpServletRequest request) {
		try {
			Session session = sessionFactory.getCurrentSession();
			if (session != null) {
				session.clear();
			}
			Cache cache = sessionFactory.getCache();
			if (cache != null) {
				cache.evictAllRegions();
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}

	}

	/**
	 * 
	 * Handler method for login user
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
	public @ResponseBody Object userLogin(@PathVariable("username") String username,
			@PathVariable("password") String password) {

		String token = null;
		UserResponse userResponse = new UserResponse();
		if (loginServices.authenticateUser(username.trim(), password.trim())) {
			token = UserHandler.getToken();
			if (userService.updateToken(username, password, token) > 0) {
				userResponse.setToken(token);
				userResponse.setTokenValidTime(UserHandler.tokenValidDate());
				userResponse.setStatus(Constants.USER_ACTIVE_STATUS);
			}
		} else {
			userResponse.setToken("");
			userResponse.setTokenValidTime("");
			userResponse.setStatus(Constants.USER_NOT_FOUND);
		}
		return userResponse;
	}

	/**
	 * 
	 * Handler method for register user
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody Object userSignup(@RequestBody UsersModel user) throws IOException {

		UserResponse userResponse = new UserResponse();
		if (user != null) {
			logger.debug("user registration received");
			String repsonseMsg = SignUpServices.insertUserInfo(user);
			if (repsonseMsg == Constants.EMAIL_EXIST) {
				logger.debug("registration failed : email exist");
				userResponse.setStatus(Constants.EMAIL_EXIST);
				return userResponse;
			} else if (repsonseMsg == Constants.PHONE_EXIST) {
				logger.debug("registration failed : phone number exist");
				userResponse.setStatus(Constants.PHONE_EXIST);
				return userResponse;
			} else {
				EmailSend emailSend = new EmailSend();
				logger.info("registration failed : email exist");
				if (emailSend.mailSend(user, repsonseMsg)) {

					userResponse.setStatus(Constants.USER_INACTIVE_STATUS);
					return userResponse;
				} else {
					userResponse.setStatus(Constants.USER_PENDING_STATUS);
					return userResponse;
				}
			}
		} else {
			userResponse.setLoginResponse(Constants.LOGIN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 * 
	 * Handler method for update user profile info
	 * 
	 * @param user
	 * @return
	 */

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody Object updateUserProfile(@RequestBody UsersModel user, HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel userByToken = userService.getUserByTokenId(token);
			if (userByToken != null) {
				if (user != null) {
					user.setUserId(userByToken.getUserId());
					user.setToken(token);
					userByToken = user;

					boolean status = SignUpServices.updateUserProfile(userByToken);
					logger.info("Profile handler");
					if (status) {
						userResponse.setSuccessMessage(Constants.SUCCESS_MESSAGE);
						return userResponse;
					} else {
						userResponse.setSuccessMessage(Constants.FAIL_MESSAGE);
						return userResponse;
					}
				} else {
					userResponse.setSuccessMessage(Constants.FAIL_MESSAGE);
					return userResponse;
				}
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}

	}

	@RequestMapping("/userProfile")
	public @ResponseBody Object getUserByToken(HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {

			UsersModel usersModel = userService.getUserByTokenId(token);

			if (usersModel.getUserId() != null) {

				UsersModel userProfilePOJO = SignUpServices.retrieveUserInfo(usersModel.getUserId());
				return userProfilePOJO;
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}

		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}

	}

	/***
	 * 
	 * 
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {

			UsersModel usersModel = userService.getUserByTokenId(token);

			if (usersModel.getUserId() != null) {
				if (userService.logout(usersModel.getEmail()) > 0) {
					userResponse.setLogoutSucc(Constants.LOGOUT_SUCCESS);
					return userResponse;
				} else {
					userResponse.setLogoutSucc(Constants.LOGOUT_FAIL);
					return userResponse;
				}
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}

		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}

	}

	/*
	 * 
	 * 
	 */
	@RequestMapping(value = "/userdata/{phoneNum}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Object getUserByMobile(@PathVariable String phoneNum, HttpServletRequest request) {
		logger.info("getUserByPnoneNum Method");

		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {

			return userService.getUsersByMobileNum(phoneNum);
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}
	}

	/*
	 * 
	 * 
	 */
	//
	@RequestMapping(value = "/contact/list", method = RequestMethod.POST)
	public @ResponseBody Object getContactList(@RequestBody ArrayList<String> nums, HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {

			return userService.getContactList(nums);
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}

	}

	@RequestMapping(value = "/fb/login", method = RequestMethod.POST)
	public @ResponseBody Object fbLogin(@RequestBody UsersModel model) {

		String token = null;
		UserResponse userResponse = new UserResponse();

		if (loginServices.checkEmailExist(model)) {

			token = UserHandler.getToken();
			if (userService.updateToken(model.getEmail(), null, token) > 0) {
				userResponse.setToken(token);
				userResponse.setTokenValidTime(UserHandler.tokenValidDate());
				userResponse.setStatus(Constants.USER_ACTIVE_STATUS);
			}
		} else {

			token = UserHandler.getToken();
			model.setToken(token);
			model.setStatus(Constants.USER_ACTIVE_STATUS);
			loginServices.saveFbUser(model);
			userResponse.setToken(token);
			userResponse.setTokenValidTime(UserHandler.tokenValidDate());
			userResponse.setStatus(Constants.USER_ACTIVE_STATUS);
		}
		return userResponse;
	}

	/*
	 * To Check Whether the opt is valid or not.
	 * 
	 * 
	 */
	@RequestMapping(value = "/chekOtp", method = RequestMethod.POST)
	public @ResponseBody Object otpValidation(@RequestBody OTPModel otpModel) {
		UserResponse userResponse = new UserResponse();
		String token = null;
		if (UserHandler.chekOtpValidate(otpModel.getUsername(), otpModel.getOtp())) {

			token = UserHandler.getToken();
			if (userService.updateToken(otpModel.getUsername(), null, token) > 0) {
				userResponse.setToken(token);
				userResponse.setTokenValidTime(UserHandler.tokenValidDate());
				userResponse.setStatus(Constants.USER_ACTIVE_STATUS);
				return userResponse;
			} else {
				userResponse.setRegistrationFailed(Constants.REGISTRATION_FAILED);
				return userResponse;
			}
		} else {
			userResponse.setInvalidateOtp(Constants.OTP_INVALIDATE);
			return userResponse;
		}
	}

	/*
	 * 
	 * To Insert UserIntrests
	 */
	@RequestMapping(value = "userIntrests/save/{intrestId}", method = RequestMethod.GET)
	public @ResponseBody Object saveUserIntrest(@PathVariable Integer intrestId, HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				UserInterest userIntrestModel = new UserInterest();
				InterestModel intretsModel = userService.getIntrestsByIntrestId(intrestId);
				if (intretsModel != null) {
					userIntrestModel.setUsersModel(usersModel);
					// userIntrestModel.setIntretsModel(intretsModel);
					return userService.saveUserIntrest(userIntrestModel);
				} else {
					userResponse.setIntrestNotFound(Constants.INTREST_NOT_FOUND);
					return userResponse;
				}
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}
	/*
	 * 
	 * 
	 * 
	 */

	@RequestMapping(value = "/intrest/list", method = RequestMethod.GET)
	public @ResponseBody Object getIntrestList(HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {

			return userService.getIntrestModels();
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}
	}

	/**
	 * @throws IOException
	 * 
	 * 
	 *                     Resend OTP
	 * 
	 */
	@RequestMapping(value = "/reSendOtp", method = RequestMethod.POST)
	public @ResponseBody Object getOtp(@RequestBody Email email) throws IOException {

		EmailSend emailSend = new EmailSend();
		UserResponse userResponse = new UserResponse();

		String otp = UserHandler.getOtp(email.getEmail());

		if (emailSend.mailResend(email.getEmail(), otp)) {
			userResponse.setStatus(Constants.USER_INACTIVE_STATUS);
			;
			return userResponse;
		} else {
			userResponse.setStatus(Constants.USER_PENDING_STATUS);
			return userResponse;
		}

	}

	/*
	 * 
	 * 
	 * To Get Group Details....
	 * 
	 */

	/*
	 * change password...
	 * 
	 */
	@RequestMapping(value = "/update/password", method = RequestMethod.POST)
	public @ResponseBody Object upadtePassword(@RequestBody UpdatePassword updatePassword, HttpServletRequest request)

	{
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null && usersModel.getEmail() != null) {
				updatePassword.setUsername(usersModel.getEmail());
				return userService.updatePassword(updatePassword);
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/*
	 * 
	 * 
	 */
	@RequestMapping(value = "/update/profilePic", method = RequestMethod.POST)
	public @ResponseBody Object upadteProfilePicture(@RequestBody UsersModel model, HttpServletRequest request)

	{
		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				usersModel.setProfilePhoto(model.getProfilePhoto());
				return userService.updateProfilePic(usersModel);
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/***
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/update/coverPic", method = RequestMethod.POST)
	public @ResponseBody Object upadteCoverPicture(@RequestBody UsersModel model, HttpServletRequest request)

	{

		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {

			UsersModel usersModel = userService.getUserByTokenId(token);

			if (usersModel.getUserId() != null) {

				usersModel.setCoverPhoto(model.getCoverPhoto());
				return userService.updateCoverPic(usersModel);
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}

		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}
	}

	/**
	 * @param req
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/update/user", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody Object updateUser(@RequestBody UsersModel model, HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			if (usersModel.getUserId() != null) {
				model.setUserId(usersModel.getUserId());
				if (model.getEmail() != null) {

				} else {
					model.setEmail(usersModel.getEmail());
				}
				if (model.getPhone() != null) {

				} else {
					model.setPhone(usersModel.getPhone());
				}
				if (model.getFirstName() != null) {

				} else {
					model.setFirstName(usersModel.getFirstName());
				}
				if (model.getLastName() != null) {

				} else {
					model.setLastName(usersModel.getLastName());
				}
				model.setCreatedDate(usersModel.getCreatedDate());
				model.setAccountType(usersModel.getAccountType());
				model.setStatus(Constants.USER_ACTIVE_STATUS);
				model.setPasswordHash(usersModel.getPasswordHash());
				model.setProfilePic(usersModel.getProfilePic());
				model.setCoverPic(usersModel.getCoverPic());
				model.setToken(token);
				model.setTokenUpdate(usersModel.getTokenUpdate());
				model.setStatus(usersModel.getStatus());
				Set<UserInterest> interests = new HashSet<>();
				interests.addAll(model.getUserInterest());
				model.getUserInterest().clear();
				model.getUserInterest().addAll(usersModel.getUserInterest());
				// return userService.updateUser(model);
				return userService.saveUserInterests(model, interests);
			} else {
				userResponse.setUserNotFound(Constants.USER_NOT_FOUND);
				return userResponse;
			}
		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/*
	 * 
	 * Check Token Valid
	 * 
	 */
	@RequestMapping(value = "/validate/token", method = RequestMethod.POST)
	public @ResponseBody Object checkTokenValid(@RequestBody Token token) {

		return userService.checkTokenAvailabe(token.getToken());

	}

	/*
	 * 
	 * Remove user from group
	 * 
	 * 
	 */
	@RequestMapping(value = "/remove/user/{groupId}/{userId}", method = RequestMethod.GET)
	public @ResponseBody Object removeUser(@PathVariable int groupId, @PathVariable int userId,
			HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {

			return userService.deleteGroupMember(userId, groupId);

		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}

	}

	/*
	 * 
	 * 
	 * Update Account type of User
	 */
	@RequestMapping(value = "/update/status/{status}", method = RequestMethod.GET)
	public @ResponseBody Object updateUserStatus(@PathVariable String status, HttpServletRequest request) {

		UserResponse userResponse = new UserResponse();

		String token = request.getHeader("token");

		if (userService.checkTokenAvailabe(token)) {

			return userService.updateUserStatus(status, token);

		} else {
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);

			return userResponse;
		}

	}

	/**
	 * 
	 */
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public @ResponseBody Object forgotPassword(@RequestBody Email email) throws IOException {
		EmailSend emailSend = new EmailSend();
		String otp = UserHandler.getOtp(email.getEmail());
		if (userService.checkEmailExists(email.getEmail())) {
			if (emailSend.forgotPassword(email.getEmail(), otp)) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else {
			return Boolean.FALSE;
		}
	}

	@RequestMapping(value = "/cities")
	public Object getAllCitys(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info(" >> getUserCitys()");
			return userService.getUserCitys();
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@RequestMapping(value = "/states")
	public Object getAllStates(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info(" >> getUserStates()");
			return userService.getUserStates();
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}

	}

	@RequestMapping(value = "/cities/state/{stateId}")
	public Object getAllCitesByStateId(@PathVariable Long stateId, HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			logger.info(" >> getUserStates()");
			return userService.getUserCitesByStateId(stateId);
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = { "/interests/{count}/{interest}" })
	public Object getInterestByChar(@PathVariable int count, @PathVariable String interest,
			HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			return userService.getInterestsByInterestName(interest, count);
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}

	}

	@ResponseBody
	@RequestMapping(value = { "/interests" })
	public Object getAllInterest(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			return userService.getAllInterests();
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/user/{userId}")
	public Object getUserById(@PathVariable Integer userId, HttpServletRequest request) {
		String token = request.getHeader("token");
		UsersModel usersModel = userService.getUserByTokenId(token);
		if (userService.checkTokenAvailabe(token)) {
			return userService.getUserByUserId(userId, usersModel.getUserId());
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	/**
	 * 
	 * Save User Interest
	 * 
	 * @param Interest    Model List<InterestModels>
	 * @param HttpRequest request
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/user/interest", method = RequestMethod.POST)
	public Object saveUserInterests(@RequestBody Set<UserInterest> interest, HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			UsersModel usersModel = userService.getUserByTokenId(token);
			return userService.saveUserInterests(usersModel, interest);
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public Object updateUserByUserModel(@RequestBody UsersModel usersModel, HttpServletRequest request) {
		String token = request.getHeader("token");
		if (userService.checkTokenAvailabe(token)) {
			return userService.updateUserByCustomCheck(token, usersModel);
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blockuser/{blockUserId}", method = RequestMethod.POST)
	public Object blockUserByUserId(@PathVariable Integer blockUserId, HttpServletRequest request) {
		String token = request.getHeader("token");
		UsersModel usersModel = userService.getUserByTokenId(token);
		if (usersModel != null && usersModel.getUserId().intValue() > 0) {
			return userService.blockUserByUserID(usersModel.getUserId(), blockUserId);
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blockeduser", method = RequestMethod.GET)
	public Object fetchAllBlockedUsers(HttpServletRequest request) {
		String token = request.getHeader("token");
		UsersModel usersModel = userService.getUserByTokenId(token);
		if (usersModel != null && usersModel.getUserId().intValue() > 0) {
			return userService.fetchBlockedContacts(usersModel.getUserId());
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setTokenInvalid(Constants.TOKEN_RESPONSE);
			return userResponse;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/filtersearch", method = RequestMethod.PUT)
	public boolean updateFilterSearchByUserId(HttpServletRequest request) {
		String token = request.getHeader("token");
		UsersModel usersModel = userService.getUserByTokenId(token);
		if (usersModel != null && usersModel.getUserId().intValue() > 0) {
			return userService.toogleFilterSearchByUser(usersModel.getUserId(), usersModel.isFilterSearch());
		} else {
			return false;
		}
	}

}
