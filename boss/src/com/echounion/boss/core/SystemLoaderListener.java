package com.echounion.boss.core;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.echounion.boss.cargosmart.si.CargoSmartFtpMonitor;
import com.echounion.boss.core.cache.CallerContainer;
import com.echounion.boss.core.cache.IpContainer;
import com.echounion.boss.core.cache.PushMailContainer;
import com.echounion.boss.core.cache.UrlContainer;

/**
 * 系统加载时监听器
 * @author 胡礼波
 * 2012-11-21 上午11:11:52
 */
@WebListener
public class SystemLoaderListener implements ServletContextListener {

	private static Logger log = Logger.getLogger(SystemLoaderListener.class);
	
	/**
	 * 系统销毁时调用
	 * @author 胡礼波
	 * 2012-11-21 上午11:13:35
	 * @param event
	 */
	public void contextDestroyed(ServletContextEvent event) 
	{
		clearCache();
		shutdownQuartz(event.getServletContext());
		shutdownJdbcDriver();
	}


	/**
	 * 清除系统缓存
	 * @author 胡礼波
	 * 2013-7-31 下午5:00:46
	 */
	public void clearCache()
	{
		IpContainer.clearContainer();
		UrlContainer.clearContainer();
		CallerContainer.clearContainer();
	}
	
	/**
	 * 关闭Quartz
	 * @author 胡礼波
	 * 2013-7-31 下午2:54:48
	 * @param ctx
	 */
	public void shutdownQuartz(ServletContext ctx)
	{
		SchedulerFactoryBean schedul=WebApplicationContextUtils.getRequiredWebApplicationContext(ctx).getBean(SchedulerFactoryBean.class);
		try {
			schedul.destroy();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭数据库连接
	 * @author 胡礼波
	 * 2013-7-31 下午2:49:32
	 */
	private void shutdownJdbcDriver()
	{
		ProxoolFacade.shutdown();
		Enumeration<Driver> drivers= DriverManager.getDrivers();
		Driver driver=null;
		while(drivers.hasMoreElements())
		{
			driver=drivers.nextElement();
			try
			{
				DriverManager.deregisterDriver(driver);
				 log.info(String.format("deregistering jdbc driver: %s", driver));
			}catch (Exception e) {
				log.info(String.format("Error deregistering driver %s", driver), e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 系统启动时调用
	 * @author 胡礼波
	 * 2012-11-21 上午11:13:46
	 * @param event
	 */
	public void contextInitialized(ServletContextEvent event) {
		log.info(".....................BOSS应用启动.....................");
		SystemConfig.initConfig();
		UrlContainer.initUrlRouter(event.getServletContext());
		
		CallerContainer.initCallerRouter(event.getServletContext());
		IpContainer.initIpRouter(event.getServletContext());
		
		PushMailContainer.initPushMailContainer(event.getServletContext());
		CargoSmartFtpMonitor.initMonitor(event.getServletContext());
	}
	
}
