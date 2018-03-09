package com.echounion.boss.Tracking.has;

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

public class HASTracking extends AbstractTrack{

	Logger logger = Logger.getLogger(HASTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info = new TrackingInfo(4);
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table", "width", "670");
				NodeList nodeList = parser.parse(filter);
				getBLTrackingInfo(info,nodeList.elementAt(0).getChildren());
				info.getSubTrackingInfo().add(getBLShippingInfo(nodeList));
				info.getSubTrackingInfo().add(getBLCNTRInfo(nodeList));
			}
		}catch(Exception e)
		{
			logger.error("HAS 数据采集出错"+e);
		}
		return info;
	}

	private TrackingInfo getBLTrackingInfo(TrackingInfo info,NodeList nodeList)
	{
		Node[] mainRows = nodeList.extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] maincolumns = null;
		for(int i=0;i<mainRows.length;i++)
		{
			maincolumns = mainRows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node column:maincolumns)
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
		return info;
	}
	
	private TrackingInfo getBLShippingInfo(NodeList nodeList)
	{
		TrackingInfo info = new TrackingInfo(7);
		getBLTrackingInfo(info,nodeList.elementAt(1).getChildren());
		return info;
	}
	
	private TrackingInfo getBLCNTRInfo(NodeList nodeList)
	{
		TrackingInfo info = new TrackingInfo(3);
		getBLTrackingInfo(info,nodeList.elementAt(2).getChildren());
		return info;
	}
	
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_CONTAINER))					//container
		{
			params.put("numkind","3");
		}
		else
		{
			params.put("numkind","2");
		}
		params.put("number", value);
		params.put("tag", "result");
		return params;
	}

}
