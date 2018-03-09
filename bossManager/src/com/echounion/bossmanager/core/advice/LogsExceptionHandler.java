package com.echounion.bossmanager.core.advice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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
 * AOP
 * 异常方法日志记录类
 * @author 胡礼波 2012-6-9 下午01:52:51
 */
public class LogsExceptionHandler {

	@Resource
	private ILogService logService;
	private static Logger logger = Logger.getLogger(LogsExceptionHandler.class);
	@Autowired
	private TaskExecutor taskExecutor;
	static private List<Class<?>> listC=null;
	static
	{
		listC=new ArrayList<Class<?>>();
		listC.add(SysLogServiceImpl.class);
		listC.add(LogServiceImpl.class);
	}
	
	/**
	 * 方法异常日志处理
	 * @author 胡礼波
	 * 2012-6-9 下午02:40:55
	 * @param join
	 */
	public void doAfterThrowing(final JoinPoint join) {
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
							SysLogCons consSysLog=new SysLogCons(classModelDes,methodModelDes,"异常日志操作成功!");
							consSysLog.setIp(ip);
							if(null!=admin)
							{
								consSysLog.setUserName(admin.getUserName());
							}
							logService.addLog(consSysLog);
						}
						catch (Exception e) {
							logger.warn("记录异常日志出错"+e);
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("AOP记录异常日志出错"+e);
			}
	}
}
