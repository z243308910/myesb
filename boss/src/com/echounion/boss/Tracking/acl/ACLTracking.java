package com.echounion.boss.Tracking.acl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;

import com.alibaba.fastjson.JSON;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class ACLTracking extends AbstractTrack{

	Logger logger = Logger.getLogger(ACLTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				System.out.println(data);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("input", "value", "Detailed Tracking");
				NodeList nodeList = parser.parse(filter);
				InputTag detailBtn = (InputTag)nodeList.elementAt(0);
				if(null != detailBtn)
				{
					String againUrl = detailBtn.getAttribute("onclick");
					againUrl = againUrl.substring(againUrl.indexOf("('")+2, againUrl.lastIndexOf("')"));
					info = doTrack("http://www.aclcargo.com/"+againUrl);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error("ACL 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo doTrack(String url) throws Exception
	{
		TrackingInfo info =new TrackingInfo(1);
		HttpClient client =getHttpClient();
		HttpUriRequest httpSubmit=getRequestSubmit(url, false);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			data = data.substring(data.indexOf("dataContent =")+14,data.indexOf("//[\"av1\"")-1);
			List<String> list = JSON.parseArray(data, String.class);
			info.addHeadTitle("Detailed tracking");
			info.addRowDataList(list);
		}
		return info;
	}
	

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("search_for", value);
		params.put("selectType","Container");
		return params;
	}

}
