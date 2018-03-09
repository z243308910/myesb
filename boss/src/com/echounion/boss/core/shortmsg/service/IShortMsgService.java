package com.echounion.boss.core.shortmsg.service;

import java.util.Date;

import com.echounion.boss.common.enums.ShortMsgStatus;
import com.echounion.boss.core.shortmsg.ShortMsg;
import com.echounion.boss.entity.MobileHistory;

/**
 * 短信业务接口
 * @author 胡礼波
 * 2012-11-17 下午05:33:43
 */
public interface IShortMsgService {

	/**
	 * 校验短信信息并发送短信并保存短信日志记录
	 * @author 胡礼波
	 * 2012-11-15 下午07:14:12
	 * @param softId 软件ID message 短信信息
	 * @return 返回短信发送的结果枚举
	 */
	public ShortMsgStatus send(ShortMsg shortMsg);
	
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
	public int editMobileHistoryStatus(int historyId,int status);
	
	/**
	 * 重新发送短信---主要是管理台调用
	 * @author 胡礼波
	 * 2012-11-20 下午01:40:51
	 * @param shortMsgIds
	 * @return
	 */
	public ShortMsgStatus reSendShortMsg(Integer[] shortMsgIds);
	
	/**
	 * 保存注册的手机号
	 * @author 胡礼波
	 * 2012-11-22 下午12:19:33
	 * @param phone
	 * @return
	 */
	public int addRegistMobile(String phone);
	
	/**
	 * 检查一段时间内的短信总量
	 * @author 胡礼波
	 * Nov 30, 2012 12:04:48 PM
	 * @param begin
	 * @param end
	 * @return
	 */
	public int getCountByTime(Date begin, Date end);
}
