package com.echounion.bossmanager.dao.shortmsg;


import java.util.List;
import java.util.Map;

import com.echounion.bossmanager.dao.IBaseDao;
import com.echounion.bossmanager.entity.MobileHistory;
import com.echounion.bossmanager.entity.MobileList;

/**
 * 短信日志数据接口
 * @author 胡礼波
 * 2012-11-19 下午07:19:01
 */
public interface IShortMsgHistoryDao extends IBaseDao<MobileHistory> {

	/**
	 * 根据条件查询年手机历史记录
	 * @author 胡礼波
	 * 2013-5-7 下午3:08:55
	 * @param params
	 * @return
	 */
	public List<MobileHistory> getAll(Map<String,Object> params);

	/**
	 * 根据条件查询年手机历史记录总和
	 * @author 胡礼波
	 * 2013-5-7 下午3:09:10
	 * @param params
	 * @return
	 */
	public int getCount(Map<String,Object> params);
	
	/**
	 * 删除注册手机
	 * @author 胡礼波
	 * 2012-12-13 下午01:43:56
	 * @param ids
	 * @return
	 */
	public int delRegMobile(Integer[] ids);

	/**
	 * 获得注册手机总和
	 * @author 胡礼波
	 * 2012-12-13 下午01:44:05
	 * @return
	 */
	public int getRegCount();

	/**
	 * 获得注册手机列表
	 * @author 胡礼波
	 * 2012-12-13 下午01:44:13
	 * @return
	 */
	public List<MobileList> getRegMobile();
}
