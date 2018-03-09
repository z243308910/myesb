package com.echounion.boss.logs.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.boss.entity.EsbApiLog;
import com.echounion.boss.logs.service.ILogService;
import com.echounion.boss.persistence.esb.EsbApiLogMapper;

/**
 * API调用日志业务类
 * @author 胡礼波
 * 2013-7-8 下午2:18:21
 */
@Service("EsbApiLogServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
public class EsbApiLogServiceImpl implements ILogService<EsbApiLog> {

	private Logger logger=Logger.getLogger(EsbApiLogServiceImpl.class);
	
	@Autowired
	private EsbApiLogMapper logMapper;
	@Autowired @Qualifier("logTaskExecutor")
	private TaskExecutor logTaskExecutor;
	
	@Transactional
	@Override
	public void addLog(final EsbApiLog apiLog) {
		logTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try
				{
					logMapper.addEsbApiLog(apiLog);
				}catch (Exception e) {
					logger.error("接口调用日志记录出错",e);
				}				
			}
		});
	}

}
