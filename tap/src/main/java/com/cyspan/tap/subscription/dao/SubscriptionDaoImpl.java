package com.cyspan.tap.subscription.dao;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.cyspan.tap.commons.CustomScheduledTask;
import com.cyspan.tap.constants.Constants;
import com.cyspan.tap.security.impl.SecurityDao;
import com.cyspan.tap.security.model.SecurityPermissions;
import com.cyspan.tap.security.model.SecurityRoles;
import com.cyspan.tap.security.model.UserTypes;
import com.cyspan.tap.signup.services.SignUpServices;
import com.cyspan.tap.subscription.models.MemberCredentials;
import com.cyspan.tap.subscription.models.OrganizationMember;
import com.cyspan.tap.subscription.models.OrganizationModel;
import com.cyspan.tap.subscription.models.SubscriptionResponses;
import com.cyspan.tap.subscription.service.MemberCredentialService;
import com.cyspan.tap.user.dao.UsersDao;
import com.cyspan.tap.user.model.UserAddress;
import com.cyspan.tap.user.model.UsersModel;
import com.cyspan.tap.utils.CryptoUtil;

@Repository
@Transactional
public class SubscriptionDaoImpl implements MemberCredentialsDao, OrganizationDao, OrganizationMemberDao {

	static Logger logger = Logger.getLogger(SubscriptionDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	UsersDao userDao;

	@Autowired
	SecurityDao securityDao;

	@Autowired
	CryptoUtil cryptoUtil;

	@Autowired
	MemberCredentialService orgCredentialsService;

	@Autowired
	SignUpServices SignUpServices;

	@Autowired
	CustomScheduledTask sendMail;

	@Override
	public MemberCredentials fetchMemberByUsernameAndPassword(String username, String password) {
		Session session = sessionFactory.getCurrentSession();
		try {
			MemberCredentials credentials = (MemberCredentials) session.createCriteria(MemberCredentials.class)
					.add(Restrictions.eq("username", username)).add(Restrictions.eq("password", password))
					.uniqueResult();
			if (credentials != null && credentials.getMember().getOrganization() != null) {
				Hibernate.initialize(credentials.getMember().getOrganization());
				credentials.getMember().getOrganization().setMembers(null);
				credentials.getMember().getOrganization().setNotices(null);
				credentials.getMember().getOrganization().setPolls(null);
				credentials.getMember().getOrganization().setFeedbacks(null);
				credentials.getMember().getOrganization().setFeedbackGroups(null);
			}
			return credentials;
		} catch (Exception e) {
			logger.error("error in login memeber");
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public OrganizationModel createOrganization(OrganizationModel model) {
		Session session = sessionFactory.getCurrentSession();
		session.save(model);
		return model;
	}

	@Override
	public OrganizationMember createOrganizationMember(OrganizationMember member) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(member);
		return member;
	}

	@Override
	public OrganizationModel updateOrganization(OrganizationModel model) {
		Session session = sessionFactory.getCurrentSession();
		session.update(model);
		return model;
	}

	@Override
	public boolean deleteOrganization(Long id) {
		Session session = sessionFactory.getCurrentSession();
		OrganizationModel model = (OrganizationModel) session.get(OrganizationModel.class, id);
		model.getMembers().clear();

		if (model.getMembers() != null && model.getMembers().size() > 0) {
			model.getMembers().forEach(obj -> obj.setUserData(null));
		}

		model.getPolls().clear();
		model.getFeedbacks().clear();
		model.getNotices().clear();
		session.delete(model);
		return false;
	}

	@Override
	public OrganizationModel fetchOrganizationById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		OrganizationModel model = (OrganizationModel) session.get(OrganizationModel.class, id);
		Hibernate.initialize(model.getMembers());
		Hibernate.initialize(model.getNotices());
		Hibernate.initialize(model.getPolls());
		Hibernate.initialize(model.getFeedbacks());
		Hibernate.initialize(model.getFeedbackGroups());
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationModel> fetchAllOrganization() {
		Session session = sessionFactory.getCurrentSession();
		List<OrganizationModel> list = session.createCriteria(OrganizationModel.class)
				.addOrder(Order.desc("createDateTime")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		list.stream().forEach(obj -> {
			obj.setMembers(null);
			obj.setNotices(null);
			obj.setPolls(null);
			obj.setFeedbacks(null);
			obj.setFeedbackGroups(null);
		});
		return list;
	}

	@Override
	public OrganizationMember updateOrganizationMember(OrganizationMember member) {
		Session session = sessionFactory.getCurrentSession();
		OrganizationMember oldModel = (OrganizationMember) session.get(OrganizationMember.class, member.getMemberId());
		if (member.getCredentials() != null) {
			if (member.getCredentials().getPassword() != null) {
				String passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, member.getCredentials().getPassword());
				System.out.println("send mail");
				member.getCredentials().setPassword(passwordHash);
				oldModel.getCredentials().setPassword(passwordHash);
			}
		}

		if (member.getUserData() != null) {
			if (member.getUserData().getEmail() != null) {
				oldModel.getUserData().setEmail(member.getUserData().getEmail());
				oldModel.getCredentials().setUsername(member.getUserData().getEmail());
			}

			if (member.getUserData().getPhone() != null) {
				oldModel.getUserData().setPhone(member.getUserData().getPhone());
			}

			if (member.getUserData().getFirstName() != null) {
				oldModel.getUserData().setFirstName(member.getUserData().getFirstName());
			}
		}

		oldModel.getUserPermissions().clear();
		if (!member.getUserPermissions().isEmpty()) {
			oldModel.setUserPermissions(member.getUserPermissions());
		}
		session.saveOrUpdate(oldModel);
		oldModel.getOrganization().setNotices(null);
		oldModel.getOrganization().setMembers(null);
		oldModel.getOrganization().setFeedbacks(null);
		oldModel.getOrganization().setPolls(null);
		oldModel.getOrganization().setFeedbackGroups(null);
		return oldModel;
	}

	@Override
	public boolean deleteOrganizationMember(Long id) {
		Session session = sessionFactory.getCurrentSession();
		OrganizationMember member = (OrganizationMember) session.get(OrganizationMember.class, id);
		if (member != null) {
			session.delete(member);
			return true;
		}
		return false;
	}

	@Override
	public OrganizationMember fetchOrganizationMemberById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		OrganizationMember member = (OrganizationMember) session.get(OrganizationMember.class, id);
		if (member != null && member.getOrganization() != null) {
			Hibernate.initialize(member.getOrganization());
			member.getOrganization().setMembers(null);
			member.getOrganization().setNotices(null);
			member.getOrganization().setPolls(null);
			member.getOrganization().setFeedbacks(null);
			member.getOrganization().setFeedbackGroups(null);
		}
		return member;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationMember> fetchAllOrganizationMember() {
		Session session = sessionFactory.getCurrentSession();
		List<OrganizationMember> list = session.createCriteria(OrganizationMember.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return list;
	}

	@Override
	public void insertDummyData() {
		try {
			JSONParser parser = new JSONParser();
			List<UsersModel> newUsers = new ArrayList<>();
			List<SecurityRoles> newRoles = new ArrayList<>();
			List<SecurityPermissions> newPermissions = new ArrayList<>();

			InputStream is = new ClassPathResource("/security-file.json").getInputStream();
			JSONObject dummyData = (JSONObject) parser.parse(new InputStreamReader(is, "UTF-8"));

			JSONArray userArray = (JSONArray) dummyData.get("user");
			JSONArray roles = (JSONArray) dummyData.get("security-roles");
			JSONArray permissions = (JSONArray) dummyData.get("security-permission");

			for (Object obj : userArray) {
				JSONObject object = (JSONObject) obj;
				UsersModel um = new UsersModel();
				um.setFirstName(object.get("firstname").toString());
				um.setLastName(object.get("lastname").toString());
				um.setPhone(object.get("phone").toString());
				um.setEmail(object.get("email").toString());
				um.setAge(Integer.parseInt(object.get("age").toString()));
				um.setGender(object.get("gender").toString());
				um.setPassword(object.get("password").toString());
				um.setUsername(object.get("username").toString());
				um.setAccountType(UserTypes.SuperAdministrator.getUserTypeIdInString());
				um.setStatus(2 + "");
				newUsers.add(um);

			}

			for (Object obj : roles) {
				JSONObject object = (JSONObject) obj;
				SecurityRoles myrole = new SecurityRoles();
				myrole.setSecurityRole(object.get("role").toString().replaceAll("\\s+", "_"));
				myrole.setSecurityRoleId(Long.valueOf(object.get("role_id").toString()));
				myrole.setCreatedBy(UserTypes.SuperAdministrator.toString());
				newRoles.add(myrole);
			}

			for (Object obj : permissions) {
				JSONObject object = (JSONObject) obj;
				SecurityPermissions myper = new SecurityPermissions();
				String per = object.get("permission").toString();
				per = per.replaceAll("\\s+", "_");
				myper.setPermission(per);
				myper.setSecurityPermissionId(Long.valueOf(object.get("permission_id").toString()));
				myper.setCreatedBy(UserTypes.SuperAdministrator.toString());
				newPermissions.add(myper);
			}

			// add roles to table
			// add permissions to table
			// save admin to User table
			// create that admin as a new member for subscription :(as super admin)
			// create user(member tbl)-permission table and assign each permission to user
			// create user(member tbl)-role table and add user

			/**
			 * mapping between organization member and role/permission, one to one :
			 * unidirectional member -> role/permission
			 */

			List<SecurityPermissions> freshListPer = securityDao.saveSecurityPermissions(newPermissions);
			List<SecurityRoles> freshListRole = securityDao.saveSecurityRoles(newRoles);

			for (UsersModel model : newUsers) {

				String passwordHash = cryptoUtil.encrypt(Constants.PRIVATE_KEY, model.getPassword());
				MemberCredentials existingMember = this.fetchMemberByUsernameAndPassword(model.getUsername(),
						passwordHash);

				if (existingMember != null) {
					continue;
				}

				UsersModel admin = userDao.createUser(model);
				MemberCredentials adminCred = new MemberCredentials();
				adminCred.setPassword(passwordHash);
				adminCred.setUsername(admin.getUsername());
				OrganizationMember member = new OrganizationMember();
				member.setAdmin(true);
				member.setMemberType(UserTypes.SuperAdministrator.getUserTypeId());
				member.setUserData(admin);
				member.setUserPermissions(new HashSet<>(freshListPer));
				member.setUserRoles(new HashSet<>(Arrays.asList(freshListRole.stream()
						.filter(obj -> obj.getSecurityRoleId() == UserTypes.SuperAdministrator.getUserTypeId())
						.findFirst().get())));
				member.setCredentials(adminCred);
				member.setOrganization(null);
				adminCred.setMember(member);
				this.createOrganizationMember(member);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public MemberCredentials updateMemberCredentials(MemberCredentials credentials) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(credentials);
		return credentials;
	}

	@Override
	public OrganizationModel fetchOrganizationByMemberId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		String query = "select orme.organization from OrganizationMember orme join OrganizationModel ormo on orme.memberId = ormo.organizationId where orme.memberId =:id";
		session.createQuery(query).setParameter("id", id).list();
		return null;
	}

	@Override
	public OrganizationModel saveOrUpdateOrganization(OrganizationModel model) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(model);
		return model;
	}

	@Override
	public MemberCredentials saveOrUpdateMemberCredentials(MemberCredentials model) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(model);
		return model;
	}

	@Override
	public OrganizationMember saveOrUpdateOrganizationMember(OrganizationMember model) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(model);
		return model;
	}

	@Override
	public OrganizationModel updateOrganizationHQLQuery(OrganizationModel model) {
		Session session = sessionFactory.getCurrentSession();

		StringBuffer hqlQuery = new StringBuffer();
		if (model != null) {
			hqlQuery.append("update OrganizationModel set");
		}
		if (model.getAuthorityName() != null) {
			hqlQuery.append(" authorityName='" + model.getAuthorityName() + "',");
		}
		if (model.getEmail() != null) {
			hqlQuery.append(" email='" + model.getEmail() + "',");
		}
		if (model.getOrganizationName() != null) {
			hqlQuery.append(" organizationName='" + model.getOrganizationName() + "',");
		}
		if (model.getCoverPhotoByte() != null) {
			hqlQuery.append(" coverPhoto='" + model.getCoverPhoto() + "',");
			model.setCoverPhotoByte(null);
		}
		if (model.getProfilePhotoByte() != null) {
			hqlQuery.append(" profilePhoto='" + model.getProfilePhoto() + "',");
			model.setProfilePhotoByte(null);
		}
		hqlQuery.replace(0, hqlQuery.length(), hqlQuery.substring(0, hqlQuery.length() - 1));
		hqlQuery.append(" where organizationId=" + model.getOrganizationId() + "");
		Query query = session.createQuery(hqlQuery.toString());

		int a = query.executeUpdate();

		return a > 0 ? model : null;
	}

	@Override
	public boolean deleteOrganizationMemberHQLquery(Long id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "delete from OrganizationMember where memberId=:id";
		Query query = session.createQuery(hql);
		query.setLong("id", id);
		int a = query.executeUpdate();
		return a > 0 ? true : false;
	}

	@Override
	public UserAddress createUserOrganizationAddress(UserAddress address) {
		Session session = sessionFactory.getCurrentSession();
		session.save(address);
		return address;
	}

	@Override
	public UserAddress updateUserOrganizationAddress(UserAddress address) {
		Session session = sessionFactory.getCurrentSession();
		session.update(address);
		return address;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SecurityPermissions> fetchAllPermissions() {
		Session session = sessionFactory.getCurrentSession();
		List<SecurityPermissions> list = session.createCriteria(SecurityPermissions.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<OrganizationModel> fetchOrganizationModelByUserId(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		List<OrganizationMember> mem = session.createCriteria(OrganizationMember.class)
				.add(Restrictions.eq("userData.userId", userId)).list();
		Set<OrganizationModel> neworg = new HashSet<>();
		for (OrganizationMember mm : mem) {
			if (mm.getOrganization() != null) {
				mm.getOrganization().setMembers(null);
				mm.getOrganization().setNotices(null);
				mm.getOrganization().setPolls(null);
				mm.getOrganization().setFeedbacks(null);
				mm.getOrganization().setFeedbackGroups(null);
				neworg.add(mm.getOrganization());
			}
		}
		return neworg;
	}

	@Override
	public Long fetchOrganizationMemberIdByUserId(Integer userid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(OrganizationMember.class);
		criteria.add(Restrictions.eq("userData.userId", userid));
		Projection projection = Projections.property("memberId");
		ProjectionList pList = Projections.projectionList();
		pList.add(projection);
		criteria.setProjection(pList);
		Long id = (Long) criteria.uniqueResult();
		return id;
	}

	@Override
	public boolean updateMemberPasswordAndShowResetPw(MemberCredentials credentials, String newPassword,
			Long memberId) {
		Session session = sessionFactory.getCurrentSession();
		String queryy = "update MemberCredentials set password =:pass where credentialsId=:id";
		Query query = session.createQuery(queryy);
		if (newPassword != null) {
			query.setString("pass", newPassword);
		} else {
			query.setString("pass", credentials.getPassword());
		}
		query.setLong("id", credentials.getCredentialsId());

		if (query.executeUpdate() > 0) {
			String queryyy = "update OrganizationMember set showResetPassword =:value where memberId =:id";
			Query query2 = session.createQuery(queryyy);
			query2.setBoolean("value", true);
			query2.setLong("id", memberId);
			return query2.executeUpdate() > 0 ? true : false;
		} else {
			return false;
		}

	}

	@Override
	public void saveResponseDetail(SubscriptionResponses obj) {
		try {
			sessionFactory.getCurrentSession().save(obj);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
