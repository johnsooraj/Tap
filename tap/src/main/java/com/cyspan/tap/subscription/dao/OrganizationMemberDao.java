package com.cyspan.tap.subscription.dao;

import java.util.List;

import com.cyspan.tap.security.model.SecurityPermissions;
import com.cyspan.tap.subscription.models.MemberCredentials;
import com.cyspan.tap.subscription.models.OrganizationMember;

public interface OrganizationMemberDao {

	public void insertDummyData();

	public OrganizationMember createOrganizationMember(OrganizationMember member);

	public OrganizationMember updateOrganizationMember(OrganizationMember member);

	public boolean deleteOrganizationMember(Long id);

	public boolean deleteOrganizationMemberHQLquery(Long id);

	public OrganizationMember fetchOrganizationMemberById(Long id);

	public List<OrganizationMember> fetchAllOrganizationMember();

	public OrganizationMember saveOrUpdateOrganizationMember(OrganizationMember model);

	public List<SecurityPermissions> fetchAllPermissions();

	public Long fetchOrganizationMemberIdByUserId(Integer userid);

	public boolean updateMemberPasswordAndShowResetPw(MemberCredentials credentials, String newPassword, Long memeberId);

}
