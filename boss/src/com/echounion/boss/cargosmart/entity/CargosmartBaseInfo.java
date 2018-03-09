package com.echounion.boss.cargosmart.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 胡礼波
 * 2013-10-29 下午2:13:58
 */
public class CargosmartBaseInfo implements Serializable{

	/**
	 * @author 胡礼波
	 * 2013-10-29 下午2:17:39
	 */
	private static final long serialVersionUID = -7014255276307798492L;
	
	private int id;
	private boolean add;			//该标志判断该信息是新增内容还是update内容--不映射到数据库
	private String vesselName;		//船名
	private String voyageNumber;	//航次
	private String serviceCode;		//服务号
	private String billNo;			//提单号
	private String bookingNo;		//订舱号
	private String carrier;			//船司
	private int numberPackage;		//包装数量
	private String packageType;		//包装类型
	private Date blIssueDate;		//提单确认时间
	private Date addTime;			//添加时间
	
	private List<CargosmartCargoInfo> listCargoInfo;
	private CargosmartRouting routingInfo;
	private CargosmartContainerInfo containerInfo;			//保存用到
	private List<CargosmartContainerInfo> containerList;	//查询用到
	private List<CargosmartEvent> listEvent;
	
	
	public CargosmartContainerInfo getContainerInfo() {
		return containerInfo;
	}
	public void setContainerInfo(CargosmartContainerInfo containerInfo) {
		this.containerInfo = containerInfo;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public List<CargosmartContainerInfo> getContainerList() {
		return containerList;
	}
	public void setContainerList(List<CargosmartContainerInfo> containerList) {
		this.containerList = containerList;
	}
	public CargosmartRouting getRoutingInfo() {
		return routingInfo;
	}
	public List<CargosmartCargoInfo> getListCargoInfo() {
		return listCargoInfo;
	}
	public void setListCargoInfo(List<CargosmartCargoInfo> listCargoInfo) {
		this.listCargoInfo = listCargoInfo;
	}
	public void setRoutingInfo(CargosmartRouting routingInfo) {
		this.routingInfo = routingInfo;
	}
	public List<CargosmartEvent> getListEvent() {
		return listEvent;
	}
	public void setListEvent(List<CargosmartEvent> listEvent) {
		this.listEvent = listEvent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
	public String getVesselName() {
		return vesselName;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}
	public String getVoyageNumber() {
		return voyageNumber;
	}
	public void setVoyageNumber(String voyageNumber) {
		this.voyageNumber = voyageNumber;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public int getNumberPackage() {
		return numberPackage;
	}
	public void setNumberPackage(int numberPackage) {
		this.numberPackage = numberPackage;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public Date getBlIssueDate() {
		return blIssueDate;
	}
	public void setBlIssueDate(Date blIssueDate) {
		this.blIssueDate = blIssueDate;
	}
}
