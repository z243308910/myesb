package com.echounion.boss.entity.dto;

import java.io.Serializable;

import com.echounion.boss.common.enums.ParamType;

/**
 * 参数实体DTO
 * @author 胡礼波
 * 2013-7-9 下午4:52:55
 */
public class ParamsDTO implements Serializable {

	/**
	 * @author 胡礼波
	 * 2013-2-20 下午01:52:06
	 */
	private static final long serialVersionUID = -252899955538692330L;
	
	private String name;		//参数名称
	private Object value;		//参数值
	private String code;		//参数代码
	private boolean require;	//是否必填
	private String remark;		//模块备注
	private int typeId;			//参数类型ID

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public String getTypeName()
	{
		ParamType type=ParamType.getParamType(getTypeId());
		return type==null? "" : type.getName();
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
