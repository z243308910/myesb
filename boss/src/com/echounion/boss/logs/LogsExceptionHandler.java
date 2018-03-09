package com.echounion.boss.logs;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;

import com.echounion.boss.Tracking.service.impl.TrackingServiceImpl;
import com.echounion.boss.core.system.service.impl.SysConfigServiceImpl;
import com.echounion.boss.core.system.service.impl.SysSequenceServiceImpl;
import com.echounion.boss.logs.service.impl.SysLogServiceImpl;

/**
 * AOP异常日志类
 * @author 胡礼波 2012-6-9 下午01:52:51
 */
public class LogsExceptionHandler extends AbstractLogAdvice{

	static private List<Class<?>> listC=null;
	static
	{
		listC=new ArrayList<Class<?>>();
		listC.add(SysLogServiceImpl.class);
		listC.add(TrackingServiceImpl.class);
		listC.add(SysConfigServiceImpl.class);
		listC.add(SysSequenceServiceImpl.class);		
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
		 super.addLog(join, c, "异常日志操作成功!");
	}
}
