package com.echounion.bossmanager.service;

import java.util.List;

import com.echounion.bossmanager.entity.SysConfig;
import com.echounion.bossmanager.entity.dto.DBTable;

/**
 * 系统配置业务接口
 * @author 胡礼波
 * 2012-10-31 下午03:03:36
 */
public interface ISysConfigService {

	/**
	 * 根据ID查询系统配置信息
	 * @author 胡礼波
	 * 2012-10-31 下午03:30:41
	 * @param sysConfigId
	 * @return
	 */
	public SysConfig getSysConfigById(int sysConfigId);
	
	/**
	 * 加载所有的系统配置
	 * @author 胡礼波
	 * 2012-10-31 下午03:06:20
	 * @return
	 */
	public List<SysConfig> getSysConfig();
	
	/**
	 * 根据类型和代号查找对应的配置信息
	 * @author 胡礼波
	 * 2012-12-27 上午11:15:21
	 * @param configCode
	 * @param type
	 * @return
	 */
	public SysConfig getSysConfig(String configCode,int type);
	
	/**
	 * 根据代号查找对应的配置信息
	 *@author 张霖
	 * 2013-7-5 下午2:48:09
	 * @param configCode
	 * @return
	 */
	public SysConfig getSysConfig(String configCode);
	
	/**
	 * 根据配置类型加载属于同种类别的配置信息
	 * @author 胡礼波
	 * 2012-10-31 下午03:06:55
	 * @param sysConfigcode
	 * @return
	 */
	public List<SysConfig> getSysConfigByType(int type);
	
	/**
	 * 根据类型查找对应的类型的总和
	 * @author 胡礼波
	 * 2012-11-6 上午09:56:14
	 * @param type
	 * @return
	 */
	public int getCount(int type);
	
	/**
	 * 添加系统配置
	 * @author 胡礼波
	 * 2012-10-31 下午03:04:23
	 * @param config
	 * @return
	 */
	public int addSysConfig(SysConfig config);
	
	/**
	 * 删除系统配置根据主键信息
	 * @author 胡礼波
	 * 2012-10-31 下午03:04:47
	 * @param sysConfigId
	 * @return
	 */
	public int delSysConfig(Integer[] sysConfigId);
	
	/**
	 * 修改系统配置
	 * @author 胡礼波
	 * 2012-10-31 下午03:05:37
	 * @param config
	 * @return
	 */
	public int editSysConfig(SysConfig config);
	
	/**
	 * 获得数据库所有的表信息
	 * @author 胡礼波
	 * 2012-11-15 上午11:54:06
	 * @return
	 */
	public List<DBTable> getDSTables();
	
	/**
	 * 删除制定表的数据
	 * @author 胡礼波
	 * 2012-12-11 下午03:36:48
	 * @param tableName
	 * @return
	 */
	public boolean initTableData(String tableName);
}
