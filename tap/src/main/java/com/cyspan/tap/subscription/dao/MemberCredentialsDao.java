package com.cyspan.tap.subscription.dao;

import com.cyspan.tap.subscription.models.MemberCredentials;

public interface MemberCredentialsDao {

	public MemberCredentials fetchMemberByUsernameAndPassword(String username, String password);

	public MemberCredentials updateMemberCredentials(MemberCredentials credentials);
	
	public MemberCredentials saveOrUpdateMemberCredentials(MemberCredentials model);
}
