package com.cyspan.tap.subscription.service;

import com.cyspan.tap.subscription.models.OrganizationMember;

public interface MemberCredentialService {

	public OrganizationMember fetchMemberByUsernameAndPassword(String username, String password);

	public OrganizationMember resetPassword(String username, String password, String newPassword);
}
