package com.cyspan.tap.security.impl;

import java.util.List;

import com.cyspan.tap.security.model.SecurityPermissions;
import com.cyspan.tap.security.model.SecurityRoles;

public interface SecurityDao {

	public List<SecurityRoles> saveSecurityRoles(List<SecurityRoles> roles);
	
	public List<SecurityPermissions> saveSecurityPermissions(List<SecurityPermissions> permissionsF);
}
