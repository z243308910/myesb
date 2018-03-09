
package com.echounion.boss.cargosmart.schedule.ssm.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.echounion.boss.cargosmart.schedule.ssm.types.common.CarrierType;


/**
 * <p>CarrierResponseDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CarrierResponseDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numberOfRecordsFound" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="carrier" type="{http://common.types.ssm.cargosmart.com/}CarrierType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarrierResponseDTO", propOrder = {
    "numberOfRecordsFound",
    "errorDescription",
    "carrier"
})
public class CarrierResponseDTO {

    protected int numberOfRecordsFound;
    @XmlElement(required = true, nillable = true)
    protected String errorDescription;
    @XmlElement(nillable = true)
    protected List<CarrierType> carrier;

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

    /**
     * Gets the value of the carrier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carrier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarrier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CarrierType }
     * 
     * 
     */
    public List<CarrierType> getCarrier() {
        if (carrier == null) {
            carrier = new ArrayList<CarrierType>();
        }
        return this.carrier;
    }

}
