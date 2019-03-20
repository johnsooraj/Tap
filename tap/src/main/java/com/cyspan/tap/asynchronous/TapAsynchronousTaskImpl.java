package com.cyspan.tap.asynchronous;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.poll.dao.PollsDAO;
import com.cyspan.tap.poll.models.MultipleOptionModel;
import com.cyspan.tap.poll.models.PollModel;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

@Async
@Repository
public class TapAsynchronousTaskImpl implements TapAsynchronousTasks {

	@Autowired
	PollsDAO pollDao;

	@Autowired
	ServletContext servletContext;

	static Logger logger = Logger.getLogger(TapAsynchronousTaskImpl.class.getName());

	@Override
	@SuppressWarnings("unchecked")
	public void pollObjecjtionalWordsSearch(Object model) {
		List<String> savedWords = (List<String>) servletContext.getAttribute("objectionalWords");
		if (model instanceof PollModel) {
			boolean updateToObjectiveContent = false;
			PollModel pollModel = (PollModel) model;

			if (pollModel.getMultipleoptions() != null) {
				List<MultipleOptionModel> listMultiOptions = pollModel.getMultipleoptions();
				for (MultipleOptionModel multipleOptionModel : listMultiOptions) {
					List<ExtractedResult> optiontext = FuzzySearch.extractAll(multipleOptionModel.getOptionText(),
							savedWords, 95);
					if (optiontext.size() > 0) {
						logger.info("poll potion text contains objective word");
						updateToObjectiveContent = true;
						for (ExtractedResult obj : optiontext) {
							System.out.println(obj);
						}
					}
				}
			}

			List<ExtractedResult> resutlsSet = FuzzySearch.extractAll(pollModel.getQuestion(), savedWords, 95);
			if (resutlsSet.size() > 0) {
				updateToObjectiveContent = true;
				logger.info("poll question text contains objective word");
				for (ExtractedResult obj : resutlsSet) {
					System.out.println(obj);
				}
			}

			if (updateToObjectiveContent) {
				pollDao.updatePollToObjectiveContentPoll(pollModel);
			}
		}
	}

}
