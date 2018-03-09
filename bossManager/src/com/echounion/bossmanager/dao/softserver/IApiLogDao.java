package com.echounion.bossmanager.dao.softserver;

import java.util.Date;
import java.util.List;

import com.echounion.bossmanager.dao.IBaseDao;
import com.echounion.bossmanager.entity.EsbApiLog;

public interface IApiLogDao extends IBaseDao<EsbApiLog> {

	public List<EsbApiLog> getApiLogs(Integer softId, Integer serviceId, Date effectDate, Date expireDate);
	
	public Integer getApiLogCount(Integer softId, Integer serviceId, Date effectDate, Date expireDate);
}
