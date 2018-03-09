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

import com.echounion.boss.common.util.ValidateUtil;
import com.echounion.boss.core.esb.service.IEsbService;

/**
 * IP路由表
 * 主要是针对调用方IP进行过滤判断 是否已经授权
 * @author 胡礼波
 * 2013-7-25 上午11:04:04
 */
public class IpContainer {

	private IpContainer(){}
	
	private static Logger logger=Logger.getLogger(IpContainer.class);
	
	/**
	 * IP路由器<br/>
	 * @author 胡礼波<br/>
	 * 2013-7-9 下午4:45:44<br/>
	 * 是一个map对象key为授权码<br/>
	 * value是服务调用方对象<br/>
	 */
	private static Map<String,String> ipRouter=new HashMap<String,String>(100);
	
	
	/**
	 * 清空容器
	 * @author 胡礼波
	 * 2013-7-11 下午3:36:49
	 */
	public synchronized static void clearContainer()
	{
		ipRouter.clear();
	}
	
	/**
	 * 添加IP
	 * @author 胡礼波
	 * 2013-7-11 下午3:38:01
	 * @param key
	 * @param provider
	 */
	public synchronized static void addContainer(String key,String ip)
	{
		ipRouter.put(key, ip);
	}
	
	/**
	 * 添加Ip
	 * @author 胡礼波
	 * 2013-7-11 下午3:45:30
	 * @param map
	 */
	public synchronized static void addContainer(Map<String,String> map)
	{
		ipRouter.putAll(map);
		logger.info("IPTables:"+map);
	}
	
	/**
	 * 检查IP是否在容器中
	 * @author 胡礼波
	 * 2013-7-9 下午4:58:23
	 * @param authorkey
	 * @return
	 */
	public static boolean isExistIp(String ip)
	{
		boolean flag=ipRouter.containsKey(ip);
		if(!flag)
		{
			logger.warn("("+ip+")该IP未授权!");
		}
		return flag;
	}
	
	/**
	 * 获得调用方对象
	 * @author 胡礼波
	 * 2013-7-9 下午5:05:02
	 * @param url
	 * @return
	 */
	public static String getCaller(String ip)
	{
		return ipRouter.get(ip);
	}
	
	/**
	 * 初始化URL路由表
	 * @author 胡礼波
	 * 2013-7-11 下午3:46:56
	 */
	public static void initIpRouter(ServletContext servletCtx)
	{
		WebApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(servletCtx);
		IEsbService esbService=ctx.getBean(IEsbService.class);
		Map<String,String> urlMap=new HashMap<>();			//接口路由表
		String ip=null;
		String[] ips=null;
		List<String> list=esbService.getIp();
		Iterator<String> it=list.iterator();
		while(it.hasNext()) {
			ip=it.next();
			if(!StringUtils.isBlank(ip))
			{
				ips=ip.split(",");
				for (String tempIp : ips) {
					if(ValidateUtil.isIp(tempIp))
					{
						urlMap.put(tempIp,tempIp);
					}
				}
			}
		}
		IpContainer.clearContainer();				//先清空服务路由表
		IpContainer.addContainer(urlMap);
		logger.info("....................更新IP防火墙成功....................");
	}
	
	/**
	 * 定时器调用更新调用方路由器
	 * @author 胡礼波
	 * 2013-7-23 下午2:28:41
	 */
	public void listener()
	{
		ServletContext servletCtx=ContextLoaderListener.getCurrentWebApplicationContext().getServletContext();
		initIpRouter(servletCtx);
	}
}
