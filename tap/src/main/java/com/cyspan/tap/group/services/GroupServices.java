package com.cyspan.tap.group.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cyspan.tap.commons.CustomNoDataFoundException;
import com.cyspan.tap.group.dao.GroupsIMPL;
import com.cyspan.tap.group.model.GroupmembersModel;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.user.model.UsersModel;

public interface GroupServices {

	public GroupsModel createGroup(GroupsModel groupsModel, UsersModel ownerModel);

	public GroupsModel updateGroup(GroupsModel groupsModel, UsersModel model) throws CustomNoDataFoundException;

	public List<GroupsModel> fetchAllGroupModelByUserId(Integer userId);

	public GroupsModel fetchGroupMembersByGroupId(Integer groupId);

	public GroupsModel fetchGroupByGroupId(Integer groupId);

	public List<GroupsModel> fetchAllGroup();

	public boolean deleteGroupByGroupId(Integer groupId);

	public boolean deleteGroupMemberByUserId(Integer groupId, Integer userId, HttpServletRequest request);

	public List<GroupsModel> fetchEmptyUniqueIdRow();

	public void generateUniqueId();

	/**
	 * Join 3 Tables Polls, Groups, Group_Polls
	 * 
	 * @see GroupsIMPL
	 * 
	 * @return groupId from group_poll, poll question from polls, groupUniquename
	 *         from group
	 * 
	 */
	public List<Object[]> fetchDataForExpireNotification(LocalDateTime now, LocalDateTime then);

}
