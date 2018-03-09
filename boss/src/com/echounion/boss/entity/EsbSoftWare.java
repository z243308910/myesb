package com.echounion.boss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 软件基本信息类
 * @author 胡礼波
 * 2012-11-2 下午02:21:49
 */
public class EsbSoftWare implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-2 下午02:22:01
	 */
	private static final long serialVersionUID = -6027404689468851114L;

	private int id;			//软件编号 编码实现自增
	private int serverId;	//服务器编号
	private int softType;	//软件类别
	private String softName;	//软件名称
	private String softCode;	//软件代号
	private String softUrl;		//软件访问地址-----是根据服务器IP软件协议和端口自动生成的访问地址管理员也可以根据实际更改
	private int softProtocol;	//软件访问协议
	private int softPort;		//软件访问端口
	private String authKey;		//软件授权key
	private String softKey;			//软件接入方的key 可选值
	private String remark;		//备注
	private String creator;		//创建人
	private Date createDate;	//创建时间
	private String mailChannel;	//邮件通道
	private String shortMsgChannel;//短信通道
	
	public String getShortMsgChannel() {
		return shortMsgChannel;
	}
	public void setShortMsgChannel(String shortMsgChannel) {
		this.shortMsgChannel = shortMsgChannel;
	}
	public String getMailChannel() {
		return mailChannel;
	}
	public void setMailChannel(String mailChannel) {
		this.mailChannel = mailChannel;
	}
	public int getId() {
		return id;
	}
	public String getSoftKey() {
		return softKey;
	}
	public void setSoftKey(String softKey) {
		this.softKey = softKey;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getSoftType() {
		return softType;
	}
	public void setSoftType(int softType) {
		this.softType = softType;
	}
	public String getSoftName() {
		return softName;
	}
	public void setSoftName(String softName) {
		this.softName = softName;
	}
	public String getSoftCode() {
		return softCode;
	}
	public void setSoftCode(String softCode) {
		this.softCode = softCode;
	}
	public String getSoftUrl() {
		return softUrl;
	}
	public void setSoftUrl(String softUrl) {
		this.softUrl = softUrl;
	}
	public int getSoftProtocol() {
		return softProtocol;
	}
	public void setSoftProtocol(int softProtocol) {
		this.softProtocol = softProtocol;
	}
	public int getSoftPort() {
		return softPort;
	}
	public void setSoftPort(int softPort) {
		this.softPort = softPort;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
