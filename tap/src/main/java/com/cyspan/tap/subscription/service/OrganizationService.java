package com.cyspan.tap.subscription.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cyspan.tap.subscription.models.OrganizationModel;
import com.cyspan.tap.subscription.models.SubscriptionResponses;

public interface OrganizationService {

	public void insertDummyData();

	/**
	 * It will create New User as the SUPER ADMINISTRATOR for current Organization.
	 * Also the same user will added into Organization Member Table with Credentials
	 * in MemberCredentials Table.
	 * <p>
	 * Images will upload and set the aws-s3 file path to model.
	 * </p>
	 * 
	 * @param model a response object of persistent class
	 * 
	 * @return object saved object data with id
	 */
	public OrganizationModel createOrganization(OrganizationModel model);

	public OrganizationModel updateOrganization(OrganizationModel model);

	public boolean deleteOrganization(Long id);

	public boolean deleteMultipleOrganization(List<Long> ids);

	public OrganizationModel fetchOrganizationById(Long organizationId);

	public List<OrganizationModel> fetchAllOrganization();

	/**
	 * Add new Administrator for a Organization
	 * 
	 * @param id   Long - organization id
	 * @param data Map - user email or user phone number
	 */
	public Object fetchAndUpdateOrganization(Long id, Map<String, String> data);

	public List<Long> deleteUsersFromOrganization(List<Long> list);

	public Set<OrganizationModel> fetchOrganizationModelByUserId(Integer userId);

	public Object fetchSubscriptionToolsByOrgId(Long id, int pageno, Integer userId, String text);

	public Long deleteOrganizationMemberByUserId(Integer userId);
	
	public boolean resetOrganizationPassword(Long id);

	public void saveResponseDetail(SubscriptionResponses obj);
	
	
}
