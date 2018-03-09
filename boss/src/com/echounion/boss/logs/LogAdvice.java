package com.echounion.boss.logs;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import com.echounion.boss.Tracking.service.impl.TrackingServiceImpl;
import com.echounion.boss.core.email.service.impl.EmailServiceImpl;
import com.echounion.boss.core.esb.service.impl.EsbServiceImpl;
import com.echounion.boss.core.shortmsg.service.impl.ShortMsgServiceImpl;
import com.echounion.boss.core.system.service.impl.SysConfigServiceImpl;
import com.echounion.boss.core.system.service.impl.SysSequenceServiceImpl;
import com.echounion.boss.logs.service.impl.EsbApiLogServiceImpl;
import com.echounion.boss.logs.service.impl.SysLogServiceImpl;

/**
 * AOP系统日志记录类
 * @author 胡礼波
 * 2012-5-24 下午04:08:25
 */
public class LogAdvice extends AbstractLogAdvice{

	
	static private List<Class<?>> listC=null;
	static
	{
		listC=new ArrayList<Class<?>>();
		listC.add(SysLogServiceImpl.class);
		listC.add(EsbApiLogServiceImpl.class);
		listC.add(TrackingServiceImpl.class);
		listC.add(SysConfigServiceImpl.class);
		listC.add(SysSequenceServiceImpl.class);
		listC.add(EmailServiceImpl.class);
		listC.add(ShortMsgServiceImpl.class);
		listC.add(EsbServiceImpl.class);
	}
	
	/**
	 * 方法级别的日志处理
	 * @author 胡礼波
	 * 2013-7-10 下午4:22:06
	 * @param join
	 */
	public void addLog(final JoinPoint join) 
	{
		final Class<?> c=join.getTarget().getClass();
		 if(listC.contains(c))	//过滤不需要日志处理的类
		 {
			 return;
		 }
		super.addLog(join, c, "日志操作成功!");
	}
}
