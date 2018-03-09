package com.echounion.bossmanager.entity.dto;

import java.io.Serializable;


/**
 * 邮件发送者配置类
 * @author 胡礼波
 * 2012-11-15 下午04:27:09
 */
public class EmailConfig implements Serializable {
	
	/**
	 * @author 胡礼波
	 * 2012-11-15 下午04:28:35
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 发送服务器IP
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private  String host;

	/**
	 * 发送服务器端口
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private  Integer port;

	/**
	 * 发送编码
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String encoding="UTF-8";			//默认为UTF-8

	/**
	 *  登录名
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String username;

	/**
	 * 密码
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String password;

	/**
	 * 发送人
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String personal;		//默认发送人为登录用户名
	
	/**
	 * 来源
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String from;
	
	/**
	 * 答复地址
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String reply;			//默认答复地址为发送地址
	
	public EmailConfig(String host,String username,String password,String from,int port)
	{
		this.host=host;
		this.username=username;
		this.password=password;
		this.from=from;
		this.port=port;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
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

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
