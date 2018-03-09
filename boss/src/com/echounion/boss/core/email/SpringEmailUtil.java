package com.echounion.boss.core.email;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import com.echounion.boss.common.util.FileUtil;
import com.echounion.boss.core.email.service.IEmailService;
import com.echounion.boss.core.exceptions.EmailException;
import com.echounion.boss.entity.MailHistory;
import com.echounion.boss.entity.SysLog;
import com.echounion.boss.logs.service.ILogService;

import freemarker.template.Template;

/**
 * 邮件工具类
 * 
 * @author 胡礼波 2012-11-15 下午04:23:53
 */
@Component
public class SpringEmailUtil {

	private static Logger logger = Logger.getLogger(SpringEmailUtil.class);
	
	private static FreeMarkerConfigurer freeMarkerConfigurer;
	private static ThreadPoolTaskExecutor gatewayTaskExecutor;
	private static IEmailService emailService;
	private static ILogService<SysLog> logService;

	/**
	 * 获得邮件Session
	 * isSpecile 是否走特殊处理 163 126
	 * @author 胡礼波 2012-11-15 下午04:25:19
	 * @return
	 */
	public synchronized static JavaMailSenderImpl getMailSender(String channel,String recever,boolean isSpecile) {
		JavaMailSenderImpl sender=new JavaMailSenderImpl();
		
		EmailConfig emailConfig=EmailConfig.getConfig(channel,recever,isSpecile);
		sender=new JavaMailSenderImpl();
		sender.setUsername(emailConfig.getUsername());
		sender.setHost(emailConfig.getHost());
		sender.setPassword(emailConfig.getPassword());
		sender.setPort(emailConfig.getPort());
		sender.setDefaultEncoding(emailConfig.getEncoding());
		sender.getJavaMailProperties().setProperty("from",emailConfig.getFrom());
		sender.getJavaMailProperties().setProperty("mail.smtp.auth","true");
		return sender;
	}

	
	/**
	 * 根据邮件实体对象组装成邮件消息对象
	 * @author 胡礼波 2012-11-15 下午06:51:36
	 * @param session
	 * @param emailMessage
	 * @return
	 * @throws Exception
	 */
	private static MimeMessage getMessage(JavaMailSenderImpl javaMailSender,EmailMessage emailMessage)
			throws Exception {
		MimeMessage message =javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(message,true,javaMailSender.getDefaultEncoding());
		
		messageHelper.setSubject(emailMessage.getSubject()); 						// 设置主题
		
		String nickName=emailMessage.getPersonal();
		String from=javaMailSender.getJavaMailProperties().getProperty("from");
		if(!StringUtils.isBlank(nickName))
		{
			messageHelper.setFrom(new InternetAddress(MimeUtility.encodeText(nickName)+ " <" + from + ">")); // 邮件来源
		}else
		{
			messageHelper.setFrom(new InternetAddress(from)); // 邮件来源			
		}
		
		List<String> addressList=emailMessage.getReciList();
		if(addressList!=null)
		{
			if(emailMessage.isSplit())				//拆分发送
			{
				for (String address: addressList) {
					messageHelper.addTo(address);				//收件人
				}				
			}
			else
			{
				String mls[]=new String[]{};
				mls=addressList.toArray(mls);
				messageHelper.setTo(mls);				
			}
		}
		
		if(!StringUtils.isBlank(emailMessage.getReply()))
		{
			messageHelper.setReplyTo(emailMessage.getReply()); // 邮件答复地址
		}
		
		addressList=emailMessage.getCcList();
		if(addressList!=null)
		{
			for (String ccAddress: addressList) {			//设置抄送人
				messageHelper.addCc(ccAddress);
			}
		}
		messageHelper.setPriority(Integer.parseInt(emailMessage.getPriority())); // 指定邮件优先级
		
		messageHelper.setText(emailMessage.getContent(),true); 		// 设置正文内容

		if(CollectionUtils.isNotEmpty(emailMessage.getAttachment()))
		{
			File file=null;
			for (String url:emailMessage.getAttachment()) {		//添加附件
				file=FileUtil.downloadFile(url);
				messageHelper.addAttachment(MimeUtility.encodeText(file.getName()),file);
			}
		}
		return message;
	}

	
	/**
	 * 发送邮件---群发、单发 该方法提高性能
	 * @author 胡礼波 2012-11-15 下午04:25:35
	 * @param emailMessage
	 * @param configName 邮件服务器代号
	 * @return
	 * @throws Exception
	 */
	public static void send(final EmailMessage... emailMessages) throws Exception {
		for (final EmailMessage emailMessage : emailMessages) {
			Runnable runnable=new Runnable() {
				@Override
				public void run() {
					MimeMessage message =null;
					try
					{
						JavaMailSenderImpl sender=getMailSender(emailMessage.getChannel(),emailMessage.getReceiver(),true);
						message= getMessage(sender,emailMessage);
						doSend(sender,emailMessage,message);
					}catch (Exception e)
					{
						logger.error("邮件系统异常",e);
					}
				}
			};
			gatewayTaskExecutor.submit(runnable);
		}
	}
	
	
	/**
	 * 执行发送操作
	 * @author 胡礼波
	 * 2012-11-19 下午01:33:49
	 * @param emailMessage
	 * @param transport
	 * @param message
	 */
	private static void doSend(JavaMailSenderImpl mailSender,final EmailMessage emailMessage, MimeMessage message) {
		int index = 1;
		String fromMail=mailSender.getUsername();
		do {
			try {
				mailSender.send(message);
				emailService.editEmailHistoryStatus(emailMessage.getHistoryId(), MailHistory.SEND_SUCCESS);		//发送成功更新状态
				logger.info("邮件<"+fromMail+"  send to  "+emailMessage.getReceiver()+">发送成功!");
				logService.addLog(new SysLog("邮件服务","发送邮件","邮件("+fromMail+"  send to  "+emailMessage.getReceiver()+")第"+index+"次发送成功!"));
				break;
			} catch (Exception e) {
				mailSender=getMailSender(emailMessage.getChannel(),emailMessage.getReceiver(),false);	//重新选择通道替换原有通道
				String nickName=emailMessage.getPersonal();
				String from=mailSender.getJavaMailProperties().getProperty("from");
				try
				{
					if(!StringUtils.isBlank(nickName))
					{
						message.setFrom(new InternetAddress(MimeUtility.encodeText(nickName)+ " <" + from + ">")); // 邮件来源
					}else
					{
						message.setFrom(new InternetAddress(from)); // 邮件来源			
					}
				}catch (Exception ex) {}
				emailService.editEmailHistoryStatus(emailMessage.getHistoryId(), MailHistory.SEND_FAIL);		//发送失败更新状态
				if(index<3)
				{
					logger.error("邮件<"+fromMail+"  send to  "+emailMessage.getReceiver()+">发送第"+index+"次失败，准备第"+(index+1)+"次尝试发送!" + e);
				}
				else
				{
					logger.error("邮件<"+fromMail+"  send to  "+emailMessage.getReceiver()+">发送第"+index+"次失败，发送停止!" + e);
				}
				logService.addLog(new SysLog("邮件服务","发送邮件","邮件("+fromMail+"  send to  "+emailMessage.getReceiver()+")发送第"+index+"次失败!"));
				index++;
				try{Thread.sleep(5000);}catch (Exception ex) {logger.error(ex);}
			}
		} while (index <=3);
	}
	
	/**
	 * 组装邮件文本内容
	 * 
	 * @author 胡礼波 2012-11-15 下午06:24:37
	 * @param map
	 * @param tplName
	 * @return
	 */
	public static String assembleContent(Map<?, ?> map, String tplName) {
		String content = null;
		try {
			Template tpl = null;
			if (MapUtils.isNotEmpty(map)) {
				tplName = tplName + ".html";
				tpl = freeMarkerConfigurer.getConfiguration().getTemplate(tplName);
				content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
			}
		} catch (Exception e) {
			logger.error("模版生成邮件内容出错!" + e);
			logService.addLog(new SysLog("邮件服务","邮件内容生成","模版生成邮件内容出错"));
			throw new EmailException("模版生成邮件内容出错"+e);
		}
		return content;
	}
	
	/**
	 * 检查模版是否存在
	 * @author 胡礼波
	 * 2013-7-27 下午2:05:08
	 * @return
	 */
	public static boolean isExistTpl(String tplName)
	{
		Template tpl=null;
		try {
			tplName = tplName + ".html";
			tpl = freeMarkerConfigurer.getConfiguration().getTemplate(tplName);
		} catch (IOException e) {
			tpl=null;
		}
		return tpl==null?false:true;
	}

	@Autowired
	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		SpringEmailUtil.freeMarkerConfigurer = freeMarkerConfigurer;
	}
	
	@Autowired
	@Qualifier("gatewayTaskExecutor")
	public void setGatewayTaskExecutor(TaskExecutor gatewayTaskExecutor) {
		SpringEmailUtil.gatewayTaskExecutor =(ThreadPoolTaskExecutor)gatewayTaskExecutor;
	}

	@Autowired
	public void setEmailService(IEmailService emailService) {
		SpringEmailUtil.emailService = emailService;
	}

	@Autowired
	@Qualifier("SysLogServiceImpl")
	public void setLogService(ILogService<SysLog> logService) {
		SpringEmailUtil.logService = logService;
	}
}
