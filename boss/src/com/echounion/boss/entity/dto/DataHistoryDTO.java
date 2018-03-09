package com.echounion.boss.entity.dto;

import java.io.Serializable;

/**
 * 数据历史记录DTO
 * @author 胡礼波
 * 2013-3-25 下午1:52:05
 */
public class DataHistoryDTO implements Serializable {

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @author 胡礼波
	 * 2013-3-25 下午1:53:52
	 */
	private static final long serialVersionUID = 4774056875483611335L;
	
	private int type;				//历史记录类型 1手机 2 邮件 3RTX
	private String receiver;		//接收方
	private String content;			//内容
	private String status;			//状态
	private String sendTime;		//发送时间
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
