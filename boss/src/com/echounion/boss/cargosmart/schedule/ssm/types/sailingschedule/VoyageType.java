
package com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VoyageType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="VoyageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="internalVoyageNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalVoyageNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoyageType", propOrder = {
    "internalVoyageNumber",
    "externalVoyageNumber"
})
public class VoyageType {

    @XmlElement(required = true, nillable = true)
    protected String internalVoyageNumber;
    protected String externalVoyageNumber;

    /**
     * ��ȡinternalVoyageNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternalVoyageNumber() {
        return internalVoyageNumber;
    }

    /**
     * ����internalVoyageNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternalVoyageNumber(String value) {
        this.internalVoyageNumber = value;
    }

    /**
     * ��ȡexternalVoyageNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalVoyageNumber() {
        return externalVoyageNumber;
    }

    /**
     * ����externalVoyageNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalVoyageNumber(String value) {
        this.externalVoyageNumber = value;
    }

}
