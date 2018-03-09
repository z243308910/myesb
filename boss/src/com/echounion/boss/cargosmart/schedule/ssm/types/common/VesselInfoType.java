
package com.echounion.boss.cargosmart.schedule.ssm.types.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VesselInfoType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="VesselInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="classSociety" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="owner" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vesselName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="grossTonnage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="operator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="regPort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="yearBuilt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lloydsNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="netTonnage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="vesselCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="callSign" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="carrier" type="{http://common.types.ssm.cargosmart.com/}CarrierType"/>
 *         &lt;element name="flagCountry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VesselInfoType", propOrder = {
    "classSociety",
    "owner",
    "vesselName",
    "grossTonnage",
    "operator",
    "regPort",
    "yearBuilt",
    "lloydsNum",
    "netTonnage",
    "vesselCode",
    "callSign",
    "carrier",
    "flagCountry"
})
public class VesselInfoType {

    @XmlElement(required = true, nillable = true)
    protected String classSociety;
    @XmlElement(required = true, nillable = true)
    protected String owner;
    @XmlElement(required = true, nillable = true)
    protected String vesselName;
    @XmlElement(required = true, type = Float.class, nillable = true)
    protected Float grossTonnage;
    @XmlElement(required = true, nillable = true)
    protected String operator;
    @XmlElement(required = true, nillable = true)
    protected String regPort;
    @XmlElement(required = true, nillable = true)
    protected String yearBuilt;
    @XmlElement(required = true, nillable = true)
    protected String lloydsNum;
    @XmlElement(required = true, type = Float.class, nillable = true)
    protected Float netTonnage;
    @XmlElement(required = true)
    protected String vesselCode;
    @XmlElement(required = true, nillable = true)
    protected String callSign;
    @XmlElement(required = true)
    protected CarrierType carrier;
    @XmlElement(required = true, nillable = true)
    protected String flagCountry;

    /**
     * ��ȡclassSociety���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassSociety() {
        return classSociety;
    }

    /**
     * ����classSociety���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassSociety(String value) {
        this.classSociety = value;
    }

    /**
     * ��ȡowner���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * ����owner���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * ��ȡvesselName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVesselName() {
        return vesselName;
    }

    /**
     * ����vesselName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVesselName(String value) {
        this.vesselName = value;
    }

    /**
     * ��ȡgrossTonnage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getGrossTonnage() {
        return grossTonnage;
    }

    /**
     * ����grossTonnage���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setGrossTonnage(Float value) {
        this.grossTonnage = value;
    }

    /**
     * ��ȡoperator���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * ����operator���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    /**
     * ��ȡregPort���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegPort() {
        return regPort;
    }

    /**
     * ����regPort���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegPort(String value) {
        this.regPort = value;
    }

    /**
     * ��ȡyearBuilt���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearBuilt() {
        return yearBuilt;
    }

    /**
     * ����yearBuilt���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearBuilt(String value) {
        this.yearBuilt = value;
    }

    /**
     * ��ȡlloydsNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLloydsNum() {
        return lloydsNum;
    }

    /**
     * ����lloydsNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLloydsNum(String value) {
        this.lloydsNum = value;
    }

    /**
     * ��ȡnetTonnage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getNetTonnage() {
        return netTonnage;
    }

    /**
     * ����netTonnage���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setNetTonnage(Float value) {
        this.netTonnage = value;
    }

    /**
     * ��ȡvesselCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVesselCode() {
        return vesselCode;
    }

    /**
     * ����vesselCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVesselCode(String value) {
        this.vesselCode = value;
    }

    /**
     * ��ȡcallSign���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallSign() {
        return callSign;
    }

    /**
     * ����callSign���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallSign(String value) {
        this.callSign = value;
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
     * ��ȡflagCountry���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagCountry() {
        return flagCountry;
    }

    /**
     * ����flagCountry���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagCountry(String value) {
        this.flagCountry = value;
    }

}
