package com.echounion.bossmanager.dao.shortmsg.impl;


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
import com.echounion.bossmanager.dao.shortmsg.IShortMsgHistoryDao;
import com.echounion.bossmanager.entity.MobileHistory;
import com.echounion.bossmanager.entity.MobileList;

@SuppressWarnings("unchecked")
@Repository
public class ShortMsgHistoryDaoImpl extends BaseDao<MobileHistory> implements IShortMsgHistoryDao {

	public int delRegMobile(Integer[] ids) {
		String hql="delete from MobileList m where m.id in(:ids)";
		return super.getQuery(hql).setParameterList("ids",ids).executeUpdate();
	}

	public int getRegCount() {
		String hql="select count(m) from MobileList m";
		int count=((Number)super.getQuery(hql).uniqueResult()).intValue();
		return count;
	}

	@Override
	public List<MobileHistory> getAll(Map<String,Object> params) {
		Criteria c=super.createCriteria();
		c.addOrder(Order.desc("id"));
		for (String key:params.keySet()) {
			if(StringUtils.isEmpty(String.valueOf(params.get(key))))
			{
				continue;
			}
			if(key.equals("phoneNo"))
			{
				c.add(Restrictions.like(key,String.valueOf(params.get(key)),MatchMode.ANYWHERE));
				continue;
			}
			c.add(Restrictions.eq(key,params.get(key)));
		}
		return c.list();
	}

	@Override
	public int getCount(Map<String,Object> params) {
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

	public List<MobileList> getRegMobile() {
		String hql="from MobileList m order by id desc";
		return super.getQuery(hql).list();
	}

}
