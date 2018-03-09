package com.echounion.bossmanager.service.softserver.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.dao.softserver.ICsFileLogDao;
import com.echounion.bossmanager.entity.CargosmartFileRoutingLog;
import com.echounion.bossmanager.service.softserver.ICsFileLogService;

@Service
@Transactional
@ActionModel(description = "Cargosmart文件传输日志")
public class CsFileLogServiceImpl implements ICsFileLogService {

	@Autowired
	ICsFileLogDao csFileLogDao;

	@Override
	public List<CargosmartFileRoutingLog> getAll() {
		return csFileLogDao.getAll();
	}

	@Override
	public int getAllCount() {
		return csFileLogDao.getCount();
	}

	@Override
	@Transactional
	public int delLogs(Integer[] ids) {
		return csFileLogDao.removeObject(ids, "id");
	}

	@Override
	public List<CargosmartFileRoutingLog> getCsFileLogs(String fileName, Date effectDate, Date expireDate) {
		return csFileLogDao.getCsFileLogs(fileName, effectDate, expireDate);
	}

	@Override
	public Integer getCsFileLogCount(String fileName, Date effectDate, Date expireDate) {
		return csFileLogDao.getCsFileLogCount(fileName, effectDate, expireDate);
	}

}
