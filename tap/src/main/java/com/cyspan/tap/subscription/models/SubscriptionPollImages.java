package com.cyspan.tap.subscription.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class SubscriptionPollImages {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pollImageId;
	private String imageURL;

	@UpdateTimestamp
	private Timestamp timestamp;

	@CreationTimestamp
	private Timestamp createDate;
	private byte status;
	private Long createdBy;

	@Transient
	private String createdByUserName;

	@Transient
	private String createdUserProfilePic;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private SubscriptionPoll poll;

	@JsonIgnoreProperties("pollImage")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pollImage")
	Set<SubscriptionImageOptionResponse> imageRespons = new HashSet<>();

	@Transient
	private byte[] imageByte;

	public SubscriptionPollImages() {
		super();
	}

	public SubscriptionPollImages(Long pollImageId) {
		super();
		this.pollImageId = pollImageId;
	}

	public Long getPollImageId() {
		return pollImageId;
	}

	public void setPollImageId(Long pollImageId) {
		this.pollImageId = pollImageId;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}

	public SubscriptionPoll getPoll() {
		return poll;
	}

	public void setPoll(SubscriptionPoll poll) {
		this.poll = poll;
	}

	public Set<SubscriptionImageOptionResponse> getImageRespons() {
		return imageRespons;
	}

	public void setImageRespons(Set<SubscriptionImageOptionResponse> imageRespons) {
		this.imageRespons = imageRespons;
	}

	public String getCreatedByUserName() {
		return createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	public String getCreatedUserProfilePic() {
		return createdUserProfilePic;
	}

	public void setCreatedUserProfilePic(String createdUserProfilePic) {
		this.createdUserProfilePic = createdUserProfilePic;
	}

}
