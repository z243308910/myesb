package com.echounion.boss.common.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * SpringBean工具类
 * @author 胡礼波
 * 2013-10-17 上午10:13:20
 */
public class SpringBeanUtil{

	/**
	 * 获得Bean实例
	 * @author 胡礼波
	 * 2013-10-17 上午10:28:20
	 * @param sctx
	 * @return
	 */
	public static Object getBean(ServletContext sctx,Class<?> cls)
	{
		ApplicationContext ctx=getApplicationContext(sctx);
		Object t=ctx.getBean(cls);
		return t;
	}
	
	
	/**
	 * 获取spring容器上下文
	 * @author 胡礼波
	 * 2013-10-17 上午10:20:33
	 * @param sctx
	 * @return
	 */
	public static ApplicationContext getApplicationContext(ServletContext sctx)
	{
		ApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);
		return ctx;
	}
}
