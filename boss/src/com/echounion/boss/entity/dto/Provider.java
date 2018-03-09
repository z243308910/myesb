package com.echounion.boss.entity.dto;

import java.io.Serializable;
import java.util.Map;
import org.springframework.http.HttpMethod;
import com.echounion.boss.common.enums.HttpReqeuestType;
import com.echounion.boss.common.enums.ProtocolType;

/**
 * 服务提供方实体DTO
 * @author 胡礼波
 * 2013-7-9 下午5:10:28
 */
public class Provider implements Serializable{

	/**
	 * @author 胡礼波
	 * 2013-7-9 下午5:11:04
	 */
	private static final long serialVersionUID = 4260369203235479528L;

	private String serviceCode;			//服务提供方URL 代号
	private int serverId;				//服务提供方服务器编号
	private String serverIp;    		//服务提供方服务器IP	
	private int softId;					//服务提供方软件编号
	private ProtocolType protocolType;	//服务提供方协议类型
	private HttpMethod method;			//请求方式 比如http有POST、get、delete请求等
	private int serviceId;				//服务提供方服务接口编号
	private String url;					//服务提供方完整url地址
	private String serviceUrl;			//每个服务的URL地址
	private Map<String,Object> paramMap;//接收到的参数
	private int protocolTypeId;	//服务提供方协议类型ID
	private int methodId;				//请求类型Id
	private String authorKey;			//接口接入方授权码--可有可无 主要是每个接入方自己有权限校验机制
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getAuthorKey() {
		return authorKey;
	}

	public void setAuthorKey(String authorKey) {
		this.authorKey = authorKey;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public int getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public HttpMethod getMethod() {
		this.method=HttpReqeuestType.getMethod(this.methodId).getMethod();
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	
	public ProtocolType getProtocolType() {
		this.protocolType=ProtocolType.getProcolType(protocolTypeId);
		return protocolType;
	}
	public void setProtocolType(ProtocolType protocolType) {
		this.protocolType = protocolType;
	}
	
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getSoftId() {
		return softId;
	}
	public void setSoftId(int softId) {
		this.softId = softId;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
//	public List<ParamsDTO> getListParam() {
//		return listParam;
//	}
//	public void setListParam(List<ParamsDTO> listParam) {
//		this.listParam = listParam;
//	}
	
}
