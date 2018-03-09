package com.echounion.boss.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.htmlparser.util.ParserUtils;


/**
 * 字符串工具类
 * @author 胡礼波
 * 2012-5-18 下午03:02:13
 */
public class StringUtil {

	public static final String CHARTSET_UTF8="UTF-8";
	public static final String CHARTSET_GBK="GBK";
	public static final String CHARTSET_ISO="ISO-8859-1";
	
	/**
	 * 根据请求类型对相应的数据进行转码
	 * @author 胡礼波
	 * 2013-7-25 下午6:36:14
	 * @param method
	 * @param data
	 * @return
	 */
	public static String chartDecodeToUTF8(String method,String data)
	{
		switch(method)
		{
			case "GET":
				return chartDecodeToUTF8(data);
				default:
			return data;
		}
	}
	/**
	 * 把字符串转为UTF-8类型的字符串
	 * @author 胡礼波
	 * 2012-5-18 下午03:04:29
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String chartDecodeToUTF8(String data)
	{
		if(StringUtils.isEmpty(data))
		{
			return null;
		}
		try {
			return new String(data.getBytes(CHARTSET_ISO),CHARTSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把字符串转为GBK类型的字符串
	 * @author 胡礼波
	 * 2013-3-17 下午2:26:21
	 * @param data
	 * @return
	 */
	public static String chartDecodeToGBK(String data)
	{
		if(StringUtils.isEmpty(data))
		{
			return null;
		}
		try {
			return new String(data.getBytes(),CHARTSET_GBK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把字符串转为GBK类型的字符串
	 * @author 胡礼波
	 * 2013-3-17 下午2:26:21
	 * @param data
	 * @return
	 */
	public static String chartEncodeToGBK(String data)
	{
		if(StringUtils.isEmpty(data))
		{
			return null;
		}
		try {
			return URLEncoder.encode(data,CHARTSET_GBK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把字符串转为UTF-8类型的字符串
	 * @author 胡礼波
	 * 2013-3-17 下午2:26:21
	 * @param data
	 * @return
	 */
	public static String chartEncodeToUTF8(String data)
	{
		if(StringUtils.isEmpty(data))
		{
			return null;
		}
		try {
			return URLEncoder.encode(data,CHARTSET_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 格式化时间为字符串 yyyy-MM-dd hh:mm:ss
	 * @author 胡礼波
	 * 2012-5-22 下午04:00:42
	 * @return
	 */
	public static String formatterDateTime(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sf.format(date);
	}
	
	/**
	 * 格式化时间为字符串yyyy-MM-dd
	 * @author 胡礼波
	 * 2012-5-22 下午04:00:42
	 * @return
	 */
	public static String formatterDate(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(date);
	}
	
	/**
	 * 格式化时间为字符串
	 * @author 胡礼波
	 * 2012-5-22 下午04:00:42
	 * @return
	 */
	public static Date formatterStrDate(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(sf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 格式化时间为字符串
	 * @author 胡礼波
	 * 2012-5-22 下午04:00:42
	 * @return
	 */
	public static Date formatterStrDateTime(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return sf.parse(sf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获得当前时间的字符串比如:20080216
	 * @author 胡礼波
	 * 2012-5-9 下午02:06:26
	 * @return
	 */
	public static String getCurrentDate()
	{
		int year=Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH)+1;
		int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String str=year+""+(month>=10?"":"0"+month)+(day>=10?"":"0")+day;
		return str;
	}
	
	/**
	 * 产生随机的最大数为maxNum的数字
	 * @author 胡礼波
	 * 2012-6-6 下午02:49:42
	 * @param maxNum
	 * @return
	 */
	public static String getRandom(int maxNum)
	{
		int num=0;
		do
		{
			num=RandomUtils.nextInt(maxNum);
		}
		while(num>maxNum);
		return String.valueOf(num);
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
	 * 去掉所有的html标签
	 * @author 胡礼波
	 * 2013-3-10 下午4:56:18
	 * @param html
	 * @return
	 */
	public static String trimHtmlTag(String html)
	{
		return ParserUtils.trimAllTags(html,false).replaceAll("\r","").replaceAll("\n","").replaceAll("\t", "");
	}
}
