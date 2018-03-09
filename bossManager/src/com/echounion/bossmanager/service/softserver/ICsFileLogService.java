package com.echounion.bossmanager.service.softserver;

import java.util.Date;
import java.util.List;

import com.echounion.bossmanager.entity.CargosmartFileRoutingLog;

public interface ICsFileLogService {

	public List<CargosmartFileRoutingLog> getAll();

	public int getAllCount();

	public int delLogs(Integer ids[]);

	public List<CargosmartFileRoutingLog> getCsFileLogs(String fileName, Date effectDate, Date expireDate);

	public Integer getCsFileLogCount(String fileName, Date effectDate, Date expireDate);
}
