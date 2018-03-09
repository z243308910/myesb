package com.echounion.boss.entity.dto;

import java.io.Serializable;

/**
 * 该类主要是封装调用方的信息
 * @author 胡礼波
 * 2013-7-8 下午1:45:53
 */
public class Caller implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-5 下午02:16:31
	 */
	private static final long serialVersionUID = -7425113267574163532L;
	
	private int serverId;		//调用方服务器编号
	private int softId;			//调用方软件编号
	private String serverIp;    //调用方服务器IP
	private String requestIp;	//调用方请求IP
	private String mailChannel;	//邮件通道
	private String shortMsgChannel;//短信通道
	private String authorKey;//安全码
	
	public String getRequestIp() {
		return requestIp;
	}
	public String getMailChannel() {
		return mailChannel;
	}
	public String getShortMsgChannel() {
		return shortMsgChannel;
	}
	public void setShortMsgChannel(String shortMsgChannel) {
		this.shortMsgChannel = shortMsgChannel;
	}
	public void setMailChannel(String mailChannel) {
		this.mailChannel = mailChannel;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getSoftId() {
		return softId;
	}
	public void setSoftId(int softId) {
		this.softId = softId;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getAuthorKey() {
		return authorKey;
	}
	public void setAuthorKey(String authorKey) {
		this.authorKey = authorKey;
	}
}
