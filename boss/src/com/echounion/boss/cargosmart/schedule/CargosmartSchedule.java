package com.echounion.boss.cargosmart.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;
import com.echounion.boss.Tracking.ITrackingService;
import com.echounion.boss.cargosmart.schedule.webservice.FindCityListElement;
import com.echounion.boss.cargosmart.schedule.webservice.FindCityListResponseElement;
import com.echounion.boss.cargosmart.schedule.webservice.FindSailingScheduleRoutesElement;
import com.echounion.boss.cargosmart.schedule.webservice.FindSailingScheduleRoutesResponseElement;
import com.echounion.boss.cargosmart.schedule.webservice.SailingScheduleSearchCriteria;
import com.echounion.boss.cargosmart.service.CargosmartService;
import com.echounion.boss.cargosmart.schedule.ssm.types.common.CarrierType;
import com.echounion.boss.cargosmart.schedule.ssm.types.common.CityListType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.OceanComponentType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.OceanLeg;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.POLType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.ParentCityKeyType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.SailingScheduleServiceType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.SailingSchedulesResultDTO;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.SearchCriteria;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.SearchResultRecord;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.VesselType;
import com.echounion.boss.cargosmart.schedule.ssm.types.sailingschedule.VoyageType;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.common.util.DateUtil;
import com.echounion.boss.common.util.SpringBeanUtil;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.entity.TrackingHistory;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.TrackingInfo;

/**
 * CargoSmart 船期接口
 * @author 胡礼波
 * 2013-10-14 上午11:59:39
 */
public class CargosmartSchedule{

	private Logger logger=Logger.getLogger(CargosmartSchedule.class);
	
	public String wsdl;

	private CargosmartSoapHeader soapHeader;
    
	public String doTrack(HttpServletRequest request){
		
		String startTime=request.getParameter("startTime");//"2011-09-18";
		String endTime=request.getParameter("endTime");//"2012-10-08";
		String pol=request.getParameter("pol");	//"HKHKG";
		String pod=request.getParameter("pod");	//"SGSIN";
		String scac=request.getParameter("carrier");
		String groupBy=request.getParameter("groupBy");
		if(StringUtils.isBlank(pol))
		{
			logger.warn("POL为空!");
			return "";
		}
		if(StringUtils.isBlank(pod))
		{
			logger.warn("POD为空!");
			return "";
		}
		if(StringUtils.isBlank(startTime))
		{
			logger.warn("开始时间为空!");
			return "";
		}
		if(StringUtils.isBlank(endTime))
		{
			logger.warn("结束时间为空!");
			return "";
		}
		if(StringUtils.isBlank(scac))
		{
			logger.warn("船司为空!");
			return "";			
		}
		JaxWsProxyFactoryBean factory=getWsFactory();
		CargosmartService service = (CargosmartService)factory.create();
        FindSailingScheduleRoutesElement params=new FindSailingScheduleRoutesElement();
        
        SailingScheduleSearchCriteria value=new SailingScheduleSearchCriteria();

        SearchCriteria c=new SearchCriteria();
        c.setSailingFromDate(startTime);
        c.setSailingToDate(endTime);
        String[] scacs=scac.split(",");
        List<String> scacList=Arrays.asList(scacs);
        c.setScac(scacList);
         
        ParentCityKeyType ori=new ParentCityKeyType();
        ori.setUNLOCODE(pol);
        ParentCityKeyType pt=new ParentCityKeyType();
        pt.setUNLOCODE(pod);

        c.setOrign(ori);
        c.setDestination(pt);
        if(groupBy!=null && groupBy.equals("1"))
        {
        	c.setGroupByCarrierIndicator(1);
        }
        
        value.setSearchCriteria(c);

        params.setSailingScheduleSearchCriteria(value);
        
        FindSailingScheduleRoutesResponseElement result = service.findSailingScheduleRoutes(params);
        SailingSchedulesResultDTO dto=result.getResult();
        List<SearchResultRecord> list=dto.getSearchResultRecord();
        OceanComponentType oceanInfo=null;
        List<OceanLeg> legList=null;
        
        TrackingInfo info =new TrackingInfo(6);
        info.setTitle("Vessel Tracking Result");
        info.addHeadTitle("ETD");
        info.addHeadTitle("Carrier");
        info.addHeadTitle("Vessel/Voyage");
        info.addHeadTitle("Service");
        info.addHeadTitle("ETA");
        info.addHeadTitle("T/T");
        
        List<String> rowData=null;
        
        for (SearchResultRecord record : list) {
    		 rowData=new ArrayList<>();
        	 oceanInfo=record.getOceanComponent();
        	 legList=oceanInfo.getOceanleg();
             if(CollectionUtils.isEmpty(legList))
             {
            	 continue;
             }
             OceanLeg oFirst=legList.get(0);				//第一个
             OceanLeg OLast=legList.get(legList.size()-1);	//最后一个
             
             POLType polType=oFirst.getPOL();
    		 String rowDataStr=null;
    		 Date date=null;
    		 if(polType!=null)
    		 {
    			 if(polType.getETD()!=null)
    			 {
    				 date=polType.getETD().toGregorianCalendar().getTime();
    				 rowDataStr=DateUtil.formatterDate(date);
    			 }
    		 }
    		 rowData.add(rowDataStr);
    		 rowDataStr=null;
			///////////////////////ETD/////////////////////////////////////
    		 
    		 CarrierType cr=OLast.getCarrier();
    		 if(cr!=null)
    		 {
    			 rowDataStr=cr.getFullName();
    		 }
    		 rowData.add(rowDataStr);
    		 rowDataStr=null;
 			///////////////////////船司名称/////////////////////////////////////    		 
    		 
    		 VesselType vessel=OLast.getVessel();
    		 VoyageType voyage=OLast.getVoyage();
    		 if(vessel!=null)
    		 {
    			 rowDataStr=vessel.getVesselName();
    			 if(voyage!=null)
    			 {
    				 rowDataStr=rowDataStr+"/"+voyage.getExternalVoyageNumber();
    			 }
    		 }
    		 rowData.add(rowDataStr);
    		 rowDataStr=null;
 			/////////////////////////船名航次///////////////////////////////////    		 
    		 
			SailingScheduleServiceType sst=OLast.getService();
			if(sst!=null)
			 {
				 rowDataStr=sst.getServiceCode(); 
			 }
     		rowData.add(rowDataStr);
     		rowDataStr=null;
			////////////////////////ServiceCode////////////////////////////////////     		

	   		date=record.getETAatFND().toGregorianCalendar().getTime();
	   		rowData.add(DateUtil.formatterDate(date));
			////////////////////////ETA////////////////////////////////////	   		 
    		 
	   		 rowData.add(String.valueOf(record.getEstimatedTransitTimeInDays()));
			/////////////////////////需要时间Days///////////////////////////////////   		 
	   		 
	   		 info.addRowDataList(rowData);
		}

         String resultJson = null;
 		try {
 			resultJson = JsonUtil.toJsonStringFilterPropter(info).toJSONString();
 			addTrackingHistory(resultJson,request.getServletContext());
 		} catch (Exception e) {
 			logger.info("采集船期信息出错" + e);
 			resultJson = JsonUtil.toJsonString(new TrackingInfo());
 		}         
		return resultJson;
	}
	
	
	/**
	 * 模糊查询城市
	 * @author 胡礼波
	 * 2013-10-30 上午10:02:29
	 * @param request
	 * @return
	 */
	public String findPort(HttpServletRequest request)
	{
		String cityName=request.getParameter("portName");
		if(StringUtils.isBlank(cityName))
		{
			logger.warn("城市名称为空!");
			return null;
		}
		
		JaxWsProxyFactoryBean factory=getWsFactory();
		CargosmartService service = (CargosmartService)factory.create();
		FindCityListElement params=new FindCityListElement();
		params.setCityName(cityName);
		FindCityListResponseElement result = service.findCityList(params);
		if(result==null)
		{
			return null;
		}
		List<CityListType> list=result.getResult().getCity();
		Iterator<CityListType> it=list.iterator();
		CityListType cityType=null;
		while(it.hasNext())
		{
			cityType=it.next();
			if(StringUtils.isBlank(cityType.getUNLOCODE()))		//去掉代号为空的数据
			{
				it.remove();
			}
		}
		String resultJson=JsonUtil.toJsonStringFilterPropterForArray(list).toJSONString();
		return resultJson;
	}
	
	/**
	 * 获得WEBSERVICE代理工厂
	 * @author 胡礼波
	 * 2013-10-17 上午10:41:00
	 * @return
	 */
	private JaxWsProxyFactoryBean getWsFactory()
	{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setOutInterceptors(getInterceptor());
        factory.setServiceClass(CargosmartService.class);  
        factory.setAddress(wsdl);
        return factory;
	}
	
	/**
	 * 得到WEBSERVICE拦截器
	 * @author 胡礼波
	 * 2013-10-17 上午10:41:39
	 * @return
	 */
	private List<Interceptor<? extends Message>> getInterceptor()
	{
		List<Interceptor<? extends Message>> interceptorList =new ArrayList<>();
	   	 
   	 	// 添加soap header 
   	 	interceptorList.add(soapHeader);
        // 添加soap消息日志打印
        interceptorList.add(new org.apache.cxf.interceptor.LoggingOutInterceptor());
        return interceptorList;
	}
	
	/**
	 * 日志记录
	 * @author 胡礼波
	 * 2013-10-17 上午10:33:53
	 * @param jsonStr
	 */
	private void addTrackingHistory(String jsonStr,ServletContext ctx)
	{
		TrackingHistory history = assembleHistory();
		history.setUrl(wsdl);
		history.setContent(jsonStr);
		history.setStatus(Constant.SUCCESS);
		history.setSourceCode("cargosmart");
		try
		{
		ITrackingService trackingService=(ITrackingService)SpringBeanUtil.getBean(ctx,ITrackingService.class);
		trackingService.addTrackingHistory(history);
		}catch (Exception e) {
			logger.info("记录船期信息日志出错" + e);
		}
	}
	
	/**
	 * 组装采集日志记录对象
	 * @author 胡礼波
	 * 2012-11-5 上午10:57:38
	 * @return
	 */
	private TrackingHistory assembleHistory()
	{
		Caller caller=(Caller)ThreadLocalUtil.getData();
		TrackingHistory history = new TrackingHistory();
		history.setSoftId(caller.getSoftId());
		history.setTime(new Date());
		return history;
	}

	public CargosmartSoapHeader getSoapHeader() {
		return soapHeader;
	}

	public void setSoapHeader(CargosmartSoapHeader soapHeader) {
		this.soapHeader = soapHeader;
	}
	
	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}
}
