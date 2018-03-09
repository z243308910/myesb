
package com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>PODType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="PODType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="portName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="facilityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="callNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ATA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ETA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="UNLOCODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PODType", propOrder = {
    "portName",
    "facilityCode",
    "callNumber",
    "ata",
    "eta",
    "unlocode"
})
public class PODType {

    protected String portName;
    @XmlElement(required = true, nillable = true)
    protected String facilityCode;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer callNumber;
    @XmlElement(name = "ATA", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ata;
    @XmlElement(name = "ETA", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eta;
    @XmlElement(name = "UNLOCODE", required = true, nillable = true)
    protected String unlocode;

    /**
     * ��ȡportName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortName() {
        return portName;
    }

    /**
     * ����portName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortName(String value) {
        this.portName = value;
    }

    /**
     * ��ȡfacilityCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityCode() {
        return facilityCode;
    }

    /**
     * ����facilityCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityCode(String value) {
        this.facilityCode = value;
    }

    /**
     * ��ȡcallNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCallNumber() {
        return callNumber;
    }

    /**
     * ����callNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCallNumber(Integer value) {
        this.callNumber = value;
    }

    /**
     * ��ȡata���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getATA() {
        return ata;
    }

    /**
     * ����ata���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setATA(XMLGregorianCalendar value) {
        this.ata = value;
    }

    /**
     * ��ȡeta���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getETA() {
        return eta;
    }

    /**
     * ����eta���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setETA(XMLGregorianCalendar value) {
        this.eta = value;
    }

    /**
     * ��ȡunlocode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNLOCODE() {
        return unlocode;
    }

    /**
     * ����unlocode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNLOCODE(String value) {
        this.unlocode = value;
    }

}
