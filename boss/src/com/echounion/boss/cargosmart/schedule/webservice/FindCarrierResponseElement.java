
package com.echounion.boss.cargosmart.schedule.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.echounion.boss.cargosmart.schedule.ssm.types.CarrierResponseDTO;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://types.ssm.cargosmart.com/}CarrierResponseDTO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "result"
})
@XmlRootElement(name = "findCarrierResponseElement")
public class FindCarrierResponseElement {

    @XmlElement(required = true, nillable = true)
    protected CarrierResponseDTO result;

    /**
     * 获取result属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CarrierResponseDTO }
     *     
     */
    public CarrierResponseDTO getResult() {
        return result;
    }

    /**
     * 设置result属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CarrierResponseDTO }
     *     
     */
    public void setResult(CarrierResponseDTO value) {
        this.result = value;
    }

}
