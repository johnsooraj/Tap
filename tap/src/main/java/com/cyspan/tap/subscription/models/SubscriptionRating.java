package com.cyspan.tap.subscription.models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "responderId", "feedback_feedbackId" }),
		@UniqueConstraint(columnNames = { "responderId", "poll_pollId" }) })
public class SubscriptionRating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ratingId;

	private byte ratingCount;
	private Long responderId;

	@Transient
	private String responderName;

	@Transient
	private String responderProfilePic;

	@UpdateTimestamp
	private Timestamp timestamp;

	@CreationTimestamp
	private Timestamp createDate;
	private byte status;

	@JsonIgnore
	@ManyToOne
	private SubscriptionFeedback feedback;

	@JsonIgnore
	@ManyToOne
	private SubscriptionPoll poll;
	
	@Transient
	private boolean respondStatus;

	public SubscriptionRating() {

	}

	public SubscriptionRating(byte ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Long getRatingId() {
		return ratingId;
	}

	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}

	public byte getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(byte ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Long getResponderId() {
		return responderId;
	}

	public void setResponderId(Long responderId) {
		this.responderId = responderId;
	}

	public String getResponderName() {
		return responderName;
	}

	public void setResponderName(String responderName) {
		this.responderName = responderName;
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

	public SubscriptionFeedback getFeedback() {
		return feedback;
	}

	public void setFeedback(SubscriptionFeedback feedback) {
		this.feedback = feedback;
	}

	public String getResponderProfilePic() {
		return responderProfilePic;
	}

	public void setResponderProfilePic(String responderProfilePic) {
		this.responderProfilePic = responderProfilePic;
	}

	public SubscriptionPoll getPoll() {
		return poll;
	}

	public void setPoll(SubscriptionPoll poll) {
		this.poll = poll;
	}

}
