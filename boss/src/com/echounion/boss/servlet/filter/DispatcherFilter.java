package com.echounion.boss.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.util.NetUtil;
import com.echounion.boss.servlet.interceptor.InterceptorChain;

/**
 * 系统总的请求分发器
 * @author 胡礼波
 * 2012-11-2 上午10:31:54
 */
@WebFilter(filterName="dispatcherFilter",urlPatterns="/*",asyncSupported=true)
public class DispatcherFilter implements Filter {

	private Logger logger=Logger.getLogger(DispatcherFilter.class);
	
	private InterceptorChain interceptorChain=null;
	
	public void destroy() {}
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {

		HttpServletRequest servletRequest=(HttpServletRequest)request;
		logger.info(NetUtil.getClientIp(servletRequest)+" "+servletRequest.getMethod()+" request resource:"+servletRequest.getRequestURL());
		request.setCharacterEncoding(Constant.ENCODEING_UTF8);
		try
		{
			boolean flag=interceptorChain.intercept((HttpServletRequest)request, (HttpServletResponse)response, interceptorChain);
			if(flag)
			{
				chain.doFilter(request,response);
			}
		}finally
		{
			interceptorChain.clearThreadLocal();
		}
		
	}

	/**
	 * 
	 * @author 胡礼波
	 * 2012-11-2 下午12:48:20
	 * @param config
	 * @throws ServletException
	 */
	public void init(FilterConfig config) throws ServletException {
		logger.info("......系统分发器启动......");
		this.interceptorChain=new InterceptorChain();
	}
}
