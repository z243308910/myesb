package com.echounion.boss.persistence.tracking;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.echounion.boss.cargosmart.entity.CargosmartBaseInfo;
import com.echounion.boss.cargosmart.entity.CargosmartCargoInfo;
import com.echounion.boss.cargosmart.entity.CargosmartContainerInfo;
import com.echounion.boss.cargosmart.entity.CargosmartEvent;
import com.echounion.boss.cargosmart.entity.CargosmartRouting;

/**
 * cs货物跟踪
 * @author 胡礼波
 * 2013-10-30 上午11:57:57
 */
public interface CargosmartTrackingMapper {

	public int addBaseInfo(CargosmartBaseInfo baseInfo);
	public int editBaseInfo(CargosmartBaseInfo baseInfo);
	
	public int addCargoInfo(@Param("list")List<CargosmartCargoInfo> listCargoInfo);
	public int editCargoInfo(@Param("list")List<CargosmartCargoInfo> cargoInfo);
	public int delCargoInfo(int baseId);
	
	public int addContainerInfo(CargosmartContainerInfo containerInfo);
	public int editContainerInfo(CargosmartContainerInfo containerInfo);
	
	public int addRoutingInfo(CargosmartRouting routing);
	public int editRoutingInfo(CargosmartRouting routing);
	
	public int addEvents(@Param("list") List<CargosmartEvent> list);
	
	/**
	 * 根据单号（订舱号、提单号）和箱号查询基础信息
	 * @author 胡礼波
	 * 2013-10-31 下午3:14:33
	 * @param bNo
	 * @param containerNo
	 * @return
	 */
	public CargosmartBaseInfo getBaseInfo(@Param("bNo")String bNo,@Param("containerNo")String containerNo);
	
	public int getCountByNo(@Param("bNo")String bNo,@Param("containerNo")String containerNo);
	
	public List<CargosmartCargoInfo> getCargoInfoByBaseId(int baseId);
	public CargosmartRouting getRoutingInfoByBaseId(int baseId);
	public CargosmartContainerInfo getContainerByBaseId(int baseId);
	public List<CargosmartContainerInfo> getContainerByNo(String no);
	public List<CargosmartEvent> getEventsByBaseId(int baseId);
	
	public CargosmartBaseInfo getBaseInfoByContainerNo(String containerNo);
	public CargosmartBaseInfo getBaseInfoByBillNo(String billNo);
	public CargosmartBaseInfo getBaseInfoByBookingNo(String bookingNo);
}
