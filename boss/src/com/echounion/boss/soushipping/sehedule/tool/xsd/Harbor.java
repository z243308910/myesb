
package com.echounion.boss.soushipping.sehedule.tool.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Harbor", propOrder = {
    "cityCode",
    "cityName",
    "countryName",
    "harborName"
})
public class Harbor {

    protected Integer cityCode;
    @XmlElementRef(name = "cityName", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cityName;
    @XmlElementRef(name = "countryName", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> countryName;
    @XmlElementRef(name = "harborName", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> harborName;

    

	public Integer getCityCode() {
		return cityCode;
	}


	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}


	public JAXBElement<String> getCityName() {
        return cityName;
    }

   
    public void setCityName(JAXBElement<String> value) {
        this.cityName = value;
    }

   
    public JAXBElement<String> getCountryName() {
        return countryName;
    }

   
    public void setCountryName(JAXBElement<String> value) {
        this.countryName = value;
    }

    
    public JAXBElement<String> getHarborName() {
        return harborName;
    }

    public void setHarborName(JAXBElement<String> value) {
        this.harborName = value;
    }

}
