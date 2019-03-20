package com.cyspan.tap.user.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class UserState {

	@Id
	@GeneratedValue
	private Long stateId;

	private String stateName;

	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userState")
	private Set<UserCity> userCities;

	public UserState() {

	}

	public UserState(Long stateId, String stateName, Set<UserCity> userCities) {
		super();
		this.stateId = stateId;
		this.stateName = stateName;
		this.userCities = userCities;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Set<UserCity> getUserCities() {
		return userCities;
	}

	public void setUserCities(Set<UserCity> userCities) {
		this.userCities = userCities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
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
		UserState other = (UserState) obj;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserState [stateId=" + stateId + ", stateName=" + stateName + ", userCities=" + userCities + "]";
	}

}
