package com.echounion.boss.logs.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echounion.boss.entity.SysLog;
import com.echounion.boss.logs.service.ILogService;
import com.echounion.boss.persistence.system.SysLogMapper;

@Service("SysLogServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
public class SysLogServiceImpl implements ILogService<SysLog> {

	@Autowired
	private SysLogMapper logMapper;

	@Transactional
	public void addLog(final SysLog sysLog) {
		sysLog.setOpDateTime(new Date());
		logMapper.addSysLog(sysLog);
	}

}
