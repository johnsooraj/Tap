package com.cyspan.tap.subscription.models;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
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
public class SubscriptionNotice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long noticeId;
	private String noticeText;
	private String imagelink;
	private String referencelink;
	private String documentlink;
	private Timestamp exprieDate;

	@Column(length = 100000)
	private String description;

	@UpdateTimestamp
	private Timestamp timestamp;

	@CreationTimestamp
	private Timestamp createDate;
	private byte status;
	private Long createdBy;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private OrganizationModel organization;

	@Transient
	private Map<String, byte[]> attachmentByte = new HashMap<>();

	@Transient
	private String createdUserName;

	@Transient
	private String createUserProfilePic;

	public SubscriptionNotice() {

	}

	public SubscriptionNotice(String noticeText) {
		this.noticeText = noticeText;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeText() {
		return noticeText;
	}

	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}

	public String getImagelink() {
		return imagelink;
	}

	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

	public String getReferencelink() {
		return referencelink;
	}

	public void setReferencelink(String referencelink) {
		this.referencelink = referencelink;
	}

	public String getDocumentlink() {
		return documentlink;
	}

	public void setDocumentlink(String documentlink) {
		this.documentlink = documentlink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public Map<String, byte[]> getAttachmentByte() {
		return attachmentByte;
	}

	public void setAttachmentByte(Map<String, byte[]> attachmentByte) {
		this.attachmentByte = attachmentByte;
	}

	public String getCreateUserProfilePic() {
		return createUserProfilePic;
	}

	public void setCreateUserProfilePic(String createUserProfilePic) {
		this.createUserProfilePic = createUserProfilePic;
	}

	public Timestamp getExprieDate() {
		return exprieDate;
	}

	public void setExprieDate(Timestamp exprieDate) {
		this.exprieDate = exprieDate;
	}

}
