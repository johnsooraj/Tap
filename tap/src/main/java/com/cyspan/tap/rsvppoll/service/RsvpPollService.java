package com.cyspan.tap.rsvppoll.service;

import java.util.List;

import com.cyspan.tap.poll.models.RsvpResponseModel;
import com.cyspan.tap.rsvppoll.model.RsvpPollModel;

public interface RsvpPollService {
	
	public Boolean save (RsvpPollModel pollModel);	
	public List<RsvpPollModel> getRsvpPollModelList();
	public RsvpPollModel getRsvpPollById(int rsvpPollId);
	public Boolean saveRsvpPollResponse(RsvpResponseModel rsvpResponseModel);
	
	
}
