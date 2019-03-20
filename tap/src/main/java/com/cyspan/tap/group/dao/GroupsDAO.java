package com.cyspan.tap.group.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.cyspan.tap.group.model.GroupmembersModel;
import com.cyspan.tap.group.model.GroupsModel;

public interface GroupsDAO {

	public GroupsModel createGroup(GroupsModel groupsModel);

	public GroupsModel updateGroup(GroupsModel groupsModel);

	public List<GroupsModel> fetchAllGroupModelByUserId(Integer userId);

	public List<GroupmembersModel> fetchGroupMembersByGroupId(Integer groupId);

	public GroupsModel fetchGroupByGroupId(Integer groupId);

	public boolean deleteGroupByGroupId(Integer groupId);

	public boolean deleteGroupMemberByUserId(Integer groupId, Integer userId);

	public List<GroupsModel> fetchAllGroup();

	public List<GroupsModel> fetchEmptyUniqueIdRow();

	public List<GroupmembersModel> fetchAllGroupMembers();

	public List<GroupmembersModel> fetchAllNotSubscribedGroupMembers();

	public GroupsModel getGroupModelUsingId(Integer userId);

	public Boolean checkTheUserInAGroup(Integer userId, GroupsModel groupsModel);

	public List<GroupmembersModel> fetchAllGroupsByUserId(Integer userId);

	public List<String> fetchAllGroupuniqueIdByGroupIds(Integer userId);
	
	public List<Object[]> fetchDataForExpireNotification(LocalDateTime now, LocalDateTime then);
	
}
