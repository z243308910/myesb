package com.echounion.boss.cargosmart.service;

import java.util.List;

import com.echounion.boss.cargosmart.entity.CargosmartBaseInfo;
import com.echounion.boss.cargosmart.entity.CargosmartCargoInfo;
import com.echounion.boss.cargosmart.entity.CargosmartContainerInfo;
import com.echounion.boss.cargosmart.entity.CargosmartEvent;
import com.echounion.boss.cargosmart.entity.CargosmartRouting;
import com.echounion.boss.entity.dto.TrackingInfo;

/**
 * 跟踪接口
 * @author 胡礼波
 * 2013-10-30 上午11:51:58
 */
public interface ICsTrackingService {

	/**
	 * 判断是否有重复数据存在--主要用于更新
	 * @author 胡礼波
	 * 2013-11-11 下午3:46:12
	 * @param billNoOrBookingNo
	 * @param containerNo
	 * @return
	 */
	public int getCountByNo(String billNoOrBookingNo,String containerNo);
	
	/**
	 * 保存跟踪信息
	 * @author 胡礼波
	 * 2013-10-30 上午11:54:56
	 * @param baseInf
	 * @param cargoInfo
	 * @param containerInfo
	 * @param listEvent
	 * @param routing
	 */
	public void addTrackingInfo(CargosmartBaseInfo baseInf,List<CargosmartCargoInfo> cargoInfoList,CargosmartContainerInfo containerInfo,List<CargosmartEvent> listEvent,CargosmartRouting routing);
	
	/**
	 * 更新跟踪信息
	 * @author 胡礼波
	 * 2013-10-30 上午11:54:56
	 * @param baseInf
	 * @param cargoInfo
	 * @param containerInfo
	 * @param listEvent
	 * @param routing
	 */
	public void updateTrackingInfo(CargosmartBaseInfo baseInf,List<CargosmartCargoInfo> cargoInfoList,CargosmartContainerInfo containerInfo,List<CargosmartEvent> listEvent,CargosmartRouting routing);
	
	/**
	 * 查询跟踪信息
	 * @author 胡礼波
	 * 2013-11-7 下午2:54:27
	 * @param type
	 * @param no
	 * @return
	 */
	public TrackingInfo getTrackingInfo(String type,String no);
}
