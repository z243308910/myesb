package com.echounion.bossmanager.service;

import java.util.List;

import com.echounion.bossmanager.entity.SysUser;

/**
 * 管理员相关业务接口
 * @author 胡礼波
 * 10:39:16 AM Oct 22, 2012
 */
public interface IAdminService {

	/**
	 * 用户登录
	 * @author 胡礼波
	 * 10:40:54 AM Oct 22, 2012 
	 * @param name
	 * @param password
	 * @return
	 */
	public SysUser login(String name,String password);
	
	/**
	 * 判断用户名是否存在
	 * @author 胡礼波
	 *  10:40:54 AM Oct 22, 2012 
	 * @param username 用户名
	 * @return 存在返回true 反之
	 */
	public boolean usernameExist(String username);
	
	/**
	 * 判断邮件地址是否存在
	 * @author 胡礼波
	 * 10:40:54 AM Oct 22, 2012 
	 * @param email
	 * @return 存在返回true 反之
	 */
	public boolean emailExist(String email);
	
	/**
	 * 添加管理员
	 * @author 胡礼波
	 *  10:40:54 AM Oct 22, 2012 
	 * @param adminUser
	 * @return true表示添加成功 false反之
	 */
	public boolean addAdmin(SysUser adminUser);
	
	/**
	 * 管理员列表
	 * @author 胡礼波
	 * 2012-12-10 下午03:05:14
	 * @return
	 */
	public List<SysUser> getAllAdmin();
	
	/**
	 * 删除管理员
	 * @author 胡礼波
	 * 2012-12-10 下午03:08:26
	 * @param ids
	 * @return
	 */
	public int delAdmins(Integer[] ids);
	
	/**
	 * 通过用户名获取指定的管理员
	 * @author 张霖
	 * 2013-2-1下午5:02:56
	 * @param username
	 * @return
	 */
	public SysUser getAdminByUserName(String username);
	
	/**
	 * 编辑管理员信息
	 * @author 张霖
	 * 2013-2-1下午5:26:05
	 * @param adminUser
	 * @return
	 */
	public int editAdmin(SysUser adminUser);
}
