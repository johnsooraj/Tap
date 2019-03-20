package com.cyspan.tap.user.services;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.cyspan.tap.commons.UpdatePassword;
import com.cyspan.tap.user.model.InterestModel;
import com.cyspan.tap.user.model.UserCity;
import com.cyspan.tap.user.model.UserInterest;
import com.cyspan.tap.user.model.UserState;
import com.cyspan.tap.user.model.UsersModel;

public interface UserService {

	public UsersModel getUsersByMobileNum(String phoneNum) throws DataAccessException;

	public List<UsersModel> getContactList(List<String> phoneNums);

	public int updateToken(String username, String password, String token);

	public Boolean checkTokenAvailabe(String token);

	public UsersModel getUserByTokenId(String token);

	public int logout(String username);

	public InterestModel getIntrestsByIntrestId(int intrestId);

	public Boolean saveUserIntrest(UserInterest intrestModel);

	public List<InterestModel> getIntrestModels();

	public Boolean updatePassword(UpdatePassword updatePassword);

	public Object updateProfilePic(UsersModel usersModel);

	public Integer pollCountByUserId(int userId);

	public Integer publicPollResponseCount(int userId);

	public Integer privatePollResponseCount(int userId);

	public Boolean updateCoverPic(UsersModel usersModel);

	public Boolean updateUser(UsersModel usersModel);

	public Object updateUserByCustomCheck(String token, UsersModel usersModel);

	public Boolean updateUserStatus(String status, String token);

	public Boolean deleteGroupMember(int userId, int groupId);

	public boolean checkEmailExists(String email);

	public Set<UserState> getUserStates();

	public List<UserCity> getUserCitys();

	public List<UserCity> getUserCitesByStateId(Long stateId);

	public List<InterestModel> getInterestsByInterestName(String interest, int count);

	public List<InterestModel> getAllInterests();

	public UsersModel saveUserInterests(UsersModel userModel, Set<UserInterest> interests);

	public UsersModel getUserByUserId(Integer userId, Integer loggedInUser);

	public UsersModel saveUserModel(UsersModel model);

	public boolean unsubscribeNotification(String userToken);

	public List<UsersModel> getAllUsers();

	public List<UsersModel> getAllUsersByFirstName(String firstname);

	public List<UsersModel> getAllUsersStartWithPhone(String firstname);

	public boolean blockUserByUserID(Integer ownerId, Integer blockedId);

	public boolean toogleFilterSearchByUser(Integer userId, boolean currentStatus);

	public List<UsersModel> fetchBlockedContacts(Integer userId);

}
