package com.cyspan.tap.subscription.service;

import java.util.List;

import com.cyspan.tap.subscription.models.OrganizationMember;
import com.cyspan.tap.subscription.models.OrganizationModel;

public interface OrganizationMemberService {

	public OrganizationMember createOrganizationMember(OrganizationMember member);

	public OrganizationMember updateOrganizationMember(OrganizationMember member);
	
	public boolean deleteOrganizationMember(Long id);

	public OrganizationMember fetchOrganizationMemberById(Long id);
	
	public OrganizationModel fetchOrganizationByMemberId(Long id);

	public List<OrganizationMember> fetchAllOrganizationMember();
}
