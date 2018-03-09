
package com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>SearchResultRecord complex type�� Java �ࡣ
 * 
 * 
 * <pre>
 * &lt;complexType name="SearchResultRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="originHaulageIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destination" type="{http://sailingschedule.types.ssm.cargosmart.com/}SailingScheduleCityType" minOccurs="0"/>
 *         &lt;element name="cutOffLocalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="oceanComponent" type="{http://sailingschedule.types.ssm.cargosmart.com/}OceanComponentType" minOccurs="0"/>
 *         &lt;element name="cutOffLocalTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FNDHaulageIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estimatedTransitTimeInDays" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ETAatFND" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="origin" type="{http://sailingschedule.types.ssm.cargosmart.com/}SailingScheduleCityType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchResultRecord", propOrder = {
    "originHaulageIndicator",
    "destination",
    "cutOffLocalDate",
    "oceanComponent",
    "cutOffLocalTime",
    "fndHaulageIndicator",
    "estimatedTransitTimeInDays",
    "etAatFND",
    "origin"
})
public class SearchResultRecord {

    @XmlElement(required = true, nillable = true)
    protected String originHaulageIndicator;
    
    protected SailingScheduleCityType destination;
    
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar cutOffLocalDate;
    
    protected OceanComponentType oceanComponent;
    
    @XmlElement(required = true, nillable = true)
    protected String cutOffLocalTime;
    
    @XmlElement(name = "FNDHaulageIndicator", required = true, nillable = true)
    protected String fndHaulageIndicator;
    
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer estimatedTransitTimeInDays;
    
    @XmlElement(name = "ETAatFND", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar etAatFND;
    
    protected SailingScheduleCityType origin;

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginHaulageIndicator() {
        return originHaulageIndicator;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginHaulageIndicator(String value) {
        this.originHaulageIndicator = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link SailingScheduleCityType }
     *     
     */
    public SailingScheduleCityType getDestination() {
        return destination;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link SailingScheduleCityType }
     *     
     */
    public void setDestination(SailingScheduleCityType value) {
        this.destination = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCutOffLocalDate() {
        return cutOffLocalDate;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCutOffLocalDate(XMLGregorianCalendar value) {
        this.cutOffLocalDate = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link OceanComponentType }
     *     
     */
    public OceanComponentType getOceanComponent() {
        return oceanComponent;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link OceanComponentType }
     *     
     */
    public void setOceanComponent(OceanComponentType value) {
        this.oceanComponent = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCutOffLocalTime() {
        return cutOffLocalTime;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCutOffLocalTime(String value) {
        this.cutOffLocalTime = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFNDHaulageIndicator() {
        return fndHaulageIndicator;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFNDHaulageIndicator(String value) {
        this.fndHaulageIndicator = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEstimatedTransitTimeInDays() {
        return estimatedTransitTimeInDays;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEstimatedTransitTimeInDays(Integer value) {
        this.estimatedTransitTimeInDays = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getETAatFND() {
        return etAatFND;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setETAatFND(XMLGregorianCalendar value) {
        this.etAatFND = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link SailingScheduleCityType }
     *     
     */
    public SailingScheduleCityType getOrigin() {
        return origin;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link SailingScheduleCityType }
     *     
     */
    public void setOrigin(SailingScheduleCityType value) {
        this.origin = value;
    }

}
