package com.echounion.bossmanager.dao;

import java.util.List;

import com.echounion.bossmanager.entity.SysConfig;
import com.echounion.bossmanager.entity.dto.DBTable;
/**
 * 系统配置数据接口
 * @author 胡礼波
 * 2012-10-31 下午03:08:53
 */
public interface ISysConfigDao extends IBaseDao<SysConfig> {

	/**
	 * 获得数据库所有的表的信息
	 * @author 胡礼波
	 * 2012-11-15 上午11:54:41
	 * @return
	 */
	public List<DBTable> getDSTables();
	
	/**
	 * 删除指定表的数据
	 * @author 胡礼波
	 * 2012-12-11 下午03:38:00
	 * @param tableName
	 * @return
	 */
	public int delTableData(String tableName);
	
	/**
	 * 根据类型和代号获得对应的配置信息
	 * @author 胡礼波
	 * 2012-12-27 上午11:16:12
	 * @param configCode
	 * @param type
	 * @return
	 */
	public SysConfig getSysConfig(String configCode, int type);
	
	/**
	 * 根据代号获得对应的配置信息
	 *@author 张霖
	 * 2013-7-5 上午11:49:31
	 * @param configCode
	 * @return
	 */
	public SysConfig getSysConfig(String configCode);
}
