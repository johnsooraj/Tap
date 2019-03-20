package com.cyspan.tap.commons;

public interface FileUploadService {

	public String uploadPictureBig(byte[] byteArray, String type);

	public String uploadPictureSmall(byte[] byteArray, String type);

	public String uploadCoverPic(byte[] byteArray, String type);

	public String uploadSubscriptionPollImage(byte[] byteArray, String type);
	
	public String uploadSubscriptionNoticePdf(byte[] byteArray, String type);
}
