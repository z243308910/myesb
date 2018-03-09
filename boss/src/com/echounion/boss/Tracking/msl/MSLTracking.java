package com.echounion.boss.Tracking.msl;

import java.util.HashMap;
import java.util.Iterator;
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

public class MSLTracking extends AbstractTrack{

	Logger logger = Logger.getLogger(MSLTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		String type=String.valueOf(params.get(TrackingAdapter.STYPE));	//获得类型
		TrackingInfo info = new TrackingInfo();		
		try
		{
			if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
			{
				info = getContainerTrackingInfo(url,params,isPost);
			}else
			{
				info = getBLTrackingInfo(url,params,isPost);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("MSL 数据采集出错"+e);
		}
		
		return info;
	}
	
	private TrackingInfo getContainerTrackingInfo(String url,Map<String, Object> params, boolean isPost) throws Exception {
		String value = String.valueOf(params.get(TrackingAdapter.SVALUE));
		params = assembleSelfParams(params);
		TrackingInfo info = getTrackingInfo(url,params,isPost);
		Map<String,Object> subParams = new HashMap<String, Object>();
		subParams.put("GET_CARGO_TRACK_VSL", 1);
		subParams.put("TYPE", "TCN");
		subParams.put("CN_NO", value);
		subParams.put("BK_NO", "");
		subParams.put("BL_NO", "");
		info = getTrackingInfo(url,subParams,isPost);
		subParams.put("GET_CARGO_TRACK_VSL", "HIST");
		info.getSubTrackingInfo().add(getTrackingInfo(url,subParams,isPost));
		return info;
	}
	

	private TrackingInfo getBLTrackingInfo(String url,Map<String, Object> params, boolean isPost) throws Exception {
		String value=String.valueOf(params.get(TrackingAdapter.SVALUE));
		params = assembleSelfParams(params);
		TrackingInfo info = getTrackingInfo(url,params,isPost);
		Map<String,Object> subParams = new HashMap<String, Object>();
		subParams.put("GET_CARGO_TRACK", 1);
		subParams.put("TYPE_CDE", "TBL");
		subParams.put("BL_NO", value);
		subParams.put("BK_NO", "");
		subParams.put("CN_NO", "");
		info.getSubTrackingInfo().add(getTrackingInfo(url,subParams,isPost));
		subParams.clear();
		subParams.put("GET_CARGO_TRACK_VSL", 1);
		subParams.put("TYPE", "TBL");
		subParams.put("BL_NO", value);
		subParams.put("BK_NO", "");
		subParams.put("CN_NO", "");
		info.getSubTrackingInfo().add(getTrackingInfo(url,subParams,isPost));
		subParams.put("GET_CARGO_TRACK_VSL", "HIST");
		info.getSubTrackingInfo().add(getTrackingInfo(url,subParams,isPost));
		return info;
	}
	
	public TrackingInfo getTrackingInfo(String url, Map<String, Object> params, boolean isPost) throws Exception
	{
		HttpClient client =getHttpClient();
		TrackingInfo info = new TrackingInfo();
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		String data = EntityUtils.toString(entity);
		List<JSONObject> rows= JSON.parseArray(data, JSONObject.class);
		if(!rows.isEmpty())
		{
			data = rows.get(0).getString("rows");
			rows = JSON.parseArray(data, JSONObject.class);
			if(!rows.isEmpty())
			{
				info = new TrackingInfo(rows.get(0).size());
				int i=0;
				for(JSONObject obj:rows){
					Iterator<String> it= obj.keySet().iterator();
					String key = null;
					for(;it.hasNext();)
					{
						key = it.next();
						if(i==0)
						{
							info.addHeadTitle(key);
						}
						info.addRowData(obj.getString(key));
					}
					i++;
				}
			}
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));	//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("GET_CARGO_TRACK", 1);
			params.put("TYPE_CDE", "TCN");
			params.put("BL_NO", "");
			params.put("BK_NO", "");
			params.put("CN_NO", value);
		}else
		{
			params.put("GET_CARGO_TRACK", "BLINFO");
			params.put("BK_NO", "");
			params.put("BL_NO", value);
		}
		return params;
	}

}
