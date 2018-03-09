
package com.echounion.boss.cargosmart.schedule.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sailingScheduleSearchCriteria" type="{http://webservice.sailingschedule.cargosmart.com/}SailingScheduleSearchCriteria"/>
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
    "sailingScheduleSearchCriteria"
})
@XmlRootElement(name = "findSailingScheduleRoutesElement")
public class FindSailingScheduleRoutesElement {

    @XmlElement(required = true, nillable = true)
    protected SailingScheduleSearchCriteria sailingScheduleSearchCriteria;

    /**
     * ��ȡsailingScheduleSearchCriteria���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SailingScheduleSearchCriteria }
     *     
     */
    public SailingScheduleSearchCriteria getSailingScheduleSearchCriteria() {
        return sailingScheduleSearchCriteria;
    }

    /**
     * ����sailingScheduleSearchCriteria���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SailingScheduleSearchCriteria }
     *     
     */
    public void setSailingScheduleSearchCriteria(SailingScheduleSearchCriteria value) {
        this.sailingScheduleSearchCriteria = value;
    }

}
