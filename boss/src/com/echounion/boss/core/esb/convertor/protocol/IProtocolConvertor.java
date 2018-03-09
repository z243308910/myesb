package com.echounion.boss.core.esb.convertor.protocol;

/**
 * 协议转换器
 * @author 胡礼波
 * 2013-7-8 下午4:36:05
 */
public interface IProtocolConvertor<T> {

	/**
	 * 协议转换返回不同的协议对象
	 * @author 胡礼波
	 * 2013-7-10 上午11:30:27
	 * @param url 协议地址
	 */
	public void convertor(String url);
	
	/**
	 * 得到转换器
	 * @author 胡礼波
	 * 2013-7-10 上午11:41:43
	 * @return
	 */
	public T getConvertor();
}
