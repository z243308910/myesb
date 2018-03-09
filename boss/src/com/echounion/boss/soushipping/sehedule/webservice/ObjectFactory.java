
package com.echounion.boss.soushipping.sehedule.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;



@XmlRegistry
public class ObjectFactory {

    private final static QName _FindHarborListHarborPreName_QNAME = new QName("http://ws.apache.org/axis2", "harborPreName");
    private final static QName _FindSailingScheduleRoutesSailingFromDate_QNAME = new QName("http://ws.apache.org/axis2", "sailingFromDate");
    private final static QName _FindSailingScheduleRoutesCarriers_QNAME = new QName("http://ws.apache.org/axis2", "carriers");

   
    public ObjectFactory() {
    }

   
    public FindHarborList createFindHarborList() {
        return new FindHarborList();
    }

    
    public FindSailingScheduleRoutesResponse createFindSailingScheduleRoutesResponse() {
        return new FindSailingScheduleRoutesResponse();
    }

    
    public FindSailingScheduleRoutes createFindSailingScheduleRoutes() {
        return new FindSailingScheduleRoutes();
    }

   
    public FindHarborListResponse createFindHarborListResponse() {
        return new FindHarborListResponse();
    }

    
    public Main createMain() {
        return new Main();
    }

    
    @XmlElementDecl(namespace = "http://ws.apache.org/axis2", name = "harborPreName", scope = FindHarborList.class)
    public JAXBElement<String> createFindHarborListHarborPreName(String value) {
        return new JAXBElement<String>(_FindHarborListHarborPreName_QNAME, String.class, FindHarborList.class, value);
    }

    
    @XmlElementDecl(namespace = "http://ws.apache.org/axis2", name = "sailingFromDate", scope = FindSailingScheduleRoutes.class)
    public JAXBElement<String> createFindSailingScheduleRoutesSailingFromDate(String value) {
        return new JAXBElement<String>(_FindSailingScheduleRoutesSailingFromDate_QNAME, String.class, FindSailingScheduleRoutes.class, value);
    }

  
    @XmlElementDecl(namespace = "http://ws.apache.org/axis2", name = "carriers", scope = FindSailingScheduleRoutes.class)
    public JAXBElement<String> createFindSailingScheduleRoutesCarriers(String value) {
        return new JAXBElement<String>(_FindSailingScheduleRoutesCarriers_QNAME, String.class, FindSailingScheduleRoutes.class, value);
    }

}
