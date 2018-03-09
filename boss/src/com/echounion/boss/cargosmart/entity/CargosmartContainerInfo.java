package com.echounion.boss.cargosmart.entity;

import java.io.Serializable;

/**
 * 
 * @author 胡礼波
 * 2013-10-29 下午2:12:12
 */
public class CargosmartContainerInfo implements Serializable{

	/**
	 * @author 胡礼波
	 * 2013-10-29 下午2:12:27
	 */
	private static final long serialVersionUID = -7907021838854296L;

	private int id;
	private int baseId;				//基础信息ID
	private String containerNo;		//箱号
	private String sizeType;		//箱型
	private String sealNumber;		//封号
	private String sealType;		//封型
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBaseId() {
		return baseId;
	}
	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	public String getSealNumber() {
		return sealNumber;
	}
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	public String getSealType() {
		return sealType;
	}
	public void setSealType(String sealType) {
		this.sealType = sealType;
	}
	
}
