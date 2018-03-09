package com.echounion.boss.persistence.tracking;

import com.echounion.boss.cargosmart.entity.CargosmartFileRoutingLog;

/**
 * 与cargosmart交互的文件日志接口
 * @author Administrator
 *
 */
public interface CargosmartFileRoutingLogMapper {

	/**
	 * 记录日志
	 * @param log
	 * @return
	 */
	public int addFileRoutingLog(CargosmartFileRoutingLog log);
}
