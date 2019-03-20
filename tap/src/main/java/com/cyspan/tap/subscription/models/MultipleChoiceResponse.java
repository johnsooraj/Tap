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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "responderId", "choiceOptions_choiceId" }))
public class MultipleChoiceResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long choiceId;
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
	private MultipleChoiceOptions choiceOptions;

	public MultipleChoiceResponse() {

	}

	public Long getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
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

	public MultipleChoiceOptions getChoiceOptions() {
		return choiceOptions;
	}

	public void setChoiceOptions(MultipleChoiceOptions choiceOptions) {
		this.choiceOptions = choiceOptions;
	}

	public String getResponderProfilePic() {
		return responderProfilePic;
	}

	public void setResponderProfilePic(String responderProfilePic) {
		this.responderProfilePic = responderProfilePic;
	}

}
