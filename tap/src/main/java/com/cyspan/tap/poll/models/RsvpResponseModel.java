package com.cyspan.tap.poll.models;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "rsvpresponse")
public class RsvpResponseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rsvpResponseId;

	private int rsvpResponse;

	@Column(name = "user_id")
	private int userId;

	private int groupId;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = true, updatable = false)
	private Date createdDate;

	@JsonBackReference("rsvpPollResponseList")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "poll_id", nullable = false)
	private PollModel poll;

	@Transient
	private String userName;

	@Transient
	private String profilePic;

	public RsvpResponseModel() {

	}

	public Long getRsvpResponseId() {
		return rsvpResponseId;
	}

	public void setRsvpResponseId(Long rsvpResponseId) {
		this.rsvpResponseId = rsvpResponseId;
	}

	public int getRsvpResponse() {
		return rsvpResponse;
	}

	public void setRsvpResponse(int rsvpResponse) {
		this.rsvpResponse = rsvpResponse;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	public String toString() {
		return "RsvpResponseModel [rsvpResponseId=" + rsvpResponseId + ", rsvpResponse=" + rsvpResponse + ", userId="
				+ userId + ", createdDate=" + createdDate + ", poll=" + poll + "]";
	}

}
