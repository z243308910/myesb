package com.echounion.boss.persistence.esb;

import java.util.List;

import com.echounion.boss.entity.EsbServer;

/**
 * 服务器数据接口
 * @author 胡礼波
 * 2012-11-8 下午05:08:24
 */
public interface EsbServerMapper {

	/**
	 * 根据服务器编号查询服务器信息
	 * @author 胡礼波
	 * 2012-11-8 下午05:06:45
	 * @param serverId
	 * @return
	 */
	public EsbServer getServerById(int serverId);
	
	/**
	 * 得到所有的IP
	 * @author 胡礼波
	 * 2013-7-25 下午2:32:05
	 * @return
	 */
	public List<String> getIp();
}
