package com.cyspan.tap.commons;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class LattestPollModel {

	private int pageNum;

	private int pageSize;

	private int groupId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String question;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return " [question=" + question + ", pageNum=" + pageNum + ", pageSize=" + pageSize + ", groupId=" + groupId
				+ ", date=" + date + "]";
	}

}
