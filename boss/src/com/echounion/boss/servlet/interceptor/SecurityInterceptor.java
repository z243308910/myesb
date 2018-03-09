package com.echounion.boss.servlet.interceptor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.SystemStatus;
import com.echounion.boss.common.util.NetUtil;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.core.cache.CallerContainer;
import com.echounion.boss.core.security.SecurityEncrypt;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.Record;

/**
 * 安全校验的拦截器
 * @author 胡礼波
 * 2012-11-1 下午07:13:52
 */
public class SecurityInterceptor extends AbstractInterceptor {

	private Logger logger=Logger.getLogger(SecurityInterceptor.class);
	
	public SecurityInterceptor()
	{
		logger.info("......加载系统安全校验器成功!......");
	}
	
	public boolean intercept(HttpServletRequest request, HttpServletResponse response,Interceptor chain) throws IOException, ServletException {
		String authorKey=getAuthorKey(request);
		SystemStatus status=validateAuthorKey(authorKey);
		if(status==SystemStatus.SUCCESS)						  //验证成功 则验证请求的合法性
		{
			Caller caller=getCaller(request,authorKey);
			ThreadLocalUtil.setData(caller);					//把当前请求对象放到请求线程中
		}
		if(status!=SystemStatus.SUCCESS)							//全部验证失败响应客户端
		{
			writeJsonData(Record.setFailRecord(status.getCode(),status.getName(),RecordType.TEXT),response);
			return false;
		}
		boolean result=chain.intercept(request, response, chain);
		return result;
	}
	
	
	
	/**
	 * 验证授权码
	 * @author 胡礼波
	 * 2013-7-8 下午6:02:02
	 * @param authorKey
	 * @return 验证失败返回Record对象 验证成功返回null
	 */
	private SystemStatus validateAuthorKey(String authorKey)
	{
		if(StringUtils.isBlank(authorKey))
		{
			return SystemStatus.AUTHORKEY_NULL;
		}
		if(!CallerContainer.isExistUrl(authorKey))
		{
			return SystemStatus.Authorkey_ERROR;
		}
		return SystemStatus.SUCCESS;
	}
	
	/**
	 * 获取授权码
	 * @author 胡礼波
	 * 2013-7-8 下午5:18:40
	 * @param request
	 * @return true表示传过来 false反之
	 */
	protected String getAuthorKey(HttpServletRequest request)
	{
		String authorKey=request.getParameter("authorKey");			//获得授权码
		return authorKey;
	}
	
	/**
	 * 解密授权码
	 * @author 胡礼波
	 * 2013-7-9 下午3:58:45
	 * @return
	 */
	public String decrypAuthorKey(String authorKey)
	{
		try
		{
			if(StringUtils.isBlank(authorKey))
			{
				return null;
			}
			authorKey=new String(Hex.decodeHex(authorKey.toCharArray()));
			authorKey=SecurityEncrypt.getInstance().getDesString(authorKey);
		}catch (Exception e) {
			logger.error("授权码解密出错!"+e);
			return null;
		}
		return authorKey;
	}
	
	/**
	 * 得到调用方的基本信息对象
	 * @author 胡礼波
	 * 2013-7-25 上午11:29:36
	 * @param request
	 * @param authorKey
	 * @return
	 */
	protected static Caller getCaller(HttpServletRequest request,String authorKey)
	{
		Caller caller=CallerContainer.getCaller(authorKey);
		try
		{
			caller.setRequestIp(NetUtil.getClientIp(request));		//客户端IP
		}catch (Exception e) {
			caller=null;
		}
		return caller;
	}
}
