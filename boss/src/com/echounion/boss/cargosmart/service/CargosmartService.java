package com.echounion.boss.cargosmart.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://www.cargosmart.com/sailingschedule", name = "SailingScheduleWebService")
@XmlSeeAlso({com.echounion.boss.cargosmart.schedule.ssm.types.common.ObjectFactory.class, com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.ObjectFactory.class, com.echounion.boss.cargosmart.schedule.ssm.types.ObjectFactory.class, com.echounion.boss.cargosmart.schedule.webservice.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CargosmartService {
    
    @WebResult(name = "findVesselInfoResponseElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/", partName = "parameters")
    @WebMethod(action = "http://www.cargosmart.com/sailingschedule/findVesselInfo")
    public com.echounion.boss.cargosmart.schedule.webservice.FindVesselInfoResponseElement findVesselInfo(
        @WebParam(partName = "parameters", name = "findVesselInfoElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/")
        com.echounion.boss.cargosmart.schedule.webservice.FindVesselInfoElement parameters
    );
	
  @WebResult(name = "findCarrierResponseElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/", partName = "parameters")
    @WebMethod(action = "http://www.cargosmart.com/sailingschedule/findCarrier")
    public com.echounion.boss.cargosmart.schedule.webservice.FindCarrierResponseElement findCarrier(
        @WebParam(partName = "parameters", name = "findCarrierElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/")
        com.echounion.boss.cargosmart.schedule.webservice.FindCarrierElement parameters
    );
  
  @WebResult(name = "findCityListResponseElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/", partName = "parameters")
  @WebMethod(action = "http://www.cargosmart.com/sailingschedule/findCityList")
  public com.echounion.boss.cargosmart.schedule.webservice.FindCityListResponseElement findCityList(
      @WebParam(partName = "parameters", name = "findCityListElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/")
      com.echounion.boss.cargosmart.schedule.webservice.FindCityListElement parameters
  );
  
  @WebResult(name = "findSailingScheduleRoutesResponseElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/", partName = "parameters")
  @WebMethod(action = "http://www.cargosmart.com/sailingschedule/findSailingScheduleRoutes")
  public com.echounion.boss.cargosmart.schedule.webservice.FindSailingScheduleRoutesResponseElement findSailingScheduleRoutes(
      @WebParam(partName = "parameters", name = "findSailingScheduleRoutesElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/")
      com.echounion.boss.cargosmart.schedule.webservice.FindSailingScheduleRoutesElement parameters
  );
  
  @WebResult(name = "findFacilityInfoResponseElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/", partName = "parameters")
  @WebMethod(action = "http://www.cargosmart.com/sailingschedule/findFacilityInfo")
  public com.echounion.boss.cargosmart.schedule.webservice.FindFacilityInfoResponseElement findFacilityInfo(
      @WebParam(partName = "parameters", name = "findFacilityInfoElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/")
      com.echounion.boss.cargosmart.schedule.webservice.FindFacilityInfoElement parameters
  );
  
  @WebResult(name = "findServiceLoopInfoResponseElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/", partName = "parameters")
  @WebMethod(action = "http://www.cargosmart.com/sailingschedule/findServiceLoopInfo")
  public com.echounion.boss.cargosmart.schedule.webservice.FindServiceLoopInfoResponseElement findServiceLoopInfo(
      @WebParam(partName = "parameters", name = "findServiceLoopInfoElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/")
      com.echounion.boss.cargosmart.schedule.webservice.FindServiceLoopInfoElement parameters
  );
  
  @WebResult(name = "findServiceResponseElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/", partName = "parameters")
  @WebMethod(action = "http://www.cargosmart.com/sailingschedule/findService")
  public com.echounion.boss.cargosmart.schedule.webservice.FindServiceResponseElement findService(
      @WebParam(partName = "parameters", name = "findServiceElement", targetNamespace = "http://webservice.sailingschedule.cargosmart.com/")
      com.echounion.boss.cargosmart.schedule.webservice.FindServiceElement parameters
  );
}
