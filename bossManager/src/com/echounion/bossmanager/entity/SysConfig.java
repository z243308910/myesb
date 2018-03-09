package com.echounion.bossmanager.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统配置类
 * 
 * @author 胡礼波 2012-10-31 下午02:50:40
 */
public class SysConfig implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-10-31 下午05:15:03
	 */
	private static final long serialVersionUID = 736148580602058007L;

	private int id;

	private String configCode; // 配置代码用于区别是那种功能的配置
	private int type;			//配置类别 1采集器配置，2短信配置，3邮件配置 数据来源于系统常量

	private String configName; // 配置名称

	private String configDesc; // 配置描述

	private String ip; // IP地址

	private int port; // 端口号

	private String url; // URL地址

	private String userName; // 用户名

	private String password; // 密码

	private String creator; // 创建者

	private Date createDate; // 创建时间
	
	public String balance;	//短信余额---该属性不映射到数据库
	
	public String channel;  //邮件通道

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
