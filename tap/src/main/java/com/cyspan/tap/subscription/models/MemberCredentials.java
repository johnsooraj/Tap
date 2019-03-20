package com.cyspan.tap.subscription.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "username", "password" }))
public class MemberCredentials {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long credentialsId;

	private String username;
	private String password;

	@UpdateTimestamp
	private Date timestamp;

	@CreationTimestamp
	private Date createDateTime;

	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "credentials")
	private OrganizationMember member;

	public MemberCredentials() {

	}

	public Long getCredentialsId() {
		return credentialsId;
	}

	public void setCredentialsId(Long credentialsId) {
		this.credentialsId = credentialsId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public OrganizationMember getMember() {
		return member;
	}

	public void setMember(OrganizationMember member) {
		this.member = member;
	}

	@Override
	public String toString() {
		return "MemberCredentials [credentialsId=" + credentialsId + ", username=" + username + ", password=" + password
				+ ", timestamp=" + timestamp + ", createDateTime=" + createDateTime + ", member=" + member + "]";
	}

}
