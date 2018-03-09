package com.echounion.boss.persistence.shortmsg;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.echounion.boss.entity.MobileHistory;

/**
 * 手机短信数据接口
 * @author 胡礼波
 * 2012-11-19 上午11:25:08
 */
public interface MobileMapper {
	
	/**
	 * 保存短信日志记录
	 * @author 胡礼波
	 * 2012-11-15 下午07:14:12
	 * @param history
	 * @return
	 */
	public int addMobileHistory(MobileHistory history);
	
	/**
	 * 更新短信历史记录的状态--该方法主要是MQ程序的消费者调用，主要用于更新是否成功和失败的次数
	 * @author 胡礼波
	 * 2012-11-16 上午09:17:06
	 * @param historyId
	 * @param status
	 * @return
	 */
	public int editMobileHistoryStatus(Map<String,Object> data);
	
	/**
	 * 更信短信失败次数
	 * @author 胡礼波
	 * 2012-11-19 上午11:29:04
	 * @param history
	 * @return
	 */
	public int editMobileHistoryErrorNum(int history);
	
	/**
	 * 根据短信日志ID查找短信记录
	 * @author 胡礼波
	 * 2012-11-20 下午01:47:37
	 * @param shortMsgIds
	 * @return
	 */
	public List<MobileHistory> getMobileHistoryByIds(Integer[] shortMsgIds);
	
	/**
	 * 保存注册的手机号
	 * @author 胡礼波
	 * 2012-11-22 下午12:19:33
	 * @param phone
	 * @return
	 */
	public int addRegistMobile(String phone);
	
	/**
	 * 查找手机号码是否注册
	 * @param phone
	 * @return
	 */
	public int getExistMobile(String phone);
	
	/**
	 * 检查一段时间内的短信总量
	 * @author 胡礼波
	 * Nov 30, 2012 12:04:48 PM
	 * @param begin
	 * @param end
	 * @return
	 */
	public int getCountByTime(Date begin, Date end);
	
	/**
	 * 根据不同参数查询
	 * @author 胡礼波
	 * 2013-3-25 下午1:37:39
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> getMobileHistoryByParam(Map<String,Object> params);
}
