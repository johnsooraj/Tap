package com.cyspan.tap.poll.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the thisorthatoptionsresponse database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "thisorthatoptionsresponse")
public class ThisorthatOptionsResponseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_response_id", unique = true, nullable = false)
	private int thisOrthatOptionResponseId;

	@Column(nullable = false, name = "this_read")
	private int read;

	@Column(nullable = false)
	private int userId;

	@Transient
	private int optionId;

	// bi-directional many-to-one association to ThisorthatOptionModel
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_options_id", nullable = false)
	private ThisorthatOptionModel thisorthatoption;

	@Column(nullable = false)
	private int groupId;

	@Transient
	private String userName;

	@Transient
	private String profileUrl;

	public ThisorthatOptionsResponseModel() {
	}

	public int getThisOrthatOptionResponseId() {
		return thisOrthatOptionResponseId;
	}

	public void setThisOrthatOptionResponseId(int thisOrthatOptionResponseId) {
		this.thisOrthatOptionResponseId = thisOrthatOptionResponseId;
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

	public ThisorthatOptionModel getThisorthatoption() {
		return thisorthatoption;
	}

	public void setThisorthatoption(ThisorthatOptionModel thisorthatoption) {
		this.thisorthatoption = thisorthatoption;
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