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
import com.echounion.boss.entity.dto.Caller;

/**
 * 调用方容器保存系统已经授权的软件相关对象信息
 * @author 胡礼波
 * 2013-7-25 上午11:04:04
 */
public class CallerContainer {

	private CallerContainer(){}
	
	private static Logger logger=Logger.getLogger(UrlContainer.class);
	
	/**
	 * 调用方容器<br/>
	 * @author 胡礼波<br/>
	 * 2013-7-9 下午4:45:44<br/>
	 * 是一个map对象key为授权码<br/>
	 * value是服务调用方对象<br/>
	 */
	private static Map<String,Caller> callerRouter=new HashMap<String,Caller>(1000);
	
	
	/**
	 * 清空容器
	 * @author 胡礼波
	 * 2013-7-11 下午3:36:49
	 */
	public synchronized static void clearContainer()
	{
		callerRouter.clear();
	}
	
	/**
	 * 添加服务接口
	 * @author 胡礼波
	 * 2013-7-11 下午3:38:01
	 * @param key
	 * @param provider
	 */
	public synchronized static void addContainer(String key,Caller caller)
	{
		callerRouter.put(key, caller);
	}
	
	/**
	 * 添加接入方
	 * @author 胡礼波
	 * 2013-7-11 下午3:45:30
	 * @param map
	 */
	public synchronized static void addContainer(Map<String,Caller> map)
	{
		callerRouter.putAll(map);
	}
	
	/**
	 * 检查调用方是否在容器中
	 * @author 胡礼波
	 * 2013-7-9 下午4:58:23
	 * @param authorkey
	 * @return
	 */
	public static boolean isExistUrl(String authorKey)
	{
		boolean flag=callerRouter.containsKey(authorKey);
		if(!flag)
		{
			logger.warn("("+authorKey+")该授权码不存在!");
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
	public static Caller getCaller(String authorKey)
	{
		return callerRouter.get(authorKey);
	}
	
	/**
	 * 初始化URL路由表
	 * @author 胡礼波
	 * 2013-7-11 下午3:46:56
	 */
	public static void initCallerRouter(ServletContext servletCtx)
	{
		WebApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(servletCtx);
		IEsbService esbService=ctx.getBean(IEsbService.class);
		Map<String,Caller> urlMap=new HashMap<>();			//接口路由表
		Caller caller=null;
		List<Caller> list=esbService.getCallers();
		Iterator<Caller> it=list.iterator();
		
		String authorKey=null;
		while(it.hasNext()) {
			caller=it.next();
			authorKey=caller.getAuthorKey();
			if(!StringUtils.isBlank(authorKey))
			{
				urlMap.put(authorKey,caller);				
			}
		}
		CallerContainer.clearContainer();				//先清空服务路由表
		CallerContainer.addContainer(urlMap);
		logger.info("....................更新调用方路由器成功....................");
	}
	
	/**
	 * 定时器调用更新调用方路由器
	 * @author 胡礼波
	 * 2013-7-23 下午2:28:41
	 */
	public void listener()
	{
		ServletContext servletCtx=ContextLoaderListener.getCurrentWebApplicationContext().getServletContext();
		initCallerRouter(servletCtx);
	}
}
