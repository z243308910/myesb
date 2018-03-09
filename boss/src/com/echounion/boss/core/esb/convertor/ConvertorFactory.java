package com.echounion.boss.core.esb.convertor;

import com.echounion.boss.core.esb.convertor.message.IMessageConvertor;
import com.echounion.boss.core.esb.convertor.protocol.IProtocolConvertor;
import com.echounion.boss.entity.dto.ResponseMessage;

/**
 * 协议转换工厂
 * @author 胡礼波
 * 2013-7-8 下午4:34:43
 */
public interface ConvertorFactory {

	/**
	 * 生产协议转换器
	 * @author 胡礼波
	 * 2013-7-10 上午11:20:13
	 * @param uri 转换器调用地址
	 * @return
	 */
	public IProtocolConvertor<?> createProtocolConvertor(String uri);
	
	/**
	 * 生产消息转换器
	 * @author 胡礼波
	 * 2013-7-10 上午11:20:44
	 * @param message 转换器所需参数
	 * @return
	 */
	public IMessageConvertor<?> createMessageConvertor(Object message);
	
	/**
	 * 生产消息
	 * @author 胡礼波
	 * 2013-7-10 下午2:48:57
	 * @param url
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage invoke(String url,Object message)throws Exception;
	
	/**
	 * 生产消息
	 * @author 胡礼波
	 * 2013-7-10 上午11:28:03
	 * @param protocol
	 * @param messageConvertor
	 * @return
	 */
	public ResponseMessage invoke(IProtocolConvertor<?> protocolConvertor,IMessageConvertor<?> messageConvertor)
			throws Exception;
}
