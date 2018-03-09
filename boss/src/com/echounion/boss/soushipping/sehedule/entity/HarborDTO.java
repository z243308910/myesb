
package com.echounion.boss.soushipping.sehedule.entity;

import java.io.Serializable;


public class HarborDTO implements Serializable {

    /**
	 * @author 胡礼波
	 * 2014-2-21 上午10:59:51
	 */
	private static final long serialVersionUID = 3036103746478982399L;
	
	private int barborCode;				//城市编号
    private String cityName;			//城市名称
    private String countryName;		//国家名称
    private String harborName;		//港口名称
    
    public HarborDTO(){}
    
    public HarborDTO(int cityCode,String cityName,String countryName,String harborName)
    {
    	this.barborCode=cityCode;
    	this.cityName=cityName;
    	this.countryName=countryName;
    	this.harborName=harborName;
    }
    
	public int getBarborCode() {
		return barborCode;
	}

	public void setBarborCode(int barborCode) {
		this.barborCode = barborCode;
	}

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getHarborName() {
		return harborName;
	}
	public void setHarborName(String harborName) {
		this.harborName = harborName;
	}
}
