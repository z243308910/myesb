package com.echounion.boss.Tracking.kuaidi;

import java.util.ArrayList;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.entity.dto.TrackingInfo;

public class KuaidiTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(KuaidiTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info = null;
		
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			
			info= new TrackingInfo(); // 总信息
			info.setTitle("Express Tracking Result"); 
			
			List<String> titles = new ArrayList<String>();     
			titles.add("time");
			titles.add("context");
			info.setHeadTitles(titles);
			
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				String status = (String)((JSONObject)JSON.parse(data)).get("status");
				
				if("200".equals(status))        //"200":表访问状态，成功获取数据       
				{
					 JSONArray arrays = (JSONArray)((JSONObject)JSON.parse(data)).get("data");
					for (int i=0;i<arrays.size();i++) {
						info.addRowData(((JSONObject)arrays.get(i)).getString("time"));
						info.addRowData(((JSONObject)arrays.get(i)).getString("context"));
					}
				}
			}
		}catch (Exception e) {
			logger.error("Kuaidi 数据采集出错",e);
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(KuaidiAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(KuaidiAdapter.SVALUE));
		params.put("postid", value);
		params.put("type", type);
		return params;
	}

}
