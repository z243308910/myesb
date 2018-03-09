package com.echounion.boss.Tracking.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.echounion.boss.Tracking.ITrackingService;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.core.system.ISysConfigService;
import com.echounion.boss.entity.SysConfig;
import com.echounion.boss.entity.TrackingHistory;
import com.echounion.boss.entity.dto.Tracktor;
import com.echounion.boss.persistence.tracking.TrackingHistoryMapper;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class TrackingServiceImpl implements ITrackingService {

	private Logger logger=Logger.getLogger(TrackingServiceImpl.class);
	@Autowired
	private ISysConfigService sysConfigService;
	@Autowired
	private TrackingHistoryMapper trackingHistoryMapper;
	
	public Tracktor getTracktor(String code) {
		Assert.notNull(code);
		SysConfig config=sysConfigService.getSysConfig("Track_"+code);
		if(config==null||config.getType()!=Constant.SYS_TRACKING)	//为空或者不是采集器类型
		{
			return null;
		}
		Tracktor tracktor=new Tracktor(config.getConfigCode(),config.getConfigName(),config.getConfigDesc(),config.getUrl());
		return tracktor;
	}

	@Transactional
	public void addTrackingHistory(TrackingHistory history) {
		try{
		trackingHistoryMapper.addTrackingHistory(history);
		}catch (Exception e) {
			logger.error("记录采集日志出错"+e);
		}
	}

	@Override
	public int getCountByTime(Date begin, Date end) {
		return trackingHistoryMapper.getCountByTime(begin, end);
	}

}
