package com.cyspan.tap.user.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "blockedId" }))
public class BlockedContacts {

	@Id
	@GeneratedValue
	private Long id;

	@UpdateTimestamp
	private Timestamp timestamp;

	@CreationTimestamp
	private Timestamp createDate;

	private Integer userId;

	private Integer blockedId;

	public BlockedContacts() {

	}

	public BlockedContacts(Integer userId) {
		super();
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBlockedId() {
		return blockedId;
	}

	public void setBlockedId(Integer blockedId) {
		this.blockedId = blockedId;
	}

}
