package com.cyspan.tap.subscription.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyspan.tap.commons.FileUploadService;
import com.cyspan.tap.subscription.dao.SubscriptionToolsDao;
import com.cyspan.tap.subscription.models.FeedbackFreeComment;
import com.cyspan.tap.subscription.models.MultipleChoiceOptions;
import com.cyspan.tap.subscription.models.MultipleChoiceResponse;
import com.cyspan.tap.subscription.models.OrganizationModel;
import com.cyspan.tap.subscription.models.SubscriptionConstants;
import com.cyspan.tap.subscription.models.SubscriptionFeedback;
import com.cyspan.tap.subscription.models.SubscriptionFeedbackGroup;
import com.cyspan.tap.subscription.models.SubscriptionImageOptionResponse;
import com.cyspan.tap.subscription.models.SubscriptionNotice;
import com.cyspan.tap.subscription.models.SubscriptionPoll;
import com.cyspan.tap.subscription.models.SubscriptionPollImages;
import com.cyspan.tap.subscription.models.SubscriptionRating;
import com.cyspan.tap.subscription.models.SubscriptionResponses;
import com.cyspan.tap.user.dao.UsersDao;

@Service
public class SubscriptionToolsServiceImpl implements SubscriptionToolsService {

	static Logger logger = Logger.getLogger(SubscriptionToolsServiceImpl.class.getName());

	@Autowired
	SubscriptionToolsDao toolsDao;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	UsersDao userDao;

	@Override
	public SubscriptionFeedback fechFeedbackById(Long id) {
		return toolsDao.fechFeedbackById(id);
	}

	@Override
	public SubscriptionPoll fechPollById(Long id) {
		return toolsDao.fechPollById(id);
	}

	@Override
	public SubscriptionNotice fetchNoticeById(Long id) {
		return toolsDao.fetchNoticeById(id);
	}

	@Override
	public Object saveSubscriptionToolsOptions(OrganizationModel model) {

		if (model.getOrganizationId() == null)
			return null;

		OrganizationModel newObject = new OrganizationModel(model.getOrganizationId());
		if (model.getFeedbacks() != null && !model.getFeedbacks().isEmpty()) {
			// multiple choice, rating, free comment options
			// save feedback and option
			// rating and free comment are response from ios/android application
			for (SubscriptionFeedback feedback : model.getFeedbacks()) {
				// Creator name, creator id, status and type are set at front end
				if (!feedback.getQuestions().isEmpty()) {
					for (MultipleChoiceOptions options : feedback.getQuestions()) {
						options.setCreaterId(feedback.getFeedbackCreaterId());
						options.setCreateUserName(feedback.getFeedbackCreaterName());
						options.setFeedback(feedback);
					}
					feedback.setFeedbackType((byte) SubscriptionConstants.MULTIPLE_CHOICE.getValue());
					logger.info("feedback multiple choice options count (" + feedback.getQuestions().size() + ")");
				}

				if (feedback.getFeedbackType() < 2) {
					logger.error("subscription feedback type is invalid (value : " + feedback.getFeedbackType() + ")");
				}
				feedback.setOrganization(newObject);
				SubscriptionFeedback obj = toolsDao.saveSubscriptionFeedback(feedback);
				logger.info("feedback saved with id : " + obj.getFeedbackId());
			}
			logger.info("subscription feedback save successfully (count:" + model.getFeedbacks().size() + ")");
		}

		if (model.getPolls() != null && !model.getPolls().isEmpty()) {
			// multiple choice, rating, image options
			for (SubscriptionPoll poll : model.getPolls()) {
				// poll type, poll text, expire date, status are set from front end

				if (!poll.getQuestions().isEmpty()) {
					for (MultipleChoiceOptions options : poll.getQuestions()) {
						options.setCreaterId(poll.getCreatedBy());
						options.setCreateUserName(poll.getCreatedUserName());
						options.setPoll(poll);
					}
					poll.setPollType((byte) SubscriptionConstants.MULTIPLE_CHOICE.getValue());
					logger.info("feedback multiple choice options count (" + poll.getQuestions().size() + ")");
				}

				// images will upload to aws s3 and set url
				if (!poll.getPollImages().isEmpty()) {
					for (SubscriptionPollImages images : poll.getPollImages()) {
						String url = fileUploadService.uploadPictureBig(images.getImageByte(), "subscription");
						images.setImageURL(url);
						images.setPoll(poll);
						images.setCreatedBy(poll.getCreatedBy());
						images.setCreatedByUserName(poll.getCreatedUserName());
						images.setImageByte(null);
					}
				}
				poll.setOrganization(newObject);
				SubscriptionPoll obj = toolsDao.saveSubscriptionPoll(poll);
				logger.info("subscription poll saved id :" + obj.getPollId());
			}
		}

		if (model.getNotices() != null && !model.getNotices().isEmpty()) {
			for (SubscriptionNotice notice : model.getNotices()) {
				// if attachment is image its upload to aws s3
				if (notice.getAttachmentByte().get("image") != null) {
					String url = fileUploadService.uploadSubscriptionPollImage(notice.getAttachmentByte().get("image"),
							"subscription");
					notice.setImagelink(url);
				}
				// if attachment is pdf its upload to aws s3
				if (notice.getAttachmentByte().get("pdf") != null) {
					String url = fileUploadService.uploadSubscriptionNoticePdf(notice.getAttachmentByte().get("pdf"),
							"subscription");
					notice.setDocumentlink(url);
				}
				notice.setOrganization(newObject);
				toolsDao.saveSubscriptionNotice(notice);
				byte[] myvar = "Any String you want".getBytes();
				notice.getAttachmentByte().put("image", myvar);
				notice.getAttachmentByte().put("pdf", myvar);

			}
			return model.getNotices();
		}

		if (model.getFeedbackGroups() != null && !model.getFeedbackGroups().isEmpty()) {
			for (SubscriptionFeedbackGroup feedbackgp : model.getFeedbackGroups()) {
				if (feedbackgp.getFeedbacks() != null && !feedbackgp.getFeedbacks().isEmpty()) {
					// set feedback group org model
					feedbackgp.setOrganization(newObject);
					// for each feedback should add org model
					Set<SubscriptionFeedback> savedList = new HashSet<>();
					for (SubscriptionFeedback feedback : feedbackgp.getFeedbacks()) {
						if (!feedback.getQuestions().isEmpty()) {
							for (MultipleChoiceOptions options : feedback.getQuestions()) {
								options.setCreaterId(feedback.getFeedbackCreaterId());
								options.setCreateUserName(feedback.getFeedbackCreaterName());
								options.setFeedback(feedback);
							}
							feedback.setFeedbackType((byte) SubscriptionConstants.MULTIPLE_CHOICE.getValue());
							logger.info(
									"feedback multiple choice options count (" + feedback.getQuestions().size() + ")");
							if (feedback.getFeedbackType() < 2) {
								logger.error("subscription feedback type is invalid (value : "
										+ feedback.getFeedbackType() + ")");
							}
						}
						feedback.setOrganization(newObject);
						SubscriptionFeedback obj = toolsDao.saveSubscriptionFeedback(feedback);
						obj.setFeedbackGroup(feedbackgp);
						savedList.add(obj);
					}
					feedbackgp.getFeedbacks().clear();
					feedbackgp.getFeedbacks().addAll(savedList);
				}
				toolsDao.saveSubscriptionFeedbackGroup(feedbackgp);
			}
			return model.getFeedbackGroups();
		}

		return null;
	}

	@Override
	public OrganizationModel updateSubscriptionToolsOptions(OrganizationModel model) {
		if (model.getOrganizationId() == null)
			return null;

		// temporary org object to mapping
		OrganizationModel newObject = new OrganizationModel(model.getOrganizationId());

		// check for feedback groups
		if (model.getFeedbackGroups() != null && !model.getFeedbackGroups().isEmpty()) {
			for (SubscriptionFeedbackGroup group : model.getFeedbackGroups()) {
				if (group.getFeedbacks() != null && !group.getFeedbacks().isEmpty()) {
					for (SubscriptionFeedback feedback : group.getFeedbacks()) {
						// check for feedbacks
						if (feedback != null) {
							for (MultipleChoiceOptions options : feedback.getQuestions()) {
								options.setFeedback(feedback);
							}
							feedback.setFeedbackGroup(group);
							feedback.setOrganization(newObject);
							feedback = toolsDao.updateSubscriptionFeedback(feedback);
						}
					}
				}
				if (!toolsDao.updateSubscriptionFeedbackGroup(group)) {
					logger.error("error in update feedback group");
				}
			}
		}

		if (model.getPolls() != null && !model.getPolls().isEmpty()) {

		}

		if (model.getNotices() != null && !model.getNotices().isEmpty()) {

		}

		return null;
	}

	@Override
	public List<Long> deleteFeedbacksById(List<Long> ids) {
		List<Long> deletedList = new ArrayList<>();
		for (Long feedbackId : ids) {
			if (toolsDao.deleteFeedbackById(feedbackId)) {
				deletedList.add(feedbackId);
			}
		}
		return deletedList;
	}

	@Override
	public List<Long> deletePollsById(List<Long> ids) {
		List<Long> deletedList = new ArrayList<>();
		for (Long pollId : ids) {
			if (toolsDao.deletePollById(pollId)) {
				deletedList.add(pollId);
			}
		}
		return deletedList;
	}

	@Override
	public List<Long> deleteNoticesById(List<Long> ids) {
		List<Long> deletedList = new ArrayList<>();
		for (Long noticeId : ids) {
			if (toolsDao.deleteNoticeById(noticeId)) {
				deletedList.add(noticeId);
			}
		}
		return deletedList;
	}

	@Override
	public List<Object> fetchSubscriptionResults(Long organizationId, Map<String, Object> parameters) {

		String rt = (String) parameters.get("fetch");
		String pg = (String) parameters.get("page");
		String pc = (String) parameters.get("count");
		String ul = (String) parameters.get("upperlimit");
		String ll = (String) parameters.get("lowerlimit");

		Integer pageNumber = pg != null ? new Integer(pg) : null;
		Integer pageCount = pc != null ? new Integer(pc) : null;
		Long upperDateLimit = ul != null ? new Long(ul) : null;
		Long lowerDateLimit = ll != null ? new Long(ll) : null;

		LocalDate f1 = null;
		LocalDate f2 = null;
		if (upperDateLimit != null && lowerDateLimit != null) {
			Date d1 = new Date(upperDateLimit);
			Date d2 = new Date(lowerDateLimit);
			f1 = d1.toLocalDate().minusDays(1); // day before given date
			f2 = d2.toLocalDate().plusDays(1); // day after given date
		}

		int rowCount = pageNumber * pageCount;
		int start = (rowCount - pageCount);
		int offset = start;

		if (rt != null && rt.equals("all")) {
			return toolsDao.fetchfeedbackGroupsAndPollsByOrganizationIdOrderByCreateDate(organizationId, offset,
					pageCount, f1, f2);
		} else if (rt != null && rt.equals("feedback")) {
			// if user needs only results of feedbacks
		} else if (rt != null && rt.equals("poll")) {
			// if user needs only results of polls
		}
		return null;
	}

	@Override
	public Object fetchDetailedSubscriptionResultById(Long id, String toolType) {
		if (toolType.equals("feedback")) {

		} else if (toolType.equals("feedback")) {

		}
		return null;
	}

	@Override
	public SubscriptionFeedbackGroup fechFeedbackGroupById(Long id) {
		SubscriptionFeedbackGroup group = toolsDao.fetchFeedbackGroupById(id);
		return group;
	}

	@Override
	public List<SubscriptionFeedbackGroup> fechFeedbackGroupByOrganizationId(Long id) {
		return toolsDao.fechFeedbackGroupByOrganizationId(id);
	}

	@Override
	public List<Long> deleteFeedbackGroupsById(List<Long> ids) {
		List<Long> deletedList = new ArrayList<>();
		for (Long noticeId : ids) {
			if (toolsDao.deleteFeedbackGroupById(noticeId)) {
				deletedList.add(noticeId);
			}
		}
		return deletedList;
	}

	@Override
	public MultipleChoiceResponse saveMultipleChoiceResponse(MultipleChoiceResponse response) {
		return toolsDao.saveMultipleChoiceResponse(response);
	}

	@Override
	public SubscriptionRating saveRatingResponse(SubscriptionRating response) {
		return toolsDao.saveRatingResponse(response);
	}

	@Override
	public SubscriptionImageOptionResponse saveImageResponse(SubscriptionImageOptionResponse response) {
		return toolsDao.saveImageResponse(response);
	}

	@Override
	public FeedbackFreeComment saveFreeCommentResponse(FeedbackFreeComment response) {
		return toolsDao.saveFreeCommentResponse(response);
	}

	@Override
	public boolean saveClearDateByUserIdAndOranizationId(Integer userId, Long orgId) {
		return toolsDao.saveClearDateByUserIdAndOranizationId(userId, orgId);
	}

	@Override
	public boolean saveUserRespondData(SubscriptionResponses responses) {
		return toolsDao.saveUserRespondData(responses);
	}

}
