package com.cyspan.tap.ztest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyspan.tap.poll.models.ImageOptionModel;
import com.cyspan.tap.poll.models.MultipleOptionModel;
import com.cyspan.tap.poll.models.PollModel;
import com.cyspan.tap.poll.models.RatingOptionModel;
import com.cyspan.tap.poll.models.ThisorthatOptionModel;

@RestController
@RequestMapping("/test")
public class TestWebservicesController {
	@Autowired
	TestDAO testDAO;
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST,headers = "Accept=application/json")
	public @ResponseBody String uploadImage2(@RequestBody UploadImagePOJO uploadImagePOJO,
			HttpServletRequest request) {
		try {
			// This will decode the String which is encoded by using Base64
			// class
//			byte[] imageByte = Base64.decodeBase64(imageValue);
			ServletContext servletContext = request.getSession().getServletContext();
//			String directory = servletContext.getRealPath("/resources/") + "images";
////			String directory = "C:data/"+ "image/sample.jpg";
//
//			File file = new File(directory);
//			if (!file.exists()) {
//				file.mkdirs();
////				file = new File(directory);
//			}
//			new FileOutputStream(directory+"/sample.jpg").write(imageByte);
			String path = servletContext.getRealPath("/resources/img");
			File file1 = new File(path);
			if (!file1.exists()) {
				file1.mkdirs();
			}
			System.out.println("image byte :: "+uploadImagePOJO.getImageValue());
			byte[] bytes =  Base64.decodeBase64(uploadImagePOJO.getImageValue());
			BufferedOutputStream buffStream = new BufferedOutputStream(
					new FileOutputStream(new File(file1.getAbsolutePath() + "/" + "sample.jpg")));
			buffStream.write(uploadImagePOJO.getImageValue());
			buffStream.close();
			return "success ";
		} catch (Exception e) {
			e.printStackTrace();
			return "error = " + e;
		}

	}
	@RequestMapping(value="/testerrors", method = RequestMethod.GET)
	public @ResponseBody Object test() throws IOException {
		throw new IOException("this is io exception");
	}
	@RequestMapping(value="/poll", method = RequestMethod.GET)
	public @ResponseBody Object testPollStructure() throws IOException {
		ImageOptionModel imageoption=new ImageOptionModel();
		imageoption.setCaption("hiii");
		List<ImageOptionModel> listImage=new ArrayList<>();
		listImage.add(imageoption);
		imageoption.setCaption("hiii2");
		listImage.add(imageoption);
		imageoption.setCaption("hiii3");
		listImage.add(imageoption);
		
		List<MultipleOptionModel> listMulti=new ArrayList<>();
		MultipleOptionModel multipleoption=new MultipleOptionModel();
		multipleoption.setOptionText("hiii");
		listMulti.add(multipleoption);
		multipleoption.setOptionText("heloooo");
		listMulti.add(multipleoption);
		
		List<RatingOptionModel> listRating=new ArrayList<>();
		RatingOptionModel ratingOptionModel=new RatingOptionModel();
		ratingOptionModel.setRating(10);
		listRating.add(ratingOptionModel);
		
		List<ThisorthatOptionModel> listThisThat=new ArrayList<>();
		ThisorthatOptionModel thisorthatOptionModel=new ThisorthatOptionModel();
		thisorthatOptionModel.setOptionText("Option txt1");
		listThisThat.add(thisorthatOptionModel);
		
		PollModel pollModel=new PollModel();
		/*pollModel.setImageoptions(listImage);
		pollModel.setMultipleoptions(listMulti);
		pollModel.setRatingoptions(listRating);*/
//		pollModel.setThisorthatoptions(listThisThat);
		
		return pollModel;
	}
	@RequestMapping(value="/retrievepoll", method = RequestMethod.GET)
	public @ResponseBody Object testRetrievePollStructure() throws IOException {
		return testDAO.retrieveAllPoll();
	}
	@RequestMapping(value="/poll", method = RequestMethod.POST,headers = "Accept=application/json")
	public @ResponseBody Object testSavePoll(@RequestBody PollModel pollModel) throws IOException {
		System.out.println(pollModel.toString());
		/*//List<ImageOptionModel> listImages=pollModel.getImageoptions();
		for(ImageOptionModel s:listImages) {
			s.setPoll(pollModel);
		}*/
		testDAO.insertPoll(pollModel);
		
		return "recieved";
	}
}
