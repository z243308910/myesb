package com.echounion.boss.common.util.reflect;

import java.lang.reflect.Method;

import com.echounion.boss.core.security.annotation.ActionModel;

/**
 * 反射工具类
 * @author 胡礼波
 * 2012-4-25 下午05:34:43
 */
public class ReflectUtils
{
	private ReflectUtils()
	{
	}

	/**
	 * 获得执行的方法
	 * @author 胡礼波
	 * 2012-4-25 下午05:34:54
	 * @param c 类
	 * @param methodName 方法名
	 * @return 方法对象
	 */
	public static Method getMethod(Class<?> c, String methodName)
	{
		Method method = null;
		try
		{
			method = c.getMethod(methodName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return method;
	}

	/**
	 * 获得权限枚举
	 * @author 胡礼波
	 * 2012-4-25 下午05:36:38
	 * @param mth
	 * @return
	 */
//	public static ActionRightCtl getActionAnnotationValue(Method mth)
//	{
//		return mth.isAnnotationPresent(ActionRightCtl.class) == false ? null : mth.getAnnotation(ActionRightCtl.class);
//	}

	/**
	 * 对象复制
	 * @author 胡礼波
	 * 2012-4-25 下午05:36:54
	 * @param dest
	 * @param src
	 */
	public static void copyValueInto(Object dest, Object src)
	{
		try
		{
			if (dest == null || src == null)
			{
				return;
			}
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, src);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * 返回类或者方法的描述信息
	 * @author 胡礼波
	 * 2012-11-1 上午11:18:37
	 * @param obj 可以是class也可以是method
	 * @return
	 */
	public static String getModelDescription(Object obj)
	{
		ActionModel model=null;
		if(obj instanceof Class)
		{
			model=getClassActionModel((Class<?>)obj);
		}
		else if(obj instanceof Method)
		{
			Method method=(Method)obj;
			model=getMethodActionModel(method);
		}
		if(model!=null)
		{
			return model.description();
		}
		return "";
	}
	
	/**
	 * 获取方法或类的描述注解
	 * @author 胡礼波
	 * 2012-11-1 上午11:18:53
	 * @param c
	 * @return
	 */
	private static ActionModel getClassActionModel(Class<?> c)
	{
		ActionModel model=c.isAnnotationPresent(ActionModel.class)==false?null:c.getAnnotation(ActionModel.class);
		return model;
	}
	
	/**
	 * 获取方法或类的描述注解
	 * @author 胡礼波
	 * 2012-11-1 上午11:18:53
	 * @param c
	 * @return
	 */
	private static ActionModel getMethodActionModel(Method method)
	{
		ActionModel model=method.isAnnotationPresent(ActionModel.class)==false?null:method.getAnnotation(ActionModel.class);
		return model;
	}
}
