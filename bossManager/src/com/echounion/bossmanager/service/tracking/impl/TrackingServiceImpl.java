package com.echounion.bossmanager.service.tracking.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.dao.tracking.ITrackingDao;
import com.echounion.bossmanager.entity.TrackingHistory;
import com.echounion.bossmanager.service.tracking.ITrackingService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="数据采集日志")
public class TrackingServiceImpl implements ITrackingService {

	@Autowired
	private ITrackingDao trackingDao;
	
	public List<TrackingHistory> getTrackingHistory() {
		return trackingDao.getAll();
	}

	public int getCount() {
		return trackingDao.getCount();
	}

	@Transactional
	@ActionModel(description="删除采集日志")
	public int delTrackingHistory(Integer[] ids) {
		return trackingDao.removeObject(ids,"id");
	}
}
