
package com.echounion.boss.soushipping.sehedule.entity;

import java.io.Serializable;


public class SailingScheduleRoutesDTO implements Serializable {

    /**
	 * @author 胡礼波
	 * 2014-2-21 上午11:06:57
	 */
	private static final long serialVersionUID = 4391834304465460100L;
	
	private String carrier;
    private String destdate;
    private Integer destid;
    private String origdate;
    private Integer origid;
    private String service;
    private String vessel;
    private String voyage;
    
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getDestdate() {
		return destdate;
	}
	public void setDestdate(String destdate) {
		this.destdate = destdate;
	}
	public Integer getDestid() {
		return destid;
	}
	public void setDestid(Integer destid) {
		this.destid = destid;
	}
	public String getOrigdate() {
		return origdate;
	}
	public void setOrigdate(String origdate) {
		this.origdate = origdate;
	}
	public Integer getOrigid() {
		return origid;
	}
	public void setOrigid(Integer origid) {
		this.origid = origid;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getVessel() {
		return vessel;
	}
	public void setVessel(String vessel) {
		this.vessel = vessel;
	}
	public String getVoyage() {
		return voyage;
	}
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

}
