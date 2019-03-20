package com.cyspan.tap.rsvppoll.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyspan.tap.poll.models.RsvpResponseModel;
import com.cyspan.tap.rsvppoll.model.RsvpPollModel;

@Repository
public class RSVPPollDaoImpl implements RSVPPollDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional
	public Boolean save(RsvpPollModel pollModel) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
		if(pollModel.getRsvpId()==0){
			session.save(pollModel);
		}
		else{
			session.update(pollModel);
		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional
	public List<RsvpPollModel> getRsvpPollModelList() {
		// TODO Auto-generated method stub
		
		List<RsvpPollModel> rsvpPollModelList = new ArrayList<RsvpPollModel>();
		
		List<RsvpPollModel> rsvpPollModelReturnList = new ArrayList<RsvpPollModel>();
		
		List<RsvpResponseModel> rsvpResponseModelList = new ArrayList<RsvpResponseModel>();

		Session session = sessionFactory.getCurrentSession();
		// session.save(object);
		Criteria rsvpPollCriteria = session.createCriteria(RsvpPollModel.class);
		
		rsvpPollModelList = rsvpPollCriteria.list();
		 
		Criteria rsvpResponseCriteria = session.createCriteria(RsvpResponseModel.class);
		
		//rsvpResponseCriteria.add(Restrictions.eq("userId", userId));
		
		
		rsvpResponseModelList = rsvpResponseCriteria.list();
		
		/*for(RsvpPollModel rsvpPollModel : rsvpPollModelList){
			
			RsvpPollModel rsvpPoll = new RsvpPollModel();
			
			List<RsvpResponseModel> rsvpResponseModelList1 = new ArrayList<RsvpResponseModel>();
			rsvpPoll.setRsvpId(rsvpPollModel.getRsvpId());
			rsvpPoll.setAddDate(rsvpPollModel.getAddDate());
			rsvpPoll.setcreatedBy(rsvpPollModel.getcreatedBy());
			rsvpPoll.setDescription(rsvpPollModel.getDescription());
			rsvpPoll.setEndTime(rsvpPollModel.getEndTime());
			rsvpPoll.setEventName(rsvpPollModel.getEventName());
			rsvpPoll.setImageurl(rsvpPollModel.getImageurl());
			rsvpPoll.setLocation(rsvpPollModel.getLocation());
			
			for(int i =0;i<rsvpPollModel.getRsvpPollResponseList().size();i++){
				
				for(int j = 0;j<rsvpResponseModelList.size();j++){
					if(rsvpPollModel.getRsvpPollResponseList().get(i).getRsvpResponseId()==rsvpResponseModelList.get(j).getRsvpResponseId()){
						rsvpResponseModelList1.add(rsvpResponseModelList.get(j));
					}
				}
				
			}
			rsvpPoll.setRsvpPollResponseList(rsvpResponseModelList1);
			
			rsvpPollModelReturnList.add(rsvpPoll);
			
		}*/
		

		return rsvpPollModelList;
	}

	@Override
	@Transactional
	public RsvpPollModel getRsvpPollById(int rsvpPollId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(RsvpPollModel.class);
		
		criteria.add(Restrictions.eq("rsvpId",rsvpPollId));
		
		RsvpPollModel rsvpPollModel = (RsvpPollModel) criteria.uniqueResult();
		
		if(rsvpPollModel!=null){
			return rsvpPollModel;
		}else{
         return new RsvpPollModel();
		}
	
	}

	@Override
	@Transactional
	public Boolean saveRsvpPollResponse(RsvpResponseModel rsvpResponseModel) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(rsvpResponseModel);
		
		
		return Boolean.TRUE;
	}

}
