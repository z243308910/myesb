package com.echounion.bossmanager.service.softserver;

import java.util.List;

import com.echounion.bossmanager.entity.EsbServer;

/**
 * 服务器业务接口
 * @author 胡礼波
 * 2012-11-8 下午05:20:14
 */
public interface IServerService {

	/**
	 * 根据服务器ID查询服务器信息
	 * @author 胡礼波
	 * 2012-11-8 下午05:20:34
	 * @param serverId
	 * @return
	 */
	public EsbServer getServerById(int serverId);
	
	/**
	 * 获得所有的服务器
	 * @author 胡礼波
	 * 2012-11-8 下午05:25:34
	 * @return
	 */
	public List<EsbServer> getServerList();
	
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
	
	/**
	 * 删除服务器
	 * @author 胡礼波
	 * 2013-7-11 下午7:01:53
	 * @param serverId
	 * @return
	 */
	public int delServer(Integer...serverId);
	
	/**
	 * 获得所有的服务器总数
	 * @author 胡礼波
	 * 2012-11-8 下午05:28:19
	 * @return
	 */
	public int getCount();

	/**
	 * 添加服务器
	 * @author 胡礼波
	 * 2012-11-8 下午05:25:52
	 * @param server
	 * @param 软件ID
	 * @return
	 */
	public int addServer(EsbServer server,Integer...softWareId);
	
	/**
	 * 编辑服务器
	 * @author 胡礼波
	 * 2013-7-16 上午10:05:34
	 * @param server 服务器对象
	 * @param softIds 软件ID
	 * @return
	 */
	public int editServer(EsbServer server,Integer...softIds);
	
	/**
	 * 检查服务器IP是否存在
	 * @author 胡礼波
	 * 2012-12-14 下午03:41:34
	 * @param ip
	 * @return
	 */
	public boolean isExistIp(String ip);

}
