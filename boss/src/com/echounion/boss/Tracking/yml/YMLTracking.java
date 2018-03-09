package com.echounion.boss.Tracking.yml;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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

public class YMLTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(YMLTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		String type = String.valueOf(params.get(TrackingAdapter.STYPE));
		HttpClient client =getHttpClient();
		params = assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(type.equals(TrackingAdapter.STYPE_CONTAINER))
			{
				info = getContainerInfo(entity);
			}
			else
			{
				info = getBLOrBookingInfo(entity);
			}
		}catch (Exception e) {
			logger.error("RCL 数据采集出错"+e);
		}
		
		return info;
	}

	
	private TrackingInfo getBLOrBookingInfo(HttpEntity entity) throws Exception
	{		
		String data = EntityUtils.toString(entity);
		data = data.substring(data.indexOf("<div class=\"eser_vewrap_wrap2\">"), data.lastIndexOf("<div class=\"eser_vewrap_wrap1\">"));
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table");
		NodeList nodeList = parser.parse(filter);
		TrackingInfo info = new TrackingInfo();
		if(nodeList.size()>0)
		{
			info = getBLMainTrackingInfo(nodeList);
			TrackingInfo subInfo = getBLSubTrackingInfo(nodeList);
			info.getSubTrackingInfo().add(subInfo);
			info.getSubTrackingInfo().add(getScheduleInfo(nodeList,4));
			info.getSubTrackingInfo().add(getContainerEvent(nodeList,6));
		}
		return info;
	}

	private TrackingInfo getBLSubTrackingInfo(NodeList nodeList)
	{
		TrackingInfo info = new TrackingInfo(7);
		Node[] rows = nodeList.elementAt(2).getChildren().extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] columnNodes = null;
		String[] columnInfo = null;
		for(Node row:rows)
		{
			columnNodes = row.getChildren().extractAllNodesThatMatch(getAndFilter("td","class","bkfont")).toNodeArray();
			for(Node column:columnNodes)
			{
				columnInfo = StringUtils.split(filterHtml(column.toPlainTextString()), ":");
				if(columnInfo.length>0)
				{
					info.addHeadTitle(columnInfo[0].trim());
					info.addRowData(columnInfo[1].trim());
				}
			}
		}
		return info;
	}

	public void getOtherInfo(TrackingInfo info,NodeList nodeList,int index)
	{
		Node[] rows = nodeList.elementAt(index).getChildren().extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] columnNodes = null;
		for(int i=0;i<rows.length;i++)
		{
			columnNodes = rows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node column:columnNodes)
			{
				if(i==0)
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
					continue;
				}
				info.addRowData(filterHtml(column.toPlainTextString()));
			}
		}

	}
	
	public TrackingInfo getScheduleInfo(NodeList nodeList,int index)
	{
		TrackingInfo info = new TrackingInfo(3);
		getOtherInfo(info,nodeList,index);
		return info;
	}
	
	public TrackingInfo getContainerEvent(NodeList nodeList,int index)
	{
		TrackingInfo info = new TrackingInfo(9);
		getOtherInfo(info,nodeList,index);
		return info;
	}
	
	private TrackingInfo getBLMainTrackingInfo(NodeList nodeList) {
		TrackingInfo info = new TrackingInfo(4);
		info.setTitle("YML Tracking Result");
		Node[] mainRows = nodeList.elementAt(1).getChildren().
				extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] maincolumns = null;
		for(int i=0;i<mainRows.length;i++)
		{
			maincolumns = mainRows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node column:maincolumns)
			{
				if(i==0)
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
					continue;
				}
				info.addRowData(filterHtml(column.toPlainTextString()));
			}
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(HttpEntity entity) throws Exception
	{
		TrackingInfo info = new TrackingInfo(5);
		info.setTitle("YML Tracking Result");
		String data = EntityUtils.toString(entity);
		data = data.substring(data.indexOf("<div id=\"content\">"), data.indexOf("<div id=\"breadcrumb\">"));
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("tr","class","field_name");
		NodeList nodeList = parser.parse(filter);
		if(nodeList.size()>0)
		{
			nodeList = nodeList.elementAt(0).getParent().getChildren().extractAllNodesThatMatch(getAndFilter("tr"));
			Node[] rowNodes = nodeList.toNodeArray();
			Node[] columnNodes = null;
			for(int i=0;i<rowNodes.length;i++){
				columnNodes = rowNodes[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
				for(Node column:columnNodes)
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
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));	//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			params.put("num1", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订舱
		{
			params.put("num1", value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("CTNRNO", value);
		}
		return params;
	}

}
