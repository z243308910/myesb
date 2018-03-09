package com.echounion.bossmanager.dao.softserver.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.echounion.bossmanager.dao.impl.BaseDao;
import com.echounion.bossmanager.dao.softserver.IApiLogDao;
import com.echounion.bossmanager.entity.EsbApiLog;

@Repository
public class ApiLogDaoImpl extends BaseDao<EsbApiLog> implements IApiLogDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<EsbApiLog> getApiLogs(Integer softId, Integer serviceId, Date effectDate, Date expireDate) {
		Criteria criteria=createCriteria();
		if(softId>0)
		{
			criteria.add(Restrictions.eq("softId",softId));
		}
		if(serviceId>0)
		{
			criteria.add(Restrictions.eq("serviceId",serviceId));
		}
		if(effectDate != null&&expireDate!=null)
		{
			criteria.add(Restrictions.between("invokeTime",effectDate,expireDate));
		}
		return criteria.list();
	}

	@Override
	public Integer getApiLogCount(Integer softId, Integer serviceId, Date effectDate, Date expireDate) {
		Criteria criteria=createCriteria();
		if(softId>0)
		{
			criteria.add(Restrictions.eq("softId",softId));
		}
		if(serviceId>0)
		{
			criteria.add(Restrictions.eq("serviceId",serviceId));
		}
		if(effectDate != null&&expireDate!=null)
		{
			criteria.add(Restrictions.between("invokeTime",effectDate,expireDate));
		}
		return ((Number)(criteria.setProjection(Projections.rowCount()).uniqueResult())).intValue();
	}
	
	
}
