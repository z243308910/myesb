package com.echounion.bossmanager.dao.softserver;


import com.echounion.bossmanager.dao.IBaseDao;
import com.echounion.bossmanager.entity.EsbSoftWare;
/**
 * 软件管理数据接口
 * @author 胡礼波
 * 2012-11-6 下午07:23:24
 */
public interface ISoftwareDao extends IBaseDao<EsbSoftWare> {

	/**
	 * 删除软件
	 * @author 胡礼波
	 * 2013-7-11 下午6:34:36
	 * @param softId
	 * @return
	 */
	public int delSoftware(Integer... softId);
}
