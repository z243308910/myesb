package com.echounion.boss.Tracking.kline.mobile;

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

public class KlineMobileTrackig extends AbstractTrack{

	private Logger logger=Logger.getLogger(KlineMobileTrackig.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		HttpClient client = getHttpClient();
		params = assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(entity != null)
			{
				info = getContainerInfo(EntityUtils.toString(entity));
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("KlineMobile 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(String data)
	{
		JSONObject json = JSON.parseObject(data);
		json = json.getJSONObject("SearchResultItem");
		String currentmovements = json.getString("CurrentMovements");
		List<JSONObject> jsonArray = JSON.parseArray(currentmovements, JSONObject.class);
		TrackingInfo info = new TrackingInfo(5);
		info.addHeadTitle("Event");
		info.addHeadTitle("Date");
		info.addHeadTitle("Location");
		info.addHeadTitle("Facility");
		info.addHeadTitle("Mode");
		for (JSONObject jsonObj :jsonArray) {
			info.addRowData(jsonObj.getString("EventDescription"));
			info.addRowData(jsonObj.getString("EventDate"));
			info.addRowData(jsonObj.getString("Location"));
			info.addRowData(jsonObj.getString("Facility"));
			info.addRowData(jsonObj.getString("Mode"));
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("searchValue", value);
		return params;
	}
}
