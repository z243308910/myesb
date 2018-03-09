package com.echounion.bossmanager.core;

import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolFacade;
import com.echounion.bossmanager.entity.dto.BossProperty;


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
	public void contextDestroyed(ServletContextEvent event) {
		shutdownJdbcDriver();
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
		BossProperty.initBoss();
	}
	
	
	public static Properties loadFile(String filename) {
		Properties prop = null;
		try {
			InputStream inputStream = SystemLoaderListener.class.getClassLoader().getResourceAsStream(filename);
			prop = new Properties();
			prop.load(inputStream);
			inputStream.close();
		} catch (Exception e) {
			log.error("不能够读取配置文件 " + filename);
			throw new RuntimeException("can not read config file " + filename, e);
		}
		return prop;
	}
}
