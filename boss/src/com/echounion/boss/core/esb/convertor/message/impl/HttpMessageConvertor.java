package com.echounion.boss.core.esb.convertor.message.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.core.esb.convertor.message.IMessageConvertor;
import com.echounion.boss.entity.dto.ParamsDTO;

/**
 * HTTP消息转换器
 * @author 胡礼波
 * 2013-7-8 下午4:41:34
 */
public class HttpMessageConvertor implements IMessageConvertor<HttpEntity> {

	private Logger logger=Logger.getLogger(HttpMessageConvertor.class);
	
	private HttpEntity messageConvertor;
	private Map<String,Object> paramMap=null;			//保存当前的参数
	
	
	/**
	 * HTTP消息转换 可以接收List<ParamsDTO> 和map类型的数据
	 * @author 胡礼波
	 * 2013-7-24 下午3:56:31
	 * @param object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void convertor(Object object) {
		if(object instanceof List)
		{
			paramMap=new HashMap<String, Object>();
			List<ParamsDTO> paramList=(List<ParamsDTO>)object;
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			for (ParamsDTO param:paramList) {
				list.add(new BasicNameValuePair(param.getCode(),String.valueOf(param.getValue())));
				paramMap.put(param.getCode(),param.getValue());
			}
			try {
				messageConvertor = new UrlEncodedFormEntity(list,Constant.ENCODEING_UTF8);
			} catch (Exception e) {
				logger.error("消息转换异常",e);
			}
		}else if(object instanceof Map)
		{
			paramMap=new HashMap<String, Object>();
			Map<String,Object> paramList=(HashMap<String, Object>)object;
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			try {
				for (String code:paramList.keySet()) {
					list.add(new BasicNameValuePair(code,String.valueOf(paramList.get(code))));
					paramMap.put(code,paramList.get(code));
				}
				messageConvertor = new UrlEncodedFormEntity(list,Constant.ENCODEING_UTF8);
			} catch (Exception e) {
				logger.error("消息转换异常",e);
			}
		}
	}

	@Override
	public HttpEntity getConvertor() {
		return messageConvertor;
	}
	
	
	/**
	 * 获得请求的参数
	 * @author 胡礼波
	 * 2013-7-24 下午2:48:58
	 * @return
	 */
	public Map<String,Object> getParamMap()
	{
		return paramMap;
	}

}
