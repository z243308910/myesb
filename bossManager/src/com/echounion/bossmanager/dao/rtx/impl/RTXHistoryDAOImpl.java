package com.echounion.bossmanager.dao.rtx.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import com.echounion.bossmanager.dao.impl.BaseDao;
import com.echounion.bossmanager.dao.rtx.IRTXHistoryDao;
import com.echounion.bossmanager.entity.RTXHistory;

@SuppressWarnings("unchecked")
@Repository
public class RTXHistoryDAOImpl extends BaseDao<RTXHistory> implements IRTXHistoryDao
{

	@Override
	public List<RTXHistory> getAll(Map<String, Object> params) {
		Criteria c=super.createCriteria();
		c.addOrder(Order.desc("id"));
		for (String key:params.keySet()) {
			if(StringUtils.isEmpty(String.valueOf(params.get(key))))
			{
				continue;
			}
			if(key.equals("receiver"))
			{
				c.add(Restrictions.like(key,String.valueOf(params.get(key)),MatchMode.ANYWHERE));
				continue;
			}			
			c.add(Restrictions.eq(key,params.get(key)));
		}
		return c.list();
	}

	@Override
	public int getCount(Map<String, Object> params) {
		Criteria c=super.createCriteria();
		for (String key:params.keySet()) {
			if(StringUtils.isEmpty(String.valueOf(params.get(key))))
			{
				continue;
			}
			c.add(Restrictions.eq(key,params.get(key)));
		}
		Long count=(Long)c.setProjection(Projections.rowCount()).uniqueResult();
		return count.intValue();
	}
}
