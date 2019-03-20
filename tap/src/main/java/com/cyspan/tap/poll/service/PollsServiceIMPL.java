package com.cyspan.tap.poll.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyspan.tap.asynchronous.TapAsynchronousTasks;
import com.cyspan.tap.commons.FileUploadService;
import com.cyspan.tap.commons.LattestPollModel;
import com.cyspan.tap.commons.PollResponseType;
import com.cyspan.tap.group.model.GroupsModel;
import com.cyspan.tap.group.services.GroupServices;
import com.cyspan.tap.notifications.GroupNotificationService;
import com.cyspan.tap.notifications.NotificationServiceDao;
import com.cyspan.tap.notifications.PollAsynNotification;
import com.cyspan.tap.poll.dao.PollsDAO;
import com.cyspan.tap.poll.models.ImageOptionModel;
import com.cyspan.tap.poll.models.ImageOptionsResponseModel;
import com.cyspan.tap.poll.models.MultipleOptionModel;
import com.cyspan.tap.poll.models.MultipleOptionsResponseModel;
import com.cyspan.tap.poll.models.PollCommentModel;
import com.cyspan.tap.poll.models.PollGroupModel;
import com.cyspan.tap.poll.models.PollInterestModel;
import com.cyspan.tap.poll.models.PollLikeModel;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.poll.models.RatingOptionModel;
import com.cyspan.tap.poll.models.RatingOptionsResponseModel;
import com.cyspan.tap.poll.models.ReportPoll;
import com.cyspan.tap.poll.models.RsvpResponseModel;
import com.cyspan.tap.poll.models.RsvpUserModel;
import com.cyspan.tap.poll.models.ThisorthatOptionModel;
import com.cyspan.tap.poll.models.ThisorthatOptionsResponseModel;
import com.cyspan.tap.rsvppoll.dao.RSVPPollDao;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.user.services.UserService;

/**
 * @author sumesh | Integretz LLC
 * 
 */
@Service
public class PollsServiceIMPL implements PollsService {

	@Autowired
	PollsDAO pollsDAO;

	@Autowired
	HttpServletRequest request;

	@Autowired
	RSVPPollDao rsvpPollDao;

	@Autowired
	UsersDao userDao;

	@Autowired
	FileUploadService uploadImage;

	@Autowired
	UserService userService;

	@Autowired
	GroupNotificationService groupNotificationService;

	@Autowired
	GroupServices groupService;

	@Autowired
	PollAsynNotification pollNotitifaction;

	@Autowired
	NotificationServiceDao notiDao;

	@Autowired
	TapAsynchronousTasks tapAsynchronousTasks;

	public static LocalDateTime lastPollLikeTime = null;

	static Logger logger = Logger.getLogger(PollsServiceIMPL.class.getName());

	/**
	*
	*/
	@Override
	@Transactional
	public PollModel savePoll(PollModel model) {

		// Case 1
		if (model.getPollGroupModels() != null && !model.getPollGroupModels().isEmpty()) {
			List<PollGroupModel> pollGroupModels = model.getPollGroupModels();
			for (PollGroupModel pollGroupModel : pollGroupModels) {
				pollGroupModel.setPoll(model);
				pollGroupModel.setSharedBy(model.getUsersModel());
			}
		} else {

			PollGroupModel pollGroupModel = new PollGroupModel();
			// rsvp
			if (model.getPollOptionType() == 5) {
				pollGroupModel.setGroupId(-1);
			} else {
				pollGroupModel.setGroupId(0);
			}
			pollGroupModel.setPoll(model);
			pollGroupModel.setSharedBy(model.getUsersModel());
			model.setPollGroupModels(Arrays.asList(pollGroupModel));
		}

		// Case 2
		if (model.getPollcomments() != null) {
			List<PollCommentModel> pollCommentModels = model.getPollcomments();
			for (PollCommentModel pollCommentModel : pollCommentModels) {
				pollCommentModel.setPoll(model);
			}
		}

		// Case 3
		if (model.getPollInterests() != null) {
			List<PollInterestModel> pollIntrestModels = model.getPollInterests();
			for (PollInterestModel pollIntrests : pollIntrestModels) {
				pollIntrests.setPoll(model);
			}
		}

		// Case 4.1
		if (model.getMultipleoptions() != null) {
			List<MultipleOptionModel> listMultiOptions = model.getMultipleoptions();
			for (MultipleOptionModel multipleOptionModel : listMultiOptions) {
				multipleOptionModel.setPoll(model);
			}
		}

		// Case 4.2
		if (model.getImageoptions() != null) {
			List<ImageOptionModel> listImages = model.getImageoptions();
			for (ImageOptionModel imageOptionModel : listImages) {
				if (imageOptionModel.getImage_url() == null || imageOptionModel.getImage_url().equalsIgnoreCase("")) {
					try {
						String imageOptionUrl = uploadImage.uploadPictureBig(imageOptionModel.getImageByte(),
								"imageOption");
						imageOptionModel.setImage_url(imageOptionUrl);
						imageOptionModel.setImageByte(null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				imageOptionModel.setPoll(model);
			}
		}

		// Case 4.3
		if (model.getRatingoptions() != null) {
			List<RatingOptionModel> listRating = model.getRatingoptions();
			for (RatingOptionModel ratingOptionModel : listRating) {
				ratingOptionModel.setPoll(model);
			}
		}

		// Case 4.4
		if (model.getThisorthatoptions() != null) {
			List<ThisorthatOptionModel> listRating = model.getThisorthatoptions();
			for (ThisorthatOptionModel thisorthatOptionModel : listRating) {
				thisorthatOptionModel.setPoll(model);
			}
		}

		// Case 5 RSVP Poll
		if (model.getPollOptionType() == 5) {
			if (model.getRsvpImage() != null) {
				String newUrl = uploadImage.uploadPictureBig(model.getRsvpImage(), "rsvpImages");
				model.setRsvpImageUrl(newUrl);
				model.setRsvpImage(null);
			}
			List<Object[]> rsvpUsersData = new ArrayList<>();
			Set<RsvpUserModel> rsvpMembers = new HashSet<>();

			if (model.getRsvpGroupIds() != null && model.getRsvpGroupIds().length > 0) {
				// fetch user id, profile pic, username from user table where group id match
				List<Object[]> groupuser = pollsDAO.fetchMinimalUserDataByGroupIds(model.getRsvpGroupIds());
				if (!groupuser.isEmpty()) {
					rsvpUsersData.addAll(groupuser);
				}
			}

			if (model.getRsvpUserIds() != null && model.getRsvpUserIds().length > 0) {
				// fetch user id, profile pic, username from user table where user id in []
				List<Object[]> userdata = pollsDAO.fetchMinimalUserDataByUserIds(model.getRsvpUserIds());
				if (!userdata.isEmpty()) {
					rsvpUsersData.addAll(userdata);
				}
			}

			if (!rsvpUsersData.isEmpty()) {
				for (Object[] obj : rsvpUsersData) {
					Integer userId = obj[0] != null ? (Integer) obj[0] : null;
					// String username = obj[1] != null ? (String) obj[1] : null;
					// String profilepic = obj[2] != null ? (String) obj[2] : null;
					String userToken = obj[3] != null ? (String) obj[3] : null;

					RsvpUserModel rsvpUser = new RsvpUserModel();
					rsvpUser.setPoll(model);
					rsvpUser.setUserId(Long.valueOf(userId));
					rsvpUser.setUserToken(userToken);
					rsvpMembers.add(rsvpUser);
				}
				model.setRsvpUsersList(rsvpMembers);
			}

		}

		PollModel poMo = pollsDAO.savePoll(model);
		if (model.getPollGroupModels() != null && !model.getPollGroupModels().isEmpty()) {
			List<PollGroupModel> pollGroupModels = model.getPollGroupModels();
			for (PollGroupModel pollGroupModel : pollGroupModels) {
				if (pollGroupModel.getGroupId() > 0) {
					GroupsModel groupsModel = groupService.fetchGroupByGroupId(pollGroupModel.getGroupId());
					pollNotitifaction.PollCreateToGroupMembers(poMo, groupsModel, model.getUsersModel().getFirstName());
					pollGroupModel.setPoll(model);
					pollGroupModel.setSharedBy(model.getUsersModel());
				}
			}
		}

		// check for objective words in poll text
		if (model.getQuestion() != null) {
			try {
				tapAsynchronousTasks.pollObjecjtionalWordsSearch(model);
			} catch (Exception e) {
				logger.error("error in FuzzyLogic");
				logger.error(e.getMessage());
			}
		}

		if (model.getPollOptionType() == 5) {
			pollNotitifaction.RsvpCreateNotification(poMo);
		}
		return poMo;
	}

	@Override
	@Transactional
	public List<PollModel> retrieveAllPoll() {
		for (PollModel model : pollsDAO.retrieveAllPoll()) {
			model.setPollCommentCount(pollsDAO.getPollCommentCount(model.getPollId()));
			model.setPollLikeCount(pollsDAO.getPollLikeCount(model.getPollId()));
			model.setUsername(model.getUsersModel().getFirstName());
			model.setProfilePic(model.getUsersModel().getProfilePic20());
			model.setCreatedUser(model.getUsersModel().getUserId());
		}
		return pollsDAO.retrieveAllPoll();
	}

	@Override
	public PollCommentModel savePollComment(PollCommentModel pollCommentModel) {
		if (pollCommentModel.getPollId() != null) {
			pollCommentModel.setPoll(pollsDAO.getPollByPollId(pollCommentModel.getPollId().intValue()));
		}
		if (pollCommentModel.getUsersModel() != null) {
			// already added at controller
		}
		PollCommentModel commentModel = pollsDAO.savePollComment(pollCommentModel);
		pollNotitifaction.PollCommentNotification(commentModel);
		return commentModel;
	}

	@Override
	public List<PollCommentModel> getPollCommentsByPollId(int pollId, int groupId) {
		List<PollCommentModel> commentModels = pollsDAO.getPollCommentsByPollId(pollId, groupId);
		commentModels.forEach(obj -> {
			UsersModel model = obj.getUsersModel();
			if (model != null) {
				obj.setUserName(model.getFirstName() + " " + model.getLastName());
				obj.setProfilePicUrl(model.getProfilePic());
			}
		});
		return commentModels;
	}

	@Override
	public PollLikeModel savePollLike(PollLikeModel pollLikeModel) throws ConstraintViolationException {
		PollModel pollModel = pollsDAO.getPollByPollId(pollLikeModel.getPollId().intValue());
		pollLikeModel.setPoll(pollModel);
		PollLikeModel likemodel = null;
		likemodel = pollsDAO.savePollLike(pollLikeModel);
		// send asyn notification
		pollNotitifaction.pollLikeNotification(pollLikeModel);
		return likemodel;
	}

	@Override
	public Boolean deleteLikeByGroupIdAndPollId(int groupId, int pollId, int userId) {
		Boolean boolean1 = pollsDAO.deleteLikeByGroupIdAndPollId(groupId, new PollModel(pollId),
				new UsersModel(userId));
		return boolean1.booleanValue();
	}

	@Override
	public List<PollLikeModel> getPollLikesByPollId(int pollId, int groupId) {
		List<PollLikeModel> pollLikes = pollsDAO.getPollLikesByPollId(pollId, groupId);
		if (!pollLikes.isEmpty()) {
			pollLikes.forEach(obj -> {
				obj.setUserName(obj.getUserName() + " " + obj.getLastName());
				obj.setProfilePic(obj.getProfilePic());
			});
			return pollLikes;
		} else {
			return pollLikes;
		}
	}

	@Override
	public List<PollModel> pollListByGroupId(int groupId) {
		return pollsDAO.pollListByGroupId(groupId);
	}

	@Override
	public PollModel getPollByPollId(int pollId) {

		PollModel pollModel = pollsDAO.getPollByPollId(pollId);
		if (pollModel != null) {
			pollModel.setCreatedUser(pollModel.getUsersModel().getUserId());
			pollModel.setUsername(pollModel.getUsersModel().getFirstName());
			pollModel.setProfilePic(pollModel.getUsersModel().getProfilePic());
		}
		return pollModel;
	}

	@Override
	public List<PollModel> lattestPolls(LattestPollModel lattestPollModel, boolean afterDate, boolean safeSerach,
			Integer userid) {

		int start = lattestPollModel.getPageNum() * lattestPollModel.getPageSize();
		int pageSize = lattestPollModel.getPageSize();

		List<PollModel> pollModels = null;

		if (lattestPollModel.getQuestion() == null || afterDate) {
			pollModels = pollsDAO.lattestPolls(start, pageSize, lattestPollModel, afterDate, safeSerach, userid);
		} else {
			pollModels = pollsDAO.lattestPollsByQuestion(start, pageSize, lattestPollModel, afterDate, safeSerach,
					userid);
		}

		for (PollModel model : pollModels) {

			int groupId = lattestPollModel.getGroupId();
			model.setPollCommentCount(pollsDAO.getPollCommentCount(model.getPollId()));
			model.setPollLikeCount(pollsDAO.getPollLikeCount(model.getPollId()));
			model.setPollShareCount(pollsDAO.getPollShareCount(model.getPollId(), groupId));
			model.setUsername(model.getUsersModel().getFirstName() + " " + model.getUsersModel().getLastName());
			model.setProfilePic(model.getUsersModel().getProfilePic());
			model.setCreatedUser(model.getUsersModel().getUserId());

			if (!model.getPollcomments().isEmpty()) {
				List<PollCommentModel> commentModels = new ArrayList<>();
				for (PollCommentModel model2 : model.getPollcomments()) {
					if (model2.getGroupId() == groupId) {
						if (model2.getUsersModel() != null) {
							model2.setUserName(
									model2.getUsersModel().getFirstName() + " " + model2.getUsersModel().getLastName());
							model2.setProfilePicUrl(model2.getUsersModel().getProfilePic());
							model2.setUserId(model2.getUsersModel().getUserId());
						}
						commentModels.add(model2);
					}
				}
				model.getPollcomments().clear();
				model.getPollcomments().addAll(commentModels);
			}

			if (!model.getPolllikes().isEmpty()) {
				List<PollLikeModel> likeModels = new ArrayList<>();
				for (PollLikeModel model2 : model.getPolllikes()) {
					if (model2.getGroupId() == groupId) {
						model2.setUserId(model2.getUsersModel().getUserId());
						model2.setUserName(model2.getUsersModel().getFirstName());
						likeModels.add(model2);
					}
				}
				model.getPolllikes().clear();
				model.getPolllikes().addAll(likeModels);
			}

			if (!model.getImageoptions().isEmpty()) {
				for (ImageOptionModel imageOptionModel : model.getImageoptions()) {
					List<ImageOptionsResponseModel> newImageOptionResponse = new ArrayList<>();
					if (!imageOptionModel.getImageoptionsresponses().isEmpty()) {
						for (ImageOptionsResponseModel optionsResponseModel : imageOptionModel
								.getImageoptionsresponses()) {
							if (optionsResponseModel.getGroupId() == groupId) {
								if (!pollsDAO.checkUserBlocked(userid, optionsResponseModel.getUserId())) {
									newImageOptionResponse.add(optionsResponseModel);
								}
							}
						}
					}
					imageOptionModel.getImageoptionsresponses().clear();
					imageOptionModel.setImageoptionsresponses(newImageOptionResponse);
				}

			}
			if (!model.getMultipleoptions().isEmpty()) {
				for (MultipleOptionModel multipleOptionModel : model.getMultipleoptions()) {
					List<MultipleOptionsResponseModel> newMultipleOptionResponse = new ArrayList<>();
					if (!multipleOptionModel.getMultipleoptionsresponses().isEmpty()) {
						for (MultipleOptionsResponseModel optionsResponseModel : multipleOptionModel
								.getMultipleoptionsresponses()) {
							if (optionsResponseModel.getGroupId() == groupId) {
								if (!pollsDAO.checkUserBlocked(userid, optionsResponseModel.getUserId())) {
									newMultipleOptionResponse.add(optionsResponseModel);
								}
							}
						}
					}
					multipleOptionModel.getMultipleoptionsresponses().clear();
					multipleOptionModel.setMultipleoptionsresponses(newMultipleOptionResponse);
				}

			}
			if (!model.getRatingoptions().isEmpty()) {
				for (RatingOptionModel ratingOptionModel : model.getRatingoptions()) {
					List<RatingOptionsResponseModel> newRatingOptionResponse = new ArrayList<>();
					if (!ratingOptionModel.getRatingoptionsresponses().isEmpty()) {
						for (RatingOptionsResponseModel optionsResponseModel : ratingOptionModel
								.getRatingoptionsresponses()) {
							if (optionsResponseModel.getGroupId() == groupId) {
								if (!pollsDAO.checkUserBlocked(userid, optionsResponseModel.getUserId())) {
									newRatingOptionResponse.add(optionsResponseModel);
								}
							}
						}
					}
					ratingOptionModel.getRatingoptionsresponses().clear();
					ratingOptionModel.setRatingoptionsresponses(newRatingOptionResponse);
				}

			}
			if (!model.getThisorthatoptions().isEmpty()) {
				for (ThisorthatOptionModel thisOrThatOptionModel : model.getThisorthatoptions()) {
					List<ThisorthatOptionsResponseModel> newThisOrThatOptionResponse = new ArrayList<>();
					if (!thisOrThatOptionModel.getThisorthatoptionsresponses().isEmpty()) {
						for (ThisorthatOptionsResponseModel optionsResponseModel : thisOrThatOptionModel
								.getThisorthatoptionsresponses()) {
							if (optionsResponseModel.getGroupId() == groupId) {
								if (!pollsDAO.checkUserBlocked(userid, optionsResponseModel.getUserId())) {
									newThisOrThatOptionResponse.add(optionsResponseModel);
								}
							}
						}
					}
					thisOrThatOptionModel.getThisorthatoptionsresponses().clear();
					thisOrThatOptionModel.setThisorthatoptionsresponses(newThisOrThatOptionResponse);
				}

			}
			if (!model.getRsvpPollResponseList().isEmpty()) {
				List<RsvpResponseModel> newRsvpResponse = new ArrayList<>();
				for (RsvpResponseModel rsvpResponse : model.getRsvpPollResponseList()) {
					if (rsvpResponse.getGroupId() == groupId) {
						if (rsvpResponse.getUserId() > 0) {
							UsersModel user = userDao.getUserByUserId(rsvpResponse.getUserId());
							if (user != null) {
								rsvpResponse.setUserName(user.getFirstName() + " " + user.getLastName());
								rsvpResponse.setProfilePic(user.getProfilePic());
							}
						}
						newRsvpResponse.add(rsvpResponse);
					}
				}
				model.getRsvpPollResponseList().clear();
				model.getRsvpPollResponseList().addAll(newRsvpResponse);
			}
		}
		return pollModels;
	}

	@Override
	public ImageOptionModel saveImageResponse(ImageOptionsResponseModel imageOptionsResponseModel) {
		ImageOptionModel imageOptionModel = pollsDAO.getImageOptionByImageId(imageOptionsResponseModel.getOptionId());
		imageOptionsResponseModel.setImageoption(imageOptionModel);
		pollsDAO.saveImageResponse(imageOptionsResponseModel);
		return imageOptionModel;
	}

	@Override
	public RatingOptionModel saveRatingOptionResponse(RatingOptionsResponseModel ratingOptionsResponseModel) {

		RatingOptionModel ratingOptionModel = pollsDAO
				.getRatingOptionByRatingId(ratingOptionsResponseModel.getOptionId());
		ratingOptionsResponseModel.setRatingoption(ratingOptionModel);
		pollsDAO.saveRatingOptionResponse(ratingOptionsResponseModel);
		return ratingOptionModel;
	}

	@Override
	public MultipleOptionModel saveMultipleOptionResponse(MultipleOptionsResponseModel multipleOptionsResponseModel) {

		MultipleOptionModel multipleOptionModel = pollsDAO
				.getMultipleOptionByMulId(multipleOptionsResponseModel.getOptionId());
		multipleOptionsResponseModel.setMultipleoption(multipleOptionModel);
		pollsDAO.saveMultipleOptionResponse(multipleOptionsResponseModel);
		return multipleOptionModel;
	}

	@Override
	public ThisorthatOptionModel saveThisOrThatOptionResponse(
			ThisorthatOptionsResponseModel thisorthatOptionsResponseModel) {

		ThisorthatOptionModel thisorthatOptionModel = pollsDAO
				.getThisOrThatOptions(thisorthatOptionsResponseModel.getOptionId());
		thisorthatOptionsResponseModel.setThisorthatoption(thisorthatOptionModel);
		pollsDAO.saveThisOrThatOptionResponse(thisorthatOptionsResponseModel);
		return thisorthatOptionModel;
	}

	@Override
	public Boolean savePollIntrests(List<PollInterestModel> pollIntrestList) {

		return pollsDAO.savePollIntrests(pollIntrestList);
	}

	@Override
	public Object sharePollByGroupList(int pollId, List<PollGroupModel> groupModels) {
		PollModel pollModel = pollsDAO.getPollByPollId(pollId);
		List<PollGroupModel> newGroupList = new ArrayList<>();
		if (!groupModels.isEmpty()) {
			for (PollGroupModel groupModel : groupModels) {
				groupModel.setPoll(pollModel);
				PollGroupModel groupModels2 = pollsDAO.getPollGroupModelByPollAndGroupId(pollModel,
						groupModel.getGroupId());
				if (groupModels2 == null) {
					newGroupList.add(groupModel);
				}
			}
		}
		return pollsDAO.savePollGroupModels(newGroupList);
	}

	@Override
	public Object getPollSharedList(int pollId, int groupId) {
		return pollsDAO.getPollSharedList(pollId, groupId);
	}

	@Override
	public PollModel getPollByPollIdAndGroupId(int pollId, int groupId) {
		PollModel model = pollsDAO.getPollByPollIdAndGroupId(pollId, groupId);

		if (model != null) {
			model.getImageoptions().stream().forEach(imgOpt -> {
				List<ImageOptionsResponseModel> newResp = imgOpt.getImageoptionsresponses().stream()
						.filter(resp -> resp.getGroupId() == groupId).collect(Collectors.toList());
				newResp.forEach(resEach -> {
					UsersModel user = userDao.getUserModelById(resEach.getUserId());
					resEach.setUserName(user != null ? user.getFirstName() + " " + user.getLastName() : null);
					resEach.setProfileUrl(user != null ? user.getProfilePic() : null);
				});
				imgOpt.getImageoptionsresponses().clear();
				imgOpt.setImageoptionsresponses(newResp);
			});

			model.getMultipleoptions().stream().forEach(mulOpt -> {
				List<MultipleOptionsResponseModel> newRes = mulOpt.getMultipleoptionsresponses().stream()
						.filter(res -> res.getGroupId() == groupId).collect(Collectors.toList());
				newRes.forEach(resEach -> {
					UsersModel user = userDao.getUserModelById(resEach.getUserId());
					resEach.setUserName(user != null ? user.getFirstName() + " " + user.getLastName() : null);
					resEach.setProfileUrl(user != null ? user.getProfilePic() : null);
				});
				mulOpt.getMultipleoptionsresponses().clear();
				mulOpt.setMultipleoptionsresponses(newRes);
			});

			model.getThisorthatoptions().stream().forEach(mulOpt -> {
				List<ThisorthatOptionsResponseModel> newRes = mulOpt.getThisorthatoptionsresponses().stream()
						.filter(res -> res.getGroupId() == groupId).collect(Collectors.toList());
				newRes.forEach(resEach -> {
					UsersModel user = userDao.getUserModelById(resEach.getUserId());
					resEach.setUserName(user != null ? user.getFirstName() + " " + user.getLastName() : null);
					resEach.setProfileUrl(user != null ? user.getProfilePic() : null);
				});
				mulOpt.getThisorthatoptionsresponses().clear();
				mulOpt.setThisorthatoptionsresponses(newRes);
			});

			model.getRatingoptions().stream().forEach(mulOpt -> {
				List<RatingOptionsResponseModel> newRes = mulOpt.getRatingoptionsresponses().stream()
						.filter(res -> res.getGroupId() == groupId).collect(Collectors.toList());
				newRes.forEach(resEach -> {
					UsersModel user = userDao.getUserModelById(resEach.getUserId());
					resEach.setUserName(user != null ? user.getFirstName() + " " + user.getLastName() : null);
					resEach.setProfileUrl(user != null ? user.getProfilePic() : null);
				});
				mulOpt.getRatingoptionsresponses().clear();
				mulOpt.setRatingoptionsresponses(newRes);
			});

			model.getRsvpPollResponseList().forEach(resEach -> {
				UsersModel user = userDao.getUserModelById(resEach.getUserId());
				resEach.setUserName(user != null ? user.getFirstName() + " " + user.getLastName() : null);
				resEach.setProfilePic(user != null ? user.getProfilePic() : null);
			});

			if (model.getUsersModel() != null) {
				model.setCreatedUser(model.getUsersModel().getUserId());
				model.setUsername(model.getUsersModel().getFirstName() + " " + model.getUsersModel().getLastName());
				model.setProfilePic(model.getUsersModel().getProfilePic());
			}

			for (PollLikeModel likeModel : model.getPolllikes()) {
				UsersModel usmd = userDao.getUserByUserId(likeModel.getUserId());
				if (usmd != null) {
					likeModel.setUserName(usmd.getFirstName() + " " + usmd.getLastName());
					likeModel.setProfilePic(usmd.getProfilePic());
				}
			}
		}
		return model;
	}

	@Override
	public boolean saveRSVPResponse(int pollId, int groupId, int userId, int responseId) {
		PollModel model = pollsDAO.getPollByPollId(pollId);
		if (model != null) {
			RsvpResponseModel responseModel = new RsvpResponseModel();
			responseModel.setGroupId(groupId);
			responseModel.setPoll(model);
			responseModel.setRsvpResponse(responseId);
			responseModel.setUserId(userId);
			return pollsDAO.saveRSVPResponse(responseModel);
		} else {
			return false;
		}
	}

	@Override
	public boolean deletePollById(Long groupId, Integer pollId) {
		return pollsDAO.deletePollById(groupId, pollId);
	}

	@Override
	public boolean updatePollDateAndResultType(byte type, Date date, Long pollId) {
		return pollsDAO.updatePollDateAndResultType(type, date, pollId);
	}

	@Override
	public List<PollModel> fetchRsvpPollsByUserId(Map<String, String> body) {

		Integer userId = body.get("userId") != null ? Integer.parseInt(body.get("userId")) : null;
		Integer pageNumber = body.get("pageNum") != null ? Integer.parseInt(body.get("pageNum")) : 0;
		Integer pageSize = body.get("pageSize") != null ? Integer.parseInt(body.get("pageSize")) : 10;
		// Integer groupId = body.get("groupId") != null ?
		// Integer.parseInt(body.get("groupId")) : null;
		// Date date = body.get("date") != null ? new
		// Date(Long.parseLong(body.get("date"))) : null;
		String question = body.get("question");

		int rowCount = pageNumber * pageSize;
		int start = (rowCount - pageSize);
		int offset = start;

		List<PollModel> list = pollsDAO.fetchRsvpPollsByUserIdByPage(userId, pageSize, offset, question);
		list.forEach(obj -> {
			if (obj.getPollGroupModels().get(0).getSharedBy() != null)
				obj.setUsername(obj.getPollGroupModels().get(0).getSharedBy().getFirstName() + " "
						+ obj.getPollGroupModels().get(0).getSharedBy().getLastName());
			obj.setProfilePic(obj.getPollGroupModels().get(0).getSharedBy().getProfilePic());
			obj.setPollGroupModels(null);
		});
		logger.info("rsvp count for current user " + list.size());

		return list;
	}

	@Override
	public boolean savePollReport(ReportPoll poll) {
		return pollsDAO.savePollReport(poll);
	}

	@Override
	public void sendNotification(Object responsModel, PollResponseType type, int groupId, UsersModel user) {
	}

}
