package com.cyspan.tap.user.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long addressId;

	private String address;
	private Long country_id;
	private Long zip;
	private Long city_id;
	private Long state_id;
	private String contact_number;
	private String website;
	private String description;

	public UserAddress() {

	}

	public UserAddress(Long addressId, String address, Long country_id, Long zip, Long city_id, Long state_id) {
		super();
		this.addressId = addressId;
		this.address = address;
		this.country_id = country_id;
		this.zip = zip;
		this.city_id = city_id;
		this.state_id = state_id;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public Long getZip() {
		return zip;
	}

	public void setZip(Long zip) {
		this.zip = zip;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "UserAddress [addressId=" + addressId + ", address=" + address + ", country_id=" + country_id + ", zip="
				+ zip + ", city_id=" + city_id + ", state_id=" + state_id + "]";
	}

}
