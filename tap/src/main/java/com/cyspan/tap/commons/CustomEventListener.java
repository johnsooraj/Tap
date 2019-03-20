package com.cyspan.tap.commons;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cyspan.tap.group.services.GroupServices;
import com.cyspan.tap.security.model.SecurityPermissions;
import com.cyspan.tap.subscription.dao.OrganizationMemberDao;
import com.cyspan.tap.subscription.service.OrganizationService;
import com.cyspan.tap.user.dao.UsersDao;

@Component
public class CustomEventListener {

	static Logger logger = Logger.getLogger(CustomEventListener.class.getName());

	@Autowired
	GroupServices groupServices;

	@Autowired
	OrganizationService organizationMemberService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	OrganizationMemberDao memberDao;

	@Autowired
	UsersDao userDao;

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) throws FileNotFoundException {
		groupServices.generateUniqueId();
		organizationMemberService.insertDummyData();
		userDao.setObjectiveWordsToServletContext();
		if (servletContext.getAttribute("securityPermissions") == null) {
			Map<Long, SecurityPermissions> permissionMap = new HashMap<>();
			List<SecurityPermissions> allList = memberDao.fetchAllPermissions();
			if (!allList.isEmpty()) {
				allList.forEach(permission -> {
					permissionMap.put(permission.getSecurityPermissionId(), permission);
				});
			}
			logger.info(permissionMap.size() + " permissions added into servlet context");
			servletContext.setAttribute("securityPermissions", permissionMap);
		}
	}

}
