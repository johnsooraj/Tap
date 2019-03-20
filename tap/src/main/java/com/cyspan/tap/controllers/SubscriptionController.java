package com.cyspan.tap.controllers;

import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.subscription.models.FeedbackFreeComment;
import com.cyspan.tap.subscription.models.MultipleChoiceOptions;
import com.cyspan.tap.subscription.models.MultipleChoiceResponse;
import com.cyspan.tap.subscription.models.SubscriptionConstants;
import com.cyspan.tap.subscription.models.SubscriptionFeedback;
import com.cyspan.tap.subscription.models.SubscriptionImageOptionResponse;
import com.cyspan.tap.subscription.models.SubscriptionPoll;
import com.cyspan.tap.subscription.models.SubscriptionPollImages;
import com.cyspan.tap.subscription.models.SubscriptionRating;
import com.cyspan.tap.subscription.models.SubscriptionResponses;
import com.cyspan.tap.subscription.service.OrganizationService;
import com.cyspan.tap.subscription.service.SubscriptionToolsService;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.user.services.UserService;

/**
 * For Android/ IOS Native Application
 */

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

	@Autowired
	UserService userService;

	@Autowired
	OrganizationService orgService;

	@Autowired
	SubscriptionToolsService toolServices;

	static Logger logger = Logger.getLogger(SubscriptionController.class.getName());

	/**
	 * To Fetch Organization List for the user, user id from TOKEN in header
	 */
	@RequestMapping()
	public Object fetchSubscriptionByOrgId(HttpServletRequest request) {
		String token = request.getHeader("token");
		UsersModel user = userService.getUserByTokenId(token);
		if (user != null && user.getUserId() != null) {
			return orgService.fetchOrganizationModelByUserId(user.getUserId());
		}
		return null;
	}

	/**
	 * Fetch All Subscription tools in a Organization, to IOS/Android device.
	 * Responses are in pages mode, limit is fixed in service.
	 * 
	 * @param request
	 * @param id      - organization id
	 * @param body    - page number
	 * @return
	 */
	@RequestMapping(value = "/org/{id}")
	public Object fetchOrganizationsByTokenAnd(HttpServletRequest request, @PathVariable Long id,
			@RequestBody Map<String, String> body) {
		String token = request.getHeader("token");
		UsersModel user = userService.getUserByTokenId(token);
		if (user != null && user.getUserId() != null) {
			Long page = new Long(body.get("page"));
			return orgService.fetchSubscriptionToolsByOrgId(id, page.intValue(), user.getUserId(), body.get("text"));
		}
		return null;
	}

	/**
	 * Saving the subscription Tools Response from IOS/Android. Request Body is
	 * custom JSON data,
	 * 
	 * @param request - HttpServletRequest
	 * @param body    { "tool":"poll", "id":12, "type":2, "option":142,
	 *                "comment":"hello" }
	 *                <p>
	 *                tool : 'specifying which subscription tool is it', values :
	 *                'poll | feedback'.
	 *                </p>
	 *                <p>
	 *                id : 'subscription tool id', if it is poll - pollID or
	 *                feedbackID
	 *                </p>
	 *                <p>
	 *                type : 'type of the option', datatype : numeric, values :
	 *                '2|3|4|5' refer the Enum - SubscriptionConstants
	 *                </p>
	 *                option : 'selected option id', if user choose 1st multiple
	 *                option out of four then the id of the 1st option should be
	 *                here. if your choose Rating option, then the rate count should
	 *                be here. if the user choose image option, the image option id
	 *                should be here.
	 *                <p>
	 *                comment : 'for the free comment option', datatype: 'string'
	 *                </p>
	 * @return Response Object of Request Type
	 * @throws Exception
	 * @see SubscriptionConstants
	 */
	@RequestMapping(value = "/response", method = RequestMethod.POST)
	public Object saveSubscriptionResponse(HttpServletRequest request, @RequestBody Map<String, String> body)
			throws Exception {
		String token = request.getHeader("token");
		UsersModel user = userService.getUserByTokenId(token);
		if (user != null && user.getUserId() != null) {

			Object rResponse = null;

			SubscriptionConstants constants = SubscriptionConstants.fromAction(body.get("type"));
			SubscriptionPoll subscriptionPoll = null;
			SubscriptionFeedback subscriptionFeedback = null;
			SubscriptionResponses responses = new SubscriptionResponses();
			responses.setUserId(user.getUserId());

			// check for tool type and create dummy object for it
			// for rating option
			if (body.get("tool").equals("poll")) {
				subscriptionPoll = new SubscriptionPoll(new Long(body.get("id")));
				responses.setPollId(subscriptionPoll.getPollId());
			} else if (body.get("tool").equals("feedback")) {
				subscriptionFeedback = new SubscriptionFeedback(new Long(body.get("id")));
				responses.setFeedbackId(subscriptionFeedback.getFeedbackId());
			} else {
				throw new Exception("invalid subscription tool");
			}

			// based on the type id in request body, @see SubscriptionConstants
			if (constants != null) {
				switch (constants) {
				case MULTIPLE_CHOICE:
					MultipleChoiceResponse response = new MultipleChoiceResponse();
					response.setResponderId(user.getUserId().longValue());
					response.setChoiceOptions(new MultipleChoiceOptions(new Long(body.get("option"))));
					rResponse = toolServices.saveMultipleChoiceResponse(response);
					break;
				case RATING:
					SubscriptionRating rating = new SubscriptionRating();
					rating.setFeedback(subscriptionFeedback);
					rating.setPoll(subscriptionPoll);
					rating.setRatingCount(new Byte(body.get("option")));
					rating.setResponderId(user.getUserId().longValue());
					rResponse = toolServices.saveRatingResponse(rating);
					break;
				case IMAGES:
					SubscriptionImageOptionResponse response2 = new SubscriptionImageOptionResponse();
					response2.setPollImage(new SubscriptionPollImages(new Long(body.get("option"))));
					response2.setResponderId(user.getUserId().longValue());
					rResponse = toolServices.saveImageResponse(response2);
					break;
				case FREE_COMMENT:
					FeedbackFreeComment comment = new FeedbackFreeComment();
					comment.setCommentText(body.get("comment"));
					comment.setResponderId(user.getUserId().longValue());
					rResponse = toolServices.saveFreeCommentResponse(comment);
					break;
				default:
					throw new Exception("invalid subscription option");
				}

				try {
					if (rResponse != null) {
						SubscriptionResponses saveResponse = new SubscriptionResponses();
						saveResponse.setFeedbackId(subscriptionFeedback.getFeedbackId());
						saveResponse.setPollId(subscriptionPoll.getPollId());
						saveResponse.setUserId(user.getUserId());
					}
				} catch (Exception e) {
					logger.error("saving SubscriptionResponses failed!");
				}

				return rResponse;
			} else {
				throw new Exception("unknown subscription option");
			}
		}
		return null;
	}

	@RequestMapping(value = "/leave/{orgid}", method = RequestMethod.GET)
	public Object leaveOragnizationByUserToken(@PathVariable("orgid") Long orgId, HttpServletRequest request)
			throws Exception {
		String token = request.getHeader("token");
		UsersModel user = userService.getUserByTokenId(token);
		if (user != null && user.getUserId() != null && orgId != null) {
			orgService.deleteOrganizationMemberByUserId(user.getUserId());
			return true;
		}
		throw new Exception("invalid id");
	}

	@RequestMapping(value = "/clearall/{orgid}", method = RequestMethod.GET)
	public Object clearSubscriptionForUser(@PathVariable("orgid") Long orgId, HttpServletRequest request)
			throws Exception {
		String token = request.getHeader("token");
		UsersModel user = userService.getUserByTokenId(token);
		if (user != null && user.getUserId() != null && orgId != null) {
			toolServices.saveClearDateByUserIdAndOranizationId(user.getUserId(), orgId);
			return true;
		}
		throw new Exception("invalid id");
	}

}
