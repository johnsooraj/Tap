package com.cyspan.tap.poll.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the imageoptionsresponse database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "imageoptionsresponse")
public class ImageOptionsResponseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, name = "image_option_response_id")
	private int imageOptionResponseId;

	@Column(nullable = false, name = "img_read")
	private int read;

	@Column(nullable = false)
	private int userId;

	@Transient
	private Integer optionId;

	// bi-directional many-to-one association to ImageOptionModel
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "image_options_id")
	private ImageOptionModel imageoption;

	@Column(nullable = false)
	private int groupId;

	@Transient
	private String userName;

	@Transient
	private String profileUrl;

	public ImageOptionsResponseModel() {
	}

	public int getImageOptionResponseId() {
		return imageOptionResponseId;
	}

	public void setImageOptionResponseId(int imageOptionResponseId) {
		this.imageOptionResponseId = imageOptionResponseId;
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

	public ImageOptionModel getImageoption() {
		return imageoption;
	}

	public void setImageoption(ImageOptionModel imageoption) {
		this.imageoption = imageoption;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
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