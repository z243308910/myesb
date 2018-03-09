package com.echounion.boss.cargosmart.si;

import java.util.Map;

import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.echounion.boss.core.monitor.MonitorDir;
import com.echounion.boss.core.monitor.service.impl.IMonitorService;

/**
 * cargosmart ftp目录监控
 * @author 胡礼波
 * 2013-10-14 下午3:44:40
 */
public class CargoSmartFtpMonitor {

	private static Logger log = Logger.getLogger(CargoSmartFtpMonitor.class);
	
	/**
	 * 初始化监控
	 * @author 胡礼波
	 * 2013-10-14 下午3:45:04
	 */
	public static void initMonitor(ServletContext servletCtx)
	{
		WebApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(servletCtx);
		IMonitorService monitorService=ctx.getBean("FtpDirMonitorServiceImpl",IMonitorService.class);
		
		CargoSmartConfig pathConfig=ctx.getBean("cargosmartPath",CargoSmartConfig.class); 
		
		MonitorDir dir=new MonitorDir();
		dir.setMonitorService(monitorService);
		try {
			Map<String,String> map=pathConfig.getMonitorLocalPath();
			
			String paths[]=new String[map.size()];
			paths=map.values().toArray(paths);
			dir.watch(paths);
			log.info("CargoSmart FTP目录监听启动成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
