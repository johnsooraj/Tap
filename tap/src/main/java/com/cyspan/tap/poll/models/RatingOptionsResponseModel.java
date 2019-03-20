package com.cyspan.tap.poll.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the ratingoptionsresponse database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "ratingoptionsresponse")
public class RatingOptionsResponseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, name = "rating_response_id")
	private int ratingOptionResponseId;

	@Column(nullable = false, name = "rating_read")
	private int read;

	@Column(nullable = false)
	private int userId;

	@Transient
	private int optionId;

	// bi-directional many-to-one association to RatingOptionModel
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "rating_option_id", nullable = false)
	private RatingOptionModel ratingoption;

	@Column(nullable = false)
	private int groupId;

	@Transient
	private String userName;

	@Transient
	private String profileUrl;

	public RatingOptionsResponseModel() {
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public RatingOptionModel getRatingoption() {
		return ratingoption;
	}

	public void setRatingoption(RatingOptionModel ratingoption) {
		this.ratingoption = ratingoption;
	}

	public int getRatingOptionResponseId() {
		return ratingOptionResponseId;
	}

	public void setRatingOptionResponseId(int ratingOptionResponseId) {
		this.ratingOptionResponseId = ratingOptionResponseId;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
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

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

}