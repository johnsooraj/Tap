package com.cyspan.tap.subscription.models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SubscriptionImageOptionResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
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
	@OneToOne
	private SubscriptionPollImages pollImage;

	public SubscriptionImageOptionResponse() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public SubscriptionPollImages getPollImage() {
		return pollImage;
	}

	public void setPollImage(SubscriptionPollImages pollImage) {
		this.pollImage = pollImage;
	}

	public String getResponderProfilePic() {
		return responderProfilePic;
	}

	public void setResponderProfilePic(String responderProfilePic) {
		this.responderProfilePic = responderProfilePic;
	}

}
