package com.echounion.boss.servlet.interceptor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.SystemStatus;
import com.echounion.boss.common.util.NetUtil;
import com.echounion.boss.core.cache.IpContainer;
import com.echounion.boss.entity.dto.Record;

/**
 * IP请求过滤器
 * @author 胡礼波
 * 2012-11-5 下午06:20:37
 */
public class IpRouterInterceptor extends AbstractInterceptor {

	public boolean intercept(HttpServletRequest request, HttpServletResponse response,Interceptor chain) throws IOException, ServletException {
		SystemStatus status=validateIp(request);
		if(status==SystemStatus.SUCCESS)						  //验证成功 则验证请求的合法性
		{
			return chain.intercept(request, response, chain);
		}
		writeJsonData(Record.setFailRecord(status.getCode(),status.getName(),RecordType.TEXT),response);
		return false;
	}

	/**
	 * 验证请求者IP
	 * @author 胡礼波
	 * 2013-7-25 下午3:55:55
	 * @param request
	 * @return
	 */
	private SystemStatus validateIp(HttpServletRequest request)
	{
		boolean flag=IpContainer.isExistIp(NetUtil.getClientIp(request));
		return flag? SystemStatus.SUCCESS:SystemStatus.UNAUTHOR_IP_INVOKE;
	}
}
