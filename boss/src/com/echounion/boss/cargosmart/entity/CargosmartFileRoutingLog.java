package com.echounion.boss.cargosmart.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 与CARGOSMART交互的文件实体类 日志
 * 主要是记录每个业务节点文件状态的变化
 * @author Administrator
 *
 */
public class CargosmartFileRoutingLog implements Serializable{

	private static final long serialVersionUID = 8431766638167221649L;
	
	private int id;
	private String fileName;		//文件名称
	private Date opTime;			//操作时间
	private String remark;			//备注
	
	public CargosmartFileRoutingLog(){}
	
	public CargosmartFileRoutingLog(String fileName,String remark)
	{
		this.fileName=fileName;
		this.remark=remark;
		this.opTime=new Date();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
