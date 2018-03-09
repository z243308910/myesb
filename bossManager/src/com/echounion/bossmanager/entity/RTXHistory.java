package com.echounion.bossmanager.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * RTXDTO类
 * @author 胡礼波
 * 2013-3-13 下午4:54:34
 */
public class RTXHistory implements Serializable{

	private static final long serialVersionUID = -7199331959651152849L;
	
	private int id;
	private int softId;				//软件ID
	private String receiver;		//接收方
	private String title;			//消息标题
	private String content;			//消息内容
	private String phone;				//手机号
	private Date sendTime;				//发送时间
	private int errorsNum=0;			//错误次数
	private Date lastTime;				//最后更新时间
	private int status=0;				//发送状态
	private String btype;					//业务类型ID
	private String bcode;				//业务代号

	public static final int SEND_SUCCESS=1;		//成功
	public static final int SEND_WAITING=0;		//待发送
	public static final int SEND_FAIL=2;		//失败
	
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public int getSoftId() {
		return softId;
	}
	public void setSoftId(int softId) {
		this.softId = softId;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public int getErrorsNum() {
		return errorsNum;
	}
	public void setErrorsNum(int errorsNum) {
		this.errorsNum = errorsNum;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
