package com.echounion.boss.cargosmart.tracking;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.echounion.boss.cargosmart.entity.CargosmartBaseInfo;
import com.echounion.boss.cargosmart.entity.CargosmartCargoInfo;
import com.echounion.boss.cargosmart.entity.CargosmartContainerInfo;
import com.echounion.boss.cargosmart.entity.CargosmartEvent;
import com.echounion.boss.cargosmart.entity.CargosmartRouting;
import com.echounion.boss.cargosmart.service.ICsTrackingService;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.common.util.DataConvertUtils;
import com.echounion.boss.common.util.DateUtil;

/**
 * 解析CT文件
 * @author 胡礼波
 * 2013-10-29 下午2:24:13
 */
@Component("TrackingXmlParse")
public class TrackingXmlParse {
	
	private Logger logger=Logger.getLogger(TrackingXmlParse.class);
	
	@Autowired 
	private ICsTrackingService trackingService;

	/**
	 * 解析CT文件
	 * @author 胡礼波
	 * 2013-10-30 上午10:41:33
	 * @param file
	 * @throws Exception
	 */
	public void parse(File file) throws Exception
	{
		List<Element> list=getRootElement(file);
		for (Element root : list) {
			CargosmartBaseInfo baseInfo=parseBaseInfo(root);
			List<CargosmartEvent> listEvent=parseEvent(root);
			CargosmartRouting routing=parseRouting(root);
			CargosmartContainerInfo containerInfo=parseContainer(root);
			List<CargosmartCargoInfo> cargoInfoList=parseCargoInfo(root);
			int count=trackingService.getCountByNo(baseInfo.getBillNo(),containerInfo.getContainerNo());
			if(count<1)			//没有对应的数据
			{
				count=trackingService.getCountByNo(baseInfo.getBookingNo(),containerInfo.getContainerNo());	
			}
			if(count>0)			//有对应的数据则更新操作
			{
				baseInfo.setAdd(false);
			}else
			{
				baseInfo.setAdd(true);
			}
			if(baseInfo.isAdd())
			{
				trackingService.addTrackingInfo(baseInfo, cargoInfoList, containerInfo, listEvent, routing);			
			}else			//更新操作
			{
				trackingService.updateTrackingInfo(baseInfo, cargoInfoList, containerInfo, listEvent, routing);
			}			
		}
	}
	
	
	/**
	 * 解析主体信息
	 * @author 胡礼波
	 * 2013-10-29 下午2:43:46
	 * @param rootElement
	 * @return
	 */
	private CargosmartBaseInfo parseBaseInfo(Element rootElement)
	{
		CargosmartBaseInfo baseInfo=new CargosmartBaseInfo();
		
		parseGeneralInfo(baseInfo,rootElement);
		parseBookingInfo(baseInfo,rootElement);
		parseBLInfo(baseInfo, rootElement);
		parseVesselVoyageInfo(baseInfo, rootElement);
		
		return baseInfo;
	}
	
	/**
	 * 解析事件信息
	 * @author 胡礼波
	 * 2013-10-29 下午3:21:05
	 * @param rootElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<CargosmartEvent> parseEvent(Element rootElement)
	{
		Element element=rootElement.element("GeneralInfo");
		List<Element> listEvent=element.elements("Event");
		if(listEvent == null)
		{
			return null;
		}
		List<CargosmartEvent> list=new ArrayList<>();
		try
		{
			CargosmartEvent event=null;
			for (Element el : listEvent) {
				event=new CargosmartEvent();
				event.setEventCode(el.elementTextTrim("EventCode"));
				event.setEventDesc(el.elementTextTrim("EventDescription"));
				event.setEventTime(DateUtil.parseStrDateTime(el.elementTextTrim("EventStatusDate"),"yyyyMMddHHmmss"));
				String local=el.elementTextTrim("EventCityName")+" "+el.elementTextTrim("EventCountryName");
				event.setEventLocal(local);
				list.add(event);
			}
		}catch (Exception e) {
			logger.error("解析Event信息出错!",e);
		}
		return list;
	}
	
	
	/**
	 * 解析Routing信息
	 * @author 胡礼波
	 * 2013-10-29 下午3:30:39
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CargosmartRouting parseRouting(Element rootElement)
	{
		Element element=rootElement.element("ShipmentDetails");
		element=element.element("RouteInformation");			//获得Routing信息节点
		if(element==null)
		{
			logger.warn("RouteInformation节点不能存在,解析RouteInformation结束!");
			return null;
		}
		List<Element> list=element.elements("Locations");
		if(list==null)
		{
			return null;
		}
		CargosmartRouting routing=new CargosmartRouting();
		try
		{
			for (Element el : list) {
				String code=el.elementTextTrim("FunctionCode");
				Element localEl=el.element("LocationDetails");
				
				String cityName=localEl.elementTextTrim("CityName");
				cityName=cityName==null?"":cityName;
				
				String county=localEl.elementTextTrim("County");
				county=county==null?"":county;
				
				String province=localEl.elementTextTrim("StateProvince");
				province=province==null?"":province;
				
				String country=localEl.elementTextTrim("CountryName");
				country=country==null?"":country;
				
				String local=cityName+" "+county +" "+province+" "+country;
				if("POR".equalsIgnoreCase(code))
				{
					routing.setPor(local);
				}else if("POD".equalsIgnoreCase(code))
				{
					routing.setPod(local);
				}else if("POL".equalsIgnoreCase(code))
				{
					routing.setPol(local);
				}else if("FND".equalsIgnoreCase(code))
				{
					routing.setFnd(local);
				}
			}
		}catch (Exception e) {
			logger.error("解析Routing信息出错!",e);
		}
		return routing;	
	}
	
	/**
	 * 解析箱型
	 * @author 胡礼波
	 * 2013-10-30 上午11:04:23
	 * @param rootElement
	 * @return
	 */
	private CargosmartContainerInfo parseContainer(Element rootElement)
	{
		Element element=rootElement.element("ShipmentDetails");
		element=element.element("ContainerInfo");			//获得箱型信息节点
		if(element==null)
		{
			logger.warn("ContainerInfo节点不能存在,解析ContainerInfo结束!");
			return null;
		}
		
		CargosmartContainerInfo containerInfo=new CargosmartContainerInfo();
		try
		{
			Element sealElement=element.element("SealNumber");
			if(sealElement!=null)
			{
				String sealNumber=sealElement.getTextTrim();
				String sealType=sealElement.attributeValue("Type");
				containerInfo.setSealNumber(sealNumber);
				containerInfo.setSealType(sealType);
			}
			
			element=element.element("Container");
			if(element!=null)
			{
				Element containerElement=element.element("ContainerNumber");
				String containerNo=containerElement.getText();
				String checkDidit=containerElement.attributeValue("CheckDigit");
				if(!StringUtils.isBlank(checkDidit))
				{
					containerNo=containerNo+checkDidit;
				}
				containerInfo.setContainerNo(containerNo);
				containerInfo.setSizeType(element.elementTextTrim("ISOSizeType"));
			}
		}catch (Exception e) {
			logger.error("解析ContainerInfo信息出错!",e);
		}
		return containerInfo;
	}
	
	
	/**
	 * 解析货物信息
	 * @author 胡礼波
	 * 2013-10-30 上午11:42:12
	 * @param rootElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<CargosmartCargoInfo> parseCargoInfo(Element rootElement)
	{
		Element element=rootElement.element("ShipmentDetails");
		List<Element> cargoInfoEls=element.elements("CargoInfo");			//获得订舱信息节点
		if(cargoInfoEls==null)
		{
			logger.warn("CargoInfo节点不能存在,解析CargoInfo结束!");
			return null;
		}
		
		CargosmartCargoInfo cargoInfo=null;
		List<CargosmartCargoInfo> list=new ArrayList<>();
		try
		{
			for (Element e : cargoInfoEls) {
				cargoInfo=new CargosmartCargoInfo();
				Element el=e.element("Cargo");
				if(el!=null)
				{
					cargoInfo.setCargoNo(el.elementTextTrim("ItemNo"));
					cargoInfo.setCargoDesc(el.elementTextTrim("CargoDescription"));
				}
				el=e.element("MarksAndNo");
				if(el!=null)
				{
					List<Element> els=el.elements("MarksandNumbers");
					List<String> maskNos=new ArrayList<>();
					for (Element tmpel:els) {
						if(tmpel==null)
						{
							continue;
						}
						maskNos.add(tmpel.getText());
					}
					String maskNo=null;
					if(!CollectionUtils.isEmpty(maskNos))
					{
						maskNo=JsonUtil.toJsonStringFilterPropterForArray(maskNos).toJSONString();		//Json的形式存放MaskNo
					}
					cargoInfo.setMaskNo(maskNo);
				}
				list.add(cargoInfo);
			}
		}catch (Exception e) {
			logger.error("解析CargoInfo信息出错!",e);
		}
		return list;
	}
	
	/**
	 * 解析普通的信息
	 * @author 胡礼波
	 * 2013-10-29 下午3:18:35
	 * @param baseInfo
	 * @param rootElement
	 */
	private void parseGeneralInfo(CargosmartBaseInfo baseInfo,Element rootElement)
	{
		try
		{
			Element element=rootElement.element("GeneralInfo");
			String carrier=element.elementTextTrim("SCAC");
			baseInfo.setCarrier(carrier);
		}catch (Exception e) {
			logger.error("解析GeneralInfo信息出错!",e);
		}
	}
	
	/**
	 * 解析订舱信息
	 * @author 胡礼波
	 * 2013-10-29 下午2:45:54
	 * @param baseInfo
	 * @param rootElement
	 * @return
	 */
	private void parseBookingInfo(CargosmartBaseInfo baseInfo,Element rootElement)
	{
		Element element=rootElement.element("ShipmentDetails");
		element=element.element("BookingInfo");			//获得订舱信息节点
		if(element==null)
		{
			logger.warn("BookingInfo节点不能存在,解析Booking结束!");
			return;
		}
		try
		{
			String bookingNo=element.elementTextTrim("BookingNumber");
			int numberPackage=DataConvertUtils.convertInteger(element.elementTextTrim("NumberofPackage"));
			String packageType=element.elementTextTrim("PackageType");
			
			baseInfo.setBookingNo(bookingNo);
			baseInfo.setNumberPackage(numberPackage);
			baseInfo.setPackageType(packageType);
		}catch (Exception e) {
			logger.error("解析Booking信息出错!",e);
		}
	}
	
	/**
	 * 解析提单信息
	 * @author 胡礼波
	 * 2013-10-29 下午2:46:33
	 * @param baseInfo
	 * @param rootElement
	 * @return
	 */
	private void parseBLInfo(CargosmartBaseInfo baseInfo,Element rootElement)
	{
		Element element=rootElement.element("ShipmentDetails");
		element=element.element("BLInfo");			//获得订舱信息节点
		if(element==null)
		{
			logger.warn("BLInfo节点不能存在,解析BLInfo结束!");
			return;
		}
		try
		{
		String billNo=element.elementTextTrim("BillofladingNumber");
		Date blIssueDate=DateUtil.parseStrDateTime(element.elementTextTrim("BLIssueDate"),"yyyyMMddHHmmss");
		
		baseInfo.setBillNo(billNo);
		baseInfo.setBlIssueDate(blIssueDate);
		}catch (Exception e) {
			logger.error("解析BLInfo信息出错!",e);
		}
	}
	
	/**
	 * 解析船名航次信息
	 * @author 胡礼波
	 * 2013-10-29 下午2:47:18
	 * @param baseInfo
	 * @param rootElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void parseVesselVoyageInfo(CargosmartBaseInfo baseInfo,Element rootElement)
	{
		Element element=rootElement.element("ShipmentDetails");
		element=element.element("VesselVoyageInformation");			//获得订舱信息节点
		if(element==null)
		{
			logger.warn("VesselVoyageInformation节点不能存在,解析VesselVoyageInformation结束!");
			return;
		}
		List<Element> list=element.elements("PortDetails");
		for (Element subEl : list) {
			element=subEl;
			if(element == null)
			{
				logger.warn("PortDetails节点不能存在,解析PortDetails结束!");
				continue;
			}
			element=element.element("VesselVoyage");
			if(element == null)
			{
				logger.warn("VesselVoyage节点不能存在,解析VesselVoyage结束!");
				continue;
			}
			
			try
			{
				Element serviceCodeElement=element.element("ServiceName");
				if(serviceCodeElement!=null)
				{
					String serviceCode=serviceCodeElement.attributeValue("Code");
					baseInfo.setServiceCode(serviceCode);
				}
				
				element=element.element("VesselInformation");
				if(element == null)
				{
					logger.warn("VesselInformation节点不能存在,解析VesselInformation结束!");
					continue;
				}
				
				String vesselName=element.elementTextTrim("VesselName");
				String voyageNumber=element.elementTextTrim("VesselCode");
				
				baseInfo.setVesselName(vesselName);
				baseInfo.setVoyageNumber(voyageNumber);
				break;
			}catch (Exception e) {
				logger.error("解析VesselVoyage信息出错!",e);
			}
		}
	}
	
	/**
	 * 获得根节点--CargoTracking
	 * @author 胡礼波
	 * 2013-10-29 下午2:41:58
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Element> getRootElement(File file) throws Exception
	{
		SAXReader reader=new SAXReader();
		Document doc=reader.read(file);
		Element root=doc.getRootElement();
		List<Element> list=root.elements("CargoTracking");
		return list;
	}
}
