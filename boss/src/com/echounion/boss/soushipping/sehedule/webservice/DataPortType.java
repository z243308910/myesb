package com.echounion.boss.soushipping.sehedule.webservice;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.echounion.boss.soushipping.sehedule.tool.xsd.Harbor;
import com.echounion.boss.soushipping.sehedule.tool.xsd.SailingScheduleRoutes;

@WebService(targetNamespace = "http://ws.apache.org/axis2", name = "dataPortType")
@XmlSeeAlso({ObjectFactory.class, ObjectFactory.class})
public interface DataPortType {

    @Oneway
    @Action(input = "urn:main")
    @RequestWrapper(localName = "main", targetNamespace = "http://ws.apache.org/axis2", className = "org.apache.ws.axis2.Main")
    @WebMethod(action = "urn:main")
    public void main(
        @WebParam(name = "args", targetNamespace = "http://ws.apache.org/axis2")
        java.util.List<java.lang.String> args
    );

    @WebResult(name = "return", targetNamespace = "http://ws.apache.org/axis2")
    @Action(input = "urn:findSailingScheduleRoutes", output = "urn:findSailingScheduleRoutesResponse")
    @RequestWrapper(localName = "findSailingScheduleRoutes", targetNamespace = "http://ws.apache.org/axis2", className = "org.apache.ws.axis2.FindSailingScheduleRoutes")
    @WebMethod(action = "urn:findSailingScheduleRoutes")
    @ResponseWrapper(localName = "findSailingScheduleRoutesResponse", targetNamespace = "http://ws.apache.org/axis2", className = "org.apache.ws.axis2.FindSailingScheduleRoutesResponse")
    public java.util.List<SailingScheduleRoutes> findSailingScheduleRoutes(
        @WebParam(name = "oringCode", targetNamespace = "http://ws.apache.org/axis2")
        java.lang.Integer oringCode,
        @WebParam(name = "destCode", targetNamespace = "http://ws.apache.org/axis2")
        java.lang.Integer destCode,
        @WebParam(name = "sailingFromDate", targetNamespace = "http://ws.apache.org/axis2")
        java.lang.String sailingFromDate,
        @WebParam(name = "carriers", targetNamespace = "http://ws.apache.org/axis2")
        java.lang.String carriers
    );

    @WebResult(name = "return", targetNamespace = "http://ws.apache.org/axis2")
    @Action(input = "urn:findHarborList", output = "urn:findHarborListResponse")
    @RequestWrapper(localName = "findHarborList", targetNamespace = "http://ws.apache.org/axis2", className = "org.apache.ws.axis2.FindHarborList")
    @WebMethod(action = "urn:findHarborList")
    @ResponseWrapper(localName = "findHarborListResponse", targetNamespace = "http://ws.apache.org/axis2", className = "org.apache.ws.axis2.FindHarborListResponse")
    public java.util.List<Harbor> findHarborList(
        @WebParam(name = "harborPreName", targetNamespace = "http://ws.apache.org/axis2")
        java.lang.String harborPreName,
        @WebParam(name = "pageSize", targetNamespace = "http://ws.apache.org/axis2")
        java.lang.Integer pageSize
    );
}
