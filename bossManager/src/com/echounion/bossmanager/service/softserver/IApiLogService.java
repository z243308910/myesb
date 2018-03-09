package com.echounion.bossmanager.service.softserver;

import java.util.Date;
import java.util.List;

import com.echounion.bossmanager.entity.EsbApiLog;

public interface IApiLogService {

	public List<EsbApiLog> getAll();
	
	public int getAllCount();
	
	public int delLogs(Integer ids[]);
	
	public EsbApiLog getUniqueById(Integer id);
	
	public List<EsbApiLog> getApiLogs(Integer softId, Integer serviceId, Date effectDate, Date expireDate);
	
	public Integer getApiLogCount(Integer softId, Integer serviceId, Date effectDate, Date expireDate);
	
}
