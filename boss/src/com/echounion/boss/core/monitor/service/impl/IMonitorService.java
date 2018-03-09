package com.echounion.boss.core.monitor.service.impl;

import java.nio.file.Path;

/**
 * 文件目录监控接口
 * @author 胡礼波
 * 2013-10-14 下午3:34:36
 */
public interface IMonitorService {

	/**
	 * 文件创建时发生的回调函数
	 * @author 胡礼波
	 * 2013-10-14 下午3:39:40
	 */
	public void onCreate(Path path);		//创建
	
	/**
	 * 文件删除时发生的回调函数 
	 * @author 胡礼波
	 * 2013-10-14 下午3:39:57
	 */
	public void onDelete(Path path);		//删除
	
	/**
	 * 文件修改时发生的回调函数
	 * @author 胡礼波
	 * 2013-10-14 下午3:40:13
	 */
	public void onModify(Path path);		//修改
}
