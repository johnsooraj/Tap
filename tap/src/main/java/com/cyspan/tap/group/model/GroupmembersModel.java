package com.cyspan.tap.group.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "groupmembers", uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "GroupId" }))
public class GroupmembersModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GroupMemberId", unique = true, length = 45)
	private Integer groupMemberId;

	@Column(name = "UserId")
	private Integer userId;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JoinDate")
	private Date joinDate;

	@Column(name = "is_admin")
	private Integer isAdmin;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GroupId", nullable = false)
	private GroupsModel groupsModel;

	private byte notificationSubscription;

	@JsonIgnore
	@Transient
	private String userToken;

	@Transient
	private String username;

	@Transient
	private String profilePic;

	public Integer getGroupMemberId() {
		return groupMemberId;
	}

	public void setGroupMemberId(Integer groupMemberId) {
		this.groupMemberId = groupMemberId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public GroupsModel getGroupsModel() {
		return groupsModel;
	}

	public void setGroupsModel(GroupsModel groupsModel) {
		this.groupsModel = groupsModel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public byte getNotificationSubscription() {
		return notificationSubscription;
	}

	public void setNotificationSubscription(byte notificationSubscription) {
		this.notificationSubscription = notificationSubscription;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

}
