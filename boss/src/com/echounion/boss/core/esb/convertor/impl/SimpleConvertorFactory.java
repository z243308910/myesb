package com.echounion.boss.core.esb.convertor.impl;

import com.echounion.boss.common.enums.ProtocolType;
import com.echounion.boss.core.esb.convertor.ConvertorFactory;
import com.echounion.boss.entity.dto.Provider;

/**
 * 实现的简单一个工厂转换器
 * @author 胡礼波
 * 2013-7-9 下午6:19:50
 */
public class SimpleConvertorFactory{

	/**
	 * 根据服务提供者创建对应的工厂实例
	 * @author 胡礼波
	 * 2013-7-9 下午6:25:31
	 * @param provider
	 * @return
	 */
	public static ConvertorFactory create(Provider provider)
	{
		ConvertorFactory factory=null;
		if(provider.getProtocolType()==ProtocolType.HTTP)
		{
			factory= new HttpConvertor();
		}
		return factory;
	}

}
