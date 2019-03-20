package com.cyspan.tap.notifications;

import java.util.Map;

import com.cyspan.tap.commons.PollResponseType;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.poll.models.PollCommentModel;
import com.cyspan.tap.poll.models.PollLikeModel;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.user.model.UsersModel;

public interface PollAsynNotification {

	public void pollResponseNotifications(PollModel pollModel, GroupsModel groupsModel, String currentUsername,
			Map<Integer, String> otherFcmTokens, Integer totalResponseCount);

	public void pollLikeNotification(PollLikeModel likeModel);

	public void PollCommentNotification(PollCommentModel commentModel);

	public void RsvpCreateNotification(PollModel model);

	public void PollExpireReminderNotification(int remianHour, String topic, String groupName, String pollQuestion,
			Integer pollId, String groupIcon, Integer groupId);

	public void PollResponseNotifications(Object responsModel, PollResponseType type, int groupId, UsersModel user);

	public void PollCreateToGroupMembers(PollModel poll, GroupsModel group, String pollOwnerName);
}
