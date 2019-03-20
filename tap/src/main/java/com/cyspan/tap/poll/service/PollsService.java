package com.cyspan.tap.poll.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cyspan.tap.commons.LattestPollModel;
import com.cyspan.tap.commons.PollResponseType;
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
import com.cyspan.tap.poll.models.ThisorthatOptionModel;
import com.cyspan.tap.poll.models.ThisorthatOptionsResponseModel;
import com.cyspan.tap.user.model.UsersModel;

public interface PollsService {

	public PollModel savePoll(PollModel model);

	public List<PollModel> retrieveAllPoll();

	public PollCommentModel savePollComment(PollCommentModel pollCommentModel);

	public List<PollCommentModel> getPollCommentsByPollId(int pollId, int groupId);

	public PollLikeModel savePollLike(PollLikeModel pollLikeModel);

	public List<PollLikeModel> getPollLikesByPollId(int pollId, int groupId);

	public List<PollModel> pollListByGroupId(int groupId);

	public PollModel getPollByPollId(int pollId);

	/**
	 * Master web service for fetch poll from database
	 * 
	 * @param lattestPollModel - pageNum, pageSize, groupId, date, question
	 * 
	 * @param afterDate        - boolean TRUE, for fetch the polls after that given
	 *                         date
	 */
	public List<PollModel> lattestPolls(LattestPollModel lattestPollModel, boolean afterDate, boolean safeSerach, Integer userid);

	public Boolean savePollIntrests(List<PollInterestModel> pollIntrestList);

	public ImageOptionModel saveImageResponse(ImageOptionsResponseModel imageOptionsResponseModel);

	public RatingOptionModel saveRatingOptionResponse(RatingOptionsResponseModel ratingOptionsResponseModel);

	public MultipleOptionModel saveMultipleOptionResponse(MultipleOptionsResponseModel multipleOptionsResponseModel);

	public ThisorthatOptionModel saveThisOrThatOptionResponse(
			ThisorthatOptionsResponseModel thisorthatOptionsResponseModel);

	public Object sharePollByGroupList(int pollId, List<PollGroupModel> groupModels);

	public Object getPollSharedList(int pollId, int groupId);

	public PollModel getPollByPollIdAndGroupId(int pollId, int groupId);

	public boolean saveRSVPResponse(int pollId, int groupId, int userId, int responseId);

	public Boolean deleteLikeByGroupIdAndPollId(int groupId, int pollId, int userId);

	public void sendNotification(Object responsModel, PollResponseType type, int groupId, UsersModel user);

	public boolean deletePollById(Long groupId, Integer pollId);

	public boolean updatePollDateAndResultType(byte type, Date date, Long pollId);

	public List<PollModel> fetchRsvpPollsByUserId(Map<String, String> body);
	
	public boolean savePollReport(ReportPoll poll);

}
