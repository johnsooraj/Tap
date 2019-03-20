package com.cyspan.tap.rsvppoll.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="rsvp_group_polls")
public class RsvpPollGroupModel implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rsvp_group_poll_id", unique = true)
	private int rsvpGroupPollId;
	
	@Transient
	private RsvpPollModel rsvpPollModel;
	
	@Column(name="group_id")
	private int groupId;
	
	public RsvpPollGroupModel(){
		
	}

	public int getRsvpGroupPollId() {
		return rsvpGroupPollId;
	}

	public void setRsvpGroupPollId(int rsvpGroupPollId) {
		this.rsvpGroupPollId = rsvpGroupPollId;
	}

	public RsvpPollModel getRsvpPollModel() {
		return rsvpPollModel;
	}

	public void setRsvpPollModel(RsvpPollModel rsvpPollModel) {
		this.rsvpPollModel = rsvpPollModel;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	
}
