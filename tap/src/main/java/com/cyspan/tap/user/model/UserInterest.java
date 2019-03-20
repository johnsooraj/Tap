package com.cyspan.tap.user.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class UserInterest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userInterestId;

	@Column(name = "InterestId", nullable = false)
	private int interestId;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private UsersModel usersModel;

	@Transient
	private Long userId;

	public UserInterest() {

	}

	public UserInterest(Long userInterestId, int interestId, UsersModel usersModel, Long userId) {
		super();
		this.userInterestId = userInterestId;
		this.interestId = interestId;
		this.usersModel = usersModel;
		this.userId = userId;
	}

	public Long getUserInterestId() {
		return userInterestId;
	}

	public void setUserInterestId(Long userInterestId) {
		this.userInterestId = userInterestId;
	}

	public int getInterestId() {
		return interestId;
	}

	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}

	public UsersModel getUsersModel() {
		return usersModel;
	}

	public void setUsersModel(UsersModel usersModel) {
		this.usersModel = usersModel;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserInterest [userInterestId=" + userInterestId + ", interestId=" + interestId + ", usersModel="
				+ usersModel + ", userId=" + userId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + interestId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInterest other = (UserInterest) obj;
		if (interestId != other.interestId)
			return false;
		return true;
	}

}
