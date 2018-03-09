package com.echounion.boss.common.util;

/**
 * 线程变量工具类
 * @author 胡礼波
 * 2013-02-28 下午16:53:37
 */
public class ThreadLocalUtil {

	private static ThreadLocal<Object> container=new ThreadLocal<Object>();
	
	/**
	 * 放入数据到threadlocal中
	 * @author 胡礼波
	 * 2013-2-28 下午4:56:47
	 * @param object
	 */
	public static void setData(Object object)
	{
		container.set(object);
	}
	
	/**
	 * 从threadlocal中获取数据
	 * @author 胡礼波
	 * 2013-2-28 下午4:58:09
	 */
	public static Object getData()
	{
		return container.get();
	}
	
	/**
	 * 移除threadlocal中的数据
	 * @author 胡礼波
	 * 2013-2-28 下午4:59:08
	 */
	public static void remove()
	{
		container.remove();
	}
}
