package com.echounion.bossmanager.entity.dto;

import java.io.Serializable;


/**
 * 短信配置类
 * @author 胡礼波
 * 2012-11-17 下午05:37:53
 */
public class ShortMsgConfig implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-17 下午05:38:10
	 */
	private static final long serialVersionUID = 6506155872608406671L;
	
	private String userName;		//短信网关用户名
	private String password;		//短信网关密码
	private String host;			//短信网关地址
	public static int MAXLENGTH=500;			//最大字符长度--默认为500
	
	public ShortMsgConfig(String userName,String password,String host)
	{
		this.userName=userName;
		this.password=password;
		this.host=host;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	
}
