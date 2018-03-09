package com.echounion.bossmanager.common.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.echounion.bossmanager.entity.SysUser;
/**
 * 系统上下文工具类
 * @author 胡礼波
 * 2012-11-1 上午10:59:08
 */
public class ContextUtil {

	private static Logger logger=Logger.getLogger(ContextUtil.class);
	
	/**
	 * 获得登录用户
	 * @author 胡礼波
	 * 2012-12-4 上午10:43:07
	 * @return
	 */
	public static SysUser getContextLoginUser()
	{
		try
		{
		SysUser admin=(SysUser)ServletActionContext.getRequest().getSession().getAttribute("adminuser");
		return admin;
		}
		catch (Exception e) {
			logger.error("获取Context User失败!"+e);
		}
		return null;
	}
	
	
	/**
	 * 获取ServletContext失败
	 * @author 胡礼波
	 * 2012-12-4 上午10:49:20
	 * @return
	 */
	public static ServletContext getServletContext()
	{
		try
		{
			ServletContext context=ServletActionContext.getServletContext();
			return context;
		}
		catch (Exception e) {
			logger.error("获取ServletContext失败!");
		}
		return null;
	}
	
	/**
	 * 获得HttpServletRequest
	 * @author 胡礼波
	 * 2012-12-4 上午10:45:11
	 */
	public static HttpServletRequest getHttpServletRequest()
	{
		try
		{
			HttpServletRequest request= ServletActionContext.getRequest();
			return request;
		}catch (Exception e) {
			logger.error("获取HttpServletRequest失败!");
		}
		return null;
	}
	
	
	/**
	 * 获取HttpServletResponse
	 * @author 胡礼波
	 * 2012-12-4 上午10:46:21
	 * @return
	 */
	public static HttpServletResponse getHttpServletResponse()
	{
		try
		{
			HttpServletResponse response= ServletActionContext.getResponse();
			return response;
		}catch (Exception e) {
			logger.error("获取HttpServletResponse失败!");
		}
		return null;
	}
}
