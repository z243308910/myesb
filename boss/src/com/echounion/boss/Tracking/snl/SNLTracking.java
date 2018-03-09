package com.echounion.boss.Tracking.snl;

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
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class SNLTracking extends AbstractTrack {

	Logger logger = Logger.getLogger(SNLTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		String type = String.valueOf(params.get(TrackingAdapter.STYPE)); // 获得类型
		String value = String.valueOf(params.get(TrackingAdapter.SVALUE));
		HttpClient client =getHttpClient();
		params = assembleSelfParams(params);
		TrackingInfo info = new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(); // 总信息
			info.setTitle("SNL Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				if (type.equals(TrackingAdapter.STYPE_CONTAINER))
				{
					info = getSubTrackingInfo(getContrinerInfo(url,isPost,data,value),"CntrStateGridView");
				}
				else
				{
					info = getBLTrackingInfo(url,isPost,data);
				}				
			}
		}catch (Exception e) {
			logger.error("SNL 数据采集出错"+e);
		}
		return info;
	}
	
	public String getContrinerInfo(String url,boolean isPost,String data,String coNO) throws Exception
	{
		Parser parser = new Parser(data);
		NodeList nodeList = parser.parse(new NodeClassFilter(InputTag.class));
		Node[] formNodes = nodeList.toNodeArray();
		HttpClient client =getHttpClient();
		Map<String, Object> params = new HashMap<String, Object>();
		InputTag input = null;
		params.put("dl_seltype", "cntrno");		
		for(Node node:formNodes)
		{
			input = (InputTag)node;
			params.put(input.getAttribute("name"), input.getAttribute("value"));
		}
		params.put("TbBlno", coNO);
		HttpUriRequest httpSubmit=getRequestSubmit(url,params,isPost);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		if(entity != null)
		{
			data = EntityUtils.toString(entity);
			return data;
		}
		return null;		
	}

	private TrackingInfo getBLTrackingInfo(String url,boolean isPost,String data) throws Exception {
		TrackingInfo info =new TrackingInfo(8);
		data = getBLTrackingDetail(url, isPost, data,"");
		data = getBLTrackingDetail(url, isPost, data,"detail");
		if(StringUtils.isNotEmpty(data))
		{
			info = getSubTrackingInfo(data,"BlnoGridView");
			info.addSubTrackingInfo(getSubTrackingInfo(data,"ScheduleGridView"));
			info.addSubTrackingInfo(getSubTrackingInfo(data,"ContainerGridView"));
			info.addSubTrackingInfo(getSubTrackingInfo(data,"CntrStateGridView"));
		}
		return info;
	}

	public TrackingInfo getSubTrackingInfo(String data,String id) throws Exception
	{
		TrackingInfo info = new TrackingInfo(8);
		Parser parser = new Parser(data);
		NodeList nodeList = parser.parse(getAndFilter("table","id",id));
		if(nodeList.size()>0)
		{
			Node[] rows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
			Node[] columns = null;
			
			for(int i=0;i<rows.length;i++)
			{
				if(i==0)
				{
					columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableHeader.class)).toNodeArray();
					info = new TrackingInfo(columns.length);
					for(Node column:columns)
					{
						info.addHeadTitle(filterHtml(column.toPlainTextString()));
					}
				}else
				{
					columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
					for(Node column:columns)
					{
						info.addRowData(filterHtml(column.toPlainTextString()));
					}
				}
			}
		}
		return info;
	}

	private String getBLTrackingDetail(String url, boolean isPost, String data,String detail) throws Exception {
		Parser parser = new Parser(data);
		NodeList nodeList = parser.parse(new NodeClassFilter(InputTag.class));
		Node[] formNodes = nodeList.toNodeArray();
		HttpClient client =getHttpClient();
		Map<String, Object> params = new HashMap<String, Object>();
		InputTag input = null;
		params.put("dl_seltype", "blno");
		for(Node node:formNodes)
		{
			input = (InputTag)node;
			params.put(input.getAttribute("name"), input.getAttribute("value"));
		}
		if(detail.endsWith("detail"))
		{
			params.remove("Button1");
			params.put("__EVENTTARGET", "BlnoGridView");
			params.put("__EVENTARGUMENT", "Select$0");
		}
		HttpUriRequest httpSubmit=getRequestSubmit(url,params,isPost);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		if(entity != null)
		{
			data = EntityUtils.toString(entity);
			return data;
		}
		return null;
	}
	

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String, Object> params = new HashMap<String, Object>();
		String type = String.valueOf(map.get(TrackingAdapter.STYPE)); // 获得类型
		String value = String.valueOf(map.get(TrackingAdapter.SVALUE));
		if (type.equals(TrackingAdapter.STYPE_CONTAINER)) // container
		{
			
			params.put("dl_seltype", "cntrno");
			params.put("__VIEWSTATE	","/wEPDwUKLTczNDQ0ODk4MQ9kFgICAw9kFgQCDQ9kFgICAw88KwARAQEQFgAWABYAZAIPD2QWAgIDDzwrABEBARAWABYAFgBkGAIFEUNudHJTdGF0ZUdyaWRWaWV3D2dkBRBTY2hlZHVsZUdyaWRWaWV3D2dk5UFN4uCZhOKc/Lh819cXCK5lQM1Vk1lV8wMF/gOk6R8");
			params.put("__EVENTVALIDATION",	"/wEWBAKFoprwCAKozYWxDAKAnbbXBwKM54rGBk1MLSj49uJuKCxm13wmggSU4S8Ai+ho++1t+89brcCs");
			params.put("Button1","查 询");
		}
		else
		{
			params.put("dl_seltype", "blno");
			params.put("__VIEWSTATE","/wEPDwUKLTQyNDE0NTEzNw9kFgICAw9kFgQCDQ88KwARAQEQFgAWABYAZAIPD2QWBgIHDzwrABEBARAWABYAFgBkAgsPPCsAEQEBEBYAFgAWAGQCDw88KwARAQEQFgAWABYAZBgEBRFDbnRyU3RhdGVHcmlkVmlldw9nZAURQ29udGFpbmVyR3JpZFZpZXcPZ2QFEFNjaGVkdWxlR3JpZFZpZXcPZ2QFDEJsbm9HcmlkVmlldw9nZBQQtkQCjNfhVdcQ0iKZra2JJ3wVXkLg1YPmebIdd/ai");
			params.put("__EVENTVALIDATION",	"/wEWBQKT04euDwKpmJ2XBwKozYWxDAKAnbbXBwKM54rGBsJQcnNEe7cdLxuTGxHP/4Wk2udKflq2q4DOyGlM0c66");
		}
		params.put("TbBlno", value);
		return params;

	}
}
