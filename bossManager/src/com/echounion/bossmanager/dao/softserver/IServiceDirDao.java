package com.echounion.bossmanager.dao.softserver;

import com.echounion.bossmanager.dao.IBaseDao;
import com.echounion.bossmanager.entity.EsbServiceDir;
/**
 * 软件服务数据接口
 * @author 胡礼波
 * 2012-11-8 上午09:37:23
 */
public interface IServiceDirDao extends IBaseDao<EsbServiceDir> {

	/**
	 * 根据软件ID删除该软件下提供的服务接口
	 * @author 胡礼波
	 * 2013-7-16 下午2:08:18
	 * @param softId
	 * @return
	 */
	public int delServiceDirBySoftId(Integer...softId);
}
