package com.echounion.bossmanager.service.email;

import java.util.List;
import com.echounion.bossmanager.entity.MailHistory;
import com.echounion.bossmanager.entity.MailList;

/**
 * 邮件历史记录业务接口
 * @author 胡礼波
 * 2012-11-19 下午07:13:31
 */
public interface IEmailHistoryService {

	/**
	 * 获得所有的Email记录列表
	 * @author 胡礼波
	 * 2012-11-19 下午07:14:14
	 * @return
	 */
	public List<MailHistory> getMailHistory();
	
	/**
	 * 根据不同条件查询邮件记录
	 * @author 胡礼波
	 * 2012-12-20 下午05:51:18
	 * @param params
	 * @return
	 */
	public List<MailHistory> getMailHistory(MailHistory history);
	
	
	/**
	 * 获得注册的邮件记录列表
	 * @author 胡礼波
	 * 2012-12-13 上午11:13:21
	 * @return
	 */
	public List<MailList> getRegMail();

	/**
	 * 删除邮件记录
	 * @author 胡礼波
	 * 2012-11-19 下午07:30:13
	 * @param ids
	 * @return
	 */
	public int delMailHistory(Integer[] ids);
	
	/**
	 * 删除注册邮件
	 * @author 胡礼波
	 * 2012-12-13 下午12:59:43
	 * @param ids
	 * @return
	 */
	public int delRegMail(Integer[] ids);
	
	/**
	 * 查看邮件历史记录
	 * @author 胡礼波
	 * 2012-12-7 下午06:28:34
	 * @param historyId
	 * @return
	 */
	public MailHistory getMailHistory(int historyId);
	
	/**
	 * 获得记录的总数
	 * @author 胡礼波
	 * 2012-11-19 下午07:14:30
	 * @return
	 */
	public int getCount();
	
	/**
	 * 根据不同条件查询总记录数
	 * @author 胡礼波
	 * 2012-12-20 下午05:52:00
	 * @param params
	 * @return
	 */
	public int getCount(MailHistory history);
	
	/**
	 * 获得已注册的邮件记录总和
	 * @author 胡礼波
	 * 2012-12-13 上午11:09:14
	 * @return
	 */
	public int getRegCount();
	
	/**
	 * 重新发送邮件
	 * @author 胡礼波
	 * 2012-11-20 上午10:57:48
	 * @return
	 */
	public String editReSendMail(Integer[] mailIds);
}
