package com.echounion.boss.entity.dto;

import java.io.Serializable;

import com.echounion.boss.common.enums.Status;

/**
 * 服务响应消息DTO
 * @author 胡礼波
 * 2013-7-10 上午11:24:49
 */
public class ResponseMessage implements Serializable{

	/**
	 * @author 胡礼波
	 * 2013-7-10 上午11:25:14
	 */
	private static final long serialVersionUID = -543291242279764248L;
	
	public static final int SUCCESS=1;		//成功状态
	public static final int FAIL=-1;		//失败状态
	
	private Status status;			//协议调用响应状态 成功1 失败 -1
	private String code;			//协议响应状态 比如HTTP:200，404，500等
	private String message;			//响应消息
	
	public ResponseMessage()
	{
		
	}
	public ResponseMessage(Status status,String code,String message)
	{
		this.status=status;
		this.code=code;
		this.message=message;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
