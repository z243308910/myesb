package com.echounion.boss.core.email.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.alibaba.fastjson.JSON;
import com.echounion.boss.common.enums.EmailStatus;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.core.email.EmailMessage;
import com.echounion.boss.core.email.SpringEmailUtil;
import com.echounion.boss.core.email.service.IEmailService;
import com.echounion.boss.core.jms.JmsTemplateUtil;
import com.echounion.boss.entity.MailHistory;
import com.echounion.boss.entity.SysLog;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.logs.service.ILogService;
import com.echounion.boss.persistence.email.EmailMapper;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class EmailServiceImpl implements IEmailService {

	@Autowired
	private EmailMapper emailMapper;
	private Logger logger=Logger.getLogger(EmailServiceImpl.class);
	@Autowired
	@Qualifier("SysLogServiceImpl")
	private ILogService<SysLog> logService;
	@Autowired
	private JmsTemplateUtil jsmTemplate;
	
	@Transactional
	public int addEmailHistory(MailHistory history) {
		int result=0;
		try
		{
			result=emailMapper.addEmailHistory(history);
		}catch (Exception e) {
			logger.warn("保存邮件历史记录<"+history.getEmailAddress()+">出错!"+e);
			logService.addLog(new SysLog("邮件服务","记录邮件日志","保存邮件历史记录<"+history.getEmailAddress()+">出错"));
			throw e;
		}
		return result;
	}

	@Transactional
	public int editEmailHistoryStatus(int historyId, int status) {
		int result=0;
		try
		{
			Map<String,Object> data=new  HashMap<String, Object>();
			data.put("historyId",historyId);
			data.put("status", status);
			result=emailMapper.editEmailHistoryStatus(data);			//更新邮件状态 保存成功
			if(status==MailHistory.SEND_FAIL)							//失败
			{
				result=emailMapper.editEmailHistoryErrorNum(historyId);	//更新失败的次数
			}
			logger.info("更新邮件<"+historyId+">状态成功!");
		}catch (Exception e) {
			logger.error("更新邮件<"+historyId+">状态失败!"+e);
			logService.addLog(new SysLog("邮件服务","更改邮件状态或失败次数","更改邮件<"+historyId+">状态或失败次数失败!"));
		}
		return result;
	}
	
	public EmailStatus send(EmailMessage emailMessage) {
		if(null==emailMessage)
		{
			logger.error("邮件对象为空，请确认参数的完整性!");
			return EmailStatus.NULL_EMAIL;
		}
		if(CollectionUtils.isEmpty(emailMessage.getReciList())){logger.error("收件人为空!");return EmailStatus.NULL_RECIVER;}
		try {
			if(!StringUtils.isBlank(emailMessage.getTpl()))			//带模版的邮件
			{
				Map<?,?> parseObject=null;
				String content=null;
				try
				{
					parseObject =JSON.parseObject(emailMessage.getContent(),HashMap.class);
				}catch (Exception e) {
					logger.error("模版数据解析JSON格式错误，请检查输入参数!"+e.getMessage());
					return EmailStatus.DATA_ERROR_TPL;
				}
				try
				{
					if(!SpringEmailUtil.isExistTpl(emailMessage.getTpl()))
					{
						logger.error("邮件模版不存在!");
						return EmailStatus.NOT_TPL;
					}
					content=SpringEmailUtil.assembleContent(parseObject,emailMessage.getTpl());
				}catch (Exception e) {
					logger.error(e.getMessage());
					logger.error("解析邮件模版错误，请确认邮件模版数据与参数是否对应!");
					return EmailStatus.DATA_ERROR_TPL;
				}				
				emailMessage.setContent(content);
			}
			if(StringUtils.isBlank(emailMessage.getContent()))
			{
				logger.error("邮件内容为空!");
				return EmailStatus.CONTENT_NULL;
			}
			sendMail(emailMessage);
		}catch(DataIntegrityViolationException e)
		{
			logger.error("发送数据过长"+e);
			return EmailStatus.DATA_TOO_LONG;
		}
		catch (JMSException e) {
			logger.error("发送邮件到JMS异常"+e);
			logService.addLog(new SysLog("邮件服务","检查邮件并发送","邮件发送到JMS异常"));
			return EmailStatus.ERROR_MQ;
		}
		catch (Exception e) {
			logger.error("发送邮件系统异常",e);
			return EmailStatus.ERROR_SYS;
		}
		return EmailStatus.SUCCESS;
	}	

	private void sendMail(EmailMessage emailMessage) throws JMSException,Exception {
		List<EmailMessage> list=new ArrayList<EmailMessage>();
		EmailMessage[] emailMessages=null;
		
		if(emailMessage.isSplit())
		{
			for (String address:emailMessage.getReciList()) {	//拆分邮件一条条发送
				EmailMessage tempEmail=assemblyMail(emailMessage, address);
				if(tempEmail!=null)
				{
					list.add(tempEmail);
				}
			}
		}else
		{
			String address=StringUtils.join(emailMessage.getReciList(),",");
			EmailMessage tempEmail=assemblyMail(emailMessage, address);
			list.add(tempEmail);
		}
		emailMessages=list.toArray(new EmailMessage[list.size()]);
		sendMailToMq(emailMessages);				//批量发送
	}
	
	private EmailMessage assemblyMail(EmailMessage emailMessage,String address)throws Exception
	{
	//	Caller caller=(Caller)ThreadLocalUtil.getData();
		MailHistory history=new MailHistory();
	//	history.setSoftId(caller.getSoftId());
		history.setSoftId(94);
		history.setContent(emailMessage.getContent());
		history.setErrosNum(0);							//第一次默认为0
		history.setStatus(MailHistory.SEND_WAITING);	//默认为等待发送
		history.setTplName(emailMessage.getTpl());			
		history.setEmailAddress(address);
		history.setSendTime(new Date());
		history.setBcode(emailMessage.getBcode());
		history.setBtype(emailMessage.getBtype());
		history.setReply(emailMessage.getReply());
		history.setCc(emailMessage.getCc());
		history.setPersonal(emailMessage.getPersonal());
		history.setSubject(emailMessage.getSubject());
		int result=addEmailHistory(history);
		if(result>0)										//发送成功
		{
			EmailMessage tempEmail=(EmailMessage)BeanUtilsBean.getInstance().cloneBean(emailMessage);//根据邮件地址拆分一条条邮件
			tempEmail.setHistoryId(history.getId());		//标志邮件的编号为数据库记录的编号
			tempEmail.setReceiver(address);					//设置当前邮件的收件人
			return tempEmail;
		}
		return null;
	}
	
	public EmailStatus reSendMail(Integer[] mailIds) {
		if(ArrayUtils.isEmpty(mailIds))
		{
			logger.warn("没有选中要发送的邮件!");
			return EmailStatus.ERROR_SYS;
		}
		try
		{
			List<MailHistory> list=emailMapper.getMailHistoryByIds(mailIds);
			List<EmailMessage> listEmailMessage=new ArrayList<EmailMessage>();
			EmailMessage[] emailMessages=null;
			EmailMessage emailMsg=null;
			for (MailHistory history : list) {
				emailMsg=new EmailMessage();
				emailMsg.setHistoryId(history.getId());				//历史记录
				emailMsg.setContent(history.getContent());			//内容
				emailMsg.setReceiver(history.getEmailAddress());	//邮件地址
				emailMsg.setSubject(history.getSubject());
				emailMsg.setCc(history.getCc());
				emailMsg.setReply(history.getReply());
				emailMsg.setPersonal(history.getPersonal());
				emailMsg.setSplit(history.isSplit());
				listEmailMessage.add(emailMsg);
			}
			emailMessages=listEmailMessage.toArray(new EmailMessage[list.size()]);
			sendMailToMq(emailMessages);
			return EmailStatus.SUCCESS;
		}catch (JMSException e) {
			logger.error("再次发送邮件到MQ异常!"+e);
			return EmailStatus.ERROR_MQ;
		}catch (Exception e) {
			logger.error("再次发送邮件到系统异常!"+e);
			return EmailStatus.ERROR_SYS;
		}
	}
	
	
	/**
	 * 把邮件发送到MQ处理
	 * @author 胡礼波
	 * 2012-11-17 上午10:55:21
	 */
	private void sendMailToMq(final EmailMessage... mail)throws JMSException
	{
		if(!ArrayUtils.isEmpty(mail))
		{
			jsmTemplate.sendDataToMQ(mail);
		}
	}

	@Override
	@Transactional
	public int addRegistMail(String email) {
		Assert.notNull(email,"添加注册邮件地址失败，地址为空!");
		int count=emailMapper.getExistMail(email);
		if(count>0)		//已存在该注册邮件
		{
			logger.info("已存在该注册邮件");
			return 1;
		}
		count=emailMapper.addRegistMail(email);
		if(count>0)
		{
			logService.addLog(new SysLog("邮件服务","添加注册邮件","添加注册邮件成功!"));
		}
		else
		{
			logService.addLog(new SysLog("邮件服务","添加注册邮件","添加注册邮件发生错误!"));
		}
		return count;
	}

	@Override
	public int getCountByTime(Date begin, Date end) {
		return 0;
	}
}
