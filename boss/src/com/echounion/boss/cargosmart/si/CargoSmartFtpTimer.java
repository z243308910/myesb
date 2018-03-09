package com.echounion.boss.cargosmart.si;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.core.esb.service.IEsbService;
import com.echounion.boss.core.monitor.service.impl.IMonitorService;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;

/**
 * cargosmart ftp目录监控定时器
 * @author 胡礼波
 * 2013-11-22 上午10:56:08
 */
public class CargoSmartFtpTimer {

	private static Logger log = Logger.getLogger(CargoSmartFtpTimer.class);
	
	private CargoSmartConfig config;
	
	private IMonitorService ftpDirMonitorService;
	private IEsbService esbService;
	
	public void setEsbService(IEsbService esbService) {
		this.esbService = esbService;
	}

	public void setConfig(CargoSmartConfig config) {
		this.config = config;
	}

	public void setFtpDirMonitorService(IMonitorService ftpDirMonitorService) {
		this.ftpDirMonitorService = ftpDirMonitorService;
	}

	/**
	 * 监控CT
	 * @author 胡礼波
	 * 2013-11-22 上午10:57:45
	 */
	public void listenerCt()
	{
		String path=config.getMonitorLocalPath().get("ct");
		log.info("定时器开始扫描"+path);
		doTimer(path);
	}
	
	/**
	 * 监控Si的回执
	 * @author 胡礼波
	 * 2013-11-22 上午11:06:01
	 */
	public void listenerAck()
	{
		String path=config.getMonitorLocalPath().get("ack");
		log.info("定时器开始扫描"+path);
		doTimer(path);		
	}

	/**
	 * 监控bl的目录
	 * @author 胡礼波
	 * 2013-12-17 上午9:59:57
	 */
	public void listenerBl()
	{
		String path=config.getMonitorLocalPath().get("bl");
		log.info("定时器开始扫描"+path);
		doTimer(path);
	}
	
	/**
	 * 监控si的目录
	 * @author 胡礼波
	 * 2014-3-29 下午1:03:25
	 */
	public void listenerSi()
	{
		String path=config.getMonitorLocalPath().get("si");
		log.info("定时器开始扫描"+path);
		doTimer(path);		
	}
	
	/***
	 * 处理监控文件
	 * @author 胡礼波
	 * 2013-11-22 上午11:07:53
	 * @param dir
	 */
	private void doTimer(String path)
	{
		File dir=new File(path);
		if(dir==null || !dir.isDirectory())			//不问空 且是文件夹
		{
			return;
		}
		File[] files=dir.listFiles();
		if(files==null)
		{
			return;
		}
		for (File file : files) {
			if(!file.isFile())
			{
				continue;
			}
			ftpDirMonitorService.onCreate(Paths.get(file.getName()));			
		}
	}
	
	/**
	 * SiCron执行计划
	 * @author 胡礼波
	 * 2013-12-9 下午4:11:41
	 */
	public void listenerSiCronFailure()
	{
		Provider provider= UrlContainer.getServiceProviderByCode("acksicron");		//前端后端各自触发
		invokeSiCron(provider);
		
		provider= UrlContainer.getServiceProviderByCode("bcacksicron");		//前端后端各自触发
		invokeSiCron(provider);
	}
	
	private void invokeSiCron(Provider provider)
	{
		try
		{
			if(provider!=null)
			{
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("clientKey",provider.getAuthorKey());
				provider.setParamMap(params);
				
				Record record=esbService.invokeService(provider);
				log.info(record.getMessage());
			}else
			{
				log.warn("SI定时失败任务回调接口服务提供方为空!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
