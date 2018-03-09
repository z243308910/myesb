package com.echounion.bossmanager.common.security;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import com.echounion.bossmanager.action.BaseAction;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.reflect.ReflectUtils;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 安全验证类
 * @author 胡礼波
 * 10:32:48 AM Oct 17, 2012
 */
public class SecurityCheck extends MethodFilterInterceptor{

	/**
	 * @author 胡礼波
	 * 2012-4-26 下午01:45:41
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SecurityCheck.class);
	
	/**
	 * 安全验证
	 * @author 胡礼波
	 * 10:32:37 AM Oct 17, 2012 
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		Method method=ReflectUtils.getMethod(invocation.getAction().getClass(),invocation.getProxy().getMethod());
		if(method==null)
		{
			log.warn("找不到调用的方法!"+method.getName());
			return BaseAction.NONE;
		}
		if(needLogin(method))
		{
			if (!hasLogined())
			{
				log.warn("您未登录!");
				return BaseAction.REDIRECTLOGN;
			}
		}		
		return invocation.invoke();
	}	
	
	/**
	 * 判断是否登录
	 * @author 胡礼波
	 * 10:18:13 AM Oct 17, 2012 
	 * @return
	 */
	private boolean hasLogined()
	{
		Object obj = ActionContext.getContext().getSession().get("adminuser");
		return obj == null ? false : true;
	}

	/**
	 * 判断是否有权限访问该资源
	 * @author 胡礼波
	 * 10:18:28 AM Oct 17, 2012 
	 * @param accessPath
	 * @return
	 */
	protected boolean hasResource(String accessPath)
	{
		return false;
	}
	
	/**
	 * 判断资源的是否有操作权限
	 * @author 胡礼波
	 * 10:18:34 AM Oct 17, 2012 
	 * @param mth
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean hasRight(Method mth)
	{
			return false;
	}	
	
	/**
	 * 判断资源是否需要登录
	 * @author 胡礼波
	 * 10:18:53 AM Oct 17, 2012 
	 * @param mth
	 * @return
	 */
	private boolean needLogin(Method mth)
	{
			ActionRightCtl arc = ReflectUtils.getActionAnnotationValue(mth);
			if (arc == null)			//默认需要登录
			{
				return true;
			}
			LoginFlag rightName = arc.login();
			return rightName == LoginFlag.YES ? true : false;
	}
	
	
	/**
	 *  获得登录的用户对象
	 * @author 胡礼波
	 * 10:19:34 AM Oct 17, 2012 
	 * @return
	 */
	protected Object getLoginedUser()
	{
		return null;
	}
}
