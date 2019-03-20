package com.cyspan.tap.subscription.dao;

import java.time.LocalDate;
import java.util.List;

import com.cyspan.tap.subscription.models.FeedbackFreeComment;
import com.cyspan.tap.subscription.models.MultipleChoiceResponse;
import com.cyspan.tap.subscription.models.SubscriptionFeedback;
import com.cyspan.tap.subscription.models.SubscriptionFeedbackGroup;
import com.cyspan.tap.subscription.models.SubscriptionImageOptionResponse;
import com.cyspan.tap.subscription.models.SubscriptionNotice;
import com.cyspan.tap.subscription.models.SubscriptionPoll;
import com.cyspan.tap.subscription.models.SubscriptionRating;
import com.cyspan.tap.subscription.models.SubscriptionResponses;

public interface SubscriptionToolsDao {
	public SubscriptionFeedback fechFeedbackById(Long id);

	public SubscriptionPoll fechPollById(Long id);

	public SubscriptionNotice fetchNoticeById(Long id);

	public SubscriptionFeedback saveSubscriptionFeedback(SubscriptionFeedback feedback);

	public SubscriptionPoll saveSubscriptionPoll(SubscriptionPoll poll);

	public SubscriptionNotice saveSubscriptionNotice(SubscriptionNotice notice);

	public SubscriptionFeedbackGroup saveSubscriptionFeedbackGroup(SubscriptionFeedbackGroup notice);

	public SubscriptionFeedback updateSubscriptionFeedback(SubscriptionFeedback feedback);

	public boolean updateSubscriptionFeedbackGroup(SubscriptionFeedbackGroup feedback);

	public boolean deleteFeedbackById(Long id);

	public boolean deletePollById(Long id);

	public boolean deleteNoticeById(Long id);

	public List<Object> fetchfeedbacksAndPollsByOrganizationIdOrderByCreateDate(Long orgId, Integer count,
			Integer offset, LocalDate upperLimit, LocalDate lowerLimit);

	public List<Object> fetchfeedbackGroupsAndPollsByOrganizationIdOrderByCreateDate(Long orgId, Integer count,
			Integer offset, LocalDate upperLimit, LocalDate lowerLimit);

	public SubscriptionFeedback fetchFeedbackByIdAndJoinUserModel(Long feedId);

	public SubscriptionPoll fetchPollByIdAndJoinUserModel(Long pollId);

	public SubscriptionFeedbackGroup fetchFeedbackGroupById(Long id);

	public List<SubscriptionFeedbackGroup> fechFeedbackGroupByOrganizationId(Long id);

	public boolean deleteFeedbackGroupById(Long id);

	public Object fetchSubscriptionToolsByOrgId(Long orgid, int start, int end, Integer userId, String text);

	public SubscriptionNotice fetchSubscriptionNoticeWithUserDataById(Long id);

	public MultipleChoiceResponse saveMultipleChoiceResponse(MultipleChoiceResponse response);

	public SubscriptionRating saveRatingResponse(SubscriptionRating response);

	public SubscriptionImageOptionResponse saveImageResponse(SubscriptionImageOptionResponse response);

	public FeedbackFreeComment saveFreeCommentResponse(FeedbackFreeComment response);

	public boolean saveClearDateByUserIdAndOranizationId(Integer userId, Long orgId);

	boolean checkIsUserRespondedOrNot(Integer userId, Long toolId);
	
	public boolean saveUserRespondData(SubscriptionResponses responses);

}
