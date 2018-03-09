package com.echounion.bossmanager.service.rtx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.dao.rtx.IRTXHistoryDao;
import com.echounion.bossmanager.entity.RTXHistory;
import com.echounion.bossmanager.service.rtx.IRTXHistoryService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="RTX记录")
public class RTXHistoryServiceImpl implements IRTXHistoryService
{
	private Logger logger = Logger.getLogger(RTXHistoryServiceImpl.class); 
	
	@Autowired
	private IRTXHistoryDao rtxHistoryDao;
	
	public List<RTXHistory> getRTXHistory(RTXHistory history) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("receiver",history.getReceiver());
		params.put("btype",history.getBtype());
		params.put("bcode",history.getBcode());
		return rtxHistoryDao.getAll(params);
	}

	public int getCount(RTXHistory history) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("receiver",history.getReceiver());
		params.put("btype",history.getBtype());
		params.put("bcode",history.getBcode());		
		return rtxHistoryDao.getCount(params);
	}

	@ActionModel(description="删除RTX日志")
	public int delRtxHistory(Integer[] ids) {
		logger.info("删除RTX");
		return rtxHistoryDao.removeObject(ids,"id");
	}

}
