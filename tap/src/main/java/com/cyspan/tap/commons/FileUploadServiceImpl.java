package com.cyspan.tap.commons;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import net.coobird.thumbnailator.Thumbnails;

@Service
@PropertySource("classpath:fileupload.properties")
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	AmazonS3 amazoneS3Client;

	static Logger logger = Logger.getLogger(FileUploadServiceImpl.class.getName());

	@Autowired
	Environment environment;

	@Override
	public String uploadPictureBig(byte[] byteArray, String type) {

		int BIG_WIDTH = Integer.parseInt(environment.getRequiredProperty("profilePicture.big.width"));
		int BIG_HEIGHT = Integer.parseInt(environment.getRequiredProperty("profilePicture.big.height"));
		String fileName = UserHandler.getToken();
		String EXTENSION = environment.getRequiredProperty("image.extension");
		String BUCKETNAME = environment.getRequiredProperty("bucketName");
		String PROFILE_BIG_PATH = type + environment.getRequiredProperty("profilePicture.big.path");
		String ROOT_URL = environment.getRequiredProperty("amazone.s3.root.url");

		File awsFileObject = null;
		try {
			awsFileObject = File.createTempFile(fileName, EXTENSION);
			BufferedOutputStream buffStream2 = new BufferedOutputStream(new FileOutputStream(awsFileObject));
			buffStream2.write(byteArray);
			buffStream2.close();
			Thumbnails.of(awsFileObject).size(BIG_WIDTH, BIG_HEIGHT).toFile(awsFileObject);
			PutObjectRequest awsRequest = new PutObjectRequest(BUCKETNAME, PROFILE_BIG_PATH + awsFileObject.getName(),
					awsFileObject);
			awsRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			amazoneS3Client.putObject(awsRequest);
			logger.info(
					"big-image uploaded : " + ROOT_URL + BUCKETNAME + "/" + PROFILE_BIG_PATH + awsFileObject.getName());
		} catch (IOException e) {
			logger.error("amazone s3 bigger image upload failed");
			e.printStackTrace();
			logger.error(e.getMessage());
			return "";
		}
		return ROOT_URL + BUCKETNAME + "/" + PROFILE_BIG_PATH + awsFileObject.getName();
	}

	@Override
	public String uploadPictureSmall(byte[] byteArray, String type) {

		int SMALL_WIDTH = Integer.parseInt(environment.getRequiredProperty("profilePicture.small.width"));
		int SMALL_HEIGHT = Integer.parseInt(environment.getRequiredProperty("profilePicture.small.height"));
		String fileName = UserHandler.getToken();
		String EXTENSION = environment.getRequiredProperty("image.extension");
		String BUCKETNAME = environment.getRequiredProperty("bucketName");
		String PROFILE_SMALL_PATH = type + environment.getRequiredProperty("profilePicture.small.path");
		String ROOT_URL = environment.getRequiredProperty("amazone.s3.root.url");
		File awsFileObject = null;
		try {
			awsFileObject = File.createTempFile(fileName, EXTENSION);
			BufferedOutputStream buffStream2 = new BufferedOutputStream(new FileOutputStream(awsFileObject));
			buffStream2.write(byteArray);
			buffStream2.close();
			Thumbnails.of(awsFileObject).size(SMALL_WIDTH, SMALL_HEIGHT).toFile(awsFileObject);
			PutObjectRequest awsRequest = new PutObjectRequest(BUCKETNAME, PROFILE_SMALL_PATH + awsFileObject.getName(),
					awsFileObject);
			awsRequest.setCannedAcl(CannedAccessControlList.PublicRead); // permission for download file for public
			amazoneS3Client.putObject(awsRequest);
			logger.info("small-image uploaded : " + ROOT_URL + BUCKETNAME + "/" + PROFILE_SMALL_PATH
					+ awsFileObject.getName());
		} catch (IOException e) {
			logger.error("amazone s3 smaller image upload failed");
			e.printStackTrace();
			logger.error(e.getMessage());
			return "";
		}
		return ROOT_URL + BUCKETNAME + "/" + PROFILE_SMALL_PATH + awsFileObject.getName();
	}

	@Override
	public String uploadCoverPic(byte[] byteArray, String type) {
		int BIG_WIDTH = Integer.parseInt(environment.getRequiredProperty("profilePicture.big.width"));
		int BIG_HEIGHT = Integer.parseInt(environment.getRequiredProperty("profilePicture.big.height"));
		String fileName = UserHandler.getToken();
		String EXTENSION = environment.getRequiredProperty("image.extension");
		String BUCKETNAME = environment.getRequiredProperty("bucketName");
		String PROFILE_BIG_PATH = type + environment.getRequiredProperty("coverPicture.cover.path");
		String ROOT_URL = environment.getRequiredProperty("amazone.s3.root.url");

		File awsFileObject = null;
		try {
			awsFileObject = File.createTempFile(fileName, EXTENSION);
			BufferedOutputStream buffStream2 = new BufferedOutputStream(new FileOutputStream(awsFileObject));
			buffStream2.write(byteArray);
			buffStream2.close();
			Thumbnails.of(awsFileObject).size(BIG_WIDTH, BIG_HEIGHT).toFile(awsFileObject);
			PutObjectRequest awsRequest = new PutObjectRequest(BUCKETNAME, PROFILE_BIG_PATH + awsFileObject.getName(),
					awsFileObject);
			awsRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			amazoneS3Client.putObject(awsRequest);
			logger.info("cover-image uploaded : " + ROOT_URL + BUCKETNAME + "/" + PROFILE_BIG_PATH
					+ awsFileObject.getName());
		} catch (IOException e) {
			logger.error("amazone s3 cover image upload failed");
			e.printStackTrace();
			logger.error(e.getMessage());
			return "";
		}
		return ROOT_URL + BUCKETNAME + "/" + PROFILE_BIG_PATH + awsFileObject.getName();
	}

	@Override
	public String uploadSubscriptionPollImage(byte[] byteArray, String type) {
		int BIG_WIDTH = Integer.parseInt(environment.getRequiredProperty("profilePicture.big.width"));
		int BIG_HEIGHT = Integer.parseInt(environment.getRequiredProperty("profilePicture.big.height"));
		String fileName = UserHandler.getToken();
		String EXTENSION = environment.getRequiredProperty("image.extension");
		String BUCKETNAME = environment.getRequiredProperty("bucketName");
		String PROFILE_BIG_PATH = type + environment.getRequiredProperty("subscription.poll");
		String ROOT_URL = environment.getRequiredProperty("amazone.s3.root.url");

		File awsFileObject = null;
		try {
			awsFileObject = File.createTempFile(fileName, EXTENSION);
			BufferedOutputStream buffStream2 = new BufferedOutputStream(new FileOutputStream(awsFileObject));
			buffStream2.write(byteArray);
			buffStream2.close();
			Thumbnails.of(awsFileObject).size(BIG_WIDTH, BIG_HEIGHT).toFile(awsFileObject);
			PutObjectRequest awsRequest = new PutObjectRequest(BUCKETNAME, PROFILE_BIG_PATH + awsFileObject.getName(),
					awsFileObject);
			awsRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			amazoneS3Client.putObject(awsRequest);
			logger.info("cover-image uploaded : " + ROOT_URL + BUCKETNAME + "/" + PROFILE_BIG_PATH
					+ awsFileObject.getName());
		} catch (IOException e) {
			logger.error("aws s3 pdf upload error " + e.getMessage());
			return "";
		}
		return ROOT_URL + BUCKETNAME + "/" + PROFILE_BIG_PATH + awsFileObject.getName();
	}

	@Override
	public String uploadSubscriptionNoticePdf(byte[] byteArray, String type) {
		String fileName = UserHandler.getToken();
		String BUCKETNAME = environment.getRequiredProperty("bucketName");
		String PROFILE_BIG_PATH = type + environment.getRequiredProperty("subscription.notice");
		String ROOT_URL = environment.getRequiredProperty("amazone.s3.root.url");
		File awsFileObject = null;
		try {
			awsFileObject = File.createTempFile(fileName, ".pdf");
			BufferedOutputStream buffStream2 = new BufferedOutputStream(new FileOutputStream(awsFileObject));
			buffStream2.write(byteArray);
			buffStream2.close();
			PutObjectRequest awsRequest = new PutObjectRequest(BUCKETNAME, PROFILE_BIG_PATH + awsFileObject.getName(),
					awsFileObject);
			awsRequest.setCannedAcl(CannedAccessControlList.PublicRead);

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("application/pdf");
			metadata.addUserMetadata("x-amz-meta-title", "someTitle");
			awsRequest.setMetadata(metadata);

			amazoneS3Client.putObject(awsRequest);
			logger.info("pdf uploaded : " + ROOT_URL + BUCKETNAME + "/" + PROFILE_BIG_PATH + awsFileObject.getName());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("aws s3 pdf upload error " + e.getMessage());
		}
		return ROOT_URL + BUCKETNAME + "/" + PROFILE_BIG_PATH + awsFileObject.getName();
	}

}
