package com.echounion.boss.logs.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.boss.cargosmart.entity.CargosmartFileRoutingLog;
import com.echounion.boss.logs.service.ILogService;
import com.echounion.boss.persistence.tracking.CargosmartFileRoutingLogMapper;

/**
 * 与cargosmart交互的文件日志接口
 * @author Administrator
 *
 */
@Service("CargosmartFileRoutingLogServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
public class CargosmartFileRoutingLogServiceImpl implements ILogService<CargosmartFileRoutingLog>{

	private Logger logger=Logger.getLogger(CargosmartFileRoutingLog.class);
	
	@Autowired
	private CargosmartFileRoutingLogMapper fileRoutingLogMapper;
	
	@Autowired @Qualifier("logTaskExecutor")
	private TaskExecutor logTaskExecutor;
	
	@Override
	public void addLog(final CargosmartFileRoutingLog log) {
		logTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try
				{
					fileRoutingLogMapper.addFileRoutingLog(log);
				}catch (Exception e) {
					logger.error("cargosmart交互文件日志记录出错:"+e.getMessage());
				}	
			}
		});
	}

}
