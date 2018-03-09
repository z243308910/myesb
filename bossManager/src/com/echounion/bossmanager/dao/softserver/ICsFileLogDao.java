package com.echounion.bossmanager.dao.softserver;

import java.util.Date;
import java.util.List;

import com.echounion.bossmanager.dao.IBaseDao;
import com.echounion.bossmanager.entity.CargosmartFileRoutingLog;

public interface ICsFileLogDao extends IBaseDao<CargosmartFileRoutingLog> {

	public List<CargosmartFileRoutingLog> getCsFileLogs(String fileName, Date effectDate, Date expireDate);

	public Integer getCsFileLogCount(String fileName, Date effectDate, Date expireDate);
}
