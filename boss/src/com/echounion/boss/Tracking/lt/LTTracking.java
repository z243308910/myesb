package com.echounion.boss.Tracking.lt;

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
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.common.util.DataConvertUtils;
import com.echounion.boss.entity.dto.TrackingInfo;

public class LTTracking extends AbstractTrack{

	Logger logger = Logger.getLogger(LTTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		String type = String.valueOf(params.get(TrackingAdapter.STYPE));	
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
				if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
				{
					info = getBLTrackingInfo(data);	
				}
				else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订舱
				{
					info = getBookingInfo(data);
				}
				else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
				{
					info = getContainerInfo(data);	
				}
			}
		}catch (Exception e) {
		
		}
		return info;
	}
	
	private TrackingInfo getBLTrackingInfo(String data) throws Exception
	{
		TrackingInfo info = new TrackingInfo(12);
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "bgcolor", "#999999");
		NodeList nodeList = parser.parse(filter);
		Node[] baseRows = nodeList.elementAt(1).getChildren().extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] basecolumns = null;
		for(int i=1;i<baseRows.length;i++)
		{
			basecolumns = baseRows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node basecolumn:basecolumns)
			{
				TableColumn column = (TableColumn)basecolumn;
				if(column.getAttribute("class").equals("f12rown2"))
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
				}else
				{
					info.addRowData(filterHtml(column.toPlainTextString()));
				}
			}
		}
		TrackingInfo subInfo = new TrackingInfo(7);
		Node[] subRows = nodeList.elementAt(2).getChildren().extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] subcolumns = null;
		for(int i=1;i<subRows.length;i++)
		{
			subcolumns = subRows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node column:subcolumns)
			{
				if(i==1)
				{
					subInfo.addHeadTitle(filterHtml(column.toPlainTextString()));
				}else
				{
					subInfo.addRowData(filterHtml(column.toPlainTextString()));
				}
			}
		}
		info.getSubTrackingInfo().add(subInfo);
		return info;
	}
	
	private TrackingInfo getContainerInfo(String data) throws Exception
	{
		TrackingInfo info = new TrackingInfo(6);
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "bgcolor", "#999999");
		NodeList nodeList = parser.parse(filter);
		Node[] baseRows = nodeList.elementAt(1).getChildren().extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] basecolumns = null;
		for(int i=1;i<baseRows.length;i++)
		{
			basecolumns = baseRows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node column:basecolumns)
			{
				if(i==1)
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
				}else
				{
					info.addRowData(filterHtml(column.toPlainTextString()));
				}
			}
		}
		return info;
	}
	
	private TrackingInfo getBookingInfo(String data) throws Exception
	{
		TrackingInfo info = new TrackingInfo(7);
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "bgcolor", "#999999");
		NodeList nodeList = parser.parse(filter);
		Node[] baseRows = nodeList.elementAt(1).getChildren().extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] basecolumns = null;
		for(int i=1;i<baseRows.length;i++)
		{
			basecolumns = baseRows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node basecolumn:basecolumns)
			{
				TableColumn column = (TableColumn)basecolumn;
				if(i==1)
				{
					int colspan = DataConvertUtils.convertInteger(column.getAttribute("colspan"));
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
					for(int j=1;j<colspan;j++)
					{
						info.addHeadTitle("");
					}
				}else
				{
					int colspan = DataConvertUtils.convertInteger(column.getAttribute("colspan"));
					info.addRowData(filterHtml(column.toPlainTextString()));
					for(int j=1;j<colspan;j++)
					{
						info.addRowData("");
					}
				}
			}
		}
		info.getSubTrackingInfo().add(getSubBookInfo(new TrackingInfo(4),nodeList.elementAt(2).getChildren()));
		info.getSubTrackingInfo().add(getSubBookInfo(new TrackingInfo(5),nodeList.elementAt(3).getChildren()));
		info.getSubTrackingInfo().add(getSubBookInfo(new TrackingInfo(10),nodeList.elementAt(4).getChildren()));
		return info;
	}
	
	private TrackingInfo getSubBookInfo(TrackingInfo info,NodeList nodeList)
	{
		Node[] baseRows = nodeList.extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
		Node[] basecolumns = null;
		for(int i=1;i<baseRows.length;i++)
		{
			basecolumns = baseRows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node column:basecolumns)
			{
				if(i==1)
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
	
	

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			params.put("BL", value);
			params.put("TYPE","BL");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订舱
		{
			params.put("bkno", value);
			params.put("TYPE","BK");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("CNTR", value);
			params.put("TYPE","CNTR");
		}
		return params;
	}
	
	

}
