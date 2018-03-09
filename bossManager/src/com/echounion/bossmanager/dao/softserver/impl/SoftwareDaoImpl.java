package com.echounion.bossmanager.dao.softserver.impl;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import com.echounion.bossmanager.dao.impl.BaseDao;
import com.echounion.bossmanager.dao.softserver.ISoftwareDao;
import com.echounion.bossmanager.entity.EsbSoftWare;

@Repository
public class SoftwareDaoImpl extends BaseDao<EsbSoftWare> implements ISoftwareDao {


	@Override
	public int delSoftware(Integer... softId) {
		int result=0;
		try
		{
			result=removeObject(softId,"id");
			super.getSqlQuery("delete from esb_permissions where soft_id in(:softIds)").setParameterList("softIds",softId).executeUpdate();
			super.getSqlQuery("delete from esb_service_dir where soft_id in(:softIds)").setParameterList("softIds",softId).executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException(e);
		}
		return result;
	}
}