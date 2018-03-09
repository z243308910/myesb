
package com.echounion.boss.soushipping.sehedule.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "oringCode",
    "destCode",
    "sailingFromDate",
    "carriers"
})
@XmlRootElement(name = "findSailingScheduleRoutes")
public class FindSailingScheduleRoutes {

    protected Integer oringCode;
    protected Integer destCode;
    @XmlElementRef(name = "sailingFromDate", namespace = "http://ws.apache.org/axis2", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sailingFromDate;
    @XmlElementRef(name = "carriers", namespace = "http://ws.apache.org/axis2", type = JAXBElement.class, required = false)
    protected JAXBElement<String> carriers;

   
    public Integer getOringCode() {
        return oringCode;
    }

   
    public void setOringCode(Integer value) {
        this.oringCode = value;
    }

   
    public Integer getDestCode() {
        return destCode;
    }

    
    public void setDestCode(Integer value) {
        this.destCode = value;
    }

    
    public JAXBElement<String> getSailingFromDate() {
        return sailingFromDate;
    }

   
    public void setSailingFromDate(JAXBElement<String> value) {
        this.sailingFromDate = value;
    }

    
    public JAXBElement<String> getCarriers() {
        return carriers;
    }

    
    public void setCarriers(JAXBElement<String> value) {
        this.carriers = value;
    }

}
