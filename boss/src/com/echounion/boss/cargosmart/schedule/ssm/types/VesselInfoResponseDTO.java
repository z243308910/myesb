
package com.echounion.boss.cargosmart.schedule.ssm.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.echounion.boss.cargosmart.schedule.ssm.types.common.VesselInfoType;



/**
 * <p>VesselInfoResponseDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="VesselInfoResponseDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numberOfRecordsFound" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vessel" type="{http://common.types.ssm.cargosmart.com/}VesselInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VesselInfoResponseDTO", propOrder = {
    "numberOfRecordsFound",
    "errorDescription",
    "vessel"
})
public class VesselInfoResponseDTO {

    protected int numberOfRecordsFound;
    @XmlElement(required = true, nillable = true)
    protected String errorDescription;
    @XmlElement(required = true, nillable = true)
    protected VesselInfoType vessel;

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
     * 获取vessel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link VesselInfoType }
     *     
     */
    public VesselInfoType getVessel() {
        return vessel;
    }

    /**
     * 设置vessel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link VesselInfoType }
     *     
     */
    public void setVessel(VesselInfoType value) {
        this.vessel = value;
    }

}
