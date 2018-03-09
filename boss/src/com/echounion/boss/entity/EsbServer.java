package com.echounion.boss.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 服务器实体类
 * @author 胡礼波
 * 2012-11-8 下午05:00:33
 */
public class EsbServer implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-8 下午05:00:45
	 */
	private static final long serialVersionUID = -4072632158237010163L;

	private int id;
	private String serverName;	//服务器名称
	private String serverIp;		//服务器IP
	private String creator;	//创建者
	private Date createDate;	//创建时间
	
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
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
}
