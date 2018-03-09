package com.echounion.boss.core.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.echounion.boss.core.esb.service.IEsbService;
import com.echounion.boss.entity.dto.Provider;

/**
 * ESB接口API容器
 * 主要是存放接入方的url基本信息
 * @author 胡礼波
 * 2013-7-9 下午4:45:44
 */
public class UrlContainer {
	
	private static Logger logger=Logger.getLogger(UrlContainer.class);
	
	/**
	 * 接入方接口路由表<br/>
	 * @author 胡礼波<br/>
	 * 2013-7-9 下午4:45:44<br/>
	 * 是一个map对象key为boss提供调入放的url<br/>
	 * value是服务提供方对象<br/>
	 */
	private static Map<String,Provider> urlRouter=new HashMap<String,Provider>(1000);
	
	/**
	 * 接入方接口路由表
	 * @author 胡礼波<br/>
	 * 2013-7-9 下午4:45:44<br/>
	 * 是一个map对象key为boss提供调入放的url代号即 service_code<br/>
	 * value是服务提供方对象<br/>
	 */
	private static Map<String,Provider> codeRouter=new HashMap<String,Provider>(1000);
	
	private UrlContainer(){}
	
	/**
	 * 清空接口路由器
	 * @author 胡礼波
	 * 2013-7-11 下午3:36:49
	 */
	public synchronized static void clearContainer()
	{
		urlRouter.clear();
		codeRouter.clear();
	}
	
	/**
	 * 添加服务接口
	 * @author 胡礼波
	 * 2013-7-11 下午3:38:01
	 * @param key
	 * @param provider
	 */
	public synchronized static void addContainer(String key,Provider provider)
	{
		urlRouter.put(key, provider);
	}

	/**
	 * 添加服务接口根据接口代号
	 * @author 胡礼波
	 * 2013-7-11 下午3:38:01
	 * @param key
	 * @param provider
	 */
	public synchronized static void addContainerByCode(String key,Provider provider)
	{
		codeRouter.put(key, provider);
	}
	
	/**
	 * 添加服务接口
	 * @author 胡礼波
	 * 2013-7-11 下午3:45:30
	 * @param map
	 */
	public synchronized static void addContainer(Map<String,Provider> map)
	{
		urlRouter.putAll(map);
	}

	/**
	 * 添加服务接口根据接口代号
	 * @author 胡礼波
	 * 2013-7-11 下午3:45:30
	 * @param map
	 */
	public synchronized static void addContainerByCode(Map<String,Provider> map)
	{
		codeRouter.putAll(map);
	}
	
	/**
	 * 检查url是否在消息路由表中
	 * @author 胡礼波
	 * 2013-7-9 下午4:58:23
	 * @param url
	 * @return
	 */
	public static boolean isExistUrl(String url)
	{
		boolean flag=urlRouter.containsKey(url);
		if(!flag)
		{
			logger.warn("("+url+")该地址不存在!");
		}
		return flag;
	}
	
	/**
	 * 检查url代号是否在消息路由表中
	 * @author 胡礼波
	 * 2013-7-9 下午4:58:23
	 * @param url
	 * @return
	 */
	public static boolean isExistCode(String serviceCode)
	{
		boolean flag=codeRouter.containsKey(serviceCode);
		if(!flag)
		{
			logger.warn("("+serviceCode+")该serviceCode不存在!");
		}
		return flag;
	}
	
	/**
	 * 获得接入方对象
	 * @author 胡礼波
	 * 2013-7-9 下午5:05:02
	 * @param url
	 * @return
	 */
	public static Provider getServiceProvider(String url)
	{
		return urlRouter.get(url);
	}

	/**
	 * 获得接入方对象根据接口代号
	 * @author 胡礼波
	 * 2013-7-9 下午5:05:02
	 * @param url
	 * @return
	 */
	public static Provider getServiceProviderByCode(String serviceCode)
	{
		return codeRouter.get(serviceCode);
	}
	
	/**
	 * 初始化URL路由表
	 * @author 胡礼波
	 * 2013-7-11 下午3:46:56
	 */
	public static void initUrlRouter(ServletContext servletCtx)
	{
		WebApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(servletCtx);
		IEsbService esbService=ctx.getBean(IEsbService.class);
		
		Map<String,Provider> urlMap=new HashMap<>();			//接口路由表
		
		Map<String,Provider> codeMap=new HashMap<>();			//接口路由表
		
		Provider provider=null;
		List<Provider> list=esbService.getProviders();
		Iterator<Provider> it=list.iterator();
		
		String serviceUrl=null;
		String serviceCode=null;
		while(it.hasNext()) {
			provider=it.next();
			serviceUrl=provider.getServiceUrl();
			serviceCode=provider.getServiceCode();
			if(!StringUtils.isBlank(serviceUrl))
			{
				urlMap.put(provider.getServiceUrl(),provider);
				if(!StringUtils.isBlank(serviceCode))
				{
					codeMap.put(provider.getServiceCode(),provider);
				}
			}
		}
		UrlContainer.clearContainer();				//先清空服务路由表
		UrlContainer.addContainer(urlMap);
		UrlContainer.addContainerByCode(codeMap);
		logger.info("....................更新服务提供方接口路由器成功....................");
	}
	
	/**
	 * 定时器调用更新接口路由器
	 * @author 胡礼波
	 * 2013-7-23 下午2:28:41
	 */
	public void listener()
	{
		ServletContext servletCtx=ContextLoaderListener.getCurrentWebApplicationContext().getServletContext();
		initUrlRouter(servletCtx);
	}
	
}
