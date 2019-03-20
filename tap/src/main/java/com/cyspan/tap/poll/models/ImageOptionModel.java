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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the imageoptions database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "imageoptions")
public class ImageOptionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_options_id", unique = true)
	private int imageOptionsId;

	@Column(nullable = false, length = 150)
	private String caption;

	@Column(nullable = false, length = 250)
	private String image_url;

	@Column(nullable = false)
	private int responsCount;

	@Transient
	private byte[] imageByte;

	// bi-directional many-to-one association to PollModel

	@JsonBackReference("imageoptions")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "poll_id")
	private PollModel poll;

	// @LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "imageoption", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ImageOptionsResponseModel> imageoptionsresponses;

	public ImageOptionModel() {
	}

	public int getImageOptionsId() {
		return imageOptionsId;
	}

	public void setImageOptionsId(int imageOptionsId) {
		this.imageOptionsId = imageOptionsId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getResponsCount() {
		return responsCount;
	}

	public void setResponsCount(int responsCount) {
		this.responsCount = responsCount;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}

	public PollModel getPoll() {
		return poll;
	}

	public void setPoll(PollModel poll) {
		this.poll = poll;
	}

	public List<ImageOptionsResponseModel> getImageoptionsresponses() {
		return imageoptionsresponses;
	}

	public void setImageoptionsresponses(List<ImageOptionsResponseModel> imageoptionsresponses) {
		this.imageoptionsresponses = imageoptionsresponses;
	}

}