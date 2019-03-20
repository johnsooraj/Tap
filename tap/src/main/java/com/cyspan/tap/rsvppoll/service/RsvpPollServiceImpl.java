package com.cyspan.tap.rsvppoll.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyspan.tap.commons.FileUploadService;
import com.cyspan.tap.poll.models.RsvpResponseModel;
import com.cyspan.tap.rsvppoll.dao.RSVPPollDao;
import com.cyspan.tap.rsvppoll.model.RsvpPollGroupModel;
import com.cyspan.tap.rsvppoll.model.RsvpPollModel;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UsersModel;

@Service
public class RsvpPollServiceImpl implements RsvpPollService {

	@Autowired
	RSVPPollDao rsvpPollDao;

	@Autowired
	HttpServletRequest request;

	@Autowired
	UsersDao userDao;

	@Autowired
	FileUploadService uploadImage;

	@Override
	public Boolean save(RsvpPollModel pollModel) {
		// TODO Auto-generated method stub

		UsersModel usersModel = userDao.getUserModelById(pollModel.getCreatedUser());
		// usersModel.setUserId();
		pollModel.setUsersModel(usersModel);

		if (pollModel.getImageurl() == null || pollModel.getImageurl().equalsIgnoreCase("")) {
			if (pollModel.getImage() != null) {
				String imageOptionUrl = uploadImage.uploadPictureBig(pollModel.getImage(), "imageOption");
				pollModel.setImageurl(imageOptionUrl);

			}
		}

		if (pollModel.getRsvpPollResponseList() != null) {
			List<RsvpResponseModel> responseModels = pollModel.getRsvpPollResponseList();
			for (RsvpResponseModel rsvpResponseModel : responseModels) {
				//rsvpResponseModel.setRsvpPollModel(pollModel);
			}

		}

		if (pollModel.getRsvpPollGroupModels() != null) {
			List<RsvpPollGroupModel> rsvpPollGroupModels = pollModel.getRsvpPollGroupModels();
			for (RsvpPollGroupModel rsGroupModel : rsvpPollGroupModels) {
				rsGroupModel.setRsvpPollModel(pollModel);
			}

		}

		return rsvpPollDao.save(pollModel);
	}

	@Override
	public List<RsvpPollModel> getRsvpPollModelList() {
		// TODO Auto-generated method stub

		for (RsvpPollModel model : rsvpPollDao.getRsvpPollModelList()) {

			model.setUsername(model.getUsersModel().getFirstName());
			model.setProfilePic(model.getUsersModel().getProfilePic());

		}

		return rsvpPollDao.getRsvpPollModelList();
	}

	@Override
	public RsvpPollModel getRsvpPollById(int rsvpPollId) {
		// TODO Auto-generated method stub

		RsvpPollModel rsvpPollModel = rsvpPollDao.getRsvpPollById(rsvpPollId);

		rsvpPollModel.setCreatedUser(rsvpPollModel.getUsersModel().getUserId());
		rsvpPollModel.setProfilePic(rsvpPollModel.getUsersModel().getProfilePic20());
		rsvpPollModel.setUsername(rsvpPollModel.getUsersModel().getFirstName());

		return rsvpPollModel;
	}

	@Override
	public Boolean saveRsvpPollResponse(RsvpResponseModel rsvpResponseModel) {
		// TODO Auto-generated method stub

		//RsvpPollModel rsvpPollModel = rsvpPollDao.getRsvpPollById(rsvpResponseModel.getRsvpPollId());

		//rsvpResponseModel.setRsvpPollModel(rsvpPollModel);
		return rsvpPollDao.saveRsvpPollResponse(rsvpResponseModel);
	}

}
