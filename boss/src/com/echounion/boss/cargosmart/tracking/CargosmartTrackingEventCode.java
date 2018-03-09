package com.echounion.boss.cargosmart.tracking;

import java.util.HashMap;
import java.util.Map;

/**
 * Cargosmart Tracking Event Code
 * @author 胡礼波
 * 2013-10-28 下午5:07:29
 */
public class CargosmartTrackingEventCode {

	public static Map<String,String> mapEventCode=new HashMap<String, String>();
	static
	{
		mapEventCode.put("EE","Empty Container Picked Up*");
		mapEventCode.put("Y1","Container Available");
		mapEventCode.put("AF","Actual Door Pickup");
		mapEventCode.put("OA","Full Container Received by Carrier at Origin*");
		mapEventCode.put("X1","Full container received by carrier*");
		mapEventCode.put("X3","Container Repacked*");
		mapEventCode.put("X4","Container Vanned at Origin*");
		mapEventCode.put("X5","Container Devanned at Origin*");
		mapEventCode.put("AL","First Loaded on Rail Under Outbound");
		mapEventCode.put("RL","Departure from First Intermodal Hub*");
		mapEventCode.put("I","Arrival at First Port of Load*");
		mapEventCode.put("Z1","Last Deramp under Outbound");
		mapEventCode.put("AE","Loaded on Board at First Port of Load*");
		mapEventCode.put("W6","Loaded at Port*");
		mapEventCode.put("VD","First Vessel Departure");
		mapEventCode.put("X2","Vessel Departure");
		mapEventCode.put("Z2","Transhipment Vessel Arrival");
		mapEventCode.put("Z3","Loaded at Port of Transhipment*");
		mapEventCode.put("Z4","Discharged at Port of Transhipment*");
		mapEventCode.put("Z5","Transhipment Vessel Departure");
		mapEventCode.put("W7","Vessel Arrival at Port");
		mapEventCode.put("VA","Last Vessel Arrival");
		mapEventCode.put("UV","Discharged from Vessel at Last Port of Discharge*");
		mapEventCode.put("W8","Discharged from Vessel at Port of Discharge*");
		mapEventCode.put("Y7","Discharged*");
		mapEventCode.put("Y9","Container Picked up from Port of Discharge/Transhipment Port");
		mapEventCode.put("Z7","First Loaded on Rail Under Inbound");
		mapEventCode.put("Z6","Intermodal Departure from Last Port of Discharge*");
		mapEventCode.put("AR","Arrival at Last Intermodal Hub*");
		mapEventCode.put("UR","Last Deramp Under Inbound");
		mapEventCode.put("X6","Container Vanned at Destination*");
		mapEventCode.put("X7","Container Devanned at Destination*");
		mapEventCode.put("X8","Container Transferred*");
		mapEventCode.put("W3","Equipment Delayed due to Transportation");
		mapEventCode.put("Y3","Loaded on Rail");
		mapEventCode.put("Y5","Loaded");
		mapEventCode.put("Y4","Rail Move");
		mapEventCode.put("Y2","Arrival at Intermodal Hub by Rail");
		mapEventCode.put("W4","Arrived at facility");
		mapEventCode.put("W2","Gate In Full at Inland Terminal");
		mapEventCode.put("W1","Gate Out Full at Inland Terminal");
		mapEventCode.put("AM","Loaded on Truck");
		mapEventCode.put("W5","Departed from facility");
		mapEventCode.put("PA","Customs Hold*");
		mapEventCode.put("X9","Carrier Held");
		mapEventCode.put("NO","Freight Charges Settled");
		mapEventCode.put("CR","Carrier Released*");
		mapEventCode.put("CT","Customs Released*");
		mapEventCode.put("CU","Carrier and Customs Released*");
		mapEventCode.put("Z8","Picked up at Final Destination for Delivery*");
		mapEventCode.put("D","Actual Door Delivery");
		mapEventCode.put("RD","Empty Container Returned to Carrier at Destination*");
	}
}
