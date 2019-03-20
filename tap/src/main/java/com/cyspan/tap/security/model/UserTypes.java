package com.cyspan.tap.security.model;

/**
 * SuperAdministrator - owner of the application</br>
 * Administrator - Owner of Organization, assigned by SA.</br>
 * SubAdminstrator - organization subscription manager, assigned by Ad.</br>
 * RegistredUser - tap application user - signed up by Mobile application</br>
 * GuestUser - unregistered tap SAd.</br>
 */
public enum UserTypes {
	/**
	 * SuperAdministrator - owner of the application</br>
	 * Value: 100
	 */
	SuperAdministrator(100),

	/**
	 * Administrator - Owner of Organization, assigned by SA.</br>
	 * Value: 101
	 */
	Administrator(101),

	/**
	 * SubAdminstrator - organization subscription manager, assigned by Ad.</br>
	 * Value: 102
	 */
	SubAdminstrator(102),

	/**
	 * RegistredUser - tap application user - signed up by Mobile application</br>
	 * Value: 103
	 */
	RegistredUser(103),

	/**
	 * GuestUser - unregistered tap SAd.</br>
	 * Value: 104
	 */
	GuestUser(104),;

	private int userValue;

	private UserTypes(int userValue) {
		this.userValue = userValue;
	}

	public int getUserTypeId() {
		return this.userValue;
	}

	public String getUserTypeIdInString() {
		return this.userValue + "";
	}

}
