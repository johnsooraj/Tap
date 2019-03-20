package com.cyspan.tap.notifications;

public interface NotificationServiceDao {

	public Object fetchNotificationByUserByPage(Integer userid, Integer limit, Integer offset);

	public void saveNotifiactionByUser(Integer userid, String title, String body);

	public void saveNotifiaction(NotificationEntity entity);

	public boolean updateNotificationReadStatus(Long id);

	public void muteNotificationForPoll(Long userId, Long pollId);

	public void muteNotificationForGroup(Long userId, Long groupId);

	/**
	 * Check weather this user muted this poll
	 * 
	 * @param userid user id for check
	 * @param pollid poll id that muted
	 * 
	 * @return true if the user muted, false if user not muted
	 */
	public boolean checkNotificationMuted(Long userid, Long pollid);

	public void muteNotificationForPollByGroup(Long userId, Long pollId, Long groupId);
}
