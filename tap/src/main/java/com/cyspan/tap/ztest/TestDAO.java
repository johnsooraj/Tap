package com.cyspan.tap.ztest;

import java.util.List;

import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.user.model.UserCity;
import com.cyspan.tap.user.model.UserState;
import com.cyspan.tap.user.model.UsersModel;

public interface TestDAO {

	public boolean insertPoll(PollModel model);

	public List<PollModel> retrieveAllPoll();

	public UsersModel saveUserModel(UsersModel usersModel);

	public UsersModel getUserModelById(Integer id);

	public UserCity saveUserCity(UserCity userCity);

	public List<UserCity> getAllUserCitys();

	public UserState saveUserState(UserState userState);

	public List<UserState> getAllUserState();
	
	public List<UserCity> getCityByStateId(Long id);
	
	public UserCity getUserCityById(Long id);

}
