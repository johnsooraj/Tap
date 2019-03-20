package com.cyspan.tap.user.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserCity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cityId;

	private String cityName;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)  
	private UserState userState;

	public UserCity() {

	}

	public UserCity(Long cityId, String cityName) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public UserState getUserState() {
		return userState;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}

	@Override
	public String toString() {
		return "UserCity [cityId=" + cityId + ", cityName=" + cityName + "]";
	}

}
