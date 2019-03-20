package com.cyspan.tap.user.dao;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import com.cyspan.tap.commons.UpdatePassword;
import com.cyspan.tap.commons.UserHandler;
import com.cyspan.tap.user.model.InterestModel;
import com.cyspan.tap.user.model.UserCity;
import com.cyspan.tap.user.model.UserInterest;
import com.cyspan.tap.user.model.UserState;
import com.cyspan.tap.user.model.UsersModel;

public interface UsersDao {

	public UsersModel getUserModelById(int userId);

	public UsersModel getUsersByMobileNum(String phoneNum);

	public List<UsersModel> getContactList(List<String> phoneNums);

	public void insertUserHandler(UserHandler userHandler);

	// public Boolean fbLogin(UserHandler userHandler);

	public Boolean checkGmailExist(UsersModel user);

	public void saveFbUser(UsersModel usersModel);

	public int updateToken(String username, String password, String token);

	public UsersModel getUserByTokenId(String token);

	public Integer pollCountByUserId(int userId);

	public Integer publicPollResponseCount(int userId);

	public Integer privatePollResponseCount(int userId);

	public int logout(String username);

	public InterestModel getIntrestsByIntrestId(int intrestId);

	public Boolean saveUserIntrest(UserInterest intrestModel);

	public List<InterestModel> getIntrestModels();

	public Boolean updatePassword(UpdatePassword updatePassword);

	public Boolean updateUser(UsersModel usersModel);

	public Boolean updateUserStatus(String status, String token);

	public Boolean deleteGroupMember(int userId, int groupId);

	public UsersModel isUserExistUserByPhone(String phoneNumber);

	public UsersModel isUserExistUserByEmail(String phoneNumber);

	public Set<UserState> getUserStates();

	public List<UserCity> getUserCitys();

	public List<UserCity> getUserCitesByStateId(Long stateId);

	public List<InterestModel> getAllInterests();

	public List<InterestModel> getInterestsByInterestName(String interest, int count);

	public UsersModel getUserByUserId(Integer userId);

	public UsersModel saveUser(UsersModel userId);

	public boolean deleteInterestByUserId(Integer userId);

	public List<String> fetchFCMTokenByListOfUserId(List<Integer> userIdList);

	public String fetchFCMTokenByUserId(Integer userId);

	public List<Object[]> fetchFCMTokenByGroupId(int groupId);

	public UsersModel createUser(UsersModel model);

	public UsersModel fetchUserByEmail(String email);

	public List<UsersModel> getAllUsersByFirstName(String firstname);

	public List<UsersModel> getAllUsersStartWithPhone(String phone);

	public List<UsersModel> getAllUsers();

	public UsersModel saveOrUpdateUsersModel(UsersModel model);

	public UsersModel fetchUserByEmailOrPhone(String email, String phone);

	public Object[] fetchUserNameAndProfilePicByUserId(Integer userid);

	public void setObjectiveWordsToServletContext() throws FileNotFoundException;

	public List<String> fetchPollReportResons();

	public boolean BlockContactByUserId(Integer userId, Integer contactId);

	public boolean toogleFilterSearchByUser(Integer userId, boolean currentStatus);

	public boolean checkUserBlocked(Integer loggedinUser, Integer contactId);

	public List<UsersModel> fetchBlockedContacts(Integer userId);

}
