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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SubscriptionFeedbackGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String feedbackFormName;
	private byte status;
	private Long organizationId;
	private Timestamp exprieDate;

	@UpdateTimestamp
	private Timestamp timestamp;

	@CreationTimestamp
	private Timestamp createDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private OrganizationModel organization;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "feedbackGroup", cascade = CascadeType.ALL)
	private Set<SubscriptionFeedback> feedbacks = new HashSet<>();

	public SubscriptionFeedbackGroup() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFeedbackFormName() {
		return feedbackFormName;
	}

	public void setFeedbackFormName(String feedbackFormName) {
		this.feedbackFormName = feedbackFormName;
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

	public Set<SubscriptionFeedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<SubscriptionFeedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public Timestamp getExprieDate() {
		return exprieDate;
	}

	public void setExprieDate(Timestamp exprieDate) {
		this.exprieDate = exprieDate;
	}

}
