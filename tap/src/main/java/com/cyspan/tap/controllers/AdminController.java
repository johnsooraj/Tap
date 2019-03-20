package com.cyspan.tap.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.security.model.UserTypes;
import com.cyspan.tap.subscription.models.MemberCredentials;
import com.cyspan.tap.subscription.models.OrganizationMember;
import com.cyspan.tap.subscription.models.OrganizationModel;
import com.cyspan.tap.subscription.service.MemberCredentialService;
import com.cyspan.tap.subscription.service.OrganizationMemberService;
import com.cyspan.tap.subscription.service.OrganizationService;
import com.cyspan.tap.subscription.service.SubscriptionToolsService;
import com.cyspan.tap.user.services.UserService;

// Rename this controller

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	MemberCredentialService credentialsService;

	@Autowired
	OrganizationMemberService orgMemberService;

	@Autowired
	OrganizationService orgService;

	@Autowired
	UserService userService;

	@Autowired
	SubscriptionToolsService toolsService;

	/* common web service start */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object loginAdmin(@RequestBody MemberCredentials credentials) {
		OrganizationMember member = credentialsService.fetchMemberByUsernameAndPassword(credentials.getUsername(),
				credentials.getPassword());
		return member;
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public Object adminPasswordReset(@RequestBody Map<String, String> userData) {
		String username = userData.get("username");
		String password = userData.get("password");
		String newPassword = userData.get("newpassword");
		OrganizationMember member = credentialsService.resetPassword(username, password, newPassword);
		return member;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Object getAllCustomer() {
		return userService.getAllUsers();
	}

	@RequestMapping(value = "/users/name/{username}", method = RequestMethod.GET)
	public Object getAllCustomerByFirstname(@PathVariable("username") String username) {
		return userService.getAllUsersByFirstName(username);
	}

	@RequestMapping(value = "/users/phone/{phone}", method = RequestMethod.GET)
	public Object getAllCustomerByPhoneNumber(@PathVariable("phone") String phone) {
		return userService.getAllUsersStartWithPhone(phone);
	}

	/* common web service end */
	/* organization web service start */

	@RequestMapping(value = "/organization", method = RequestMethod.GET)
	public Object fetchAllOrganization() {
		List<OrganizationModel> models = orgService.fetchAllOrganization();
		return models;
	}

	@RequestMapping(value = "/organization/{organizationId}", method = RequestMethod.GET)
	public Object fetchOrganizationById(@PathVariable("organizationId") Long organizationId,
			@PathParam(value = "type") String type) {

		OrganizationModel model = orgService.fetchOrganizationById(organizationId);
		if (type != null) {
			switch (type) {
			case "administrator":
				return model.getMembers().stream()
						.filter(mem -> mem.getMemberType() == UserTypes.SubAdminstrator.getUserTypeId())
						.collect(Collectors.toList());
			case "members":
				return model.getMembers().stream()
						.filter(mem -> mem.getMemberType() == UserTypes.RegistredUser.getUserTypeId()
								|| mem.getMemberType() == UserTypes.GuestUser.getUserTypeId())
						.collect(Collectors.toList());
			case "feedbacks":
				return model.getFeedbackGroups();
			case "feedbackgroup":
				return toolsService.fechFeedbackGroupByOrganizationId(organizationId);
			case "polls":
				return model.getPolls();
			case "notices":
				return model.getNotices();
			default:
				break;
			}
		} else {
			return model;
		}
		return null;
	}

	/**
	 * Create Organization
	 * 
	 * @param OrganizationModel
	 * @return OrganizationModel
	 */
	@RequestMapping(value = "/organization", method = RequestMethod.POST)
	public Object saveOrganization(@RequestBody OrganizationModel organization) {
		OrganizationModel model = orgService.createOrganization(organization);
		return model;
	}

	@RequestMapping(value = "/organization", method = RequestMethod.PUT)
	public Object updateOrganization(@RequestBody OrganizationModel organization) {
		OrganizationModel model = orgService.updateOrganization(organization);
		return model;
	}

	@RequestMapping(value = "/reset-organization/{id}", method = RequestMethod.PUT)
	public Object resetOrganizationPassword(@PathVariable("id") Long id) {
		return orgService.resetOrganizationPassword(id);
	}

	@RequestMapping(value = "/organization/{organizationId}", method = RequestMethod.DELETE)
	public Object deleteOrganization(@PathVariable("organizationId") Long organizationId) {
		boolean result = orgService.deleteOrganization(organizationId);
		return result;
	}

	@RequestMapping(value = "/organization/delete", method = RequestMethod.POST)
	public Object deleteMultipleOrganization(@RequestBody List<Long> ids) {
		boolean result = orgService.deleteMultipleOrganization(ids);
		return result;
	}

	@RequestMapping(value = "/organization/members/{orgid}", method = RequestMethod.GET)
	public Object getAllMemberByOrgId(@PathVariable Long orgid) {
		OrganizationMember model = orgMemberService.fetchOrganizationMemberById(orgid);
		return model;
	}

	@RequestMapping(value = "/organization/member/{userId}", method = RequestMethod.GET)
	public Object getMemberById(@PathVariable Long userId) {
		OrganizationMember model = orgMemberService.fetchOrganizationMemberById(userId);
		return model;
	}

	@RequestMapping(value = "/organization/addmember/{orgId}", method = RequestMethod.POST)
	public Object addNewMemberToOrganization(@PathVariable("orgId") Long orgId,
			@RequestBody Map<String, String> memberdata) {
		return orgService.fetchAndUpdateOrganization(orgId, memberdata);
	}

	@RequestMapping(value = "/organization/addadmin", method = RequestMethod.POST)
	public Object addNewSubAdminToOrganization(@RequestBody OrganizationMember member) {
		return orgMemberService.createOrganizationMember(member);
	}

	@RequestMapping(value = "/organization/updateadmin", method = RequestMethod.POST)
	public Object updateSubAdminToOrganization(@RequestBody OrganizationMember member) {
		return orgMemberService.updateOrganizationMember(member);
	}

	@RequestMapping(value = "/organization/removemember", method = RequestMethod.POST)
	public Object removeMembersFromOrganization(@RequestBody List<Long> deleteUsers) {
		return orgService.deleteUsersFromOrganization(deleteUsers);
	}

	/* organization web service end */

	/* subscription tools webservice start */

	@RequestMapping(value = "/organization/feedbackgroup/{id}")
	public Object getFeedbackGroupById(@PathVariable Long id) {
		return toolsService.fechFeedbackGroupById(id);
	}

	@RequestMapping(value = "/organization/feedback/{id}")
	public Object getFeedbackById(@PathVariable Long id) {
		return toolsService.fechFeedbackById(id);
	}

	@RequestMapping(value = "/organization/poll/{id}")
	public Object getPollById(@PathVariable Long id) {
		return toolsService.fechPollById(id);
	}

	@RequestMapping(value = "/organization/notice/{id}")
	public Object getNoticeById(@PathVariable Long id) {
		return toolsService.fetchNoticeById(id);
	}

	@RequestMapping(value = "/subscriptiontool", method = RequestMethod.POST)
	public Object saveSubscriptionOptions(@RequestBody OrganizationModel model) {
		return toolsService.saveSubscriptionToolsOptions(model);
	}

	@RequestMapping(value = "/subscriptiontool", method = RequestMethod.PUT)
	public Object updateSubscriptionOptions(@RequestBody OrganizationModel model) {
		return toolsService.updateSubscriptionToolsOptions(model);
	}

	/**
	 * Reason for POST method for delete is, json body
	 */
	@RequestMapping(value = "/organization/feedbackgroup/delete", method = RequestMethod.POST)
	public Object deleteFeedbackGroups(@RequestBody List<Long> ids) {
		return toolsService.deleteFeedbackGroupsById(ids);
	}

	/**
	 * Reason for POST method for delete is, json body
	 */
	@RequestMapping(value = "/organization/feedback/delete", method = RequestMethod.POST)
	public Object deleteFeedbacks(@RequestBody List<Long> ids) {
		return toolsService.deleteFeedbacksById(ids);
	}

	/**
	 * Reason for POST method for delete is, json body
	 */
	@RequestMapping(value = "/organization/poll/delete", method = RequestMethod.POST)
	public Object deletePolls(@RequestBody List<Long> ids) {
		return toolsService.deletePollsById(ids);
	}

	/**
	 * Reason for POST method for delete is, json body
	 */
	@RequestMapping(value = "/organization/notice/delete", method = RequestMethod.POST)
	public Object deleteNotices(@RequestBody List<Long> ids) {
		return toolsService.deleteNoticesById(ids);
	}

	/* subscription tools webservice end */

	/**
	 * To fetch data for display subscription results. It is a web service with
	 * options.
	 * 
	 * @param fetch: String, all|feedback|poll|notice, not null
	 * @param page: Integer, page number, not null
	 * @param count: Integer, number of results, not null
	 * @param condition: String, order by condition, default create date
	 * @param upperlimit: Timestamp, from date
	 * @param lowerlimit: Timestamp, to date
	 * 
	 * @return {@link List<Object>}: list of objects based on request params
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/results/{orgId}", method = RequestMethod.GET)
	public Object fetchSubscriptionResult(@PathVariable Long orgId, @RequestParam Map<String, Object> parameters)
			throws Exception {

		if (orgId == null)
			throw new Exception("invalid id!");

		if (parameters.get("fetch") == null)
			throw new Exception("paramerter 'fetch' required!");

		if (parameters.get("page") == null)
			throw new Exception("paramerter 'page number' required!");

		if (parameters.get("count") == null)
			throw new Exception("paramerter 'count' required!");

		if (parameters.get("upperlimit") != null) {
			if (parameters.get("upperlimit").equals("undefined")) {
				parameters.remove("upperlimit");
				parameters.remove("lowerlimit");
			}
		}

		return toolsService.fetchSubscriptionResults(orgId, parameters);
	}

}
