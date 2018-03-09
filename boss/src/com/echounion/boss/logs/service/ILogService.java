package com.echounion.boss.logs.service;

/**
 * 系统日志业务接口
 * @author 胡礼波
 * 2012-11-1 下午03:56:07
 */
public interface ILogService<T> {

	/**
	 * 添加系统日志
	 * @author 胡礼波
	 * 2012-11-1 下午03:56:33
	 * @param sysLog
	 */
	public void addLog(T t);
}
