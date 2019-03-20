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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the multipleoptions database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "multipleoptions")
public class MultipleOptionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "m_poll_options_id")
	private int mPollOptionsId;

	@Column(nullable = false, length = 150)
	private String optionText;

	@Column(nullable = false)
	private int responseCount;

	@JsonBackReference("multipleoptions")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "poll_id", nullable = false)
	private PollModel poll;

	@OneToMany(mappedBy = "multipleoption", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<MultipleOptionsResponseModel> multipleoptionsresponses;

	public MultipleOptionModel() {
	}

	public int getmPollOptionsId() {
		return mPollOptionsId;
	}

	public void setmPollOptionsId(int mPollOptionsId) {
		this.mPollOptionsId = mPollOptionsId;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public int getResponseCount() {
		return responseCount;
	}

	public void setResponseCount(int responseCount) {
		this.responseCount = responseCount;
	}

	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public List<MultipleOptionsResponseModel> getMultipleoptionsresponses() {
		return multipleoptionsresponses;
	}

	public void setMultipleoptionsresponses(List<MultipleOptionsResponseModel> multipleoptionsresponses) {
		this.multipleoptionsresponses = multipleoptionsresponses;
	}

}