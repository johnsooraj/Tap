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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The persistent class for the ratingoptions database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "ratingoptions")
public class RatingOptionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rating_option_id", unique = true, nullable = false)
	private int ratingOptionId;

	@Column(nullable = false)
	private int rating;

	@Column(nullable = false)
	private int responseCount;

	// bi-directional many-to-one association to PollModel
	@JsonBackReference("ratingoptions")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "poll_id", nullable = false)
	private PollModel poll;

	// @LazyCollection(LazyCollectionOption.FALSE)
	@JsonInclude(Include.NON_NULL)
	@OneToMany(mappedBy = "ratingoption", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RatingOptionsResponseModel> ratingoptionsresponses;

	public RatingOptionModel() {
	}

	public int getRatingOptionId() {
		return ratingOptionId;
	}

	public void setRatingOptionId(int ratingOptionId) {
		this.ratingOptionId = ratingOptionId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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

	public List<RatingOptionsResponseModel> getRatingoptionsresponses() {
		return ratingoptionsresponses;
	}

	public void setRatingoptionsresponses(List<RatingOptionsResponseModel> ratingoptionsresponses) {
		this.ratingoptionsresponses = ratingoptionsresponses;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}