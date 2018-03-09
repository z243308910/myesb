package com.echounion.bossmanager.dao.softserver.impl;

import org.springframework.stereotype.Repository;

import com.echounion.bossmanager.dao.impl.BaseDao;
import com.echounion.bossmanager.dao.softserver.IServiceDirDao;
import com.echounion.bossmanager.entity.EsbServiceDir;

@Repository
public class ServiceDirDaoImpl extends BaseDao<EsbServiceDir> implements IServiceDirDao {

	@Override
	public int delServiceDirBySoftId(Integer... softId) {
		String hql="delete from EsbServiceDir as dir where dir.softId in(:softId)";
		int count=super.getQuery(hql).setParameterList("softId",softId).executeUpdate();
		return count;
	}

}
