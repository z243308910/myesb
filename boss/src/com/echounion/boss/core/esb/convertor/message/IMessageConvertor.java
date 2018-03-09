package com.echounion.boss.core.esb.convertor.message;

/**
 * 消息转换器接口
 * @author 胡礼波
 * 2013-7-8 下午4:35:38
 */
public interface IMessageConvertor<T> {

	/**
	 * 消息转换动作
	 * @author 胡礼波
	 * 2013-7-10 上午10:03:05
	 * @param t
	 * @return
	 */
	public void convertor(Object message);
	
	/**
	 * 获得转换器
	 * @author 胡礼波
	 * 2013-7-10 上午11:39:16
	 * @return
	 */
	public T getConvertor();
}
