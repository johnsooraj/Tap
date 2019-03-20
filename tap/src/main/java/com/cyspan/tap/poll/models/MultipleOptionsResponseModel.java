package com.cyspan.tap.poll.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the multipleoptionsresponse database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "multipleoptionsresponse")
public class MultipleOptionsResponseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, name = "mul_option_response_id")
	private int multipleOptionResponseId;

	@Column(nullable = false, name = "mul_read")
	private int read;

	@Column(nullable = false)
	private int userId;

	@Transient
	private int optionId;

	// bi-directional many-to-one association to MultipleOptionModel
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "m_poll_options_id")
	private MultipleOptionModel multipleoption;

	@Column(nullable = false)
	private int groupId;

	@Transient
	private String userName;

	@Transient
	private String profileUrl;

	public MultipleOptionsResponseModel() {
	}

	public int getMultipleOptionResponseId() {
		return multipleOptionResponseId;
	}

	public void setMultipleOptionResponseId(int multipleOptionResponseId) {
		this.multipleOptionResponseId = multipleOptionResponseId;
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

	public MultipleOptionModel getMultipleoption() {
		return multipleoption;
	}

	public void setMultipleoption(MultipleOptionModel multipleoption) {
		this.multipleoption = multipleoption;
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