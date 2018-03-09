package com.echounion.boss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 注册邮件实体类
 * @author 胡礼波
 * 2012-11-22 下午12:15:43
 */
public class MailList implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-22 下午12:16:00
	 */
	private static final long serialVersionUID = 8669203792927863336L;
	
	private int id;
	private String email;		//邮箱地址
	private Date confirmDate;	//注册时间
	
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
