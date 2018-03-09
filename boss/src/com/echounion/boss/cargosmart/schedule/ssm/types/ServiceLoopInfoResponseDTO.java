
package com.echounion.boss.cargosmart.schedule.ssm.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.echounion.boss.cargosmart.schedule.ssm.types.common.ServiceLoopInfoType;


/**
 * <p>ServiceLoopInfoResponseDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ServiceLoopInfoResponseDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="service" type="{http://common.types.ssm.cargosmart.com/}ServiceLoopInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="numberOfRecordsFound" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLoopInfoResponseDTO", propOrder = {
    "service",
    "numberOfRecordsFound",
    "errorDescription"
})
public class ServiceLoopInfoResponseDTO {

    @XmlElement(nillable = true)
    protected List<ServiceLoopInfoType> service;
    protected int numberOfRecordsFound;
    @XmlElement(required = true, nillable = true)
    protected String errorDescription;

    /**
     * Gets the value of the service property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the service property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceLoopInfoType }
     * 
     * 
     */
    public List<ServiceLoopInfoType> getService() {
        if (service == null) {
            service = new ArrayList<ServiceLoopInfoType>();
        }
        return this.service;
    }

    /**
     * 获取numberOfRecordsFound属性的值。
     * 
     */
    public int getNumberOfRecordsFound() {
        return numberOfRecordsFound;
    }

    /**
     * 设置numberOfRecordsFound属性的值。
     * 
     */
    public void setNumberOfRecordsFound(int value) {
        this.numberOfRecordsFound = value;
    }

    /**
     * 获取errorDescription属性的值。
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
     * 设置errorDescription属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDescription(String value) {
        this.errorDescription = value;
    }

}
