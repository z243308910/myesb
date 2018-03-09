package com.echounion.boss.Tracking.mcc;

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
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class MCCTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(MCCTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		String stype=params.get(TrackingAdapter.STYPE).toString();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(); // 总信息
			info.setTitle("MCC Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				if(stype.equals(TrackingAdapter.STYPE_CONTAINER))
				{
					logger.warn("MCC 不支持Container信息的抓取!");
				}else
				{
					info=getBill(data, info);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("MCC 数据采集出错"+e);
		}
		return info;
	}

	public TrackingInfo getBill(String data,TrackingInfo info)throws Exception
	{
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("div", "class", "group");
		NodeList nodeList = parser.parse(filter);
		if(nodeList!=null)
		{
			nodeList=nodeList.elementAt(0).getChildren();
			List<String> headList=new ArrayList<>();
			List<String> rowList=new ArrayList<>();
			for(int i=0;i<nodeList.size();i++)
			{
				if(i==0)
				{
					continue;
				}
				Node tmpNode=nodeList.elementAt(i);
				if(tmpNode instanceof Div)
				{
					headList.add(filterHtml(tmpNode.toPlainTextString()));
				}else if(tmpNode instanceof TextNode)
				{
					rowList.add(filterHtml(tmpNode.toPlainTextString()));
				}else if(tmpNode instanceof TagNode)
				{
					break;
				}
			}
			info.setHeadTitles(headList);
			info.addRowDataList(rowList);
		}
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			params.put("nr", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("nr", value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("containerNumber", value);
		}
		return params;
	}

}
