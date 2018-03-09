
package com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.echounion.boss.cargosmart.schedule.ssm.types.common.CarrierType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.PODType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.POLType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.SailingScheduleServiceType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.VesselType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.VoyageType;



/**
 * <p>OceanLeg complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="OceanLeg">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="service" type="{http://sailingschedule.types.ssm.cargosmart.com/}SailingScheduleServiceType" minOccurs="0"/>
 *         &lt;element name="voyage" type="{http://sailingschedule.types.ssm.cargosmart.com/}VoyageType" minOccurs="0"/>
 *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="POL" type="{http://sailingschedule.types.ssm.cargosmart.com/}POLType" minOccurs="0"/>
 *         &lt;element name="legSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="POD" type="{http://sailingschedule.types.ssm.cargosmart.com/}PODType" minOccurs="0"/>
 *         &lt;element name="carrier" type="{http://common.types.ssm.cargosmart.com/}CarrierType" minOccurs="0"/>
 *         &lt;element name="vessel" type="{http://sailingschedule.types.ssm.cargosmart.com/}VesselType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OceanLeg", propOrder = {
    "service",
    "voyage",
    "direction",
    "pol",
    "legSequence",
    "pod",
    "carrier",
    "vessel"
})
public class OceanLeg {

    protected SailingScheduleServiceType service;
    protected VoyageType voyage;
    @XmlElement(required = true, nillable = true)
    protected String direction;
    @XmlElement(name = "POL")
    protected POLType pol;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer legSequence;
    @XmlElement(name = "POD")
    protected PODType pod;
    protected CarrierType carrier;
    protected VesselType vessel;

    /**
     * 获取service属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SailingScheduleServiceType }
     *     
     */
    public SailingScheduleServiceType getService() {
        return service;
    }

    /**
     * 设置service属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SailingScheduleServiceType }
     *     
     */
    public void setService(SailingScheduleServiceType value) {
        this.service = value;
    }

    /**
     * 获取voyage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link VoyageType }
     *     
     */
    public VoyageType getVoyage() {
        return voyage;
    }

    /**
     * 设置voyage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link VoyageType }
     *     
     */
    public void setVoyage(VoyageType value) {
        this.voyage = value;
    }

    /**
     * 获取direction属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 设置direction属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
    }

    /**
     * 获取pol属性的值。
     * 
     * @return
     *     possible object is
     *     {@link POLType }
     *     
     */
    public POLType getPOL() {
        return pol;
    }

    /**
     * 设置pol属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link POLType }
     *     
     */
    public void setPOL(POLType value) {
        this.pol = value;
    }

    /**
     * 获取legSequence属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLegSequence() {
        return legSequence;
    }

    /**
     * 设置legSequence属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLegSequence(Integer value) {
        this.legSequence = value;
    }

    /**
     * 获取pod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PODType }
     *     
     */
    public PODType getPOD() {
        return pod;
    }

    /**
     * 设置pod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PODType }
     *     
     */
    public void setPOD(PODType value) {
        this.pod = value;
    }

    /**
     * 获取carrier属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CarrierType }
     *     
     */
    public CarrierType getCarrier() {
        return carrier;
    }

    /**
     * 设置carrier属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CarrierType }
     *     
     */
    public void setCarrier(CarrierType value) {
        this.carrier = value;
    }

    /**
     * 获取vessel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link VesselType }
     *     
     */
    public VesselType getVessel() {
        return vessel;
    }

    /**
     * 设置vessel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link VesselType }
     *     
     */
    public void setVessel(VesselType value) {
        this.vessel = value;
    }

}
