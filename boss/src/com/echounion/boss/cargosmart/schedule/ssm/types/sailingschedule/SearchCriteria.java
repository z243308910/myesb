
package com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SearchCriteria complex type�� Java �ࡣ
 * 
 * <pre>
 * &lt;complexType name="SearchCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="groupByCarrierIndicator" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="arrivalToDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destination" type="{http://sailingschedule.types.ssm.cargosmart.com/}ParentCityKeyType"/>
 *         &lt;element name="orign" type="{http://sailingschedule.types.ssm.cargosmart.com/}ParentCityKeyType"/>
 *         &lt;element name="sortBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cargoNature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arrivalFromDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sailingToDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SCAC" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="nearbyPort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sailingFromDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchCriteria", propOrder = {
    "groupByCarrierIndicator",
    "arrivalToDate",
    "destination",
    "orign",
    "sortBy",
    "serviceCode",
    "cargoNature",
    "arrivalFromDate",
    "sailingToDate",
    "scac",
    "nearbyPort",
    "sailingFromDate"
})
public class SearchCriteria {

    protected Integer groupByCarrierIndicator;
    protected String arrivalToDate;
    @XmlElement(required = true)
    protected ParentCityKeyType destination;
    @XmlElement(required = true)
    protected ParentCityKeyType orign;
    protected String sortBy;
    protected String serviceCode;
    protected String cargoNature;
    protected String arrivalFromDate;
    protected String sailingToDate;
    @XmlElement(name = "SCAC", required = true)
    protected List<String> scac;
    protected Integer nearbyPort;
    protected String sailingFromDate;

    public void setScac(List<String> scac) {
		this.scac = scac;
	}

	/**
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGroupByCarrierIndicator() {
        return groupByCarrierIndicator;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGroupByCarrierIndicator(Integer value) {
        this.groupByCarrierIndicator = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalToDate() {
        return arrivalToDate;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalToDate(String value) {
        this.arrivalToDate = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link ParentCityKeyType }
     *     
     */
    public ParentCityKeyType getDestination() {
        return destination;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link ParentCityKeyType }
     *     
     */
    public void setDestination(ParentCityKeyType value) {
        this.destination = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link ParentCityKeyType }
     *     
     */
    public ParentCityKeyType getOrign() {
        return orign;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link ParentCityKeyType }
     *     
     */
    public void setOrign(ParentCityKeyType value) {
        this.orign = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortBy(String value) {
        this.sortBy = value;
    }

    /**
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
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCargoNature() {
        return cargoNature;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCargoNature(String value) {
        this.cargoNature = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalFromDate() {
        return arrivalFromDate;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalFromDate(String value) {
        this.arrivalFromDate = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSailingToDate() {
        return sailingToDate;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSailingToDate(String value) {
        this.sailingToDate = value;
    }

    /**
     * Gets the value of the scac property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scac property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSCAC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSCAC() {
        if (scac == null) {
            scac = new ArrayList<String>();
//            scac.add("OOLU");
//            scac.add("COSU");
//            scac.add("MOLU");
//            scac.add("NYKS");
//            scac.add("EGLV");
//            scac.add("YMLU");
//            scac.add("HJSC");
//            scac.add("HLCU");
//            scac.add("CHNJ");
//            scac.add("PABV");
//            scac.add("NCLL");
//            scac.add("CHIW");
//            scac.add("ZIMU");
//            scac.add("WHLC");
//            scac.add("HDMU");
//            scac.add("CMDU");
//            scac.add("DAAE");
        }
        return this.scac;
    }

    /**
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNearbyPort() {
        return nearbyPort;
    }

    /**
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNearbyPort(Integer value) {
        this.nearbyPort = value;
    }

    /**
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSailingFromDate() {
        return sailingFromDate;
    }

    /**
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSailingFromDate(String value) {
        this.sailingFromDate = value;
    }

}
