package com.cyspan.tap.security.model;

import java.util.Date;

import javax.persistence.Column;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "permission", "securityPermissionId" }))
public class SecurityPermissions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long permissionId;
	private String permission;

	@Column(unique = true)
	private Long securityPermissionId;
	private String permissionUC;

	@JsonIgnore
	private String createdBy;

	@JsonIgnore
	@UpdateTimestamp
	private Date timestamp;

	@JsonIgnore
	@CreationTimestamp
	private Date createDateTime;

	public SecurityPermissions() {

	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.setPermissionUC(permission.toUpperCase());
		this.permission = permission;
	}

	public Long getSecurityPermissionId() {
		return securityPermissionId;
	}

	public void setSecurityPermissionId(Long securityPermissionId) {
		this.securityPermissionId = securityPermissionId;
	}

	public String getPermissionUC() {
		return permissionUC;
	}

	public void setPermissionUC(String permissionUC) {
		this.permissionUC = permissionUC;
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
