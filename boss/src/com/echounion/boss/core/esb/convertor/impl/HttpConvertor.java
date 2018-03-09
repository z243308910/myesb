package com.echounion.boss.core.esb.convertor.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.common.util.NetUtil;
import com.echounion.boss.core.esb.convertor.ConvertorFactory;
import com.echounion.boss.core.esb.convertor.message.IMessageConvertor;
import com.echounion.boss.core.esb.convertor.message.impl.HttpMessageConvertor;
import com.echounion.boss.core.esb.convertor.protocol.IProtocolConvertor;
import com.echounion.boss.core.esb.convertor.protocol.impl.HttpProtocolConvertor;
import com.echounion.boss.entity.dto.ResponseMessage;

/**
 * HTTP协议转换器默认是POST请求
 * @author 胡礼波
 * 2013-7-8 下午4:38:57
 */
public class HttpConvertor implements ConvertorFactory {

	@Override
	public IProtocolConvertor<HttpRequestBase> createProtocolConvertor(String url) {
		IProtocolConvertor<HttpRequestBase> convertor=new HttpProtocolConvertor();
		convertor.convertor(url);
		return convertor;
	}

	/**
	 * 重载HTTP协议转换
	 * @author 胡礼波
	 * 2013-7-25 上午10:13:15
	 * @param url
	 * @param method
	 * @return
	 */
	public IProtocolConvertor<HttpRequestBase> createProtocolConvertor(String url,HttpMethod method) {
		HttpProtocolConvertor convertor=new HttpProtocolConvertor();
		convertor.setHttpMethod(method);				//指定http请求的方法 POST GET等
		convertor.convertor(url);
		return convertor;
	}
	
	@Override
	public IMessageConvertor<HttpEntity> createMessageConvertor(Object message) {
		IMessageConvertor<HttpEntity> convertor=new HttpMessageConvertor();
		convertor.convertor(message);
		return convertor;
	}

	@Override
	public ResponseMessage invoke(String url,Object message)throws Exception
	{
		return invoke(url, message,HttpMethod.POST);
	}
	
	/**
	 * 重载HTTP调用方法
	 * @author 胡礼波
	 * 2013-7-25 上午10:21:09
	 * @param url
	 * @param message
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage invoke(String url,Object message,HttpMethod method)throws Exception
	{
		IProtocolConvertor<HttpRequestBase> protolConvertor=createProtocolConvertor(url,method);
		IMessageConvertor<HttpEntity> messageConvertor=createMessageConvertor(message);
		return invoke(protolConvertor,messageConvertor);
	}	
	
	@Override
	public ResponseMessage invoke(IProtocolConvertor<?> protocolConvertor,IMessageConvertor<?> messageConvertor)throws Exception {
		
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(HTTP.CONTENT_ENCODING,Constant.ENCODEING_UTF8);
		
		HttpProtocolConvertor httpProConv=(HttpProtocolConvertor)protocolConvertor;		//强制转换为HTTP协议转换器
		HttpMessageConvertor httpMsgProConv=(HttpMessageConvertor)messageConvertor;		//强制转换为HTTP消息转换器
		
		HttpRequestBase httpRequest=(HttpRequestBase)httpProConv.getConvertor();
		
		if(httpProConv.getHttpMethod()==HttpMethod.POST)					//HTTPPOST协议
		{
			HttpPost httpPost=(HttpPost)httpRequest;
			httpPost.setEntity((HttpEntity)httpMsgProConv.getConvertor());
			httpRequest=httpPost;
		}
		else if(httpProConv.getHttpMethod()==HttpMethod.GET)				//HTTPGET协议
		{
			String url=httpRequest.getURI().toString();
			HttpGet httpGet=new HttpGet(NetUtil.getGetURL(url,httpMsgProConv.getParamMap()));
			httpRequest=httpGet;
		}
		httpRequest.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10*1500);		//超时时间15s
		HttpResponse response = client.execute(httpRequest);
		
		ResponseMessage responseMsg=new ResponseMessage();
		HttpEntity entity = response.getEntity();
		String message=EntityUtils.toString(entity,Constant.ENCODEING_UTF8);
		int code=response.getStatusLine().getStatusCode();
		if(code==200)			//请求成功
		{
			responseMsg.setStatus(Status.SUCCESS);
		}
		else
		{
			responseMsg.setStatus(Status.FAIL);
		}
		responseMsg.setCode("HTTP-"+code);
		responseMsg.setMessage(message);
		return responseMsg;
	}

}
