package com.cyspan.tap.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "securityRole", "securityRoleId" }))
public class SecurityRoles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long roleId;
	
	private String securityRole;
	
	private Long securityRoleId;
	
	private String securityRoleUC;
	
	@JsonIgnore
	private String createdBy;

	@JsonIgnore
	@UpdateTimestamp
	private Date timestamp;

	@JsonIgnore
	@CreationTimestamp
	private Date createDateTime;

	public SecurityRoles() {

	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getSecurityRole() {
		return securityRole;
	}

	public void setSecurityRole(String securityRole) {
		this.setSecurityRoleUC(securityRole.toUpperCase());
		this.securityRole = securityRole;
	}

	public Long getSecurityRoleId() {
		return securityRoleId;
	}

	public void setSecurityRoleId(Long securityRoleId) {
		this.securityRoleId = securityRoleId;
	}

	public String getSecurityRoleUC() {
		return securityRoleUC;
	}

	public void setSecurityRoleUC(String securityRoleUC) {
		this.securityRoleUC = securityRoleUC;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

}
