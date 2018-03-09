package com.echounion.boss.Tracking.sitc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class SITCTracking extends AbstractTrack {

	private Logger logger = Logger.getLogger(SITCTracking.class);

	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		String type = String.valueOf(params.get(TrackingAdapter.STYPE));
		HttpClient client = getHttpClient();
		params = assembleSelfParams(params);
		TrackingInfo info = new TrackingInfo();
		try {
			HttpUriRequest httpSubmit = getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info.setTitle("SITC Tracking Result");
			if (entity != null) {
				if(type.equals(TrackingAdapter.STYPE_CONTAINER))
				{
					info = getContainerInfo(entity);
				}
				else
				{
					info = getBLTracking(entity);
					info.addSubTrackingInfo(getBLSailingInfo(url, params, isPost));
					info.addSubTrackingInfo(getBLContainersInfo(url, params, isPost));
				}
			}
		} catch (Exception e) {
			logger.error("SITC 数据采集出错" + e);
		}
		return info;
	}

	private TrackingInfo getContainerInfo(HttpEntity entity) throws Exception
	{
		TrackingInfo info;
		info = new TrackingInfo(5); // 总信息
		JSONObject data = JSON.parseObject(EntityUtils.toString(entity), JSONObject.class);
		List<JSONObject> list = JSON.parseArray(data.getString("list"), JSONObject.class);
		info.addHeadTitle("Current Status");
		info.addHeadTitle("Locale");
		info.addHeadTitle("Occurrence Time");
		info.addHeadTitle("Vessel Name");
		info.addHeadTitle("Voyage No.");
		for (JSONObject obj : list) {
			info.addRowData(obj.getString("trackMovementCode"));
			info.addRowData(obj.getString("trackEventPort"));
			info.addRowData(obj.getString("trackEventDate"));
			info.addRowData(obj.getString("trackVesselName"));
			info.addRowData(obj.getString("trackVoyageNo"));
		}
		return info;
	}
	
	private TrackingInfo getBLTracking(HttpEntity entity) throws Exception {
		TrackingInfo info;
		info = new TrackingInfo(3); // 总信息
		JSONObject data = JSON.parseObject(EntityUtils.toString(entity), JSONObject.class);
		List<JSONObject> list = JSON.parseArray(data.getString("list"), JSONObject.class);
		info.addHeadTitle("B/L No.");
		info.addHeadTitle("POL");
		info.addHeadTitle("Final Destination");
		for (JSONObject obj : list) {
			info.addRowData(obj.getString("blNo"));
			info.addRowData(obj.getString("pol"));
			info.addRowData(obj.getString("del"));
		}
		return info;
	}

	private TrackingInfo getBLSailingInfo(String url, Map<String, Object> params, boolean isPost) throws Exception {
		HttpClient client = getHttpClient();
		TrackingInfo info = new TrackingInfo(8);
		info.setTitle("Sailing Schedule Information");
		JSONObject queryInfo = (JSONObject) params.get("queryInfo");
		queryInfo.put("fields", new String[]{});
		queryInfo.put("queryObjectName", "com.sitc.track.bean.BlNoBkRouting4Track");
		queryInfo.put("listType", "page");
		queryInfo.put("pageSize", 20);
		queryInfo.put("pageStart", 1);
		params.put("queryInfo", queryInfo);
		params.put("method", "billNoIndexSailingNew");
		HttpUriRequest httpSubmit = getRequestSubmit(url, params, isPost);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			JSONObject data = JSON.parseObject(EntityUtils.toString(entity), JSONObject.class);
			List<JSONObject> list = JSON.parseArray(data.getString("list"), JSONObject.class);
			info.addHeadTitle("VesselName");
			info.addHeadTitle("Voyage");
			info.addHeadTitle("POL");
			info.addHeadTitle("POD");
			info.addHeadTitle("ETD");
			info.addHeadTitle("ETA");
			info.addHeadTitle("ATD");
			info.addHeadTitle("ATA");
			for (JSONObject obj : list) {
				info.addRowData(obj.getString("vesselName"));
				info.addRowData(obj.getString("voyage"));
				info.addRowData(obj.getString("portFrom"));
				info.addRowData(obj.getString("portTo"));
				info.addRowData(obj.getString("etd"));
				info.addRowData(obj.getString("eta"));
				info.addRowData(obj.getString("atd"));
				info.addRowData(obj.getString("ata"));
			}
		}
		return info;
	}
	
	private TrackingInfo getBLContainersInfo(String url, Map<String, Object> params, boolean isPost) throws Exception
	{
		HttpClient client = getHttpClient();
		TrackingInfo info = new TrackingInfo(8);
		info.setTitle("Containers Information");
		JSONObject queryInfo = (JSONObject) params.get("queryInfo");
		queryInfo.put("fields", new String[]{});
		queryInfo.put("queryObjectName", "com.sitc.track.bean.BlNoBkContainer4Track");
		queryInfo.put("listType", "page");
		queryInfo.put("pageSize", 20);
		queryInfo.put("pageStart", 1);
		params.put("queryInfo", queryInfo);
		params.put("method", "billNoIndexContainersNew");
		HttpUriRequest httpSubmit = getRequestSubmit(url, params, isPost);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			JSONObject data = JSON.parseObject(EntityUtils.toString(entity), JSONObject.class);
			List<JSONObject> list = JSON.parseArray(data.getString("list"), JSONObject.class);
			info.addHeadTitle("ContainerNo");
			info.addHeadTitle("Seal No.");
			info.addHeadTitle("Voyage No.");
			info.addHeadTitle("Container Type");
			info.addHeadTitle("Quantity");
			info.addHeadTitle("Measurement");
			info.addHeadTitle("Gross Weight");
			info.addHeadTitle("Current Status");
			for (JSONObject obj : list) {
				info.addRowData(obj.getString("containerNo"));
				info.addRowData(obj.getString("sealNo"));
				info.addRowData(obj.getString("voyage"));
				info.addRowData(obj.getString("containerType"));
				info.addRowData(obj.getString("quantity"));
				info.addRowData(obj.getString("cntrSize"));
				info.addRowData(obj.getString("weight"));
				info.addRowData(obj.getString("moveCode"));
			}
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String, Object> params = new HashMap<String, Object>();
		String type = String.valueOf(map.get(TrackingAdapter.STYPE));
		String value = String.valueOf(map.get(TrackingAdapter.SVALUE));
		if (type.equals(TrackingAdapter.STYPE_CONTAINER)) // container
		{
			params.put("containerNo", value);
			JSONObject obj = new JSONObject();
			obj.put("fields", new String[]{});
			obj.put("queryObjectName", "com.sitc.track.bean.TrackBookingHead");
			obj.put("listType", "page");
			obj.put("pageSize", 20);
			obj.put("pageStart", 1);
			params.put("queryInfo", obj);
			params.put("method", "boxNoIndexNew");
		}else
		{
			params.put("blNo", value);
			JSONObject obj = new JSONObject();
			obj.put("fields", new String[]{});
			obj.put("queryObjectName", "com.sitc.track.bean.BlNoBookingHead4Track");
			obj.put("listType", "page");
			obj.put("pageSize", 1);
			obj.put("pageStart", 1);
			params.put("queryInfo", obj);
			params.put("method", "billNoIndexBasicNew");
		}
		return params;
	}

}
