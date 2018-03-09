package com.echounion.bossmanager.dao.email;


import java.util.List;
import java.util.Map;

import com.echounion.bossmanager.dao.IBaseDao;
import com.echounion.bossmanager.entity.MailHistory;
import com.echounion.bossmanager.entity.MailList;
/**
 * 邮件记录数据接口
 * @author 胡礼波
 * 2012-11-19 下午07:18:17
 */
public interface IEmailHistoryDao extends IBaseDao<MailHistory> {

	/**
	 * 获得注册的邮件记录列表
	 * @author 胡礼波
	 * 2012-12-13 上午11:13:21
	 * @return
	 */
	public List<MailList> getRegMail();
	
	/**
	 * 获得已注册的邮件记录总和
	 * @author 胡礼波
	 * 2012-12-13 上午11:09:14
	 * @return
	 */
	public int getRegCount();
	
	/**
	 * 删除注册邮件
	 * @author 胡礼波
	 * 2012-12-13 下午01:00:18
	 * @param ids
	 * @return
	 */
	public int delRegMail(Integer[] ids);
	
	/**
	 * 多条件查询邮件记录数
	 * @author 胡礼波
	 * 2012-12-20 下午05:54:44
	 * @param params
	 * @return
	 */
	public int getCount(Map<String, Object> params);

	/**
	 * 多条件查询邮件记录数
	 * @author 胡礼波
	 * 2012-12-20 下午05:54:29
	 * @param params
	 * @return
	 */
	public List<MailHistory> getMailHistory(Map<String, Object> params);
}
