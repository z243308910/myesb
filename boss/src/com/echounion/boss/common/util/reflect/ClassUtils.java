package com.echounion.boss.common.util.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类反射工具类
 * @author 胡礼波
 * 2012-4-25 下午05:34:12
 */
public class ClassUtils
{
	private ClassUtils()
	{
	}


	public static Class<?> getSuperClassGenricType(Class<?> clazz)
	{
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index)
	{

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType))
		{
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0)
		{
			return Object.class;
		}
		if (!(params[index] instanceof Class))
		{
			return Object.class;
		}
		return (Class<?>) params[index];
	}
}
