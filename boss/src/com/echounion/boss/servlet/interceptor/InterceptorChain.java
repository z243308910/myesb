package com.echounion.boss.servlet.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.echounion.boss.common.util.ThreadLocalUtil;

/**
 * 拦截器处理链
 * @author 胡礼波
 * 2012-11-2 下午12:04:14
 */
public class InterceptorChain implements Interceptor {

	protected List<Interceptor> executeInterceptors=new ArrayList<Interceptor>();		//存放过滤的Filter
	protected Lock lock=new ReentrantLock();
	private int index=0;

	public boolean intercept(HttpServletRequest request, HttpServletResponse response,Interceptor chain)throws IOException, ServletException {
		try
		{
			lock.lock();
			if(index==executeInterceptors.size())
			{
				return true;
			}
			Interceptor interceptor=executeInterceptors.get(index);
			index++;
			return interceptor.intercept(request,response,chain);
		}
		finally
		{
			index=0;
			lock.unlock();
		}
	}
	
	public InterceptorChain(){initChain();}
	
	/**
	 * 往拦截器链中添加拦截器
	 * @author 胡礼波
	 * 2012-11-2 下午12:42:29
	 * @param interceptor
	 * @return
	 */
	public InterceptorChain addInterceptor(Interceptor interceptor )
	{
		this.executeInterceptors.add(interceptor);
		return this;
	}
	
	/**
	 * 初始化拦截器链条
	 * @author 胡礼波
	 * 2012-11-2 下午12:50:18
	 */
	public void initChain()
	{
		this.addInterceptor(new IpRouterInterceptor());
		this.addInterceptor(new SecurityInterceptor());
		this.addInterceptor(new UrlResolveInterceptor());
	}
	
	/**
	 * 从ThreadLocal中移除线程绑定数据
	 * @author 胡礼波
	 * 2013-7-9 下午3:50:18
	 */
	public void clearThreadLocal()
	{
		ThreadLocalUtil.remove();
	}
	
}
