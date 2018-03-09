package com.echounion.bossmanager.service.impl;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.bossmanager.common.export.ExcelUtil;
import com.echounion.bossmanager.common.util.DateUtil;
import com.echounion.bossmanager.dao.ILogDao;
import com.echounion.bossmanager.entity.SysLogCons;
import com.echounion.bossmanager.service.ILogService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class LogServiceImpl implements ILogService {

	@Autowired
	private ILogDao logDao;
	
	@Transactional
	public int delLog(Integer[] logIds) {
		return logDao.removeObject(logIds,"id");
	}

	public List<SysLogCons> getAllLog() {
		return logDao.getAll();
	}

	public int getCountLog() {
		return logDao.getCount();
	}

	@Transactional
	public void addLog(final SysLogCons log) {
		log.setOpDateTime(new Date());
		logDao.saveObject(log);
	}

	public int exportLogsToExcel(String path) {
		List<SysLogCons> list=logDao.getAll();
		Map<String,String> titleMap=new LinkedHashMap<String, String>();
		titleMap.put("id", "编号");
		titleMap.put("modelName","模块名称");
		titleMap.put("methodName","操作描述");
		titleMap.put("remark","备注");
		titleMap.put("userName", "操作员");
		path=path+File.separator+"系统日志"+DateUtil.getCurrentDateTime()+".xls";
		int count=ExcelUtil.exportData(titleMap,list,path);
		return count;
	}
}
