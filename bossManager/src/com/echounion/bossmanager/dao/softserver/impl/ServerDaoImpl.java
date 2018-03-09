package com.echounion.bossmanager.dao.softserver.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.echounion.bossmanager.dao.impl.BaseDao;
import com.echounion.bossmanager.dao.softserver.IServerDao;
import com.echounion.bossmanager.entity.EsbServer;


@SuppressWarnings("unchecked")
@Repository
public class ServerDaoImpl extends BaseDao<EsbServer> implements IServerDao {

	public List<EsbServer> getServerBySoftId(int softId, boolean flag) {
		String hql=null;
		List<EsbServer> list=null;
		if(flag)		//查询出所有的服务器 包括已经禁用掉的
		{
			hql="select sv from Server sv where sv.id in(select dv.serverId from SoftDeploy dv where dv.softId=:sid)";
		}
		else
		{
			hql="select sv from Server sv where sv.id in(select dv.serverId from SoftDeploy dv where dv.softId=:sid and dv.disabled=false and dv.deleted=false)";
		}
		list=super.getQuery(hql).setParameter("sid",softId).list();
		return list;
	}

	public int getServerBySoftCount(int softId, boolean flag) {
		String hql=null;
		int count=0;
		if(flag)		//查询出所有的服务器 包括已经禁用掉的
		{
			hql="select count(sv) from Server sv where sv.id in(select dv.serverId from SoftDeploy dv where dv.softId=:sid)";
		}
		else
		{
			hql="select count(sv) from Server sv where sv.id in(select dv.serverId from SoftDeploy dv where dv.softId=:sid and dv.disabled=false and dv.deleted=false)";
		}
		count=((Long)super.getQuery(hql).setParameter("sid",softId).uniqueResult()).intValue();
		return count;
	}

}
