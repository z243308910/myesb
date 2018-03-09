package com.echounion.bossmanager.entity;

import java.io.Serializable;

/**
 * 接口服务目录
 * @author 胡礼波
 * 2013-7-8 上午11:11:58
 */
public class EsbServiceDir implements Serializable {

	/**
	 * @author 胡礼波
	 * 2013-7-8 上午11:11:55
	 */
	private static final long serialVersionUID = -5889030970657217392L;
	private int id;
	private int softId;		//软件编号
	private String serviceCode;	//服务代号
	private String serviceName;	//服务名称
	private String serviceUrl;	//服务URL
	private String remark;		//备注
	private int status;			//状态----主要是后期可能针对某个服务启用和停用的控制
	private int methodId;		//http请求方式 参见httprequesttype枚举
	
	public int getMethodId() {
		return methodId;
	}
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSoftId() {
		return softId;
	}
	public void setSoftId(int softId) {
		this.softId = softId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
