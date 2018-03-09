package com.echounion.boss.Tracking.nordan;

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
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class NORDANTracking extends AbstractTrack{

	Logger logger = Logger.getLogger(NORDANTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		String type = String.valueOf(params.get(TrackingAdapter.STYPE));			//获得类型
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(entity!=null)
			{
				String data = EntityUtils.toString(entity);
				if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
				{
					info = getContainerInfo(data);
				}else
				{
					info = getBLInfo(data);
				}
			}
			
		}catch (Exception e) {
			logger.error("NORDAN 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(String data) throws Exception
	{
		TrackingInfo info =new TrackingInfo(9);
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("tr", "bgcolor", "#4A516B");
		NodeList nodeList = parser.parse(filter);
		Node[] rows = nodeList.elementAt(0).getParent().getChildren().extractAllNodesThatMatch(getAndFilter("tr")).toNodeArray();
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
		return info;
	}
	
	private TrackingInfo getBLInfo(String data) throws Exception
	{
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "cellpadding", "3");
		NodeList nodeList = parser.parse(filter);
		return getMainBLInfo(nodeList);
	}
	
	public TrackingInfo getMainBLInfo(NodeList nodeList)
	{
		NodeList tableChildrens = nodeList.elementAt(2).getChildren();
		TrackingInfo info = new TrackingInfo(3);
		getTableInfo(tableChildrens, info);
		TrackingInfo sbuInfo = new TrackingInfo(10);
		getTableInfo(nodeList.elementAt(3).getChildren(), sbuInfo);
		TrackingInfo sbuInfo1 = new TrackingInfo(7);
		getTableInfo(nodeList.elementAt(4).getChildren(), sbuInfo1);
		TrackingInfo sbuInfo2 = new TrackingInfo(10);
		getTableInfo(nodeList.elementAt(5).getChildren(), sbuInfo2);
		info.getSubTrackingInfo().add(sbuInfo);
		info.getSubTrackingInfo().add(sbuInfo1);
		info.getSubTrackingInfo().add(sbuInfo2);
		return info;
	}
	

	private void getTableInfo(NodeList tableChildrens, TrackingInfo info) {
		Node[] rows = tableChildrens.extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
		Node[] columns = null;
		for(int i=0;i<rows.length;i++)
		{
			columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
			for(Node column:columns)
			{
				if(i==0)
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
				}else
				{
					info.addRowData(filterHtml(column.toPlainTextString()));
				}
			}
		}
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("SCTYPE","EQ");
		}else
		{
			params.put("SCTYPE","BL");
		}
		params.put("SCREFN", value);
		params.put("SCPRIN","NU");
		return params;
	}

}
