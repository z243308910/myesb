package com.echounion.bossmanager.common.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;


/**
 * 数据转化工具类
 * @author 胡礼波
 * 2012-10-31 下午03:44:19
 */
public class DataConvertUtils {

	/**
	 * 字符串数组转化为数字数组
	 * @author 胡礼波
	 * 2012-10-31 下午03:44:59
	 * @return
	 */
	public static Integer[] convertIntegerByString(String[] datas)
	{
		Integer[] data=null;
		if(ArrayUtils.isEmpty(datas))
		{
			return null;
		}
		data=(Integer[])ConvertUtils.convert(datas,Integer.class);
		return data;
	}
	
	/**
	 * 字符串转换为Integer型
	 * @author 胡礼波
	 * 2013-7-16 下午3:11:09
	 * @param data
	 * @return
	 */
	public static Integer convertInteger(String data)
	{
		Integer result=(Integer)ConvertUtils.convert(data,Integer.class);
		return result;
	}
}
