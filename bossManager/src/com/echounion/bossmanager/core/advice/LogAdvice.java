package com.echounion.bossmanager.core.advice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.echounion.bossmanager.common.reflect.ReflectUtils;
import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.common.util.NetUtil;
import com.echounion.bossmanager.entity.SysLogCons;
import com.echounion.bossmanager.entity.SysUser;
import com.echounion.bossmanager.service.ILogService;
import com.echounion.bossmanager.service.impl.LogServiceImpl;
import com.echounion.bossmanager.service.impl.SysLogServiceImpl;

/**
 * AOP系统日志记录类
 * @author 胡礼波
 * 2012-5-24 下午04:08:25
 */
public class LogAdvice{

	@Autowired
	private ILogService logService;
	private static Logger logger=Logger.getLogger(LogAdvice.class);
	@Autowired
	private TaskExecutor taskExecutor;
	static private List<Class<?>> listC=null;
	static
	{
		listC=new ArrayList<Class<?>>();
		listC.add(SysLogServiceImpl.class);
		listC.add(LogServiceImpl.class);
	}
	
	public void addLog(final JoinPoint join) 
	{
		final Class<?> c=join.getTarget().getClass();
		if(listC.contains(c))	//过滤不需要日志处理的类
		 {
			 return;
		 }
			try {
				final SysUser admin=ContextUtil.getContextLoginUser();
				final String ip=NetUtil.getClientIp(ContextUtil.getHttpServletRequest());
				taskExecutor.execute(new Runnable()
				{
					public void run()
					{
						try
						{
							String methodName=join.getSignature().getName();
							String classModelDes=ReflectUtils.getModelDescription(c);
							Class<?>[] types=((CodeSignature)join.getStaticPart().getSignature()).getParameterTypes();
							String methodModelDes=ReflectUtils.getModelDescription(c.getMethod(methodName,types));
							SysLogCons consSysLog=new SysLogCons(classModelDes,methodModelDes,"日志操作成功!");
							consSysLog.setIp(ip);
							if(null!=admin)
							{
								consSysLog.setUserName(admin.getUserName());
							}
							logService.addLog(consSysLog);
						}
						catch (Exception e) {
							logger.warn("记录日志出错"+e);
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("AOP记录日志出错"+e);
			}
	}
}
