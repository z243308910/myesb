package com.echounion.bossmanager.dao;

import com.echounion.bossmanager.entity.SysUser;

/**
 * 管理员相关数据接口
 * @author 胡礼波
 * 10:42:41 AM Oct 22, 2012
 */
public interface IAdminDao extends IBaseDao<SysUser> {

	/**
	 * 通过用户名获得管理员对象
	 * @author 胡礼波
	 * 10:43:20 AM Oct 22, 2012 
	 * @param userName
	 * @return 管理员对象
	 */
	public SysUser getByUsername(String userName);
	
	/**
	 * 添加管理员
	 * @author 胡礼波
	 * 10:45:38 AM Oct 22, 2012 
	 * @param admin
	 * @return
	 */
	public int addAdmin(SysUser admin);
}
