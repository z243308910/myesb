package com.echounion.boss.servlet.interceptor;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.SystemStatus;
import com.echounion.boss.core.SystemConfig;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.core.esb.service.IEsbService;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;

/**
 * URL解析拦截器器
 * @author 胡礼波
 * 2013-7-9 下午4:24:27
 */
public class UrlResolveInterceptor extends AbstractInterceptor {

	private IEsbService esbService;
	
	@Override
	public boolean intercept(HttpServletRequest request,HttpServletResponse response, Interceptor chain)throws IOException, ServletException {
		String url=request.getRequestURI();
		url=getServiceUrl(request);
		if(!UrlContainer.isExistUrl(url))			//服务接口不存在
		{
			writeJsonData(Record.setFailRecord(SystemStatus.URL_NOT_EXIST.getCode(),SystemStatus.URL_NOT_EXIST.getName(),RecordType.TEXT),response);
			return false;
		}
		Provider provider=UrlContainer.getServiceProvider(url);
		if(isSelfService(provider))				   //是boss自身的服务 则调用自身的servlet 否则远程调用走工厂
		{
			boolean result=chain.intercept(request, response, chain);
			return result;
		}
		
		esbService=(IEsbService)WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(IEsbService.class);
		Map<String,Object> paramMap=getRequestParams(request);
		String softKey=provider.getAuthorKey();	//接口接入方的授权码
		if(!StringUtils.isBlank(softKey))
		{
			paramMap.put("clientKey",softKey);
		}
		provider.setParamMap(paramMap);			//重新封装值
		Record record=esbService.invokeService(provider);			//返回远程请求数据
		writeJsonData(record, response);
		return false;
	}

	/**
	 * 获得参数值并返回参数集合列表
	 * @author 胡礼波
	 * 2013-7-10 上午11:13:00
	 * @return
	 */
	private static Map<String,Object> getRequestParams(HttpServletRequest request)
	{
		String paramValue=null;
		String paramName=null;
		Map<String,Object> paramMap=new  HashMap<String, Object>();
		Enumeration<String> enu= request.getParameterNames();
		while(enu.hasMoreElements())
		{
			paramName=enu.nextElement();
			paramValue=request.getParameter(paramName);
			paramValue=paramValue!=null?paramValue.trim():null;
			paramMap.put(paramName, paramValue);
		}
		return paramMap;
	}
	
	/**
	 * 判断是否是自身的Servlet服务
	 * @author 胡礼波
	 * 2013-7-10 下午5:04:29
	 * @return
	 */
	public static boolean isSelfService(Provider provider)
	{
		String ips[]=provider.getServerIp().split(",");
		for (String ip : ips) {
			if(ip.equals(SystemConfig.SERVERIP))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得提供的服务url路径
	 * @author 胡礼波
	 * 2013-7-9 下午5:24:31
	 * @return
	 */
	public static String getServiceUrl(HttpServletRequest request)
	{
		String subStr="/api";
		String requestUri =request.getRequestURL().toString();
		int index=requestUri.indexOf(subStr);
		if(index==-1)
		{
			return null;
		}
		requestUri=requestUri.substring(index);
		return requestUri;
	}
}
