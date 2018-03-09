package com.echounion.boss.core;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.echounion.boss.common.util.FileUtil;

/**
 * 系统配置类
 * @author 胡礼波
 * 2013-7-22 下午2:06:55
 */
public class SystemConfig {

	private static Logger log = Logger.getLogger(SystemConfig.class);
	
	public static String SERVERIP;		//服务器IP
	
	/**
	 * 初始化系统配置
	 * @author 胡礼波
	 * 2013-7-22 下午2:08:21
	 */
	public static void initConfig()
	{
		Properties p =FileUtil.getPropertiesFile("system.properties");
		SERVERIP=p.getProperty("hostIp");
		log.info("....................服务器配置初始化成功....................");
	}
}
