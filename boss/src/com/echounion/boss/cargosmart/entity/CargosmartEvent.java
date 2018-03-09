package com.echounion.boss.cargosmart.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * CargoSmart事件表
 * @author 胡礼波
 * 2013-10-29 上午11:51:38
 */
public class CargosmartEvent implements Serializable{

	/**
	 * @author 胡礼波
	 * 2013-10-29 上午11:51:56
	 */
	private static final long serialVersionUID = 864672698527618839L;
	
	private int id;
	private int baseId;				//基础信息ID
	private String eventCode;		//事件代码
	private String eventDesc;		//事件描述
	private String eventLocal;		//事件发生地点
	private Date eventTime;			//事件发生时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBaseId() {
		return baseId;
	}
	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public String getEventLocal() {
		return eventLocal;
	}
	public void setEventLocal(String eventLocal) {
		this.eventLocal = eventLocal;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

}
