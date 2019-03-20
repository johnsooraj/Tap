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
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class SubscriptionFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long feedbackId;
	private String feedbackText;
	private Long feedbackCreaterId;
	private Timestamp exprieDate;

	@Transient
	private String feedbackCreaterName;

	@Transient
	private String feedbackCreaterProfilePic;

	@Transient
	private boolean respondStatus;

	private byte feedbackType;

	@UpdateTimestamp
	private Timestamp timestamp;

	@CreationTimestamp
	private Timestamp createDate;
	private byte status;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private OrganizationModel organization;

	@JsonIgnore
	@ManyToOne
	private SubscriptionFeedbackGroup feedbackGroup;

	@JsonIgnoreProperties("feedback")
	@OrderBy("createDate DESC")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "feedback", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<MultipleChoiceOptions> questions = new HashSet<>();

	@JsonIgnoreProperties("feedback")
	@OrderBy("createDate DESC")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "feedback", cascade = CascadeType.ALL)
	private Set<SubscriptionRating> ratings = new HashSet<>();

	@JsonIgnoreProperties("feedback")
	@OrderBy("createDate DESC")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "feedback", cascade = CascadeType.ALL)
	private Set<FeedbackFreeComment> freeComments = new HashSet<>();

	public SubscriptionFeedback() {

	}

	public SubscriptionFeedback(Long feedbackId) {
		super();
		this.feedbackId = feedbackId;
	}

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getFeedbackText() {
		return feedbackText;
	}

	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}

	public Long getFeedbackCreaterId() {
		return feedbackCreaterId;
	}

	public void setFeedbackCreaterId(Long feedbackCreaterId) {
		this.feedbackCreaterId = feedbackCreaterId;
	}

	public String getFeedbackCreaterName() {
		return feedbackCreaterName;
	}

	public void setFeedbackCreaterName(String feedbackCreaterName) {
		this.feedbackCreaterName = feedbackCreaterName;
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

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
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

	public Set<FeedbackFreeComment> getFreeComments() {
		return freeComments;
	}

	public void setFreeComments(Set<FeedbackFreeComment> freeComments) {
		this.freeComments = freeComments;
	}

	public byte getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(byte feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getFeedbackCreaterProfilePic() {
		return feedbackCreaterProfilePic;
	}

	public void setFeedbackCreaterProfilePic(String feedbackCreaterProfilePic) {
		this.feedbackCreaterProfilePic = feedbackCreaterProfilePic;
	}

	public SubscriptionFeedbackGroup getFeedbackGroup() {
		return feedbackGroup;
	}

	public void setFeedbackGroup(SubscriptionFeedbackGroup feedbackGroup) {
		this.feedbackGroup = feedbackGroup;
	}

	public boolean isRespondStatus() {
		return respondStatus;
	}

	public void setRespondStatus(boolean respondStatus) {
		this.respondStatus = respondStatus;
	}

	public Timestamp getExprieDate() {
		return exprieDate;
	}

	public void setExprieDate(Timestamp exprieDate) {
		this.exprieDate = exprieDate;
	}

}
