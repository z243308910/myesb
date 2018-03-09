package com.echounion.bossmanager.dao.softserver;

import java.util.List;

import com.echounion.bossmanager.dao.IBaseDao;
import com.echounion.bossmanager.entity.EsbServer;
/**
 * 服务器数据接口
 * @author 胡礼波
 * 2012-11-8 下午05:22:06
 */
public interface IServerDao extends IBaseDao<EsbServer>{

	/**
	 * 根据软件ID查看该软件部署在了哪些服务器上
	 * @author 胡礼波
	 * 2012-11-9 下午05:47:14
	 * @param flag tru表示查询出所有的部署的服务器，false表示当前活动状态的服务器 不包含已经禁用和删除的服务器
	 * @return 返回服务器列表
	 */
	public List<EsbServer> getServerBySoftId(int softId,boolean flag);
	
	/**
	 * 根据软件ID查看该软件部署在了哪些服务器上的总和
	 * @author 胡礼波
	 * 2012-11-9 下午05:47:14
	 * @param flag tru表示查询出所有的部署的服务器，false表示当前活动状态的服务器 不包含已经禁用和删除的服务器
	 * @return 返回服务器列表
	 */
	public int getServerBySoftCount(int softId,boolean flag);
}
