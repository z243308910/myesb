package com.echounion.bossmanager.dao.email.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.echounion.bossmanager.dao.email.IEmailHistoryDao;
import com.echounion.bossmanager.dao.impl.BaseDao;
import com.echounion.bossmanager.entity.MailHistory;
import com.echounion.bossmanager.entity.MailList;

@SuppressWarnings("unchecked")
@Repository
public class EmailHistoryDaoImpl extends BaseDao<MailHistory> implements IEmailHistoryDao {

	public int getRegCount() {
		String hql="select count(ml) from MailList ml";
		return ((Number)super.getQuery(hql).uniqueResult()).intValue();
	}

	public List<MailList> getRegMail() {
		String hql="from MailList order by id desc";
		return super.getQuery(hql).list();
	}

	public int delRegMail(Integer[] ids) {
		String hql="delete from MailList ml where ml.id in(:ids)";
		int count=super.getQuery(hql).setParameterList("ids",ids).executeUpdate();
		return count;
	}

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

	public List<MailHistory> getMailHistory(Map<String, Object> params) {
		Criteria c=super.createCriteria();
		c.addOrder(Order.desc("id"));
		for (String key:params.keySet()) {
			if(StringUtils.isEmpty(String.valueOf(params.get(key))))
			{
				continue;
			}
			if(key.equals("emailAddress"))
			{
				c.add(Restrictions.like(key,String.valueOf(params.get(key)),MatchMode.ANYWHERE));
				continue;
			}
			c.add(Restrictions.eq(key,params.get(key)));
		}
		return c.list();
	}


}
