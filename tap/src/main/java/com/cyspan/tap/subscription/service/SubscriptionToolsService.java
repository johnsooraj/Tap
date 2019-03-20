package com.cyspan.tap.subscription.service;

import java.util.List;
import java.util.Map;

import com.cyspan.tap.subscription.models.FeedbackFreeComment;
import com.cyspan.tap.subscription.models.MultipleChoiceResponse;
import com.cyspan.tap.subscription.models.OrganizationModel;
import com.cyspan.tap.subscription.models.SubscriptionFeedback;
import com.cyspan.tap.subscription.models.SubscriptionFeedbackGroup;
import com.cyspan.tap.subscription.models.SubscriptionImageOptionResponse;
import com.cyspan.tap.subscription.models.SubscriptionNotice;
import com.cyspan.tap.subscription.models.SubscriptionPoll;
import com.cyspan.tap.subscription.models.SubscriptionRating;
import com.cyspan.tap.subscription.models.SubscriptionResponses;

public interface SubscriptionToolsService {

	public SubscriptionFeedback fechFeedbackById(Long id);

	public SubscriptionFeedbackGroup fechFeedbackGroupById(Long id);

	public List<SubscriptionFeedbackGroup> fechFeedbackGroupByOrganizationId(Long id);

	public SubscriptionPoll fechPollById(Long id);

	public SubscriptionNotice fetchNoticeById(Long id);

	public Object saveSubscriptionToolsOptions(OrganizationModel model);

	public OrganizationModel updateSubscriptionToolsOptions(OrganizationModel model);

	public List<Long> deleteFeedbacksById(List<Long> ids);
	
	public List<Long> deleteFeedbackGroupsById(List<Long> ids);

	public List<Long> deletePollsById(List<Long> ids);

	public List<Long> deleteNoticesById(List<Long> ids);

	public List<Object> fetchSubscriptionResults(Long organizationId, Map<String, Object> parameters);

	public Object fetchDetailedSubscriptionResultById(Long id, String toolType);
	
	public MultipleChoiceResponse saveMultipleChoiceResponse(MultipleChoiceResponse response);
	
	public SubscriptionRating saveRatingResponse(SubscriptionRating response);
	
	public SubscriptionImageOptionResponse saveImageResponse(SubscriptionImageOptionResponse response);
	
	public FeedbackFreeComment saveFreeCommentResponse(FeedbackFreeComment response);
	
	public boolean saveClearDateByUserIdAndOranizationId(Integer userId, Long orgId);
	
	public boolean saveUserRespondData(SubscriptionResponses responses);

}
