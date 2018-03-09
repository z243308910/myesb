package com.echounion.boss.core.email.service;

import java.util.Date;
import com.echounion.boss.common.enums.EmailStatus;
import com.echounion.boss.core.email.EmailMessage;
import com.echounion.boss.entity.MailHistory;

/**
 * 邮件业务接口
 * @author 胡礼波
 * 2012-11-15 下午07:12:54
 */
public interface IEmailService {

	/**
	 * 校验邮件信息并发送邮件并保存邮件日志记录---
	 * @author 胡礼波
	 * 2012-11-15 下午07:14:12
	 * @param softId 软件ID emailMessage邮件主体信息
	 * @return 返回邮件发送的结果枚举
	 */
	public EmailStatus send(EmailMessage emailMessage);	
	
	/**
	 * 保存邮件日志记录
	 * @author 胡礼波
	 * 2012-11-15 下午07:14:12
	 * @param history
	 * @return
	 */
	public int addEmailHistory(MailHistory history);
	
	/**
	 * 更新邮件历史记录的状态--该方法主要是MQ程序的消费者调用，主要用于更新是否成功和失败的次数
	 * @author 胡礼波
	 * 2012-11-16 上午09:17:06
	 * @param historyId
	 * @param status
	 * @return
	 */
	public int editEmailHistoryStatus(int historyId,int status);
	
	/**
	 * 邮件重新发送---该功能主要是管理平台调用
	 * @author 胡礼波
	 * 2012-11-20 上午10:20:28
	 * @param mailIds
	 * @return 返回发送邮件的状态
	 */
	public EmailStatus reSendMail(Integer[] mailIds);
	
	/**
	 * 保存注册的邮箱地址
	 * @author 胡礼波
	 * 2012-11-22 下午12:20:00
	 * @param email
	 * @return
	 */
	public int addRegistMail(String email);
	
	/**
	 * 查询一段时间内的邮件总量
	 * @author 胡礼波
	 * Nov 30, 2012 12:03:43 PM
	 * @param begin
	 * @param end
	 * @return
	 */
	public int getCountByTime(Date begin,Date end);
}
