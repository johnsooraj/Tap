package com.cyspan.tap.commons;

/**
 * 
 * This interface is used for global usage eg : check valid user ... etc
 * 
 * @author sumesh | Integretz LLC
 * 
 */
public interface GlobalClassDAO {

	public boolean checkUserExistsByUsername(String username);

	public boolean checkUserExistsByUserId(String userId);

	public boolean checkUsernameExists(String usename);

	public boolean checkPhoneExists(String phoneNo);

	public boolean checkEmailExists(String email);
}
