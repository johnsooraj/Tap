package com.cyspan.tap.poll.dao;

import java.util.Date;
import java.util.List;

import com.cyspan.tap.commons.LattestPollModel;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.poll.models.ImageOptionModel;
import com.cyspan.tap.poll.models.ImageOptionsResponseModel;
import com.cyspan.tap.poll.models.MultipleOptionModel;
import com.cyspan.tap.poll.models.MultipleOptionsResponseModel;
import com.cyspan.tap.poll.models.PollCommentModel;
import com.cyspan.tap.poll.models.PollGroupModel;
import com.cyspan.tap.poll.models.PollInterestModel;
import com.cyspan.tap.poll.models.PollLikeModel;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.poll.models.RatingOptionModel;
import com.cyspan.tap.poll.models.RatingOptionsResponseModel;
import com.cyspan.tap.poll.models.ReportPoll;
import com.cyspan.tap.poll.models.RsvpResponseModel;
import com.cyspan.tap.poll.models.ThisorthatOptionModel;
import com.cyspan.tap.poll.models.ThisorthatOptionsResponseModel;
import com.cyspan.tap.user.model.UsersModel;

public interface PollsDAO {

	public PollModel savePoll(PollModel model);

	public List<PollModel> retrieveAllPoll();

	public PollCommentModel savePollComment(PollCommentModel pollCommentModel);

	public List<PollCommentModel> getPollCommentsByPollId(int pollId, int groupId);

	public PollLikeModel savePollLike(PollLikeModel pollLikeResponse);

	public List<PollLikeModel> getPollLikesByPollId(int pollModel, int groupId);

	public Object pollById(int pollId);

	public List<PollModel> pollListByGroupId(int groupId);

	public PollModel getPollByPollId(int pollId);

	public List<PollModel> lattestPolls(int start, int pageSize, LattestPollModel lattestPollModel, boolean afterDate,
			boolean safeSerach, Integer userid);

	public List<PollModel> lattestPollsByQuestion(int start, int pageSize, LattestPollModel lattestPollModel,
			boolean afterDate, boolean safeSerach, Integer userid);

	public ImageOptionsResponseModel saveImageResponse(ImageOptionsResponseModel imageOptionsResponseModel);

	public ImageOptionModel getImageOptionByImageId(int imgId);

	public RatingOptionsResponseModel saveRatingOptionResponse(RatingOptionsResponseModel ratingOptionsResponseModel);

	public RatingOptionModel getRatingOptionByRatingId(int ratId);

	public MultipleOptionsResponseModel saveMultipleOptionResponse(
			MultipleOptionsResponseModel multipleOptionsResponseModel);

	public MultipleOptionModel getMultipleOptionByMulId(int multId);

	public ThisorthatOptionsResponseModel saveThisOrThatOptionResponse(
			ThisorthatOptionsResponseModel thisorthatOptionsResponseModel);

	public ThisorthatOptionModel getThisOrThatOptions(int thisId);

	public int getPollLikeCount(int pollId);

	public int getPollCommentCount(int pollId);

	public int getPollShareCount(int pollId, int groupId);

	public Boolean savePollIntrests(List<PollInterestModel> pollIntrestList);

	public PollGroupModel getPollGroupModelByPollAndGroupId(PollModel model, int groupId);

	public List<PollGroupModel> savePollGroupModels(List<PollGroupModel> pollGroupModel);

	public Object getPollSharedList(int pollId, int groupId);

	public PollModel getPollByPollIdAndGroupId(int pollId, int groupId);

	public boolean saveRSVPResponse(RsvpResponseModel model);

	public List<Object[]> fetchUserTokensByGroupIdAndPollIdFromPollLike(Integer groupId, Integer pollId);

	public boolean deleteLikeByGroupIdAndPollId(int groupId, PollModel pollId, UsersModel userId);

	public String fetchPollOwnerTokenFromPollId(int pollId);

	public GroupsModel fetchGroupModelByGroupId(int id);

	public boolean deletePollById(Long groupId, Integer pollId);

	public boolean updatePollDateAndResultType(byte type, Date date, Long pollId);

	public List<Object[]> fetchMinimalUserDataByGroupIds(Long[] ids);

	public List<Object[]> fetchMinimalUserDataByUserIds(Long[] ids);

	public List<PollModel> fetchRsvpPollsByUserId(Integer userId);

	public List<PollModel> fetchRsvpPollsByUserIdByPage(Integer userId, Integer count, Integer offset, String event);

	public boolean updatePollToObjectiveContentPoll(PollModel model);

	public boolean savePollReport(ReportPoll poll);

	public boolean checkUserBlocked(Integer userId, Integer blockedId);
}
