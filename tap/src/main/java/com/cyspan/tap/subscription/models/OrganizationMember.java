package com.cyspan.tap.subscription.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cyspan.tap.security.model.SecurityPermissions;
import com.cyspan.tap.security.model.SecurityRoles;
import com.cyspan.tap.security.model.UserTypes;
import com.cyspan.tap.user.model.UsersModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class OrganizationMember {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long memberId;

	private int memberType;
	private boolean notificationEnable = false;
	private boolean showResetPassword = true;
	private boolean isAdmin = false;

	@UpdateTimestamp
	private Date timestamp;

	@CreationTimestamp
	private Date createDateTime;

	@OneToOne(cascade = CascadeType.ALL)
	private UsersModel userData;

	@JsonIgnoreProperties("members")
	@ManyToOne
	private OrganizationModel organization;

	@OneToOne(cascade = CascadeType.ALL)
	private MemberCredentials credentials;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "memberId"), inverseJoinColumns = @JoinColumn(name = "permissionId"))
	private Set<SecurityPermissions> userPermissions = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "memberId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<SecurityRoles> userRoles = new HashSet<>();

	public OrganizationMember() {
		super();
	}

	public OrganizationMember(Long memberId) {
		super();
		this.memberId = memberId;
	}

	public OrganizationMember(UsersModel userData) {
		super();
		this.userData = userData;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	public boolean isNotificationEnable() {
		return notificationEnable;
	}

	public void setNotificationEnable(boolean notificationEnable) {
		this.notificationEnable = notificationEnable;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public UsersModel getUserData() {
		return userData;
	}

	public void setUserData(UsersModel userData) {
		this.userData = userData;
	}

	public MemberCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(MemberCredentials credentials) {
		this.credentials = credentials;
	}

	public boolean isShowResetPassword() {
		return showResetPassword;
	}

	public void setShowResetPassword(boolean showResetPassword) {
		this.showResetPassword = showResetPassword;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<SecurityPermissions> getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(Set<SecurityPermissions> userPermissions) {
		this.userPermissions = userPermissions;
	}

	public Set<SecurityRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<SecurityRoles> userRoles) {
		this.userRoles = userRoles;
	}

	public void setUserType(UserTypes types) {
		switch (types) {
		case Administrator:
			this.setMemberType(UserTypes.Administrator.getUserTypeId());
			this.isAdmin = true;
			break;
		case GuestUser:
			this.setMemberType(UserTypes.GuestUser.getUserTypeId());
			this.isAdmin = false;
			break;
		case RegistredUser:
			this.setMemberType(UserTypes.RegistredUser.getUserTypeId());
			this.isAdmin = false;
			break;
		case SuperAdministrator:
			this.setMemberType(UserTypes.SuperAdministrator.getUserTypeId());
			this.isAdmin = true;
			break;
		default:
			break;

		}
	}

	@Override
	public String toString() {
		return "OrganizationMember [memberId=" + memberId + ", memberType=" + memberType + ", notificationEnable="
				+ notificationEnable + ", showResetPassword=" + showResetPassword + ", isAdmin=" + isAdmin
				+ ", timestamp=" + timestamp + ", createDateTime=" + createDateTime + ", userData=" + userData
				+ ", credentials=" + credentials + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userData == null) ? 0 : userData.hashCode());
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
		OrganizationMember other = (OrganizationMember) obj;
		if (userData == null) {
			if (other.userData != null)
				return false;
		} else if (!userData.equals(other.userData))
			return false;
		return true;
	}

}
