package com.echounion.boss.cargosmart.entity;

import java.io.Serializable;

/**
 * 
 * @author 胡礼波
 * 2013-10-29 下午2:08:25
 */
public class CargosmartRouting implements Serializable{

	/**
	 * @author 胡礼波
	 * 2013-10-29 下午2:08:46
	 */
	private static final long serialVersionUID = -3691533553046320303L;

	private int id;
	private int baseId;			//基础信息ID
	private String por;			//Place of Receipt
	private String pod;			//Port of Discharge
	private String pol;			//Port of Load
	private String fnd;			//Final Destination
	
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
	public String getPor() {
		return por;
	}
	public void setPor(String por) {
		this.por = por;
	}
	public String getPod() {
		return pod;
	}
	public void setPod(String pod) {
		this.pod = pod;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getFnd() {
		return fnd;
	}
	public void setFnd(String fnd) {
		this.fnd = fnd;
	}
}
