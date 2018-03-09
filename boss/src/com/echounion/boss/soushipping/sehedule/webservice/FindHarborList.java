
package com.echounion.boss.soushipping.sehedule.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "harborPreName",
    "pageSize"
})
@XmlRootElement(name = "findHarborList")
public class FindHarborList {

    @XmlElementRef(name = "harborPreName", namespace = "http://ws.apache.org/axis2", type = JAXBElement.class, required = false)
    protected JAXBElement<String> harborPreName;
    protected Integer pageSize;

   
    public JAXBElement<String> getHarborPreName() {
        return harborPreName;
    }

  
    public void setHarborPreName(JAXBElement<String> value) {
        this.harborPreName = value;
    }

    
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer value) {
        this.pageSize = value;
    }

}
