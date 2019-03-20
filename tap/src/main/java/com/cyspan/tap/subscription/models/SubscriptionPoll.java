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
public class SubscriptionPoll {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pollId;
	private String pollText;
	private Timestamp exprieDate;
	private boolean liveResult;
	private boolean colsePoll;
	private byte pollType;

	@UpdateTimestamp
	private Timestamp timestamp;

	@CreationTimestamp
	private Timestamp createDate;
	private byte status;
	private Long createdBy;

	@Transient
	private String createdUserName;

	@Transient
	private String createdUserProfilePic;

	@Transient
	private boolean respondStatus;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private OrganizationModel organization;

	@JsonIgnoreProperties("poll")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "poll", cascade = CascadeType.ALL)
	private Set<SubscriptionPollImages> pollImages = new HashSet<>();

	@JsonIgnoreProperties("poll")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "poll", cascade = CascadeType.ALL)
	private Set<MultipleChoiceOptions> questions = new HashSet<>();

	@JsonIgnoreProperties("poll")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "poll", cascade = CascadeType.ALL)
	private Set<SubscriptionRating> ratings = new HashSet<>();

	public SubscriptionPoll() {

	}

	public SubscriptionPoll(Long pollId) {
		super();
		this.pollId = pollId;
	}

	public Long getPollId() {
		return pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

	public String getPollText() {
		return pollText;
	}

	public void setPollText(String pollText) {
		this.pollText = pollText;
	}

	public Timestamp getExprieDate() {
		return exprieDate;
	}

	public void setExprieDate(Timestamp exprieDate) {
		this.exprieDate = exprieDate;
	}

	public boolean isLiveResult() {
		return liveResult;
	}

	public void setLiveResult(boolean liveResult) {
		this.liveResult = liveResult;
	}

	public boolean isColsePoll() {
		return colsePoll;
	}

	public void setColsePoll(boolean colsePoll) {
		this.colsePoll = colsePoll;
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

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public Set<SubscriptionPollImages> getPollImages() {
		return pollImages;
	}

	public void setPollImages(Set<SubscriptionPollImages> pollImages) {
		this.pollImages = pollImages;
	}

	public Set<MultipleChoiceOptions> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<MultipleChoiceOptions> questions) {
		this.questions = questions;
	}

	public Set<SubscriptionRating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<SubscriptionRating> ratings) {
		this.ratings = ratings;
	}

	public byte getPollType() {
		return pollType;
	}

	public void setPollType(byte pollType) {
		this.pollType = pollType;
	}

	public String getCreatedUserProfilePic() {
		return createdUserProfilePic;
	}

	public void setCreatedUserProfilePic(String createdUserProfilePic) {
		this.createdUserProfilePic = createdUserProfilePic;
	}

	public boolean isRespondStatus() {
		return respondStatus;
	}

	public void setRespondStatus(boolean respondStatus) {
		this.respondStatus = respondStatus;
	}

}
