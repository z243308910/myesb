package com.echounion.boss.entity.dto;

import java.io.Serializable;

/**
 * 采集器DTO
 * @author 胡礼波 2012-11-3 下午05:22:00
 */
public class Tracktor implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-3 下午05:23:16
	 */
	private static final long serialVersionUID = -6227272871291482411L;

	private String code; // 采集器代号

	private String name; // 采集器名称

	private String remark; // 采集器描述

	private String url; // 采集器地址
	
	public Tracktor(){}
	
	public Tracktor(String code,String name,String remark,String url)
	{
		this.code=code;
		this.name=name;
		this.remark=remark;
		this.url=url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
