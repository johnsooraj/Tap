package com.cyspan.tap.notifications;

import com.cyspan.tap.group.model.GroupsModel;

public interface GroupAsynNotification {

	public void subscribeUserToGroup(GroupsModel group, Integer userId);

	public void groupCreateNotification(GroupsModel model);

	public void groupNameChangeNotification(GroupsModel model, String oldName, String username);

	public void groupIconChangeNotification(GroupsModel model, String username);

	public void groupNewUserAddedToOthers(GroupsModel model, String newUsername);

	public void groupNewUserAddedToNewuser(GroupsModel model, String token, Integer userid);

	public void groupMemberRemovedBy(GroupsModel model, String removedUsername, String removerUsername);

	public void groupMemberLeftfromGroup(GroupsModel model, String removedUsername);

}
