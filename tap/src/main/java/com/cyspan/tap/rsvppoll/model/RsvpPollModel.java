package com.cyspan.tap.rsvppoll.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import com.cyspan.tap.poll.models.RsvpResponseModel;
import com.cyspan.tap.user.model.UsersModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="rsvp_polls")
public class RsvpPollModel implements Serializable {
private static final long serialVersionUID = 1L;
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name ="rsvp_poll_id")
private int rsvpId;

@Column(name ="event_name")
private String eventName;

@Column(name ="location")
private String location;

@Transient 
private byte[] image;

@Column(name ="event_photo")
private String imageurl;

@Temporal(TemporalType.TIMESTAMP)
@Column(name ="start_date_time")
private Date addDate;

@Temporal(TemporalType.TIMESTAMP)
@Column(name ="end_date_time")
private Date endTime;

@CreationTimestamp
@Temporal(TemporalType.TIMESTAMP)
@Column(name ="created_date")
private Date createdDate;

@Column(name ="description")
private String description;


@Column(name="poll_type")
private int pollType;


@Transient
private int createdUser;

@Transient
private List<RsvpResponseModel> rsvpPollResponseList;

@Transient
private List<RsvpPollGroupModel> rsvpPollGroupModels;

@JsonIgnore
@OneToOne
@JoinColumn(name="created_by")
private UsersModel usersModel;


@Transient
private String username;


@Transient
private String profilePic;

public RsvpPollModel(){
	
}

public int getRsvpId() {
	return rsvpId;
}

public void setRsvpId(int rsvpId) {
	this.rsvpId = rsvpId;
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

public byte[] getImage() {
	return image;
}

public void setImage(byte[] image) {
	this.image = image;
}

public String getImageurl() {
	return imageurl;
}

public void setImageurl(String imageurl) {
	this.imageurl = imageurl;
}

public Date getAddDate() {
	return addDate;
}

public void setAddDate(Date addDate) {
	this.addDate = addDate;
}

public Date getEndTime() {
	return endTime;
}

public void setEndTime(Date endTime) {
	this.endTime = endTime;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}



public int getPollType() {
	return pollType;
}

public void setPollType(int pollType) {
	this.pollType = pollType;
}

public List<RsvpResponseModel> getRsvpPollResponseList() {
	return rsvpPollResponseList;
}

public void setRsvpPollResponseList(List<RsvpResponseModel> rsvpPollResponseList) {
	this.rsvpPollResponseList = rsvpPollResponseList;
}



public List<RsvpPollGroupModel> getRsvpPollGroupModels() {
	return rsvpPollGroupModels;
}

public void setRsvpPollGroupModels(List<RsvpPollGroupModel> rsvpPollGroupModels) {
	this.rsvpPollGroupModels = rsvpPollGroupModels;
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

public Date getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}


}
