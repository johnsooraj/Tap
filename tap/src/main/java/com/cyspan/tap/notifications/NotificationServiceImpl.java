package com.cyspan.tap.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

	public static final Integer ROW_LIMIT = 20;

	@Autowired
	NotificationServiceDao notidao;

	@Override
	public Object fetchNotificationByUserByPage(Integer userid, int pageno) {

		int rowCount = pageno * ROW_LIMIT;
		int start = (rowCount - ROW_LIMIT);
		int offset = start;

		Object list = notidao.fetchNotificationByUserByPage(userid, ROW_LIMIT, offset);
		return list;
	}

	@Override
	public boolean updateNotificationReadStatus(List<Long> id) {
		id.forEach(odObj -> notidao.updateNotificationReadStatus(odObj));
		return true;
	}

	@Override
	public void muteNotificationForPoll(Long userId, Long pollId) {
		notidao.muteNotificationForPoll(userId, pollId);
	}

	@Override
	public void muteNotificationForGroup(Long userId, Long groupId) {
		notidao.muteNotificationForGroup(userId, groupId);
	}

	@Override
	public void muteNotificationForPollByGroup(Long userId, Long pollId, Long groupId) {
		notidao.muteNotificationForPollByGroup(userId, pollId, groupId);
	}

}
