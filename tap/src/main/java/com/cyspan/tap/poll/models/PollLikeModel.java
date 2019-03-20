package com.cyspan.tap.poll.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.cyspan.tap.user.model.UsersModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "polllikes", uniqueConstraints = @UniqueConstraint(columnNames = { "poll_id", "user_id", "groupId" }))
public class PollLikeModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "poll_like_id", unique = true, nullable = false)
	private int pollLikeId;

	@Column(name = "Status", nullable = false)
	private String status;

	@JsonBackReference("polllikes")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "poll_id")
	private PollModel poll;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id")
	private UsersModel usersModel;

	@Column(nullable = false)
	private int groupId;

	@Transient
	private int userId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long pollId;

	@Transient
	private String userName;

	@Transient
	private String profilePic;

	@JsonIgnore
	@Transient
	private String lastName;

	public PollLikeModel() {

	}

	public int getPollLikeId() {
		return pollLikeId;
	}

	public void setPollLikeId(int pollLikeId) {
		this.pollLikeId = pollLikeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public UsersModel getUsersModel() {
		return usersModel;
	}

	public void setUsersModel(UsersModel usersModel) {
		this.usersModel = usersModel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Long getPollId() {
		return pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	public String toString() {
		return "PollLikeModel [pollLikeId=" + pollLikeId + ", status=" + status + ", poll=" + poll + ", usersModel="
				+ usersModel + ", groupId=" + groupId + ", userId=" + userId + ", userName=" + userName + ", lastName="
				+ lastName + "]";
	}

}
