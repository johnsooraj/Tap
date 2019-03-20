package com.cyspan.tap.notifications;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class NotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String body;

	@JsonIgnore
	private String click_action;

	private String notificationType;
	private String icon;

	@CreationTimestamp
	private Timestamp timestamp;
	private boolean readStatus;
	private Integer userId;

	public NotificationEntity() {

	}

	public NotificationEntity(String title, String body, String click_action, String notificationType, String icon,
			Integer userId) {
		super();
		this.title = title;
		this.body = body;
		this.click_action = click_action;
		this.notificationType = notificationType;
		this.icon = icon;
		this.userId = userId;
	}

	public NotificationEntity(String title, String body, String notificationType, String icon, Integer userId) {
		super();
		this.title = title;
		this.body = body;
		this.notificationType = notificationType;
		this.icon = icon;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isReadStatus() {
		return readStatus;
	}

	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getClick_action() {
		return click_action;
	}

	public void setClick_action(String click_action) {
		this.click_action = click_action;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
