package com.cyspan.tap.group.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "groups")
public class GroupsModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer groupId;
	private String groupName;
	private Integer groupAdminId;
	private String groupIconUrl;
	private String groupIconBigUrl;

	private Timestamp timestamp;

	private Timestamp createDate;

	@Column(insertable = false, updatable = false)
	private String groupUniqueName;

	private List<GroupmembersModel> groupMembers;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private byte[] groupIcon;

	public GroupsModel() {
	}

	public GroupsModel(Integer groupId) {
		super();
		this.groupId = groupId;
	}

	public GroupsModel(Integer groupId, Integer groupAdminId) {
		super();
		this.groupId = groupId;
		this.groupAdminId = groupAdminId;
	}

	public GroupsModel(String groupName, int groupAdminId) {
		this.groupName = groupName;
		this.groupAdminId = groupAdminId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GroupId")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "GroupName", nullable = false, length = 50)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "GroupAdminId")
	public Integer getGroupAdminId() {
		return this.groupAdminId;
	}

	public void setGroupAdminId(Integer groupAdminId) {
		this.groupAdminId = groupAdminId;
	}

	@Column(name = "Group_Icon")
	public String getGroupIconUrl() {
		return groupIconUrl;
	}

	public void setGroupIconUrl(String groupIconUrl) {
		this.groupIconUrl = groupIconUrl;
	}

	public String getGroupIconBigUrl() {
		return groupIconBigUrl;
	}

	public void setGroupIconBigUrl(String groupIconBigUrl) {
		this.groupIconBigUrl = groupIconBigUrl;
	}

	@Transient
	public byte[] getGroupIcon() {
		return groupIcon;
	}

	public void setGroupIcon(byte[] groupIcon) {
		this.groupIcon = groupIcon;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "groupsModel", fetch = FetchType.EAGER)
	public List<GroupmembersModel> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupmembersModel> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public String getGroupUniqueName() {
		return groupUniqueName;
	}

	public void setGroupUniqueName(String groupUniqueName) {
		this.groupUniqueName = groupUniqueName;
	}

	@UpdateTimestamp
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@CreationTimestamp
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupsModel other = (GroupsModel) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}

}
