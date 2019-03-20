package com.cyspan.tap.notifications;

import java.util.List;

public interface NotificationService {

	public Object fetchNotificationByUserByPage(Integer userid, int pageno);

	public boolean updateNotificationReadStatus(List<Long> id);

	public void muteNotificationForPoll(Long userId, Long pollId);

	public void muteNotificationForGroup(Long userId, Long groupId);

	public void muteNotificationForPollByGroup(Long userId, Long pollId, Long groupId);

}
