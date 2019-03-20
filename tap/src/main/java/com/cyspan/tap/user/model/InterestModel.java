package com.cyspan.tap.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "interests")
public class InterestModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "InterestId")
	private int interestId;

	@Column(name = "Interest")
	private String interest;

	public InterestModel() {

	}

	public InterestModel(int interestId, String interest) {
		super();
		this.interestId = interestId;
		this.interest = interest;
	}

	public int getInterestId() {
		return interestId;
	}

	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	@Override
	public String toString() {
		return "InterestModel [interestId=" + interestId + ", interest=" + interest + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interest == null) ? 0 : interest.hashCode());
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
		InterestModel other = (InterestModel) obj;
		if (interest == null) {
			if (other.interest != null)
				return false;
		} else if (!interest.equals(other.interest))
			return false;
		return true;
	}

}
