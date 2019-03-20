package com.cyspan.tap.poll.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the thisorthatoptions database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "thisorthatoptions")
public class ThisorthatOptionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_options_id", unique = true, nullable = false)
	private int thisOrThatOptionId;

	@Lob
	@Column(name = "option_text", nullable = false)
	private String optionText;

	/*
	 * @Column(nullable=false) private int userId;
	 */

	// bi-directional many-to-one association to PollModel
	@JsonBackReference("thisorthatoptions")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "poll_id", nullable = false)
	private PollModel poll;

	// @LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "thisorthatoption", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ThisorthatOptionsResponseModel> thisorthatoptionsresponses;

	public ThisorthatOptionModel() {
	}

	public int getThisOrThatOptionId() {
		return thisOrThatOptionId;
	}

	public void setThisOrThatOptionId(int thisOrThatOptionId) {
		this.thisOrThatOptionId = thisOrThatOptionId;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	/*
	 * public int getUserId() { return userId; }
	 * 
	 * public void setUserId(int userId) { this.userId = userId; }
	 */
	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public List<ThisorthatOptionsResponseModel> getThisorthatoptionsresponses() {
		return thisorthatoptionsresponses;
	}

	public void setThisorthatoptionsresponses(List<ThisorthatOptionsResponseModel> thisorthatoptionsresponses) {
		this.thisorthatoptionsresponses = thisorthatoptionsresponses;
	}

}