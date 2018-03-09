package com.echounion.boss.Tracking.hjs;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class HJSTracking extends AbstractTrack {

	Logger logger = Logger.getLogger(HJSTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		String type = String.valueOf(params.get(TrackingAdapter.STYPE));			//获得类型
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info = new TrackingInfo(4);
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				JSONObject data = JSON.parseObject(EntityUtils.toString(entity));
				if(type.equals(TrackingAdapter.STYPE_CONTAINER))
				{
					info = getContainerTrackingInfo(url,data,params);
				}
				else
				{
					info = getBLTrackingInfo(url, isPost, data);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error("HJS 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerTrackingInfo(String url, JSONObject data, Map<String, Object> params) throws Exception {
		TrackingInfo info = new TrackingInfo(9);
		JSONArray list = data.getJSONArray("list");
		if(!list.isEmpty())
		{
			JSONObject obj = list.getJSONObject(0);
			params.put("bkg_no", obj.getString("bkgNo"));
			params.put("cop_no", obj.getString("copNo"));
			params.put("f_cmd", "123");
			HttpClient client =getHttpClient();
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, true);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				data = JSON.parseObject(EntityUtils.toString(entity));
				list = data.getJSONArray("list");
				if(!list.isEmpty())
				{
					obj = list.getJSONObject(0);
					info.addHeadTitle("Container No.");
					info.addHeadTitle("Seal No.");
					info.addHeadTitle("Size");
					info.addHeadTitle("Event Date");
					info.addHeadTitle("Status");
					info.addHeadTitle("Location");
					info.addHeadTitle("Piece");
					info.addHeadTitle("Weight");
					info.addHeadTitle("P/O No.");
					info.addRowData(obj.getString("cntrNo"));
					info.addRowData(obj.getString("sealNo"));
					info.addRowData(obj.getString("cntrTpszNm"));
					info.addRowData(obj.getString("eventDt"));
					info.addRowData(obj.getString("statusNm"));
					info.addRowData(obj.getString("piece"));
					info.addRowData(obj.getString("placeNm"));
					info.addRowData(obj.getString("weight"));
					info.addRowData(obj.getString("poNo"));
					info.addSubTrackingInfo(getSubBLTrackingInfo(url,obj,true));
					info.addSubTrackingInfo(getScheduleInfo(url,obj,true));
				}
			}
		}
		return info;
	}	
	
	private TrackingInfo getBLTrackingInfo(String url, boolean isPost, JSONObject data) throws Exception {
		TrackingInfo info = new TrackingInfo(9);
		JSONArray list = data.getJSONArray("list");
		if(!list.isEmpty())
		{
			JSONObject obj = list.getJSONObject(0);
			info.addHeadTitle("Booking No.");
			info.addHeadTitle("Container No.");
			info.addHeadTitle("Seal No.");
			info.addHeadTitle("Size");
			info.addHeadTitle("Event Date / Time");
			info.addHeadTitle("Status");
			info.addHeadTitle("Place");
			info.addHeadTitle("Weight");
			info.addHeadTitle("Purchase Order No");
			/////////////////////////////////////
			info.addRowData(obj.getString("bkgNo"));
			info.addRowData(obj.getString("cntrNo"));
			info.addRowData(obj.getString("sealNo"));
			info.addRowData(obj.getString("cntrTpszNm"));
			info.addRowData(obj.getString("eventDt"));
			info.addRowData(obj.getString("statusNm"));
			info.addRowData(obj.getString("yardNm"));
			info.addRowData(obj.getString("weight"));
			info.addRowData(obj.getString("poNo"));
			info.addSubTrackingInfo(getSubBLTrackingInfo(url,obj,isPost));
			info.addSubTrackingInfo(getScheduleInfo(url,obj,isPost));
		}
		return info;
	}

	public TrackingInfo getSubBLTrackingInfo(String url,Map<String, Object> params,boolean isPost) throws Exception
	{
		HttpClient client =getHttpClient();
		TrackingInfo info = new TrackingInfo(3);
		Map<String, Object> subParams = new HashMap<String, Object>();
		subParams.put("cntr_no", params.get("cntrNo"));
		subParams.put("bkg_no", params.get("bkgNo"));
		subParams.put("cop_no", params.get("copNo"));
		subParams.put("f_cmd", "125");
		HttpUriRequest httpSubmit = getRequestSubmit(url, subParams, isPost);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			JSONObject data = JSON.parseObject(EntityUtils.toString(entity));
			JSONArray list = data.getJSONArray("list");
			JSONObject obj = null;
			info.addHeadTitle("Status");
			info.addHeadTitle("Location");
			info.addHeadTitle("Event Date");
			for(int i=0;i<list.size();i++)
			{
				obj = list.getJSONObject(i);
				/////////////////////////////////
				info.addRowData(obj.getString("statusNm"));
				info.addRowData(obj.getString("placeNm")+"\n"+obj.getString("yardNm"));
				info.addRowData(obj.getString("eventDt"));
			}
		}	
		return info;
	}
	
	public TrackingInfo getScheduleInfo(String url,Map<String, Object> params,boolean isPost) throws Exception
	{
		HttpClient client =getHttpClient();
		TrackingInfo info = new TrackingInfo(5);
		Map<String, Object> subParams = new HashMap<String, Object>();
		subParams.put("bkg_no", params.get("bkgNo"));
		subParams.put("f_cmd", "124");
		HttpUriRequest httpSubmit = getRequestSubmit(url, subParams, isPost);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			JSONObject data = JSON.parseObject(EntityUtils.toString(entity));
			JSONArray list = data.getJSONArray("list");
			info.addHeadTitle("Vessel");
			info.addHeadTitle("Port of Loading");
			info.addHeadTitle("Departure Date");
			info.addHeadTitle("Port of Discharging");
			info.addHeadTitle("Arrival Time");
			if(!list.isEmpty())
			{
				JSONObject obj = list.getJSONObject(0);
				info.addRowData(obj.getString("vslEngNm")+" "+obj.getString("skdVoyNo")+" "+obj.getString("skdDirCd")+" ("+obj.getString("vslCd")+")");
				info.addRowData(obj.getString("polNm"));
				info.addRowData(obj.getString("etd"));
				info.addRowData(obj.getString("podNm"));
				info.addRowData(obj.getString("eta"));
			}
		}
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_CONTAINER))					//container
		{
			params.put("f_cmd","122");
			params.put("cntr_no",value);
			params.put("cust_cd","B");
		}
		else
		{
			params.put("f_cmd","121");
			params.put("search_name",value);
			params.put("search_type","B");
		}
		return params;
	}

}
