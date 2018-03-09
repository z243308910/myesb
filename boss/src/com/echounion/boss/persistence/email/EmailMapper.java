package com.echounion.boss.persistence.email;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.echounion.boss.entity.MailHistory;

/**
 * 邮件数据接口
 * @author 胡礼波
 * 2012-11-15 下午07:15:56
 */
public interface EmailMapper {

	/**
	 * 添加邮件历史记录
	 * @author 胡礼波
	 * 2012-11-15 下午07:16:33
	 * @param history
	 * @return
	 */
	public int addEmailHistory(MailHistory history);
	
	/**
	 * 更新邮件历史记录的失败次数
	 * @author 胡礼波
	 * 2012-11-16 上午09:19:49
	 * @param historyId
	 * @return
	 */
	public int editEmailHistoryErrorNum(int historyId);
	
	
	/**
	 * 更新邮件历史记录的状态
	 * @author 胡礼波
	 * 2012-11-16 上午09:20:29
	 * @param historyId 邮件历史记录
	 * @param status 状态之
	 * @return
	 */
	public int editEmailHistoryStatus(Map<String,Object> data);
	
	/**
	 * 根据邮件历史ID集合查找对应的邮件历史记录
	 * @author 胡礼波
	 * 2012-11-20 上午10:23:23
	 * @param ids
	 * @return
	 */
	public List<MailHistory> getMailHistoryByIds(Integer[] mailIds);
	
	/**
	 * 检查邮件是否存在
	 * @param mail
	 * @return
	 */
	public int getExistMail(String email);
	
	/**
	 * 保存注册的邮箱地址
	 * @author 胡礼波
	 * 2012-11-22 下午12:20:00
	 * @param email
	 * @return
	 */
	public int addRegistMail(String email);

	/**
	 * 检查一段时间内的邮件总量
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
	public List<Map<String,Object>> getMailHistoryByParam(Map<String,Object> params);
}
