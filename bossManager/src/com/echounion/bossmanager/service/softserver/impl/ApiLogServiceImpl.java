package com.echounion.bossmanager.service.softserver.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.dao.softserver.IApiLogDao;
import com.echounion.bossmanager.entity.EsbApiLog;
import com.echounion.bossmanager.service.softserver.IApiLogService;

@Service
@ActionModel(description="ESB日志")
@Transactional(propagation=Propagation.SUPPORTS)
public class ApiLogServiceImpl implements IApiLogService{


	@Autowired
	IApiLogDao esbApiLogDao;
	
	@Override
	public List<EsbApiLog> getAll() {
		return esbApiLogDao.getAll();
	}

	@Override
	public int getAllCount() {
		return esbApiLogDao.getCount();
	}
	

	@Override
	public EsbApiLog getUniqueById(Integer id) {
		return esbApiLogDao.getUniqueByProperty("id", id);
	}

	@Override
	@Transactional
	@ActionModel(description="删除API日志")
	public int delLogs(Integer[] ids) {
		return esbApiLogDao.removeObject(ids, "id");
	}

	@Override
	public List<EsbApiLog> getApiLogs(Integer softId, Integer serviceId, Date effectDate, Date expireDate) {
		return esbApiLogDao.getApiLogs(softId, serviceId, effectDate, expireDate);
	}

	@Override
	public Integer getApiLogCount(Integer softId, Integer serviceId, Date effectDate, Date expireDate) {
		return esbApiLogDao.getApiLogCount(softId, serviceId, effectDate, expireDate);
	}
	
}
