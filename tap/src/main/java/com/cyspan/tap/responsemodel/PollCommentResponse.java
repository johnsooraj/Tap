package com.cyspan.tap.responsemodel;

import java.sql.Date;

public class PollCommentResponse {

	private int pollId;
	private int userId;
	private String commentText;
	private int parentId;
	private Date commentCreate;

	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Date getCommentCreate() {
		return commentCreate;
	}

	public void setCommentCreate(Date commentCreate) {
		this.commentCreate = commentCreate;
	}

}
