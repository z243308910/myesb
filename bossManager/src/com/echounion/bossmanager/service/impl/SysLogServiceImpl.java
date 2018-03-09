package com.echounion.bossmanager.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.bossmanager.dao.ISysLogDao;
import com.echounion.bossmanager.entity.SysLog;
import com.echounion.bossmanager.service.ISysLogService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class SysLogServiceImpl implements ISysLogService {

	@Autowired
	private ISysLogDao logDao;
	
	@Transactional
	public int delLog(Integer[] logIds) {
		return logDao.removeObject(logIds,"id");
	}

	public List<SysLog> getAllLog() {
		return logDao.getAll();
	}

	public int getCountLog() {
		return logDao.getCount();
	}
}
