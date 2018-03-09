package com.echounion.boss.logs;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;

import com.echounion.boss.common.util.reflect.ReflectUtils;
import com.echounion.boss.entity.SysLog;
import com.echounion.boss.logs.service.ILogService;

/**
 * 抽象日志记录类
 * @author 胡礼波
 * 2013-2-28 下午5:38:21
 */
public class AbstractLogAdvice {

	@Autowired
	@Qualifier("SysLogServiceImpl")
	private ILogService<SysLog> logService;
	@Autowired @Qualifier("logTaskExecutor")
	private TaskExecutor logTaskExecutor;
	private static Logger logger=Logger.getLogger(AbstractLogAdvice.class);
	
	public void addLog(final JoinPoint join,final Class<?> c,final String message) 
	{
		try
		{
			logTaskExecutor.execute(new Runnable()
			{
				public void run()
				{
					try {
								String methodName=join.getSignature().getName();
								String classModelDes=ReflectUtils.getModelDescription(c);
								Class<?>[] types=((CodeSignature)join.getStaticPart().getSignature()).getParameterTypes();
								String methodModelDes=ReflectUtils.getModelDescription(c.getMethod(methodName,types));
								SysLog log=new SysLog(classModelDes,methodModelDes,message);
								logService.addLog(log);
					}
					catch (Exception e) {
						logger.error(message+e);
					}
				}
			});
		}catch (Exception e) {
			logger.error(message+e);
		}
	}
}
