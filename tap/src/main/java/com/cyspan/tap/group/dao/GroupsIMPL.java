package com.cyspan.tap.group.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyspan.tap.group.model.GroupmembersModel;
import com.cyspan.tap.group.model.GroupsModel;

@Repository
@Transactional
public class GroupsIMPL implements GroupsDAO {

	static Logger logger = Logger.getLogger(GroupsIMPL.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public GroupsModel createGroup(GroupsModel groupsModel) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(groupsModel);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return groupsModel;
	}

	@Override
	public GroupsModel updateGroup(GroupsModel groupsModel) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(groupsModel);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new UsernameNotFoundException("user alrady a member");
		}
		return groupsModel;
	}

	@Override
	public GroupsModel getGroupModelUsingId(Integer groupId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GroupsModel.class);
		criteria.add(Restrictions.eq("groupId", groupId));
		return (GroupsModel) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupsModel> fetchEmptyUniqueIdRow() {
		return sessionFactory
				.getCurrentSession().createCriteria(GroupsModel.class).add(Restrictions.disjunction()
						.add(Restrictions.eq("groupUniqueName", "")).add(Restrictions.isNull("groupUniqueName")))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupmembersModel> fetchAllGroupMembers() {
		return sessionFactory.getCurrentSession().createCriteria(GroupmembersModel.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupmembersModel> fetchAllNotSubscribedGroupMembers() {
		return sessionFactory.getCurrentSession().createCriteria(GroupmembersModel.class)
				.add(Restrictions.isNull("notificationSubscription")).list();
	}

	@Override
	public Boolean checkTheUserInAGroup(Integer userId, GroupsModel groupsModel) {
		return sessionFactory.getCurrentSession().createQuery("from GroupmembersModel where userId=? and groupsModel=?")
				.setInteger(0, userId).setEntity(1, groupsModel).list().isEmpty();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GroupsModel> fetchAllGroupModelByUserId(Integer userId) {
		String sql = "SELECT DISTINCT aa.groupid, \r\n" + 
				"                aa.groupname, \r\n" + 
				"                aa.groupadminid, \r\n" + 
				"                aa.groupiconurl, \r\n" + 
				"                aa.groupiconbigurl, \r\n" + 
				"                aa.createdate, \r\n" + 
				"                aa.timestamp \r\n" + 
				"FROM   (SELECT DISTINCT tgg.groupid         AS groupId, \r\n" + 
				"                        tgg.groupname       AS groupName, \r\n" + 
				"                        tgg.groupadminid    AS groupAdminId, \r\n" + 
				"                        tgg.group_icon      AS groupIconUrl, \r\n" + 
				"                        tgg.groupiconbigurl AS groupIconBigUrl, \r\n" + 
				"                        tgg.createdate      AS createDate, \r\n" + 
				"                        tgg.timestamp       AS timestamp, \r\n" + 
				"                        tgps.createddate    AS pollCreateDate \r\n" + 
				"        FROM   tap.groupmembers tgm \r\n" + 
				"               LEFT JOIN tap.groups tgg \r\n" + 
				"                      ON tgm.groupid = tgg.groupid \r\n" + 
				"               LEFT JOIN tap.group_polls tgps \r\n" + 
				"                      ON tgg.groupid = tgps.group_id \r\n" + 
				"        WHERE  tgm.userid = ? \r\n" + 
				"        ORDER  BY pollcreatedate DESC) aa; ";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addScalar("groupId").addScalar("groupName").addScalar("groupAdminId")
				.addScalar("groupIconUrl").addScalar("groupIconBigUrl").addScalar("createDate").addScalar("timestamp");
		query.setInteger(0, userId);
		List<GroupsModel> list = query.setResultTransformer(Transformers.aliasToBean(GroupsModel.class)).list();
		logger.info("group by user count "+list.size());
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GroupmembersModel> fetchGroupMembersByGroupId(Integer groupId) {
		List<GroupmembersModel> groupmembersModels = sessionFactory.getCurrentSession()
				.createCriteria(GroupmembersModel.class).add(Restrictions.eq("groupsModel", new GroupsModel(groupId)))
				.list();
		return groupmembersModels;
	}

	@Override
	public GroupsModel fetchGroupByGroupId(Integer groupId) {
		return (GroupsModel) sessionFactory.getCurrentSession().get(GroupsModel.class, groupId);
	}

	@Override
	public boolean deleteGroupByGroupId(Integer groupId) {
		GroupsModel group = (GroupsModel) sessionFactory.getCurrentSession().get(GroupsModel.class, groupId);
		if (group != null) {
			group.getGroupMembers().clear();
			sessionFactory.getCurrentSession().delete(group);
		}
		return true;
	}

	@Override
	public boolean deleteGroupMemberByUserId(Integer groupId, Integer userId) {
		GroupmembersModel groupmembersModel = (GroupmembersModel) sessionFactory.getCurrentSession()
				.createCriteria(GroupmembersModel.class).add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("groupsModel", new GroupsModel(groupId))).uniqueResult();
		if (groupmembersModel != null) {
			groupmembersModel.setGroupsModel(null);
			sessionFactory.getCurrentSession().delete(groupmembersModel);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GroupsModel> fetchAllGroup() {
		return sessionFactory.getCurrentSession().createCriteria(GroupsModel.class).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GroupmembersModel> fetchAllGroupsByUserId(Integer userId) {
		return sessionFactory.getCurrentSession().createCriteria(GroupmembersModel.class)
				.add(Restrictions.eq("userId", userId)).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> fetchAllGroupuniqueIdByGroupIds(Integer userId) {
		String querySQL = "select gr.groupUniqueName from groups gr join groupmembers grm on gr.groupId=grm.GroupId where grm.UserId = ?";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(querySQL);
		List<String> list = query.setInteger(0, userId).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchDataForExpireNotification(LocalDateTime now, LocalDateTime then) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String queryString = "select grpol.group_id, pol.Question, gr.groupUniqueName, gr.GroupName, pol.poll_id, gr.Group_Icon from tap.polls pol\r\n"
				+ "join tap.group_polls grpol on pol.poll_id = grpol.poll_id\r\n"
				+ "join tap.groups gr on grpol.group_id = gr.GroupId\r\n"
				+ "where pol.ExpiresOn >= ? and pol.ExpiresOn <= ? and grpol.group_id > 0;";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(queryString);
		query.setString(1, now.format(formatter));
		query.setString(0, then.format(formatter));
		List<Object[]> list = query.list();
		return list;
	}

}
