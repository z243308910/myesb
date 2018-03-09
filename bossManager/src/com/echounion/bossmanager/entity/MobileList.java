package com.echounion.bossmanager.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 注册手机实体类
 * @author 胡礼波
 * 2012-12-13 上午11:10:20
 */
public class MobileList implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-12-13 上午11:10:37
	 */
	private static final long serialVersionUID = 2392397228169977280L;
	
	private int id;
	private String mobileNumber;		//手机
	private Date confirmTime;	//确认时间
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
