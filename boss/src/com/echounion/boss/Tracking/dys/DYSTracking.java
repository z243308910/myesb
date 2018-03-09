package com.echounion.boss.Tracking.dys;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class DYSTracking extends AbstractTrack{

	Logger logger = Logger.getLogger(DYSTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo(9);
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(entity!=null)
			{
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table", "bgcolor", "#dcdcdc");
				NodeList nodeList = parser.parse(filter);
				Node[] rows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
				Node[] columns = null;
				for(int i=0;i<rows.length;i++)
				{
					columns = rows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
					for(Node column:columns)
					{
						if(i==0)
						{
							info.addHeadTitle(filterHtml(column.toPlainTextString()));
						}
						else
						{
							info.addRowData(filterHtml(column.toPlainTextString()));
						}
					}
				}
			}
				
		}catch(Exception e)
		{
			logger.error("DYS 数据采集出错"+e);
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("type","CO");
		}
		else if(type.equals(TrackingAdapter.STYPE_BILL))		//bill
		{
			params.put("type","BL");
		}
		params.put("number", value);
		params.put("action", "search");
		return params;
	}
	

}
