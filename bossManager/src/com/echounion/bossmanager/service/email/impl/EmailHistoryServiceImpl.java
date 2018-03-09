package com.echounion.bossmanager.service.email.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.bossmanager.common.enums.EmailStatus;
import com.echounion.bossmanager.common.security.Boss;
import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.common.util.StringUtil;
import com.echounion.bossmanager.dao.email.IEmailHistoryDao;
import com.echounion.bossmanager.entity.MailHistory;
import com.echounion.bossmanager.entity.MailList;
import com.echounion.bossmanager.entity.dto.BossProperty;
import com.echounion.bossmanager.entity.dto.Record;
import com.echounion.bossmanager.service.email.IEmailHistoryService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="邮件日志")
public class EmailHistoryServiceImpl implements IEmailHistoryService {

	private Logger logger=Logger.getLogger(EmailHistoryServiceImpl.class);
	
	@Autowired
	private IEmailHistoryDao emailHistoryDao; 
	
	public int getCount() {
		return emailHistoryDao.getCount();
	}

	public List<MailHistory> getMailHistory() {
		return emailHistoryDao.getAll();
	}

	@ActionModel(description="删除邮件日志")
	public int delMailHistory(Integer[] ids) {
		logger.info("删除邮件日志");
		return emailHistoryDao.removeObject(ids, "id");
	}

	@ActionModel(description="重新发送邮件")
	public String editReSendMail(Integer[] mailIds) {
		if(ArrayUtils.isEmpty(mailIds))
		{
			logger.info("没有选择要重新发送的邮件");
			return "没有选中要重新发送的邮件";
		}
		String url=BossProperty.getInstance().getMailResendUrl();
		String result=null;
		Map<String,Object> mappara=new HashMap<String, Object>();
		mappara.put("data",StringUtil.convertArray(mailIds));
		try {
			Record record=new Boss().requestBoss(url, mappara);
			if(record.getStatus()==Record.SUCCESS)		//成功
			{
				result="邮件已经发出";
			}
			else										//失败
			{
				record.getErrorCode();
				result=EmailStatus.getStatus(record.getErrorCode()).getName();
				result=result==null?"未知异常,发送失败!":result+",发送失败!";
			}
		} catch (IOException e) {
			logger.error("重新发送邮件网络异常"+e);
			result="网络异常";
		}catch (Exception e) {
			logger.error("重新发送邮件系统异常"+e);
			result="系统异常";
		}
		return result;
	}

	public MailHistory getMailHistory(int historyId) {
		return emailHistoryDao.getObject(historyId);
	}

	public int getRegCount() {
		return emailHistoryDao.getRegCount();
	}

	public List<MailList> getRegMail() {
		return emailHistoryDao.getRegMail();
	}

	@ActionModel(description="删除注册邮件")
	public int delRegMail(Integer[] ids) {
		return emailHistoryDao.delRegMail(ids);
	}

	public int getCount(MailHistory history) {
		if(history==null)
		{
			return getCount();
		}
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("emailAddress",history.getEmailAddress());
		params.put("btype",history.getBtype());
		params.put("bcode",history.getBcode());
		return emailHistoryDao.getCount(params);
	}

	public List<MailHistory> getMailHistory(MailHistory history) {
		if(history==null)
		{
			return getMailHistory();
		}
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("emailAddress",history.getEmailAddress());
		params.put("btype",history.getBtype());
		params.put("bcode",history.getBcode());
		return emailHistoryDao.getMailHistory(params);
	}

}
