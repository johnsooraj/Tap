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

@Entity
public class MultipleChoiceOptions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long choiceId;
	private String optionText;
	private Long createrId;

	@Transient
	private String createUserName;

	@Transient
	private String createProfilePic;

	@UpdateTimestamp
	private Timestamp timestamp;

	@CreationTimestamp
	private Timestamp createDate;
	private byte status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "choiceOptions")
	Set<MultipleChoiceResponse> responses = new HashSet<>();

	@JsonIgnore
	@ManyToOne
	private SubscriptionFeedback feedback;

	@JsonIgnore
	@ManyToOne
	private SubscriptionPoll poll;

	public MultipleChoiceOptions(Long choiceId) {
		super();
		this.choiceId = choiceId;
	}

	public Set<MultipleChoiceResponse> getResponses() {
		return responses;
	}

	public void setResponses(Set<MultipleChoiceResponse> responses) {
		this.responses = responses;
	}

	public MultipleChoiceOptions() {

	}

	public MultipleChoiceOptions(String optionText) {
		this.optionText = optionText;
	}

	public Long getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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

	public SubscriptionPoll getPoll() {
		return poll;
	}

	public void setPoll(SubscriptionPoll poll) {
		this.poll = poll;
	}

	public String getCreateProfilePic() {
		return createProfilePic;
	}

	public void setCreateProfilePic(String createProfilePic) {
		this.createProfilePic = createProfilePic;
	}

}
