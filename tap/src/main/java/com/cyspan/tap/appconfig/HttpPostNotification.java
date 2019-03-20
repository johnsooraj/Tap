package com.cyspan.tap.appconfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

@Configuration
@PropertySource("classpath:mail.properties")
@SuppressWarnings("unchecked")
public class HttpPostNotification {

	static Logger logger = Logger.getLogger(HttpPostNotification.class.getName());

	HttpClient httpclient;
	HttpPost httppost;
	HttpResponse response;
	HttpEntity entity;
	JSONObject body;
	JSONObject notification;

	static HttpPostNotification obj;

	@Autowired
	Environment environment;

	public HttpPostNotification() {
		this.body = new JSONObject();
		this.notification = new JSONObject();
		this.httpclient = HttpClients.createDefault();
		this.httppost = new HttpPost("https://fcm.googleapis.com/fcm/send");
		this.httppost.addHeader("Content-Type", "application/json");
		this.httppost.addHeader("Authorization", "key=AIzaSyBlq5jG6IsN7qfo7xMXdyMhdFGS3a9XRuY");
	}

	public JSONObject getBody() {
		return body;
	}

	public void buildToken(String token, String no_title, String no_body, String icon, String c_action,
			String notification_type, boolean content_available) {
		this.body.put("to", token);
		this.notification.put("title", no_title);
		this.notification.put("body", no_body);
		this.notification.put("icon", icon);
		this.notification.put("click_action", c_action);
		this.notification.put("notificationType", notification_type);
		this.notification.put("content_available", content_available);
	}

	public void buildTopic(String topic, String no_title, String no_body, String icon, String c_action,
			String notification_type, boolean content_available) {
		this.body.put("to", "/topics/" + topic);
		this.notification.put("title", no_title);
		this.notification.put("body", no_body);
		this.notification.put("icon", icon);
		this.notification.put("click_action", c_action);
		this.notification.put("notificationType", notification_type);
		this.notification.put("content_available", content_available);
	}

	public void setTopic(String to) {
		this.body.put("to", "/topics/" + to);
	}

	public void setToken(String to) {
		this.body.put("to", to);
	}

	public void setTitle(String title) {
		this.notification.put("title", title);
	}

	public void setBody(String body) {
		this.notification.put("body", body);
	}

	public void setIcon(String icon) {
		this.notification.put("icon", icon);
	}

	public void setClickAction(String c_action) {
		this.notification.put("click_action", c_action);
	}

	public void setNotificationType(String type) {
		this.notification.put("notificationType", type);
	}

	public void setContentAvailable(boolean status) {
		this.notification.put("content_available", status);
	}

	public boolean sendNotification() {
		try {
			this.body.put("notification", notification);
			HttpEntity stringEntity = new StringEntity(body.toJSONString(), ContentType.APPLICATION_JSON);
			this.httppost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.OK.value()) {
				HttpEntity entity = response.getEntity();
				String content = EntityUtils.toString(entity);
				logger.info("notification send success" + content);
				return true;
			}
			if (statusLine.getStatusCode() == HttpStatus.BAD_REQUEST.value()) {
				logger.error("notification send failed");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
