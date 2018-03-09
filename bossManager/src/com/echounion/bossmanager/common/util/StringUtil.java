package com.echounion.bossmanager.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.echounion.bossmanager.common.constant.Constant;
/**
 * 字符串工具类
 * @author 胡礼波
 * 10:41:25 AM Oct 17, 2012
 */
public class StringUtil {

	/**
	 * 产生随机的长度为length的数字字符串
	 * @author 胡礼波
	 * 2012-6-6 下午02:49:42
	 * @param length
	 * @return
	 */
	public static String getRandom(int length)
	{
		
		int num=0;
		StringBuffer sb=new StringBuffer();
		do
		{
			num=RandomUtils.nextInt(10);
			sb.append(num);
		}
		while(sb.length()<length);
		return sb.toString();
	}
	
	/**
	 * 把字符串转为UTF-8类型的字符串
	 * @author 胡礼波
	 * 2012-5-18 下午03:04:29
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String chartDecodeToUTF8(String data) throws UnsupportedEncodingException
	{
		if(StringUtils.isEmpty(data))
		{
			return null;
		}
		return new String(data.getBytes(),Constant.ENCODEING_UTF8);
	}
	
	/**
	 * 把数组转变为逗号分开的字符串
	 * @author 胡礼波
	 * 2012-11-20 上午11:35:49
	 * @param objs
	 * @return
	 */
	public static String convertArray(Object[] objs)
	{
		if(ArrayUtils.isEmpty(objs))
		{
			return null;
		}
		StringBuilder sb=new StringBuilder();
		for (Object object : objs) {
			sb.append(object);
		}
		return sb.toString();
	}
	
	/**
	 * 随机产生数字和字母指定长度的字符串
	 * @param length
	 * @return
	 */
	public static String getCharAndNumr(int length)     
	{     
		Random r=new Random();
		String code="";
		
		for(int i=0;i<length;++i)
		{
			if(i%2==0) //偶数位生产随机整数
			{
				code=code+r.nextInt(10);
			}
			else	//奇数产生随机字母包括大小写
			{
				int temp=r.nextInt(52);
				char x=(char)(temp<26?temp+97:(temp%26)+65);
				code+=x;
			}
		}
	             
	    return code;     
	}
	
	/**
	 * 字符串分割
	 * @author 胡礼波
	 * 2013-7-16 上午10:32:07
	 * @param data 要分割的数据
	 * @param splitChart 分割符
	 * @return
	 */
	public static String[] splitData(String data,String splitChart)
	{
		if(StringUtils.isBlank(data))
		{
			return null;
		}
		return data.split(splitChart);
	}
}
