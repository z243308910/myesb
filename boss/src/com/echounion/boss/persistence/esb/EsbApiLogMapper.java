package com.echounion.boss.persistence.esb;

import com.echounion.boss.entity.EsbApiLog;

/**
 * ESB接口调用日志数据接口
 * @author 胡礼波
 * 2013-7-8 下午2:25:11
 */
public interface EsbApiLogMapper {

	/**
	 * 添加系统日志
	 * @author 胡礼波
	 * 2012-11-1 下午03:47:11
	 * @param sysLog
	 * @return
	 */
	public int addEsbApiLog(EsbApiLog apiLog);
}
