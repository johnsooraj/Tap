package com.cyspan.tap.poll.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.cyspan.tap.user.model.UsersModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The persistent class for the polls database table.
 * 
 * @author sumesh | Integretz LLC
 * 
 * @author sumesh | Integretz LLC
 * 
 */
@Entity
@Table(name = "polls")
@JsonInclude(Include.NON_NULL)
public class PollModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "poll_id", unique = true, length = 45)
	private int pollId;

	@Transient
	private int createdUser;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = true, updatable = false)
	private Date createdDate;

	@Column(nullable = false)
	private byte displayCreator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date expiresOn;

	@Column
	private int groupId;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastAnsweredDate;

	@Column(nullable = false)
	private int participantCount;

	@Column(nullable = false)
	private int pollOptionType;

	@Column(length = 10)
	private String pollOrder;

	@Column(nullable = false)
	private int pollSeriesId;

	@Column(nullable = false)
	private byte pollStatus;

	@Column(nullable = false, name = "PollType")
	private int pollType;

	@Column(length = 500)
	private String question;

	@Column(length = 50)
	private String resultDisplayTo;

	private byte resultDisplayType;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ImageOptionModel> imageoptions;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<MultipleOptionModel> multipleoptions;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PollCommentModel> pollcomments;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<RatingOptionModel> ratingoptions;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ThisorthatOptionModel> thisorthatoptions;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PollLikeModel> polllikes;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PollInterestModel> pollInterests;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PollGroupModel> pollGroupModels;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "createdBy")
	private UsersModel usersModel;

	// New Fields For RSVP Polls

	private String eventName;

	private String location;

	private String description;

	@Transient
	private byte[] rsvpImage;

	private String rsvpImageUrl;

	private Date eventWhen;

	private Date startsOn;

	private Date deleteDate;

	private boolean deleteStatus;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<RsvpResponseModel> rsvpPollResponseList;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<RsvpUserModel> rsvpUsersList = new HashSet<RsvpUserModel>();

	@Transient
	private String username;

	@Transient
	private String profilePic;

	@Transient
	private int pollLikeCount;

	@Transient
	private int pollCommentCount;

	@Transient
	private int pollShareCount;

	@Transient
	private Long[] rsvpUserIds;

	@Transient
	private Long[] rsvpGroupIds;

	public PollModel() {

	}

	public PollModel(int pollId) {
		super();
		this.pollId = pollId;
	}

	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	/*
	 * public int getCreatedBy() { return createdBy; }
	 * 
	 * public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public byte getDisplayCreator() {
		return displayCreator;
	}

	public void setDisplayCreator(byte displayCreator) {
		this.displayCreator = displayCreator;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Date getLastAnsweredDate() {
		return lastAnsweredDate;
	}

	public void setLastAnsweredDate(Date lastAnsweredDate) {
		this.lastAnsweredDate = lastAnsweredDate;
	}

	public int getParticipantCount() {
		return participantCount;
	}

	public void setParticipantCount(int participantCount) {
		this.participantCount = participantCount;
	}

	public int getPollOptionType() {
		return pollOptionType;
	}

	public void setPollOptionType(int pollOptionType) {
		this.pollOptionType = pollOptionType;
	}

	public String getPollOrder() {
		return pollOrder;
	}

	public void setPollOrder(String pollOrder) {
		this.pollOrder = pollOrder;
	}

	public int getPollSeriesId() {
		return pollSeriesId;
	}

	public void setPollSeriesId(int pollSeriesId) {
		this.pollSeriesId = pollSeriesId;
	}

	public byte getPollStatus() {
		return pollStatus;
	}

	public void setPollStatus(byte pollStatus) {
		this.pollStatus = pollStatus;
	}

	public int getPollType() {
		return pollType;
	}

	public void setPollType(int pollType) {
		this.pollType = pollType;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getResultDisplayTo() {
		return resultDisplayTo;
	}

	public void setResultDisplayTo(String resultDisplayTo) {
		this.resultDisplayTo = resultDisplayTo;
	}

	public byte getResultDisplayType() {
		return resultDisplayType;
	}

	public void setResultDisplayType(byte resultDisplayType) {
		this.resultDisplayType = resultDisplayType;
	}

	public List<ImageOptionModel> getImageoptions() {
		return imageoptions;
	}

	public void setImageoptions(List<ImageOptionModel> imageoptions) {
		this.imageoptions = imageoptions;
	}

	public List<MultipleOptionModel> getMultipleoptions() {
		return multipleoptions;
	}

	public void setMultipleoptions(List<MultipleOptionModel> multipleoptions) {
		this.multipleoptions = multipleoptions;
	}

	public List<PollCommentModel> getPollcomments() {
		return pollcomments;
	}

	public void setPollcomments(List<PollCommentModel> pollcomments) {
		this.pollcomments = pollcomments;
	}

	public List<RatingOptionModel> getRatingoptions() {
		return ratingoptions;
	}

	public void setRatingoptions(List<RatingOptionModel> ratingoptions) {
		this.ratingoptions = ratingoptions;
	}

	public List<ThisorthatOptionModel> getThisorthatoptions() {
		return thisorthatoptions;
	}

	public void setThisorthatoptions(List<ThisorthatOptionModel> thisorthatoptions) {
		this.thisorthatoptions = thisorthatoptions;
	}

	public List<PollLikeModel> getPolllikes() {
		return polllikes;
	}

	public void setPolllikes(List<PollLikeModel> polllikes) {
		this.polllikes = polllikes;
	}

	/*
	 * public RsvpPollModel getRsvpPollModel() { return rsvpPollModel; }
	 * 
	 * public void setRsvpPollModel(RsvpPollModel rsvpPollModel) {
	 * this.rsvpPollModel = rsvpPollModel; }
	 */

	public List<PollGroupModel> getPollGroupModels() {
		return pollGroupModels;
	}

	public void setPollGroupModels(List<PollGroupModel> pollGroupModels) {
		this.pollGroupModels = pollGroupModels;
	}

	public UsersModel getUsersModel() {
		return usersModel;
	}

	public void setUsersModel(UsersModel usersModel) {
		this.usersModel = usersModel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public int getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(int createdUser) {
		this.createdUser = createdUser;
	}

	public int getPollLikeCount() {
		return pollLikeCount;
	}

	public void setPollLikeCount(int pollLikeCount) {
		this.pollLikeCount = pollLikeCount;
	}

	public int getPollCommentCount() {
		return pollCommentCount;
	}

	public void setPollCommentCount(int pollCommentCount) {
		this.pollCommentCount = pollCommentCount;
	}

	public List<PollInterestModel> getPollInterests() {
		return pollInterests;
	}

	public void setPollInterests(List<PollInterestModel> pollInterests) {
		this.pollInterests = pollInterests;
	}

	public int getPollShareCount() {
		return pollShareCount;
	}

	public void setPollShareCount(int pollShareCount) {
		this.pollShareCount = pollShareCount;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getRsvpImage() {
		return rsvpImage;
	}

	public void setRsvpImage(byte[] rsvpImage) {
		this.rsvpImage = rsvpImage;
	}

	public String getRsvpImageUrl() {
		return rsvpImageUrl;
	}

	public void setRsvpImageUrl(String rsvpImageUrl) {
		this.rsvpImageUrl = rsvpImageUrl;
	}

	public List<RsvpResponseModel> getRsvpPollResponseList() {
		return rsvpPollResponseList;
	}

	public void setRsvpPollResponseList(List<RsvpResponseModel> rsvpPollResponseList) {
		this.rsvpPollResponseList = rsvpPollResponseList;
	}

	public Date getEventWhen() {
		return eventWhen;
	}

	public void setEventWhen(Date eventWhen) {
		this.eventWhen = eventWhen;
	}

	public Date getStartsOn() {
		return startsOn;
	}

	public void setStartsOn(Date startOn) {
		this.startsOn = startOn;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public boolean isDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Long[] getRsvpUserIds() {
		return rsvpUserIds;
	}

	public void setRsvpUserIds(Long[] rsvpUserIds) {
		this.rsvpUserIds = rsvpUserIds;
	}

	public Long[] getRsvpGroupIds() {
		return rsvpGroupIds;
	}

	public void setRsvpGroupIds(Long[] rsvpGroupIds) {
		this.rsvpGroupIds = rsvpGroupIds;
	}

	public Set<RsvpUserModel> getRsvpUsersList() {
		return rsvpUsersList;
	}

	public void setRsvpUsersList(Set<RsvpUserModel> rsvpUsersList) {
		this.rsvpUsersList = rsvpUsersList;
	}

	@Override
	public String toString() {
		return "PollModel [pollId=" + pollId + ", createdUser=" + createdUser + ", createdDate=" + createdDate
				+ ", displayCreator=" + displayCreator + ", expiresOn=" + expiresOn + ", groupId=" + groupId
				+ ", lastAnsweredDate=" + lastAnsweredDate + ", participantCount=" + participantCount
				+ ", pollOptionType=" + pollOptionType + ", pollOrder=" + pollOrder + ", pollSeriesId=" + pollSeriesId
				+ ", pollStatus=" + pollStatus + ", pollType=" + pollType + ", question=" + question + ", eventName="
				+ eventName + ", rsvpImage=" + Arrays.toString(rsvpImage) + ", rsvpImageUrl=" + rsvpImageUrl
				+ ", eventWhen=" + eventWhen + ", startsOn=" + startsOn + ", deleteDate=" + deleteDate
				+ ", deleteStatus=" + deleteStatus + "]";
	}

}