package com.cyspan.tap.subscription.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cyspan.tap.commons.CustomScheduledTask;
import com.cyspan.tap.commons.FileUploadService;
import com.cyspan.tap.commons.UserHandler;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.security.model.SecurityPermissions;
import com.cyspan.tap.security.model.UserTypes;
import com.cyspan.tap.subscription.dao.MemberCredentialsDao;
import com.cyspan.tap.subscription.dao.OrganizationDao;
import com.cyspan.tap.subscription.dao.OrganizationMemberDao;
import com.cyspan.tap.subscription.dao.SubscriptionToolsDao;
import com.cyspan.tap.subscription.models.MemberCredentials;
import com.cyspan.tap.subscription.models.OrganizationMember;
import com.cyspan.tap.subscription.models.OrganizationModel;
import com.cyspan.tap.subscription.models.SubscriptionResponses;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UserAddress;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.utils.CryptoUtil;

@Service
public class SubscriptionServiceImpl
		implements MemberCredentialService, OrganizationService, OrganizationMemberService {

	static Logger logger = Logger.getLogger(SubscriptionServiceImpl.class.getName());

	@Autowired
	MemberCredentialsDao credentialsDao;

	@Autowired
	OrganizationDao orgDao;

	@Autowired
	OrganizationMemberDao memberDao;

	@Autowired
	CryptoUtil cryptoUtil;

	@Autowired
	FileUploadService imageUpload;

	@Autowired
	CustomScheduledTask scheduledTask;

	@Autowired
	UsersDao userdao;

	@Autowired
	SubscriptionToolsDao subToolsDao;

	@Autowired
	private ServletContext servletContext;

	@Override
	public OrganizationMember fetchMemberByUsernameAndPassword(String username, String password) {
		String passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, password);
		MemberCredentials member = credentialsDao.fetchMemberByUsernameAndPassword(username, passwordHash);
		if (member == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return member != null ? member.getMember() : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrganizationMember createOrganizationMember(OrganizationMember member) {
		if (member.getMemberType() == UserTypes.SubAdminstrator.getUserTypeId()) {
			String passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, member.getCredentials().getPassword());
			member.getCredentials().setPassword(passwordHash);
			System.out.println("send mail");
		}
		Map<Long, SecurityPermissions> permissionMap = (Map<Long, SecurityPermissions>) servletContext
				.getAttribute("securityPermissions");
		if (!member.getUserPermissions().isEmpty()) {
			Set<SecurityPermissions> newPermissions = new HashSet<>();
			member.getUserPermissions().forEach(userPermissions -> {
				newPermissions.add(permissionMap.get(userPermissions.getSecurityPermissionId()));
			});
			member.getUserPermissions().clear();
			member.setUserPermissions(newPermissions);
		}
		member.setMemberType(UserTypes.SubAdminstrator.getUserTypeId());
		member.setNotificationEnable(true);
		member.setShowResetPassword(true);
		return memberDao.createOrganizationMember(member);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrganizationMember updateOrganizationMember(OrganizationMember member) {
		Map<Long, SecurityPermissions> permissionMap = (Map<Long, SecurityPermissions>) servletContext
				.getAttribute("securityPermissions");
		if (!member.getUserPermissions().isEmpty()) {
			Set<SecurityPermissions> newPermissions = new HashSet<>();
			member.getUserPermissions().forEach(userPermissions -> {
				newPermissions.add(permissionMap.get(userPermissions.getSecurityPermissionId()));
			});
			member.getUserPermissions().clear();
			member.setUserPermissions(newPermissions);
		}
		return memberDao.updateOrganizationMember(member);
	}

	@Override
	public boolean deleteOrganizationMember(Long id) {
		return memberDao.deleteOrganizationMember(id);
	}

	@Override
	public OrganizationMember fetchOrganizationMemberById(Long id) {
		return memberDao.fetchOrganizationMemberById(id);
	}

	@Override
	public List<OrganizationMember> fetchAllOrganizationMember() {
		return memberDao.fetchAllOrganizationMember();
	}

	@Override
	public OrganizationMember resetPassword(String username, String password, String newPassword) {
		String passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, password);
		MemberCredentials credentials = credentialsDao.fetchMemberByUsernameAndPassword(username, passwordHash);
		if (credentials != null) {
			String newHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, newPassword);
			credentials.setPassword(newHash);
			credentials.getMember().setShowResetPassword(false);
			try {
				credentials = credentialsDao.updateMemberCredentials(credentials);
			} catch (DataIntegrityViolationException e) {
				throw new HibernateException("try another password");
			} catch (Exception e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return credentials.getMember();
	}

	@Override
	public OrganizationModel createOrganization(OrganizationModel model) {

		String password = UserHandler.subscriptionAdminCreadentials();
		String passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, password);

		if (model.getCoverPhotoByte() != null) {
			model.setCoverPhoto(imageUpload.uploadCoverPic(model.getCoverPhotoByte(), "cover"));
		}
		if (model.getProfilePhotoByte() != null) {
			model.setProfilePhoto(imageUpload.uploadPictureBig(model.getProfilePhotoByte(), "profile"));
		}
		if (model.getEmail() != null) {

			MemberCredentials credentials = new MemberCredentials();
			OrganizationMember member = new OrganizationMember();
			member.setAdmin(true);
			member.setMemberType(UserTypes.Administrator.getUserTypeId());
			member.setCredentials(credentials);
			member.setNotificationEnable(false);
			member.setShowResetPassword(true);
			member.setOrganization(model);

			credentials.setMember(member);
			credentials.setUsername(model.getEmail());
			credentials.setPassword(passwordHash);
			model.setMembers(new HashSet<>(Arrays.asList(member)));

		}
		model.setProfilePhotoByte(null);
		model.setCoverPhotoByte(null);
		UserAddress address = new UserAddress();
		model.setOrganizationAddress(address);
		OrganizationModel organization = orgDao.createOrganization(model);
		scheduledTask.sendSubscriptioCredentitals(organization, password);
		return organization;
	}

	@Override
	public OrganizationModel updateOrganization(OrganizationModel model) {
		if (model.getOrganizationAddress() != null) {
			if (model.getOrganizationAddress().getAddressId() == null) {
				model.setOrganizationAddress(orgDao.createUserOrganizationAddress(model.getOrganizationAddress()));
			} else {
				model.setOrganizationAddress(orgDao.updateUserOrganizationAddress(model.getOrganizationAddress()));
			}
		}
		if (model.getCoverPhotoByte() != null) {
			model.setCoverPhoto(imageUpload.uploadCoverPic(model.getCoverPhotoByte(), "cover"));
		}
		if (model.getProfilePhotoByte() != null) {
			model.setProfilePhoto(imageUpload.uploadPictureBig(model.getProfilePhotoByte(), "profile"));
		}
		orgDao.updateOrganizationHQLQuery(model);
		return model;
	}

	@Override
	public boolean deleteOrganization(Long id) {
		return orgDao.deleteOrganization(id);
	}

	@Override
	public OrganizationModel fetchOrganizationById(Long organizationId) {
		return orgDao.fetchOrganizationById(organizationId);
	}

	@Override
	public List<OrganizationModel> fetchAllOrganization() {
		return orgDao.fetchAllOrganization();
	}

	@Override
	public void insertDummyData() {
		memberDao.insertDummyData();
	}

	@Override
	public boolean deleteMultipleOrganization(List<Long> ids) {
		try {
			ids.forEach(id -> {
				orgDao.deleteOrganization(id);
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public OrganizationModel fetchOrganizationByMemberId(Long id) {
		return orgDao.fetchOrganizationByMemberId(id);
	}

	@Override
	public Object fetchAndUpdateOrganization(Long id, Map<String, String> data) {

		OrganizationModel orModel = orgDao.fetchOrganizationById(id);
		OrganizationMember member = new OrganizationMember();
		String email = data.get("email");
		String phone = data.get("phone");
		UsersModel user = null;

		if (email != null) {
			user = userdao.fetchUserByEmail(email);
		}
		if (phone != null) {
			user = userdao.getUsersByMobileNum(phone);
		}

		if (user == null) {
			user = new UsersModel();
			user.setFirstName("-");
			user.setEmail(email);
			user.setPhone(phone);
			user.setAccountType(UserTypes.GuestUser.getUserTypeIdInString());
			user.setStatus(Constants.USER_INACTIVE_STATUS);
			userdao.createUser(user);
		}

		member.setAdmin(false);
		member.setShowResetPassword(false);
		member.setNotificationEnable(true);
		if (user.getStatus() == Constants.USER_ACTIVE_STATUS) {
			member.setMemberType(UserTypes.RegistredUser.getUserTypeId());
		} else {
			member.setMemberType(UserTypes.GuestUser.getUserTypeId());
		}
		member.setUserData(user);
		member.setOrganization(orModel);

		if (orModel.getMembers() != null) {
			if (!orModel.getMembers().contains(member)) {
				orModel.getMembers().add(member);
			} else {
				throw new IllegalArgumentException("The user is already a member.");
			}
		} else {
			orModel.setMembers(new HashSet<>(Arrays.asList(member)));
		}

		try {
			return orgDao.updateOrganization(orModel);
		} catch (DataIntegrityViolationException e) {
			throw new HibernateException("The user is already a member.");
		} catch (Exception e) {
			throw new HibernateException("The user is already a member.");
		}
	}

	@Override
	public List<Long> deleteUsersFromOrganization(List<Long> list) {
		List<Long> ids = new ArrayList<>();
		list.forEach(id -> {
			try {
				if (memberDao.deleteOrganizationMemberHQLquery(id)) {
					logger.info("organization member with id :" + id + " remove success");
					ids.add(id);
				} else {
					logger.error("organization member with id :" + id + " remove failed");
				}
			} catch (Exception e) {
				logger.error("organization member with id :" + id + " remove error");
				logger.error(e.getMessage());
			}
		});
		return ids;
	}

	@Override
	public Set<OrganizationModel> fetchOrganizationModelByUserId(Integer userId) {
		return orgDao.fetchOrganizationModelByUserId(userId);
	}

	static int pcount = 5;

	@Override
	public Object fetchSubscriptionToolsByOrgId(Long id, int pageno, Integer userId, String text) {
		int start = pcount * pageno;
		int end = start + pcount;
		return subToolsDao.fetchSubscriptionToolsByOrgId(id, start, end, userId, text);
	}

	@Override
	public Long deleteOrganizationMemberByUserId(Integer userId) {
		Long id = memberDao.fetchOrganizationMemberIdByUserId(userId);
		if (id != null) {
			this.deleteUsersFromOrganization(Arrays.asList(id));
		}
		return null;
	}

	@Override
	public boolean resetOrganizationPassword(Long id) {
		boolean status = false;
		String password = UserHandler.subscriptionAdminCreadentials();
		String passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, password);

		OrganizationModel org = this.fetchOrganizationById(id);
		OrganizationMember member = org.getMembers().stream()
				.filter(obj -> obj.getMemberType() == UserTypes.Administrator.getUserTypeId()).findFirst().get();
		status = memberDao.updateMemberPasswordAndShowResetPw(member.getCredentials(), passwordHash,
				member.getMemberId());
		if (status) {
			// send mail
			scheduledTask.sendSubscriptioCredentitals(org, password);
			return true;
		}
		return false;
	}

	@Override
	public void saveResponseDetail(SubscriptionResponses obj) {
		this.orgDao.saveResponseDetail(obj);
	}

}
