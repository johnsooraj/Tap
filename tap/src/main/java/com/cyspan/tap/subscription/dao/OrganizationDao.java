package com.cyspan.tap.subscription.dao;

import java.util.List;
import java.util.Set;

import com.cyspan.tap.subscription.models.OrganizationModel;
import com.cyspan.tap.subscription.models.SubscriptionResponses;
import com.cyspan.tap.user.model.UserAddress;

public interface OrganizationDao {

	public OrganizationModel createOrganization(OrganizationModel model);

	public OrganizationModel updateOrganization(OrganizationModel model);

	public OrganizationModel updateOrganizationHQLQuery(OrganizationModel model);

	public boolean deleteOrganization(Long id);

	public OrganizationModel fetchOrganizationById(Long organizationId);

	public List<OrganizationModel> fetchAllOrganization();

	public OrganizationModel fetchOrganizationByMemberId(Long id);

	public OrganizationModel saveOrUpdateOrganization(OrganizationModel model);

	public UserAddress createUserOrganizationAddress(UserAddress address);

	public UserAddress updateUserOrganizationAddress(UserAddress address);

	public Set<OrganizationModel> fetchOrganizationModelByUserId(Integer userId);

	public void saveResponseDetail(SubscriptionResponses obj);

}
