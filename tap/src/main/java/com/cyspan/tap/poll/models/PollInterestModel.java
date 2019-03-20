package com.cyspan.tap.poll.models;

import java.io.Serializable;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "pollinterests")
public class PollInterestModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PollInterestId", unique = true, nullable = false)
	private int PollInterestId;

	@Column(name = "InterestId", nullable = false)
	private int interestId;

	@JsonBackReference("pollInterests")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "PollId")
	private PollModel poll;

	@Transient
	int pollId;

	public PollInterestModel() {

	}

	public int getPollInterestId() {
		return PollInterestId;
	}

	public void setPollInterestId(int pollInterestId) {
		PollInterestId = pollInterestId;
	}

	public int getInterestId() {
		return interestId;
	}

	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}

	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

}
