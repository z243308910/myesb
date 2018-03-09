
package com.echounion.boss.cargosmart.schedule.ssm.types.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ServiceLoopInfoType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ServiceLoopInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="facilityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isTurnAroundStop" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="facilityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facilityCounty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facilityContinent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facilityState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facilityCityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="carrier" type="{http://common.types.ssm.cargosmart.com/}CarrierType"/>
 *         &lt;element name="facilityCountry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLoopInfoType", propOrder = {
    "callSequence",
    "facilityCode",
    "isTurnAroundStop",
    "facilityName",
    "facilityCounty",
    "facilityContinent",
    "facilityState",
    "direction",
    "serviceCode",
    "serviceName",
    "facilityCityName",
    "carrier",
    "facilityCountry"
})
public class ServiceLoopInfoType {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer callSequence;
    @XmlElement(required = true, nillable = true)
    protected String facilityCode;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer isTurnAroundStop;
    @XmlElement(required = true, nillable = true)
    protected String facilityName;
    @XmlElement(required = true, nillable = true)
    protected String facilityCounty;
    @XmlElement(required = true, nillable = true)
    protected String facilityContinent;
    @XmlElement(required = true, nillable = true)
    protected String facilityState;
    @XmlElement(required = true, nillable = true)
    protected String direction;
    @XmlElement(required = true)
    protected String serviceCode;
    @XmlElement(required = true, nillable = true)
    protected String serviceName;
    @XmlElement(required = true, nillable = true)
    protected String facilityCityName;
    @XmlElement(required = true)
    protected CarrierType carrier;
    @XmlElement(required = true, nillable = true)
    protected String facilityCountry;

    /**
     * ��ȡcallSequence���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCallSequence() {
        return callSequence;
    }

    /**
     * ����callSequence���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCallSequence(Integer value) {
        this.callSequence = value;
    }

    /**
     * ��ȡfacilityCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityCode() {
        return facilityCode;
    }

    /**
     * ����facilityCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityCode(String value) {
        this.facilityCode = value;
    }

    /**
     * ��ȡisTurnAroundStop���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsTurnAroundStop() {
        return isTurnAroundStop;
    }

    /**
     * ����isTurnAroundStop���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsTurnAroundStop(Integer value) {
        this.isTurnAroundStop = value;
    }

    /**
     * ��ȡfacilityName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityName() {
        return facilityName;
    }

    /**
     * ����facilityName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityName(String value) {
        this.facilityName = value;
    }

    /**
     * ��ȡfacilityCounty���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityCounty() {
        return facilityCounty;
    }

    /**
     * ����facilityCounty���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityCounty(String value) {
        this.facilityCounty = value;
    }

    /**
     * ��ȡfacilityContinent���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityContinent() {
        return facilityContinent;
    }

    /**
     * ����facilityContinent���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityContinent(String value) {
        this.facilityContinent = value;
    }

    /**
     * ��ȡfacilityState���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityState() {
        return facilityState;
    }

    /**
     * ����facilityState���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityState(String value) {
        this.facilityState = value;
    }

    /**
     * ��ȡdirection���Ե�ֵ��
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
     * ����direction���Ե�ֵ��
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
     * ��ȡserviceCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * ����serviceCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceCode(String value) {
        this.serviceCode = value;
    }

    /**
     * ��ȡserviceName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * ����serviceName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * ��ȡfacilityCityName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityCityName() {
        return facilityCityName;
    }

    /**
     * ����facilityCityName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityCityName(String value) {
        this.facilityCityName = value;
    }

    /**
     * ��ȡcarrier���Ե�ֵ��
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
     * ����carrier���Ե�ֵ��
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
     * ��ȡfacilityCountry���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityCountry() {
        return facilityCountry;
    }

    /**
     * ����facilityCountry���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityCountry(String value) {
        this.facilityCountry = value;
    }

}
