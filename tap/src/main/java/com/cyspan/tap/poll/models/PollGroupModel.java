package com.cyspan.tap.poll.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import com.cyspan.tap.user.model.UsersModel;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "group_polls", uniqueConstraints = @UniqueConstraint(columnNames = { "poll_id", "group_id" }))
public class PollGroupModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_poll_id", unique = true)
	private int groupPollId;

	@JsonBackReference("pollGroupModels")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "poll_id")
	private PollModel poll;

	@Column(name = "group_id")
	private int groupId;

	@CreationTimestamp
	private Date createdDate;

	@OneToOne
	private UsersModel sharedBy;

	private boolean deleteStatus;

	private Timestamp deleteDate;

	public PollGroupModel() {
	}

	public boolean isDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteDate = Timestamp.valueOf(LocalDateTime.now());
		this.deleteStatus = deleteStatus;
	}

	public Timestamp getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Timestamp deleteDate) {
		this.deleteDate = deleteDate;
	}

	public int getGroupPollId() {
		return groupPollId;
	}

	public void setGroupPollId(int groupPollId) {
		this.groupPollId = groupPollId;
	}

	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UsersModel getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(UsersModel sharedBy) {
		this.sharedBy = sharedBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + groupId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollGroupModel other = (PollGroupModel) obj;
		if (groupId != other.groupId)
			return false;
		return true;
	}

}
