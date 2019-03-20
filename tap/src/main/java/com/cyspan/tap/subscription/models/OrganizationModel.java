package com.cyspan.tap.subscription.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cyspan.tap.user.model.UserAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class OrganizationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long organizationId;

	private String organizationName;
	private String coverPhoto;
	private String profilePhoto;
	private String email;
	private String authorityName;

	@UpdateTimestamp
	private Date timestamp;

	@CreationTimestamp
	private Date createDateTime;

	@OneToOne(cascade = CascadeType.ALL)
	private UserAddress organizationAddress;

	@JsonIgnoreProperties("organization")
	@OrderBy("createDateTime DESC")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organization")
	private Set<OrganizationMember> members = new HashSet<>();

	@JsonIgnoreProperties("organization")
	@OrderBy("createDate DESC")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organization")
	private Set<SubscriptionNotice> notices = new HashSet<>();

	@JsonIgnoreProperties("organization")
	@OrderBy("createDate DESC")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organization")
	private Set<SubscriptionPoll> polls = new HashSet<>();

	@JsonIgnoreProperties("organization")
	@OrderBy("createDate DESC")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organization")
	private Set<SubscriptionFeedback> feedbacks = new HashSet<>();

	@JsonIgnoreProperties("organization")
	@OrderBy("createDate DESC")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organization")
	private Set<SubscriptionFeedbackGroup> feedbackGroups = new HashSet<>();

	@Transient
	private byte coverPhotoByte[];

	@Transient
	private byte profilePhotoByte[];

	public OrganizationModel() {

	}

	public OrganizationModel(Long organizationId) {
		super();
		this.organizationId = organizationId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Set<OrganizationMember> getMembers() {
		return members;
	}

	public void setMembers(Set<OrganizationMember> members) {
		this.members = members;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public UserAddress getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(UserAddress organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getCoverPhotoByte() {
		return coverPhotoByte;
	}

	public void setCoverPhotoByte(byte[] coverPhotoByte) {
		this.coverPhotoByte = coverPhotoByte;
	}

	public byte[] getProfilePhotoByte() {
		return profilePhotoByte;
	}

	public void setProfilePhotoByte(byte[] profilePhotoByte) {
		this.profilePhotoByte = profilePhotoByte;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public Set<SubscriptionNotice> getNotices() {
		return notices;
	}

	public void setNotices(Set<SubscriptionNotice> notices) {
		this.notices = notices;
	}

	public Set<SubscriptionPoll> getPolls() {
		return polls;
	}

	public void setPolls(Set<SubscriptionPoll> polls) {
		this.polls = polls;
	}

	public Set<SubscriptionFeedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<SubscriptionFeedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public Set<SubscriptionFeedbackGroup> getFeedbackGroups() {
		return feedbackGroups;
	}

	public void setFeedbackGroups(Set<SubscriptionFeedbackGroup> feedbackGroups) {
		this.feedbackGroups = feedbackGroups;
	}

}
