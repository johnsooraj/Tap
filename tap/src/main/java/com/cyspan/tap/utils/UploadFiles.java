/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyspan.tap.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author sumesh
 *
 */
@Repository
public class UploadFiles {

	private static final List<String> imageUrlList = new ArrayList<String>();

	public static String uploadFile(MultipartFile[] imgfiles, String name, HttpServletRequest request,
			String directories, String extension) {

		imageUrlList.clear();
		String URL = request.getScheme() + "://" + // "http" + "://
				request.getServerName() + ":" + // "myhost"ervletContext
												// servletContex ":" + // ":"
				request.getServerPort() + request.getContextPath(); // "8080" ;

		for (int i = 0; i < imgfiles.length; i++) {
			MultipartFile imgfile = imgfiles[i];
			if (!imgfile.isEmpty()) {
				try {
					byte[] bytes = imgfile.getBytes();
					/* get real path */
					ServletContext servletContext = request.getSession().getServletContext();
					String relativeWebPath = "img/image.png";
					String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

					/* end get real path */
					String path = servletContext.getRealPath(directories);
					File file = new File(path);
					if (!file.exists()) {
						file.mkdirs();
					}
					// Create the file on server
					File serverFile = new File(file.getAbsolutePath() + File.separator + name + i + extension);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					imageUrlList.add(URL + directories + "/" + name + i + extension);
				} catch (Exception e) {

				}
			} else {

			}
		}

		if (imageUrlList.size() > 0) {
			String idList = imageUrlList.toString();
			String imgUrls = idList.substring(1, idList.length() - 1).replace(", ", ",");

			return imgUrls;
		} else {
			return "no";
		}

	}

	public static String uploadExcelFile(MultipartFile[] excelFiles, HttpServletRequest request) {
		String localLocation = "no";
		String URL = request.getScheme() + "://" + // "http" + "://
				request.getServerName() + ":" + // "myhost"ervletContext
												// servletContex ":" + // ":"
				request.getServerPort() + request.getContextPath(); // "8080" ;

		String directories = "/exceluploads";
		for (int i = 0; i < excelFiles.length; i++) {
			MultipartFile excelFile = excelFiles[i];
			if (!excelFile.isEmpty()) {
				try {
					byte[] bytes = excelFile.getBytes();

					/* get real path */
					ServletContext servletContext = request.getSession().getServletContext();
					String relativeWebPath = "img/image.png";
					String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

					/* end get real path */
					String path = servletContext.getRealPath(directories);
					File file = new File(path);
					if (!file.exists()) {
						file.mkdirs();
					}
					// Create the file on server
					File serverFile = new File(
							file.getAbsolutePath() + File.separator + excelFile.getOriginalFilename());
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					localLocation = serverFile.getAbsolutePath();
				} catch (Exception e) {
				}
			} else {

			}
		}

		return localLocation;
	}

	public static String uploadDocument(MultipartFile file, HttpServletRequest request) {
		String fileName = null;
		String originalPath = null;
		String URL = request.getScheme() + "://" + // "http" + "://
				request.getServerName() + ":" + // "myhost"ervletContext
												// servletContex ":" + // ":"
				request.getServerPort() + request.getContextPath(); // "8080" ;
		if (!file.isEmpty()) {
			try {
				fileName = file.getOriginalFilename();
				ServletContext servletContext = request.getSession().getServletContext();
				String path = servletContext.getRealPath("/resources/img");
				File file1 = new File(path);
				if (!file1.exists()) {
					file1.mkdirs();
				}
				byte[] bytes = file.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(
						new FileOutputStream(new File(file1.getAbsolutePath() + "/" + fileName)));
				buffStream.write(bytes);
				buffStream.close();
				System.out.println("FILE NAMEEEEEEEEEE;;;;;;;;;;;;;;;;;>" + file1.getAbsolutePath() + fileName);
				originalPath = URL + "/resources/img" + "/" + fileName;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return originalPath;
	}

	// Upload Multiple Files of any extension

	public static String uploadFiles(MultipartFile[] imgfiles, HttpServletRequest request, String directories) {
		String fileName = null;
		imageUrlList.clear();
		String URL = request.getScheme() + "://" + // "http" + "://
				request.getServerName() + ":" + // "myhost"ervletContext
												// servletContex ":" + // ":"
				request.getServerPort() + request.getContextPath(); // "8080" ;

		for (int i = 0; i < imgfiles.length; i++) {
			MultipartFile imgfile = imgfiles[i];
			if (!imgfile.isEmpty()) {
				try {
					fileName = imgfile.getOriginalFilename();
					byte[] bytes = imgfile.getBytes();
					ServletContext servletContext = request.getSession().getServletContext();
					String path = servletContext.getRealPath(directories);
					File file = new File(path);
					if (!file.exists()) {
						file.mkdirs();
					}
					// Create the file on server

					BufferedOutputStream buffStream = new BufferedOutputStream(
							new FileOutputStream(new File(file.getAbsolutePath() + "/" + fileName)));
					buffStream.write(bytes);
					buffStream.close();

					imageUrlList.add(URL + directories + "/" + fileName);
					System.out.println(imageUrlList.toString());

				} catch (Exception e) {

				}
			} else {

			}
		}

		if (imageUrlList.size() > 0) {
			String idList = imageUrlList.toString();
			String imgUrls = idList.substring(1, idList.length() - 1).replace(", ", ",");

			return imgUrls;
		} else {
			return "no";
		}

	}

	///// UPLOAD FILE WITH RETURN AS
	///// FILE////////////////////////////////////////

	public static List<File> uploadAttachment(MultipartFile[] excelFiles, HttpServletRequest request) {
		String localLocation = "no";
		String URL = request.getScheme() + "://" + // "http" + "://
				request.getServerName() + ":" + // "myhost"ervletContext
												// servletContex ":" + // ":"
				request.getServerPort() + request.getContextPath(); // "8080" ;
		System.out.println("SIZE::>" + excelFiles.length);
		List<File> fileList = new ArrayList<File>();
		String directories = "/exceluploads";
		for (int i = 0; i < excelFiles.length; i++) {
			MultipartFile excelFile = excelFiles[i];
			if (!excelFile.isEmpty()) {
				try {
					byte[] bytes = excelFile.getBytes();

					/* get real path */
					ServletContext servletContext = request.getSession().getServletContext();
					String relativeWebPath = "img/image.png";
					String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

					/* end get real path */
					String path = servletContext.getRealPath(directories);
					File file = new File(path);
					if (!file.exists()) {
						file.mkdirs();
					}
					// Create the file on server
					File serverFile = new File(
							file.getAbsolutePath() + File.separator + excelFile.getOriginalFilename());
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

				
					localLocation = serverFile.getAbsolutePath();
					
					System.out.println("LOCATION:::>>"+localLocation);
					File rootDir = new File(localLocation);
					System.out.println("REAL:::>"
							+ servletContext.getRealPath(path + File.separator + excelFile.getOriginalFilename()));
					fileList.add(rootDir);

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("EMPTYYYYYYYYYYYYYYYYYYYYYYY");
			}
		}

		return fileList;
	}

}
