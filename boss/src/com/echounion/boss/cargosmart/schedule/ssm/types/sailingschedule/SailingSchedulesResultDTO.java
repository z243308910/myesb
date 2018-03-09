
package com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SailingSchedulesResultDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SailingSchedulesResultDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="searchResultRecord" type="{http://sailingschedule.types.ssm.cargosmart.com/}SearchResultRecord" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="errorDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shortestEstTransitTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="scheduleLastUpdate" type="{http://sailingschedule.types.ssm.cargosmart.com/}ScheduleLastUpdate" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SailingSchedulesResultDTO", propOrder = {
    "searchResultRecord",
    "errorDescription",
    "shortestEstTransitTime",
    "scheduleLastUpdate"
})
public class SailingSchedulesResultDTO {

    @XmlElement(nillable = true)
    protected List<SearchResultRecord> searchResultRecord;
    @XmlElement(required = true, nillable = true)
    protected String errorDescription;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer shortestEstTransitTime;
    @XmlElement(nillable = true)
    protected List<ScheduleLastUpdate> scheduleLastUpdate;

    /**
     * Gets the value of the searchResultRecord property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the searchResultRecord property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSearchResultRecord().add(newItem);
     * </pre>
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchResultRecord }
     * 
     * 
     */
    public List<SearchResultRecord> getSearchResultRecord() {
        if (searchResultRecord == null) {
            searchResultRecord = new ArrayList<SearchResultRecord>();
        }
        return this.searchResultRecord;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDescription(String value) {
        this.errorDescription = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getShortestEstTransitTime() {
        return shortestEstTransitTime;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setShortestEstTransitTime(Integer value) {
        this.shortestEstTransitTime = value;
    }

    /**
     * Gets the value of the scheduleLastUpdate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scheduleLastUpdate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScheduleLastUpdate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduleLastUpdate }
     * 
     * 
     */
    public List<ScheduleLastUpdate> getScheduleLastUpdate() {
        if (scheduleLastUpdate == null) {
            scheduleLastUpdate = new ArrayList<ScheduleLastUpdate>();
        }
        return this.scheduleLastUpdate;
    }

}
