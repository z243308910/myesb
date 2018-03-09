package com.echounion.bossmanager.entity.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 协议服务DTO
 * @author 胡礼波
 * 2012-11-13 下午04:27:34
 */
public class LeaseServiceDTO implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-13 下午04:30:48
	 */
	private static final long serialVersionUID = -6964999649551671010L;
	
	private int agreementId;		//协议ID
	private String subject;			//协议标题
	private int leaseType;			//租赁类型
	private int deployId;
	private int softId;				//软件ID
	private int serverId;			//服务器ID
	private Date effectDate;		//生效时间
	private Date expireDate;		//失效时间
	private String serviceIds;		//软件服务ID集合
	private String remark;			//备注
	
	public int getAgreementId() {
		return agreementId;
	}
	public void setAgreementId(int agreementId) {
		this.agreementId = agreementId;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public Date getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public int getLeaseType() {
		return leaseType;
	}
	public void setLeaseType(int leaseType) {
		this.leaseType = leaseType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getServiceIds() {
		return serviceIds;
	}
	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
	}
	public int getSoftId() {
		return softId;
	}
	public void setSoftId(int softId) {
		this.softId = softId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getDeployId() {
		return deployId;
	}
	public void setDeployId(int deployId) {
		this.deployId = deployId;
	}
}
