package com.echounion.boss.soushipping.sehedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;
import com.echounion.boss.Tracking.ITrackingService;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.common.util.DataConvertUtils;
import com.echounion.boss.common.util.DateUtil;
import com.echounion.boss.common.util.SpringBeanUtil;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.entity.TrackingHistory;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.TrackingInfo;
import com.echounion.boss.soushipping.sehedule.entity.HarborDTO;
import com.echounion.boss.soushipping.sehedule.tool.xsd.Harbor;
import com.echounion.boss.soushipping.sehedule.tool.xsd.SailingScheduleRoutes;
import com.echounion.boss.soushipping.sehedule.webservice.DataPortType;

/**
 * CargoSmart 船期接口
 * @author 胡礼波
 * 2013-10-14 上午11:59:39
 */
public class SouShipingSchedule{

	private Logger logger=Logger.getLogger(SouShipingSchedule.class);
	private static final QName SERVICE_NAME = new QName("http://ws.apache.org/axis2", "data");
	
	public String wsdl;

	private SouShippingSoapHeader soapHeader;
    
	public String doTrackScheduleRoutes(HttpServletRequest request){
		
        String resultJson = null;
        TrackingInfo info=getTrackingInfo(request);
 		try {
 			resultJson = JsonUtil.toJsonStringFilterPropter(info).toJSONString();
 			addTrackingHistory(resultJson,request.getServletContext());
 		} catch (Exception e) {
 			logger.info("船期信息出错" + e);
 			resultJson = JsonUtil.toJsonString(new TrackingInfo());
 		}
 		return resultJson;
	}
	
	private TrackingInfo getTrackingInfo(HttpServletRequest request)
	{
		String time=request.getParameter("time");
		int pol=DataConvertUtils.convertInteger(request.getParameter("pol"));
		int pod=DataConvertUtils.convertInteger(request.getParameter("pod"));
		String scac=request.getParameter("carrier");

		if(StringUtils.isBlank(time))
		{
			logger.warn("时间为空!");
			return null;
		}
		if(StringUtils.isBlank(scac))
		{
			logger.warn("船司为空!");
			return null;			
		}
		JaxWsProxyFactoryBean factory=getWsFactory();
		DataPortType service = (DataPortType)factory.create();
        
		List<SailingScheduleRoutes> dataList = service.findSailingScheduleRoutes(pol,pod,time,scac);

        TrackingInfo info =new TrackingInfo(7);
        info.setTitle("Vessel Tracking Result");
        info.addHeadTitle("Carrier");
        info.addHeadTitle("Vessel");
        info.addHeadTitle("voyage");
        info.addHeadTitle("Service");
        info.addHeadTitle("ETD");
        info.addHeadTitle("ETA");
        info.addHeadTitle("T/T");
        
        List<String> rowData=null;
        Date eta=null;
        Date etd=null;
        String dataStr=null;
        if(dataList!=null)
        {
	        for (SailingScheduleRoutes route:dataList) {
	        	rowData=new ArrayList<>();
	        	rowData.add(getNoNulltext(route.getCarrier().getValue()));
	        	rowData.add(getNoNulltext(route.getVessel().getValue()));
	        	rowData.add(getNoNulltext(route.getVoyage().getValue()));
	        	rowData.add(getNoNulltext(route.getService().getValue()));
	        	
	        	dataStr=route.getOrigdate().getValue();
	        	etd=DateUtil.parseStrDate(dataStr);
	        	rowData.add(getNoNulltext(route.getOrigdate().getValue()));
	        	
	        	dataStr=route.getDestdate().getValue();
	        	eta=DateUtil.parseStrDate(dataStr);
	        	rowData.add(dataStr);
	        	
	        	rowData.add(getNoNulltext(String.valueOf(DateUtil.getSubDays(etd,eta))));
	        	info.addRowDataList(rowData);
			}
        }
        return info;
	}

	private String getNoNulltext(String data)
	{
		return data==null?"":data;
	}
	
	/**
	 * 模糊查询城市
	 * @author 胡礼波
	 * 2013-10-30 上午10:02:29
	 * @param request
	 * @return
	 */
	public String findHarbor(HttpServletRequest request)
	{
		String cityName=request.getParameter("portName");
		int count=DataConvertUtils.convertInteger(request.getParameter("count"));
		if(StringUtils.isBlank(cityName))
		{
			logger.warn("城市名称为空!");
			return null;
		}
		
		JaxWsProxyFactoryBean factory=getWsFactory();
		DataPortType service = (DataPortType)factory.create();
		List<Harbor> listHarbor = service.findHarborList(cityName, count);
		
		if(listHarbor==null)
		{
			return null;
		}
		List<HarborDTO> list=new ArrayList<>();
		HarborDTO harborDTO=null;
		for (Harbor harbor : listHarbor) {
			harborDTO=new HarborDTO(
					harbor.getCityCode(),
					harbor.getCityName().getValue(),
					harbor.getCountryName().getValue(),
					harbor.getHarborName().getValue());
			list.add(harborDTO);
			
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
        factory.setServiceClass(DataPortType.class);  
        factory.setAddress(wsdl);
        factory.setServiceName(SERVICE_NAME);
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
		history.setSourceCode("soushipping");
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

	public SouShippingSoapHeader getSoapHeader() {
		return soapHeader;
	}

	public void setSoapHeader(SouShippingSoapHeader soapHeader) {
		this.soapHeader = soapHeader;
	}


	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}
}
