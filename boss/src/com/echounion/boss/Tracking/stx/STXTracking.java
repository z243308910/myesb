package com.echounion.boss.Tracking.stx;

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
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class STXTracking extends AbstractTrack{
	
	private Logger logger=Logger.getLogger(STXTracking.class);

	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(params.get("searchType").equals("contNo"))
			{
				info = getContainerInfo(entity);
			}
			else
			{
				info = getBLOrBookingInfo(entity);
			}
		}catch (Exception e) {
			logger.error("STX 数据采集出错"+e);
		}
		
		return info;
	}
	
	private TrackingInfo getBLOrBookingInfo(HttpEntity entity) throws Exception
	{
		TrackingInfo info = new TrackingInfo(3);
		info.setTitle("STX Tracking Result");
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("div", "id", "contents");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {		 				
				nodeList = nodeList.elementAt(0).getChildren();
				nodeList = filterNode(nodeList, Div.class);
				nodeList = nodeList.elementAt(0).getChildren();
				nodeList = filterNode(nodeList, Div.class);
				nodeList = nodeList.elementAt(0).getChildren();
				nodeList = filterNode(nodeList, TableTag.class);
				nodeList = nodeList.elementAt(0).getChildren();
				nodeList = filterNode(nodeList, TableRow.class);
				NodeList titleNodeList = nodeList.elementAt(0).getChildren();
				titleNodeList = filterNode(titleNodeList, TableHeader.class);
				Node[] titles = titleNodeList.toNodeArray();
				for(Node title:titles)
				{
					info.addHeadTitle(filterHtml(title.toPlainTextString()));
				}
				Node[] rowNodes = nodeList.toNodeArray();
				Node[] columnNodes = null;
				StringBuffer sb = null;
				for(int i=1;i<rowNodes.length;i++)
				{
					columnNodes = rowNodes[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
					info.addRowData((columnNodes[0].toPlainTextString()));
					info.addRowData((columnNodes[1].toPlainTextString()));
					Node[] cns = columnNodes[2].getChildren().extractAllNodesThatMatch(getAndFilter("a")).toNodeArray();
					sb = new StringBuffer();
					for(Node cnNo:cns)
					{
						sb.append(filterHtml(cnNo.toPlainTextString())).append(",");
					}
					info.addRowData(sb.toString());
				}
			}
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(HttpEntity entity) throws Exception
	{
		TrackingInfo info = new TrackingInfo(7);
		info.setTitle("STX Tracking Result");
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("div", "id", "contents");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {		 				
				nodeList = nodeList.elementAt(0).getChildren();
				nodeList = filterNode(nodeList, Div.class);
				nodeList = nodeList.elementAt(0).getChildren();
				nodeList = filterNode(nodeList, Div.class);
				nodeList = nodeList.elementAt(1).getChildren();
				nodeList = filterNode(nodeList, Div.class);
				NodeList inNodeList = nodeList.extractAllNodesThatMatch(getAndFilter("div", "class", "boxA"));
				inNodeList = inNodeList.elementAt(0).getChildren();
				inNodeList = inNodeList.extractAllNodesThatMatch(getAndFilter("div", "class", "in"));
				inNodeList = inNodeList.elementAt(0).getChildren();
				inNodeList = filterNode(inNodeList, TextNode.class);
				Node[] inNodes = inNodeList.toNodeArray();
				for(int i=1;i<inNodes.length;i++)
				{
					if(i%2==1)
					{
						info.addHeadTitle(filterHtml(inNodes[i].toPlainTextString()));
					}
					else
					{
						info.addRowData(filterHtml(inNodes[i].toPlainTextString()));
					}
				}
				
				NodeList tableAList = nodeList.extractAllNodesThatMatch(getAndFilter("div", "class", "tableA"));
				tableAList = tableAList.elementAt(0).getChildren().extractAllNodesThatMatch(getAndFilter("table"));
				NodeList rowList = tableAList.elementAt(0).getChildren().extractAllNodesThatMatch(getAndFilter("tr"));
				Node[] rows = rowList.toNodeArray();
				NodeList columnList = null;
				Node[] titleList = null;
				Node[] dataList = null;
				for(Node row:rows)
				{
					columnList = row.getChildren();
					titleList = columnList.extractAllNodesThatMatch(getAndFilter("th")).toNodeArray();
					for(Node title:titleList)
					{
						info.addHeadTitle(filterHtml(title.toPlainTextString()));
					}
					dataList = columnList.extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
					for(Node content:dataList)
					{
						info.addRowData(filterHtml(content.toPlainTextString()));
					}
				}
				info.getSubTrackingInfo().add(getSubTrackingInfo(nodeList));				
			}
		}
		return info;
	}
	
	public TrackingInfo getSubTrackingInfo(NodeList nodeList)
	{
		TrackingInfo info = new TrackingInfo(4);
		NodeList tableAList = nodeList.extractAllNodesThatMatch(getAndFilter("div","class","tableA clear"));
		tableAList = tableAList.elementAt(0).getChildren().extractAllNodesThatMatch(getAndFilter("table"));
		tableAList = tableAList.elementAt(0).getChildren().extractAllNodesThatMatch(getAndFilter("tr"));
		Node[] rows = tableAList.toNodeArray();
		Node[] titles = rows[0].getChildren().extractAllNodesThatMatch(getAndFilter("th")).toNodeArray();
		Node[] datas = null;
		for(Node title:titles)
		{
			info.addHeadTitle(filterHtml(title.toPlainTextString()));
		}
		for(int i=1;i<rows.length;i++)
		{
			datas = rows[i].getChildren().extractAllNodesThatMatch(getAndFilter("td")).toNodeArray();
			for(Node data:datas)
			{
				info.addRowData(filterHtml(data.toPlainTextString()));
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
			params.put("schNum", value);
			params.put("searchType","blNo");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订舱
		{
			params.put("schNum", value);
			params.put("searchType","bookingNo");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("schNum", value);
			params.put("searchType","contNo");
		}
		return params;
	}

}
