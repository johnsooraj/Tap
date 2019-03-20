package com.cyspan.tap.group.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyspan.tap.commons.CustomNoDataFoundException;
import com.cyspan.tap.commons.FileUploadService;
import com.cyspan.tap.commons.UserHandler;
import com.cyspan.tap.facade.DaoFacade;
import com.cyspan.tap.group.dao.GroupsDAO;
import com.cyspan.tap.group.model.GroupmembersModel;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.notifications.GroupAsynNotification;
import com.cyspan.tap.notifications.GroupNotificationService;
import com.cyspan.tap.notifications.NotificationServiceDao;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UsersModel;

@Service
public class GroupServicesImpl implements GroupServices {

	@Autowired
	DaoFacade daoFacade;

	@Autowired
	GroupsDAO groupDao;

	@Autowired
	UsersDao userDao;

	@Autowired
	HttpServletRequest request;

	@Autowired
	FileUploadService uploadImage;

	@Autowired
	GroupNotificationService groupNotificationService;

	@Autowired
	NotificationServiceDao notidao;

	@Autowired
	GroupAsynNotification asynNotificationService;

	public GroupsModel createGroup(GroupsModel groupsModel, UsersModel ownerModel) {
		groupsModel.setGroupUniqueName(UserHandler.generateGroupUniqueId(groupsModel.getGroupName()));
		if (groupsModel.getGroupIcon() != null) {
			String bigImageUrl = uploadImage.uploadPictureBig(groupsModel.getGroupIcon(), "group");
			String smallImageUrl = uploadImage.uploadPictureSmall(groupsModel.getGroupIcon(), "group");
			groupsModel.setGroupIconBigUrl(bigImageUrl);
			groupsModel.setGroupIconUrl(smallImageUrl);
		}
		if (groupsModel.getGroupMembers() != null) {
			for (GroupmembersModel model : groupsModel.getGroupMembers()) {
				model.setGroupsModel(groupsModel);
				model.setGroupMemberId(null);
			}
		}
		groupsModel.setGroupIcon(null);
		groupDao.createGroup(groupsModel);
		asynNotificationService.subscribeUserToGroup(groupsModel, ownerModel.getUserId());
		return groupsModel;
	}

	@Override
	public GroupsModel updateGroup(GroupsModel groupsModel, UsersModel logedInUser) throws CustomNoDataFoundException {
		GroupsModel oldModel = groupDao.getGroupModelUsingId(groupsModel.getGroupId());
		// Existing data can only update
		if (oldModel == null) {
			throw new CustomNoDataFoundException("group not exist");
		}

		List<Integer> userIdList = oldModel.getGroupMembers().stream().map(GroupmembersModel::getUserId)
				.collect(Collectors.toList());
		List<String> existingUsersToken = new ArrayList<>();
		if (!userIdList.isEmpty()) {
			existingUsersToken.addAll(userDao.fetchFCMTokenByListOfUserId(userIdList));
		}

		// keep unique name same
		groupsModel.setGroupUniqueName(oldModel.getGroupUniqueName());

		// Check for groupname change
		if (groupsModel.getGroupName() != null && !groupsModel.getGroupName().equals(oldModel.getGroupName())) {
			asynNotificationService.groupNameChangeNotification(groupsModel, oldModel.getGroupName(),
					logedInUser.getFirstName());
		} else {
			// if the sender not include the group name
			groupsModel.setGroupName(oldModel.getGroupName());
		}

		// Check for new user into this group
		if (!groupsModel.getGroupMembers().isEmpty()) {

			for (int i = 0; i < groupsModel.getGroupMembers().size(); i++) {
				GroupmembersModel model = groupsModel.getGroupMembers().get(i);
				model.setGroupMemberId(null);
				model.setGroupsModel(groupsModel);
				UsersModel usersModel = userDao.getUserByUserId(model.getUserId());
				if (groupDao.checkTheUserInAGroup(usersModel.getUserId(), groupsModel)) {
					asynNotificationService.groupNewUserAddedToOthers(oldModel, usersModel.getFirstName());
					asynNotificationService.subscribeUserToGroup(groupsModel, logedInUser.getUserId());
				}
			}
		}
		// Check for new icon
		if (groupsModel.getGroupIcon() != null) {
			String bigImageUrl = uploadImage.uploadPictureBig(groupsModel.getGroupIcon(), "group");
			String smallImageUrl = uploadImage.uploadPictureSmall(groupsModel.getGroupIcon(), "group");
			groupsModel.setGroupIconBigUrl(bigImageUrl);
			groupsModel.setGroupIconUrl(smallImageUrl);
			asynNotificationService.groupIconChangeNotification(groupsModel, logedInUser.getFirstName());
		}
		groupDao.updateGroup(groupsModel);
		return groupsModel;
	}

	@Override
	public List<GroupsModel> fetchAllGroupModelByUserId(Integer userId) {
		return groupDao.fetchAllGroupModelByUserId(userId);
	}

	@Override
	public GroupsModel fetchGroupMembersByGroupId(Integer groupId) {
		//List<GroupmembersModel> list = groupDao.fetchGroupMembersByGroupId(groupId);
		/*for (GroupmembersModel model : list) {
			UsersModel usersModel = userDao.getUserByUserId(model.getUserId());
			if(usersModel != null) {
				model.setUsername(usersModel.getFirstName() + " " + usersModel.getLastName());
				model.setProfilePic(usersModel.getProfilePic());
			}
		}*/
		// above code commented because ios app need group modal with array of users
		
		GroupsModel newModel = groupDao.fetchGroupByGroupId(groupId);
		newModel.getGroupMembers().forEach(member ->{
			UsersModel usersModel = userDao.getUserByUserId(member.getUserId());
			if(usersModel != null) {
				member.setUsername(usersModel.getFirstName() + " " + usersModel.getLastName());
				member.setProfilePic(usersModel.getProfilePic());
			}
		});
		
		return newModel;
	}

	@Override
	public List<GroupsModel> fetchEmptyUniqueIdRow() {
		return groupDao.fetchEmptyUniqueIdRow();
	}

	@Override
	public void generateUniqueId() {
		List<GroupsModel> groupsModelsList = groupDao.fetchEmptyUniqueIdRow();
		for (GroupsModel groupsModel : groupsModelsList) {
			groupsModel.setGroupUniqueName(UserHandler.generateGroupUniqueId(groupsModel.getGroupName()));
			groupDao.createGroup(groupsModel);
		}
	}

	@Override
	public GroupsModel fetchGroupByGroupId(Integer groupId) {
		return groupDao.fetchGroupByGroupId(groupId);
	}

	@Override
	public boolean deleteGroupByGroupId(Integer groupId) {
		return groupDao.deleteGroupByGroupId(groupId);
	}

	@Override
	public boolean deleteGroupMemberByUserId(Integer groupId, Integer userId, HttpServletRequest request) {

		boolean status = false;

		// Check for who is trying to delete is Admin or Himself
		String token = request.getHeader("token");
		UsersModel userModel = userDao.getUserByTokenId(token);
		UsersModel removedUserModel = userDao.getUserByUserId(userId);
		GroupsModel groupsModel = groupDao.fetchGroupByGroupId(groupId);
		if (userModel != null && groupsModel != null) {
			// request user id and token user id same
			if (userId.equals(userModel.getUserId())) {
				// delete by same user or user left that group
				// send notification to all members
				status = groupDao.deleteGroupMemberByUserId(groupId, userId);
				if (status)
					asynNotificationService.groupMemberLeftfromGroup(groupsModel, removedUserModel.getFirstName());

				// Admin removed himselft so need another admin
				if (userModel.getUserId().equals(groupsModel.getGroupAdminId())) {
					// handle in fronted
				}

			} else {
				status = groupDao.deleteGroupMemberByUserId(groupId, userId);
				// check who is admin and compare token id to admin id
				asynNotificationService.groupMemberRemovedBy(groupsModel, removedUserModel.getFirstName(),
						userModel.getFirstName());

			}
		}
		return status;
	}

	@Override
	public List<GroupsModel> fetchAllGroup() {
		return groupDao.fetchAllGroup();
	}

	@Override
	public List<Object[]> fetchDataForExpireNotification(LocalDateTime now, LocalDateTime then) {
		return groupDao.fetchDataForExpireNotification(now, then);
	}

}
