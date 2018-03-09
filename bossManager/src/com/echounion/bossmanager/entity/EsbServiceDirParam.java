package com.echounion.bossmanager.entity;

import java.io.Serializable;

import com.echounion.bossmanager.common.enums.ParamType;

/**
 * 接口服务参数
 * @author 胡礼波
 * 2013-7-17 下午3:58:48
 */
public class EsbServiceDirParam implements Serializable {

	/**
	 * @author 胡礼波
	 * 2013-2-20 下午01:52:06
	 */
	private static final long serialVersionUID = -252899955538692330L;
	
	private int id;
	private int serviceDirId;	//接口服务ID
	private String name;		//参数名称
	private String code;		//参数代码
	private boolean require;	//是否必填
	private String remark;		//模块备注
	private int typeId;			//参数类型ID
	
	public String getTypeName()
	{
		ParamType type=ParamType.getParamType(getTypeId());
		return type==null? "" : type.getName();
	}
	
	public int getServiceDirId() {
		return serviceDirId;
	}


	public void setServiceDirId(int serviceDirId) {
		this.serviceDirId = serviceDirId;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isRequire() {
		return require;
	}
	public void setRequire(boolean require) {
		this.require = require;
	}
	
}
