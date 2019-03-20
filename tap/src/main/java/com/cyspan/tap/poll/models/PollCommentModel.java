package com.cyspan.tap.poll.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cyspan.tap.user.model.UsersModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The persistent class for the pollcomments database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "pollcomments")
public class PollCommentModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int pollCommentId;

	@Lob
	@Column(nullable = false)
	private String commentText;

	@Column(nullable = false)
	private int groupId;

	@Column(name = "parentId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long parentId;

	@Column
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonBackReference("pollcomments")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "poll_id", nullable = false)
	private PollModel poll;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id")
	private UsersModel usersModel;

	@Transient
	private int userId;

	@Transient
	private String userName;

	@Transient
	private String profilePicUrl;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long pollId;

	public PollCommentModel() {
	}

	public int getPollCommentId() {
		return pollCommentId;
	}

	public void setPollCommentId(int pollCommentId) {
		this.pollCommentId = pollCommentId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
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

	public UsersModel getUsersModel() {
		return usersModel;
	}

	public void setUsersModel(UsersModel usersModel) {
		this.usersModel = usersModel;
	}

	@Override
	public String toString() {
		return "PollCommentModel [pollCommentId=" + pollCommentId + ", commentText=" + commentText + ", userId="
				+ userId + ", groupId=" + groupId + ", parentId=" + parentId + ", createdDate=" + createdDate
				+ ", poll=" + poll + ", userName=" + userName + ", profilePicUrl=" + profilePicUrl + ", pollId="
				+ pollId + "]";
	}

}