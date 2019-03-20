package com.cyspan.tap.security.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.security.model.SecurityPermissions;
import com.cyspan.tap.security.model.SecurityRoles;

@Repository
@Transactional
public class SecurityDaoImpl implements SecurityDao {

	static Logger logger = Logger.getLogger(SecurityDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<SecurityRoles> saveSecurityRoles(List<SecurityRoles> roles) {

		List<SecurityRoles> newListOfRole = new ArrayList<SecurityRoles>();
		Session session = sessionFactory.getCurrentSession();
		for (SecurityRoles roles2 : roles) {
			try {
				SecurityRoles obj = (SecurityRoles) session.createCriteria(SecurityRoles.class)
						.add(Restrictions.eq("securityRole", roles2.getSecurityRole()))
						.add(Restrictions.eq("securityRoleId", roles2.getSecurityRoleId())).uniqueResult();
				if (obj == null) {
					session.save(roles2);
					newListOfRole.add(roles2);
				} else {
					newListOfRole.add(obj);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return newListOfRole;
	}

	@Override
	public List<SecurityPermissions> saveSecurityPermissions(List<SecurityPermissions> permissions) {

		List<SecurityPermissions> newListOfPer = new ArrayList<SecurityPermissions>();
		Session session = sessionFactory.getCurrentSession();
		for (SecurityPermissions per : permissions) {
			try {
				SecurityPermissions obj = (SecurityPermissions) session.createCriteria(SecurityPermissions.class)
						.add(Restrictions.eq("permission", per.getPermission()))
						.add(Restrictions.eq("securityPermissionId", per.getSecurityPermissionId())).uniqueResult();
				if (obj == null) {
					session.save(per);
					newListOfPer.add(per);
				} else {
					newListOfPer.add(obj);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return newListOfPer;

	}

}
