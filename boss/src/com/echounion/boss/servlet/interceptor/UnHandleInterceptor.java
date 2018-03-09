package com.echounion.boss.servlet.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤掉不处理的请求
 * @author 胡礼波
 * 2012-11-5 下午06:20:37
 */
public class UnHandleInterceptor implements Interceptor {

	public boolean intercept(HttpServletRequest request, HttpServletResponse response,Interceptor chain) throws IOException, ServletException {
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		String requestUrl=httpRequest.getRequestURI();
		if(requestUrl.contains("/api/sys/login"))			//登录的请求不处理
		{
			return true;
		}
		return chain.intercept(request, response, chain);
	}

}
