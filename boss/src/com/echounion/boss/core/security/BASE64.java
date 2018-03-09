package com.echounion.boss.core.security;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * HEX将数据转化为16进制字符串工具类
 * @author 胡礼波
 * 2012-11-24 上午10:55:34
 */
public class BASE64 {

	/**
	 * 加密
	 * @author 胡礼波
	 * 2012-11-24 下午01:46:27
	 * @param data
	 * @return
	 */
	public static String encode(String data)
	{
		return new BASE64Encoder().encodeBuffer(data.getBytes());
	}
	
	/**
	 * 解密
	 * @author 胡礼波
	 * 2012-11-24 下午01:48:29
	 * @param data
	 * @return
	 */
	public static String decode(String data)
	{
		try {
			return new String(new BASE64Decoder().decodeBuffer(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
