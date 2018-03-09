package com.echounion.bossmanager.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理台系统日志类
 * @author 胡礼波 2012-11-1 上午10:22:04
 */
public class SysLogCons implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-1 上午10:25:12
	 */
	private static final long serialVersionUID = 130405334747521303L;

	private int id;

	public SysLogCons(){}
	
	public SysLogCons(String modelName,String methodName,String remark)
	{
		this.modelName=modelName;
		this.methodName=methodName;
		this.remark=remark;
	}
	
	private Date opDateTime; // 操作时间

	private String modelName; // 模块名称

	private String methodName; // 方法名称

	private String remark; // 备注

	private String userName;// 操作员名称
	private String ip;		//操作IP

	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Date getOpDateTime() {
		return opDateTime;
	}

	public void setOpDateTime(Date opDateTime) {
		this.opDateTime = opDateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
