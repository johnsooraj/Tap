package com.cyspan.tap.poll.models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RsvpUserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	private Long userId;
	@UpdateTimestamp
	private Timestamp timestamp;
	@CreationTimestamp
	private Timestamp createDate;
	private byte status;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private PollModel poll;

	@JsonIgnore
	@Transient
	String userToken;

	public RsvpUserModel() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

}
