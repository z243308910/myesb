package com.echounion.boss.core.esb.convertor.protocol.impl;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.http.HttpMethod;

import com.echounion.boss.core.esb.convertor.protocol.IProtocolConvertor;

/**
 * HTTP协议转换器
 * @author 胡礼波
 * 2013-7-8 下午4:40:48
 */
public class HttpProtocolConvertor implements IProtocolConvertor<HttpRequestBase> {

	private HttpRequestBase httpRequest=null;
	
	private HttpMethod method=HttpMethod.POST;			//默认为POST请求
	
	public void setHttpMethod(HttpMethod method)
	{
		this.method=method;
	}
	
	public HttpMethod getHttpMethod()
	{
		return this.method;
	}
	
	@Override
	public void convertor(String url) {
		if(this.method==HttpMethod.POST)
		{
			this.httpRequest=new HttpPost(url);
		}
		else if(this.method==HttpMethod.GET)
		{
			this.httpRequest=new HttpGet(url);
		}
	}

	@Override
	public HttpRequestBase getConvertor() {
		return httpRequest;
	}


}
