
package com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>OceanComponentType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="OceanComponentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oceanleg" type="{http://sailingschedule.types.ssm.cargosmart.com/}OceanLeg" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OceanComponentType", propOrder = {
    "oceanleg"
})
public class OceanComponentType {

    protected List<OceanLeg> oceanleg;

    /**
     * Gets the value of the oceanleg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oceanleg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOceanleg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OceanLeg }
     * 
     * 
     */
    public List<OceanLeg> getOceanleg() {
        if (oceanleg == null) {
            oceanleg = new ArrayList<OceanLeg>();
        }
        return this.oceanleg;
    }

}
