package com.echounion.boss.cargosmart.entity;

import java.io.Serializable;

/**
 * 
 * @author 胡礼波
 * 2013-10-29 下午2:10:51
 */
public class CargosmartCargoInfo implements Serializable{

	/**
	 * @author 胡礼波
	 * 2013-10-29 下午2:11:51
	 */
	private static final long serialVersionUID = -73794419561071032L;
	
	private int id;
	private int baseId;				//基础信息ID
	private String cargoNo;			//货物编号
	private String cargoDesc;		//货物描述
	private String maskNo;			//货物商标
	
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
	public String getCargoNo() {
		return cargoNo;
	}
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
	public String getCargoDesc() {
		return cargoDesc;
	}
	public void setCargoDesc(String cargoDesc) {
		this.cargoDesc = cargoDesc;
	}
	public String getMaskNo() {
		return maskNo;
	}
	public void setMaskNo(String maskNo) {
		this.maskNo = maskNo;
	}
}
