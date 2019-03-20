package com.cyspan.tap.user.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Author Sumesh
 */
/**
 * @author sumesh | Integretz LLC
 * @author john | Integretz LLC
 * 
 */
@Entity
@Table(name = "users")
public class UsersModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "UserId", unique = true, nullable = false)
	private Integer userId;

	@Column(name = "FirstName", length = 50)
	private String firstName;

	@Column(name = "LastName", length = 50)
	private String lastName;

	@Column(name = "Phone", length = 50)
	private String phone;

	@Column(name = "Email", length = 50)
	private String email;

	@JsonIgnore
	@Column(name = "PasswordHash", updatable = false)
	private String passwordHash;

	@Column(name = "Age")
	private Integer age;

	@Column(name = "Gender", length = 10)
	private String gender;

	@Column(name = "Status", length = 3)
	private String status;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedDate", length = 19, insertable = true, updatable = false)
	private Date createdDate;

	@Column(name = "attribute_name", length = 50)
	private String attributeName;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastModifiedDate", length = 19)
	private Date lastModifiedDate;

	@Column(name = "profile_pic")
	private String profilePic;

	@Column(name = "cover_pic")
	private String coverPic;

	@JsonIgnore
	@Column(name = "Token")
	private String token;

	@JsonIgnore
	private String fcmToken;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	@Column(name = "Token_Last_Update", length = 19)
	private Date tokenUpdate;

	@Column(name = "Account_Type")
	private String accountType;

	// @JsonIgnore
	@Column(name = "profile_pic_20")
	private String profilePic20;

	@JsonIgnore
	@Column(name = "cover_pic_20")
	private String coverPic20;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Date_Of_Birth")
	private Date dateOfBirth;

	@OneToOne(cascade = CascadeType.ALL)
	private UserAddress userAddress;

	@JsonInclude(Include.NON_NULL)
	@OneToMany(mappedBy = "usersModel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<UserInterest> userInterest;

	@Transient
	private String password;

	@Transient
	private byte[] profilePhoto;

	@Transient
	private byte[] coverPhoto;

	@Transient
	private Integer createdPollCount;

	@Transient
	private Integer publicPollResponseCount;

	@Transient
	private Integer privatePollResponseCount;

	@Transient
	@JsonIgnore
	private String username;

	@Transient
	private boolean isBlocked;

	private boolean isFilterSearch;

	public UsersModel() {

	}

	public UsersModel(Integer userId) {
		super();
		this.userId = userId;
	}

	public UsersModel(Integer userId, String firstName, String lastName, String phone, String email,
			String passwordHash, Integer age, String gender, String status, Date createdDate, String attributeName,
			Date lastModifiedDate, String profilePic, String coverPic, String token, Date tokenUpdate,
			String accountType, String profilePic20, String coverPic20, Date dateOfBirth, String password,
			byte[] profilePhoto, byte[] coverPhoto, Integer createdPollCount, Integer publicPollResponseCount,
			Integer privatePollResponseCount) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.passwordHash = passwordHash;
		this.age = age;
		this.gender = gender;
		this.status = status;
		this.createdDate = createdDate;
		this.attributeName = attributeName;
		this.lastModifiedDate = lastModifiedDate;
		this.profilePic = profilePic;
		this.coverPic = coverPic;
		this.token = token;
		this.tokenUpdate = tokenUpdate;
		this.accountType = accountType;
		this.profilePic20 = profilePic20;
		this.coverPic20 = coverPic20;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		this.profilePhoto = profilePhoto;
		this.coverPhoto = coverPhoto;
		this.createdPollCount = createdPollCount;
		this.publicPollResponseCount = publicPollResponseCount;
		this.privatePollResponseCount = privatePollResponseCount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenUpdate() {
		return tokenUpdate;
	}

	public void setTokenUpdate(Date tokenUpdate) {
		this.tokenUpdate = tokenUpdate;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getProfilePic20() {
		return profilePic20;
	}

	public void setProfilePic20(String profilePic20) {
		this.profilePic20 = profilePic20;
	}

	public String getCoverPic20() {
		return coverPic20;
	}

	public void setCoverPic20(String coverPic20) {
		this.coverPic20 = coverPic20;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public byte[] getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(byte[] coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public Integer getCreatedPollCount() {
		return createdPollCount;
	}

	public void setCreatedPollCount(Integer createdPollCount) {
		this.createdPollCount = createdPollCount;
	}

	public Integer getPublicPollResponseCount() {
		return publicPollResponseCount;
	}

	public void setPublicPollResponseCount(Integer publicPollResponseCount) {
		this.publicPollResponseCount = publicPollResponseCount;
	}

	public Integer getPrivatePollResponseCount() {
		return privatePollResponseCount;
	}

	public void setPrivatePollResponseCount(Integer privatePollResponseCount) {
		this.privatePollResponseCount = privatePollResponseCount;
	}

	public Set<UserInterest> getUserInterest() {
		return userInterest;
	}

	public void setUserInterest(Set<UserInterest> userInterest) {
		this.userInterest = userInterest;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public boolean isFilterSearch() {
		return isFilterSearch;
	}

	public void setFilterSearch(boolean isFilterSearch) {
		this.isFilterSearch = isFilterSearch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UsersModel other = (UsersModel) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsersModel [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", age=" + age + ", gender=" + gender + ", createdDate=" + createdDate + ", profilePic="
				+ profilePic + ", coverPic=" + coverPic + ", fcmToken=" + fcmToken + ", dateOfBirth=" + dateOfBirth
				+ "]";
	}

}
