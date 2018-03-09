package com.echounion.boss.persistence.system;

import com.echounion.boss.entity.SysLog;

/**
 * 系统日志数据接口
 * @author 胡礼波
 * 2012-11-1 下午03:46:43
 */
public interface SysLogMapper {

	/**
	 * 添加系统日志
	 * @author 胡礼波
	 * 2012-11-1 下午03:47:11
	 * @param sysLog
	 * @return
	 */
	public int addSysLog(SysLog sysLog);
}
