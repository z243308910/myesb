package com.echounion.boss.common.enums;

import org.springframework.http.HttpMethod;

/**
 * Http请求类型
 * @author 胡礼波
 * 2013-7-25 上午10:25:40
 */
public enum HttpReqeuestType {

	POST(HttpMethod.POST,1),
	GET(HttpMethod.GET,2),
	DELETE(HttpMethod.DELETE,3);
	private HttpMethod method;
	private int id;
	
	
	/**
	 * 返回HTTP请求类型 默认是POST类型
	 * @author 胡礼波
	 * 2013-7-25 上午10:30:43
	 * @param id
	 * @return
	 */
	public static HttpReqeuestType getMethod(int id)
	{
		for (HttpReqeuestType type:HttpReqeuestType.values()) {
			if(id==type.getId())
			{
				return type;
			}
		}
		return POST;
	}
	
	private HttpReqeuestType(HttpMethod method,int id)
	{
		this.id=id;
		this.method=method;
	}
	
	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}