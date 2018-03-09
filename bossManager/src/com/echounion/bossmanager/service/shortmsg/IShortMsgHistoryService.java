package com.echounion.bossmanager.service.shortmsg;

import java.util.List;

import com.echounion.bossmanager.entity.MobileHistory;
import com.echounion.bossmanager.entity.MobileList;

/**
 * 短信历史记录业务接口
 * @author 胡礼波
 * 2012-11-19 下午07:15:02
 */
public interface IShortMsgHistoryService {

	/**
	 * 获得所有的ShortMsg记录列表
	 * @author 胡礼波
	 * 2012-11-19 下午07:14:14
	 * @return
	 */
	public List<MobileHistory> getMobileHistory(MobileHistory history);
	
	/**
	 * 获得注册手机
	 * @author 胡礼波
	 * 2012-12-13 下午01:43:15
	 * @return
	 */
	public List<MobileList> getRegMobile();
	
	/**
	 * 删除短信日志
	 * @author 胡礼波
	 * 2012-11-19 下午07:29:41
	 * @param ids
	 * @return
	 */
	public int delMobileHistory(Integer[] ids);
	
	/**
	 * 删除注册手机
	 * @author 胡礼波
	 * 2012-12-13 下午01:42:54
	 * @param ids
	 * @return
	 */
	public int delRegMobile(Integer[] ids);
	
	/**
	 * 获得记录的总数
	 * @author 胡礼波
	 * 2012-11-19 下午07:14:30
	 * @return
	 */
	public int getCount(MobileHistory history);
	
	/**
	 * 获得注册手机的总数
	 * @author 胡礼波
	 * 2012-12-13 下午01:42:24
	 * @return
	 */
	public int getRegCount();
	
	/**
	 * 重新发送短信
	 * @author 胡礼波
	 * 2012-11-20 下午01:35:45
	 * @param shortMsgIds
	 * @return
	 */
	public String editReSendShortMsg(Integer[] shortMsgIds);
	
}
