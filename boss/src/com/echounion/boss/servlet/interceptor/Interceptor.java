package com.echounion.boss.servlet.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统拦截器接口
 * @author 胡礼波
 * 2012-11-2 上午11:59:22
 */
public interface Interceptor {

	/**
	 * 拦截处理
	 * @author 胡礼波
	 * 2012-11-2 下午12:00:08
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean intercept(HttpServletRequest request,HttpServletResponse response,Interceptor chain) throws IOException, ServletException ;
}
