package com.echounion.bossmanager.dao.softserver.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.echounion.bossmanager.dao.impl.BaseDao;
import com.echounion.bossmanager.dao.softserver.ICsFileLogDao;
import com.echounion.bossmanager.entity.CargosmartFileRoutingLog;

@Repository
public class CsFileLogDaoImpl extends BaseDao<CargosmartFileRoutingLog> implements ICsFileLogDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<CargosmartFileRoutingLog> getCsFileLogs(String fileName, Date effectDate, Date expireDate) {
		Criteria criteria=createCriteria();
		if(StringUtils.isNotEmpty(fileName))
		{
			criteria.add(Restrictions.ilike("fileName", fileName, MatchMode.START));
		}
		if(effectDate != null&&expireDate!=null)
		{
			criteria.add(Restrictions.between("opTime",effectDate,expireDate));
		}
		return criteria.list();
	}

	@Override
	public Integer getCsFileLogCount(String fileName, Date effectDate, Date expireDate) {
		Criteria criteria=createCriteria();
		if(StringUtils.isNotEmpty(fileName))
		{
			criteria.add(Restrictions.ilike("fileName", fileName, MatchMode.START));
		}
		if(effectDate != null&&expireDate!=null)
		{
			criteria.add(Restrictions.between("opTime",effectDate,expireDate));
		}
		return ((Number)(criteria.setProjection(Projections.rowCount()).uniqueResult())).intValue();
	}
}
