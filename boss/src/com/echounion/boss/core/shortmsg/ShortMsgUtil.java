package com.echounion.boss.core.shortmsg;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;
import com.echounion.boss.common.enums.ShortMsgStatus;
import com.echounion.boss.common.util.DateUtil;
import com.echounion.boss.core.exceptions.ShortMsgException;
import com.echounion.boss.core.shortmsg.api.ShortMsgSession;
import com.echounion.boss.core.shortmsg.service.IShortMsgService;
import com.echounion.boss.entity.MobileHistory;
import com.echounion.boss.entity.SysLog;
import com.echounion.boss.logs.service.ILogService;


/**
 * 短信工具类
 * @author 胡礼波
 * 2012-11-17 下午05:39:03
 */
@Component
public class ShortMsgUtil {

	private static Logger logger = Logger.getLogger(ShortMsgUtil.class);
	private static ILogService<SysLog> logService;
	private static IShortMsgService mobileService;
	private static ShortMsgConfig config;
	private static ThreadPoolTaskExecutor gatewayTaskExecutor;
	
	@Autowired
	@Qualifier("shortMsgConfig")
	public void setConfig(ShortMsgConfig config) {
		ShortMsgUtil.config = config;
	}

	@Autowired
	public void setMobileService(IShortMsgService mobileService) {
		ShortMsgUtil.mobileService = mobileService;
	}

	@Autowired
	@Qualifier("SysLogServiceImpl")
	public void setLogService(ILogService<SysLog> logService) {
		ShortMsgUtil.logService = logService;
	}
	
	@Autowired
	@Qualifier("gatewayTaskExecutor")
	public void setGatewayTaskExecutor(TaskExecutor gatewayTaskExecutor) {
		ShortMsgUtil.gatewayTaskExecutor =(ThreadPoolTaskExecutor)gatewayTaskExecutor;
	}
	
	/**
	 * 短信敏感词过滤或转意
	 * @author 胡礼波
	 * 2012-11-19 上午09:35:08
	 * @param shortmsg
	 */
	public static String filterMsg(String shortmsg)
	{
		return HtmlUtils.htmlEscape(shortmsg);
	}
	
	/**
	 * 发送短信
	 * @author 胡礼波
	 * 2012-11-19 上午11:13:37
	 * @param emailMessage
	 * @throws Exception
	 */
	public static void send(final ShortMsg... shortMsg) throws Exception {
			for (final ShortMsg msg : shortMsg) {
				msg.setContent(filterMsg(msg.getContent()));		//对内容过滤转码
				Runnable runnable=new Runnable() {
					@Override
					public void run() {
						doSend(getSession(msg.getChannel()),msg);
					}
				};
				 gatewayTaskExecutor.submit(runnable);
			}
	}
	
	/**
	 * 执行发送处理
	 * @author 胡礼波
	 * 2012-11-19 下午02:06:11
	 * @param shortMsg
	 */
	private static void doSend(final ShortMsgSession session, final ShortMsg shortMsg)
	{
		Lock lock=new ReentrantLock();
			try
			{
				lock.lock();
				int index = 1;
			do{
				String result=session.mt(shortMsg.getMobile(),shortMsg.getContent(),"", DateUtil.formatterDateTime(shortMsg.getSendTime()),"");
				try {
					if(result.startsWith("-"))			//以负号判断是否发送成功
					{
						int sysValue=Integer.parseInt(result);
						if(sysValue==ShortMsgStatus.THREAD_ERROR.getSysValue() || sysValue==ShortMsgStatus.MORE_SUBMIT.getSysValue())			//由于网关不支持多线程
						{
							throw new ShortMsgException("上一次短信提交未返回，发送异常！");	
						}
						ShortMsgStatus status=ShortMsgStatus.getStatusBySysValue(sysValue);
						logger.info("短信<"+shortMsg.getMobile()+">发送失败，返回值为("+status.getCode()+"):"+status.getName());
						logService.addLog(new SysLog("短信服务","发送短信","短信<"+shortMsg.getMobile()+">发送失败!，返回值为("+status.getCode()+"):"+status.getName()));
						break;
					}
					else
					{
						mobileService.editMobileHistoryStatus(shortMsg.getHistoryId(),MobileHistory.SEND_SUCCESS);		//发送成功更新状态
						logger.info("短信<"+shortMsg.getMobile()+">第"+index+"次发送成功!");
						logService.addLog(new SysLog("短信服务","发送短信","短信<"+shortMsg.getMobile()+">发送成功!"));
						break;
					}
				} catch (Exception e) {
					mobileService.editMobileHistoryStatus(shortMsg.getHistoryId(), MobileHistory.SEND_FAIL);		//发送失败更新状态
					if(index<3)
					{
						logger.error("短信<"+shortMsg.getMobile()+">发送第"+index+"次失败，准备第"+(index+1)+"次尝试发送!" + e);
					}
					else
					{
						logger.error("短信<"+shortMsg.getMobile()+">发送第"+index+"次失败，发送停止!" + e);							
					}
					logService.addLog(new SysLog("短信服务","发送短信","短信<"+shortMsg.getMobile()+">发送第"+index+"次失败!"));
					index++;
					try{Thread.sleep(5000);}catch (Exception ex) {}
				}
			}while(index<=3);
		}
		finally
		{
			lock.unlock();
		}
			
	}
	
	/**
	 * 获得短信网关session
	 * 根据不同的短信配置调用不同的短信网关
	 * @author 胡礼波
	 * 2012-11-19 下午02:04:45
	 * @return
	 */
	public static ShortMsgSession getSession(String name)
	{
		ShortMsgSession session=null;
		ShortMsgConfigDTO config=getConfigDTO(name);
		try {
			session = new  ShortMsgSession(config.getUserName(),config.getPassword(),config.getHost());
		} catch (UnsupportedEncodingException e) {
			logger.error("获得短信网关通道出错!"+e);
		}
		return session;
	}
	
	/**
	 * 获得短信通道DTO 如果没有指定的通道则获取默认的通道
	 * @author 胡礼波
	 * 2014-1-21 上午10:14:23
	 * @param name
	 * @return
	 */
	private static ShortMsgConfigDTO getConfigDTO(String name)
	{
		List<ShortMsgConfigDTO> configs=config.getConfigs();
		ShortMsgConfigDTO configDTO=null;
		for (ShortMsgConfigDTO shortMsgConfigDTO : configs) {
			if(shortMsgConfigDTO.getCode().equals(name))
			{
				configDTO=shortMsgConfigDTO;
				break;
			}
		}
		configDTO=configDTO==null?getConfigDTO("default"):configDTO;
		return configDTO;
	}
}
