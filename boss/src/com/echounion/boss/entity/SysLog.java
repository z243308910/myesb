package com.echounion.boss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志类
 * @author 胡礼波 2012-11-1 下午03:43:17
 */
public class SysLog implements Serializable {

	/**
	 * @author 胡礼波 2012-11-1 下午03:45:28
	 */
	private static final long serialVersionUID = -6561150590079977550L;

	private int id;

	private Date opDateTime; // 操作时间

	private String modelName; // 模块名称

	private String methodName; // 方法名称

	private String remark; // 备注

	public SysLog() {
	}

	public SysLog(String modelName, String methodName, String remark) {
		this.modelName = modelName;
		this.methodName = methodName;
		this.remark = remark;
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
