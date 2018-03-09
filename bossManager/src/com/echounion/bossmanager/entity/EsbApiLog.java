package com.echounion.bossmanager.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.service.softserver.IServiceDirService;
import com.echounion.bossmanager.service.softserver.ISoftwareService;

/**
 * ESB接口调用日志类
 * 
 * @author 胡礼波 2013-7-8 上午10:56:42
 */
public class EsbApiLog implements Serializable {

	/**
	 * @author 胡礼波 2013-7-8 上午10:56:57
	 */
	private static final long serialVersionUID = 6679923889133633387L;

	private int id;
	private int softId; // 软件ID
	private int serviceId; // 服务接口ID
	private String invokeIp; // 调用方的IP
	private Date invokeTime; // 调用时间
	private String resCode; // 接口响应状态码
	private String resMsg; // 接口响应返回值

	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
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
	public String getInvokeIp() {
		return invokeIp;
	}
	public void setInvokeIp(String invokeIp) {
		this.invokeIp = invokeIp;
	}
	public Date getInvokeTime() {
		return invokeTime;
	}
	public void setInvokeTime(Date invokeTime) {
		this.invokeTime = invokeTime;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getSoftName() {
		try {
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ContextUtil
					.getServletContext());
			ISoftwareService service = context.getBean(ISoftwareService.class);
			EsbSoftWare softWare = service.getSoftwareById(getSoftId());
			return softWare.getSoftName();
		} catch (Exception e) {

		}
		return "";
	}

	public String getServiceCode() {
		try {
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ContextUtil
					.getServletContext());
			IServiceDirService service = context.getBean(IServiceDirService.class);
			EsbServiceDir serviceDir = service.getDirById(getServiceId());
			
			return new StringBuffer(serviceDir.getServiceCode()).append("(").append(serviceDir.getServiceUrl()).append(")").toString();
		} catch (Exception e) {

		}
		return "";
	}
}
