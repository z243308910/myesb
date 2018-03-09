
package com.echounion.boss.soushipping.sehedule.tool.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SailingScheduleRoutes", propOrder = {
    "carrier",
    "destdate",
    "destid",
    "origdate",
    "origid",
    "service",
    "vessel",
    "voyage"
})
public class SailingScheduleRoutes {

    @XmlElementRef(name = "carrier", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> carrier;
    @XmlElementRef(name = "destdate", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> destdate;
    protected Integer destid;
    @XmlElementRef(name = "origdate", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> origdate;
    protected Integer origid;
    @XmlElementRef(name = "service", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> service;
    @XmlElementRef(name = "vessel", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vessel;
    @XmlElementRef(name = "voyage", namespace = "http://tool/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> voyage;

    public JAXBElement<String> getCarrier() {
        return carrier;
    }

    public void setCarrier(JAXBElement<String> value) {
        this.carrier = value;
    }

    public JAXBElement<String> getDestdate() {
        return destdate;
    }

    public void setDestdate(JAXBElement<String> value) {
        this.destdate = value;
    }

    public Integer getDestid() {
        return destid;
    }

    public void setDestid(Integer value) {
        this.destid = value;
    }

    public JAXBElement<String> getOrigdate() {
        return origdate;
    }

    public void setOrigdate(JAXBElement<String> value) {
        this.origdate = value;
    }

    public Integer getOrigid() {
        return origid;
    }

    public void setOrigid(Integer value) {
        this.origid = value;
    }

    public JAXBElement<String> getService() {
        return service;
    }

    public void setService(JAXBElement<String> value) {
        this.service = value;
    }

    public JAXBElement<String> getVessel() {
        return vessel;
    }

    public void setVessel(JAXBElement<String> value) {
        this.vessel = value;
    }

    public JAXBElement<String> getVoyage() {
        return voyage;
    }

    public void setVoyage(JAXBElement<String> value) {
        this.voyage = value;
    }

}
