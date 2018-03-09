package com.echounion.boss.common.util;

import org.apache.commons.beanutils.ConvertUtils;


/**
 * 数据转化工具类
 * @author 胡礼波
 * 2012-10-31 下午03:44:19
 */
public class DataConvertUtils {

	/**
	 * 字符串转为boolean类型
	 * @author 胡礼波
	 * 2013-11-5 下午12:46:07
	 * @param data
	 * @return
	 */
	public static boolean convertBooleanByString(String data)
	{
		Boolean bool=(Boolean) ConvertUtils.convert(data,Boolean.class);
		return bool;
	}
	
	/**
	 * 字符串数组转化为数字数组
	 * @author 胡礼波
	 * 2012-10-31 下午03:44:59
	 * @return
	 */
	public static Integer[] convertIntegerByString(String[] datas)
	{
		Integer data[]=(Integer[])ConvertUtils.convert(datas,Integer.class);
		return data;
	}
	
	/**
	 * 转换为字符串
	 * @author 胡礼波
	 * 2013-7-11 下午2:47:39
	 * @param data
	 * @return
	 */
	public static Integer convertInteger(Object data)
	{
		Integer integer=(Integer)ConvertUtils.convert(data,Integer.class);
		return integer;
	}
	
	
	/**
	 * 转换为字符串
	 * @author 胡礼波
	 * 2013-7-11 下午2:48:08
	 * @param data
	 * @return
	 */
	public static String convertString(Object data)
	{
		String str=String.valueOf(data);
		return str;
	}
	
}
