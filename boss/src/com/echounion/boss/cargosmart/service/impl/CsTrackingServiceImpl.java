package com.echounion.boss.cargosmart.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.cargosmart.entity.CargosmartBaseInfo;
import com.echounion.boss.cargosmart.entity.CargosmartCargoInfo;
import com.echounion.boss.cargosmart.entity.CargosmartContainerInfo;
import com.echounion.boss.cargosmart.entity.CargosmartEvent;
import com.echounion.boss.cargosmart.entity.CargosmartRouting;
import com.echounion.boss.cargosmart.service.ICsTrackingService;
import com.echounion.boss.common.util.DateUtil;
import com.echounion.boss.core.security.annotation.ActionModel;
import com.echounion.boss.entity.dto.TrackingInfo;
import com.echounion.boss.persistence.tracking.CargosmartTrackingMapper;

@Service("CargosmartTrackingServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="Cargosmart货物跟踪")
public class CsTrackingServiceImpl implements ICsTrackingService {

	private Logger logger=Logger.getLogger("TrackingServiceImpl");
	
	@Autowired
	private CargosmartTrackingMapper trackingMapper;
	
	@Override
	@Transactional
	@ActionModel(description="保存跟踪信息")
	public void addTrackingInfo(CargosmartBaseInfo baseInf,List<CargosmartCargoInfo> cargoInfoList,CargosmartContainerInfo containerInfo,List<CargosmartEvent> listEvent, CargosmartRouting routing) {
		int result=0;
		if(baseInf!=null)
		{
			result=trackingMapper.addBaseInfo(baseInf);
		}
		if(result>0)					//主单保存成功
		{
			try
			{
				if(cargoInfoList!=null && cargoInfoList.size()>0)
				{
					Iterator<CargosmartCargoInfo> it=cargoInfoList.iterator();
					CargosmartCargoInfo cInfo=null;
					while(it.hasNext())
					{
						cInfo=it.next();
						if(cInfo!=null)
						{
							cInfo.setBaseId(baseInf.getId());
						}
					}
					trackingMapper.addCargoInfo(cargoInfoList);
				}
			}catch (Exception e) {
				logger.error("保存货物信息失败!",e);
			}
			try
			{
				if(containerInfo!=null)
				{
					containerInfo.setBaseId(baseInf.getId());
					trackingMapper.addContainerInfo(containerInfo);
				}
			}catch (Exception e) {
				logger.error("保存箱信息失败!",e);
			}
			try
			{
				if(routing!=null)
				{
					routing.setBaseId(baseInf.getId());
					trackingMapper.addRoutingInfo(routing);
				}
			}catch (Exception e) {
				logger.error("保存Routing信息失败!",e);
			}
			addEvent(baseInf.getId(),listEvent);
		}
	}

	
	@Override
	public void updateTrackingInfo(CargosmartBaseInfo baseInf,List<CargosmartCargoInfo> cargoInfoList,CargosmartContainerInfo containerInfo,List<CargosmartEvent> listEvent, CargosmartRouting routing) {
		CargosmartBaseInfo baseInfo=trackingMapper.getBaseInfo(baseInf.getBillNo(),containerInfo.getContainerNo());
		if(baseInfo==null)
		{
			baseInfo=trackingMapper.getBaseInfo(baseInf.getBookingNo(),containerInfo.getContainerNo());
		}
		if(baseInfo==null) 		//不存在 则新增 否则做update操作
		{
			addTrackingInfo(baseInf, cargoInfoList, containerInfo, listEvent, routing);
		}else
		{
				int baseId=baseInfo.getId();
				if(baseInf!=null)
				{
					baseInf.setId(baseId);
					try
					{
						trackingMapper.editBaseInfo(baseInf);
					}catch (Exception e) {logger.error("主单更新失败!",e);}
				}
	
				if(!CollectionUtils.isEmpty(cargoInfoList))
				{
					try
					{
						trackingMapper.delCargoInfo(baseId);		//先删除原始数据再添加
						Iterator<CargosmartCargoInfo> it=cargoInfoList.iterator();
						CargosmartCargoInfo tmp=null;
						while(it.hasNext())
						{
							tmp=it.next();
							if(tmp==null)
							{
								it.remove();
							}
							tmp.setBaseId(baseId);
						}
						trackingMapper.addCargoInfo(cargoInfoList);
					}catch (Exception e) {logger.error("更新货物信息失败!",e);}
				}
	
				if(containerInfo!=null)
				{
					try
					{
							containerInfo.setBaseId(baseId);
							CargosmartContainerInfo conInfo=trackingMapper.getContainerByBaseId(baseId);
							if(conInfo!=null)
							{
								containerInfo.setId(conInfo.getId());
								trackingMapper.editContainerInfo(containerInfo);							
							}else
							{
								trackingMapper.addContainerInfo(containerInfo);
							}
					}catch (Exception e) {logger.error("更新箱信息失败!",e);}			
				}
	
				if(routing!=null)
				{
					try
					{
							routing.setBaseId(baseId);
							CargosmartRouting rting=baseInfo.getRoutingInfo();
							if(rting!=null)
							{
								routing.setId(rting.getId());
								trackingMapper.editRoutingInfo(routing);							
							}else
							{
								trackingMapper.addRoutingInfo(routing);
							}
					}catch (Exception e) {logger.error("保存Routing信息失败!",e);}				
				}
				addEvent(baseId,listEvent);
			}
		}

	
	private void addEvent(int baseId,List<CargosmartEvent> listEvent)
	{
		try
		{
			if(listEvent!=null && listEvent.size()>0)
			{
				Iterator<CargosmartEvent> it=listEvent.iterator();
				CargosmartEvent event=null;
				while(it.hasNext())
				{
					event=it.next();
					if(event!=null)
					{
						event.setBaseId(baseId);
					}
				}
				trackingMapper.addEvents(listEvent);
			}
		}catch (Exception e) {
			logger.error("保存Event信息失败!",e);
		}
	}


	@Override
	public TrackingInfo getTrackingInfo(String type, String no) {
		CargosmartBaseInfo baseInfo=null;
		switch(type)
		{
			case TrackingAdapter.STYPE_CONTAINER:
				no=no.replaceAll("-","");
				baseInfo=trackingMapper.getBaseInfoByContainerNo(no);
				break;
			case TrackingAdapter.STYPE_BILL:
				baseInfo=trackingMapper.getBaseInfoByBillNo(no);
				break;
			case TrackingAdapter.STYPE_BOOKING:
				baseInfo=trackingMapper.getBaseInfoByBookingNo(no);
			break;
		}
		if(baseInfo==null)
		{
			return null;
		}
		TrackingInfo info=new TrackingInfo(9);
		List<TrackingInfo> subs=new ArrayList<>();
		info.setTitle("Tracking Result");
		info.addHeadTitle("billNo");info.addHeadTitle("bookingNo");
		info.addHeadTitle("carrier");info.addHeadTitle("serviceCode");
		info.addHeadTitle("vesselName");info.addHeadTitle("voyageNumber");
		info.addHeadTitle("numberPackage");info.addHeadTitle("packageType");
		info.addHeadTitle("blIssueDate");

		info.addRowData(baseInfo.getBillNo());info.addRowData(baseInfo.getBookingNo());
		info.addRowData(baseInfo.getCarrier());info.addRowData(baseInfo.getServiceCode());
		info.addRowData(baseInfo.getVesselName());info.addRowData(baseInfo.getVoyageNumber());
		info.addRowData(String.valueOf(baseInfo.getNumberPackage()));info.addRowData(baseInfo.getPackageType());
		info.addRowData(DateUtil.formatterDateTime(baseInfo.getBlIssueDate()));
		
		List<CargosmartContainerInfo> containerInfoList=trackingMapper.getContainerByNo(no);
		if(containerInfoList!=null)
		{
			TrackingInfo subInfo=new TrackingInfo(4);
			subInfo.setTitle("ContainerInfo");
			subInfo.addHeadTitle("containerNo");subInfo.addHeadTitle("sizeType");
			subInfo.addHeadTitle("sealNumber");subInfo.addHeadTitle("sealType");
			for (CargosmartContainerInfo containerInfo : containerInfoList) {
				if(containerInfo!=null)
				{
					subInfo.addRowData(containerInfo.getContainerNo());
					subInfo.addRowData(containerInfo.getSizeType());
					subInfo.addRowData(containerInfo.getSealNumber());
					subInfo.addRowData(containerInfo.getSealType());
				}			
			}
			subs.add(subInfo);
		}
		
		if(containerInfoList!=null)
		{
			TrackingInfo subInfo=new TrackingInfo(3);
			subInfo.setTitle("CargoInfo");
			subInfo.addHeadTitle("cargoNo");subInfo.addHeadTitle("maskNo");
			subInfo.addHeadTitle("cargoDesc");
			List<CargosmartCargoInfo> cargoList=null;
			for (CargosmartContainerInfo containerInfo : containerInfoList) {
				cargoList=trackingMapper.getCargoInfoByBaseId(containerInfo.getBaseId());	//查找每个箱子的货物
				if(cargoList==null)
				{
					continue;
				}
				for (CargosmartCargoInfo cInfo : cargoList) {
					subInfo.addRowData(cInfo.getCargoNo());
					subInfo.addRowData(cInfo.getMaskNo());
					subInfo.addRowData(cInfo.getCargoDesc());
				}
			}
			subs.add(subInfo);
		}
		
		
		CargosmartRouting routingInfo=baseInfo.getRoutingInfo();
		if(routingInfo!=null)
		{
			TrackingInfo subInfo=new TrackingInfo(4);
			subInfo.setTitle("RoutingInfo");
			subInfo.addHeadTitle("por");subInfo.addHeadTitle("pod");
			subInfo.addHeadTitle("pol");subInfo.addHeadTitle("fnd");
			
			subInfo.addRowData(routingInfo.getPor());
			subInfo.addRowData(routingInfo.getPod());
			subInfo.addRowData(routingInfo.getPol());
			subInfo.addRowData(routingInfo.getFnd());
			subs.add(subInfo);
		}
		
		List<CargosmartEvent> listEvent =baseInfo.getListEvent();
		if(listEvent!=null && listEvent.size()>0)
		{
			TrackingInfo subInfo=new TrackingInfo(4);
			subInfo.setTitle("Event");
			subInfo.addHeadTitle("eventCode");subInfo.addHeadTitle("eventDesc");
			subInfo.addHeadTitle("eventLocal");subInfo.addHeadTitle("eventTime");
			
			for (CargosmartEvent event : listEvent) {
				subInfo.addRowData(event.getEventCode());
				subInfo.addRowData(event.getEventDesc());
				subInfo.addRowData(event.getEventLocal());
				subInfo.addRowData(DateUtil.formatterDateTime(event.getEventTime()));
			}
			subs.add(subInfo);
		}
		info.addSubTrackingInfo(subs);
		return info;
	}


	@Override
	public int getCountByNo(String billNoOrBookingNo, String containerNo) {
		return trackingMapper.getCountByNo(billNoOrBookingNo, containerNo);
	}
}
