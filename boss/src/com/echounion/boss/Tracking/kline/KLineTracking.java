package com.echounion.boss.Tracking.kline;

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
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class KLineTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(KLineTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client = getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			String jsonUrl="http://ecom.kline.com/Tracking/Search/FindShipment";
			params.put("searchValue",params.get("gctSearchInput"));
			HttpUriRequest secondHttpSubmit=getRequestSubmit(jsonUrl, params, false);
			client =getHttpClient();
			HttpResponse secondresponse = client.execute(secondHttpSubmit);
			HttpEntity secondEntity = secondresponse.getEntity();
			info=getBillBookingInfo(entity,secondEntity);
			
		}catch (Exception e) {
			logger.error("PIL 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(TrackingInfo info,JSONObject json)
	{
		List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
		try
		{
			JSONArray jsonArray=json.getJSONArray("currentmovements");
			Map<String,TrackingInfo> map=new HashMap<>();
			
			TrackingInfo subInfo=null;
			JSONObject jsonObj=null;
			String key=null;
			for (Object object :jsonArray) {
				jsonObj=(JSONObject)object;
				key=jsonObj.getString("containersequence");
				if(map.containsKey(key))
				{
					subInfo=map.get(key);
				}else
				{
					subInfo=new TrackingInfo(5);
					map.put(key,subInfo);
					subInfo.setTitle("ContainerNumber:"+jsonObj.getString("containernumber")+" ContainerType:"+jsonObj.getString("containertype"));
					subInfo.addHeadTitle("Event");
					subInfo.addHeadTitle("Date");
					subInfo.addHeadTitle("Location");
					subInfo.addHeadTitle("Facility");
					subInfo.addHeadTitle("Mode");
					subList.add(subInfo);
				}
				subInfo.addRowData(jsonObj.getString("eventdescription"));
				subInfo.addRowData(jsonObj.getString("eventdate"));
				subInfo.addRowData(jsonObj.getString("location"));
				subInfo.addRowData(jsonObj.getString("facility"));
				subInfo.addRowData(jsonObj.getString("mode"));
			}
			info.addSubTrackingInfo(subList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	private TrackingInfo getBillBookingInfo(HttpEntity entity,HttpEntity secondEntity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(6); // 总信息
		info.setTitle("PIL Tracking Result");
		JSONObject json=null;
		try
		{
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			String jsonStr=EntityUtils.toString(secondEntity).toLowerCase();
			json=JSON.parseObject(jsonStr);
			json=json.getJSONObject("searchresultitem");
			if(json==null)
			{
				return info;
			}
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("table", "id", "blHeader");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				
				try
				{
					info.addHeadTitle("");info.addHeadTitle("");info.addHeadTitle("");
					info.addHeadTitle("");info.addHeadTitle("");info.addHeadTitle("");
					
					NodeList  firstNodeList = nodeList.elementAt(0).getChildren();
					firstNodeList=filterNode(firstNodeList, TableRow.class);
					for (Node node:firstNodeList.toNodeArray()) {		//遍历每行
						NodeList tempNodeList=node.getChildren();	//行的列
						tempNodeList=filterNode(tempNodeList, TableColumn.class);
						info.addRowDataList(getDataList(json,cutNodeList(0, 6,tempNodeList)));							
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		}catch (Exception e) {
			logger.error("采集PIL信息出错"+e);
		}
		info=getContainerInfo(info, json);
		return info;
	}
	
	public List<String> getDataList(JSONObject json, NodeList nodeList) {
		List<String> list = new ArrayList<String>();
		Node node = null;
		SimpleNodeIterator it = nodeList.elements();
		while (it.hasMoreNodes()) {
			node = it.nextNode();
			String text =null;
			if(node==null)
			{
				text="";
			}
			else
			{
				TableColumn column=(TableColumn)node;
				String attr=column.getAttribute("data-bind");
				if(attr!=null)		//数据绑定名称
				{
					text=getExName(attr).toLowerCase();
					text=json.getString(text);
					text=text==null?column.toPlainTextString().trim():text;
				}else
				{
					text= node.toPlainTextString().trim();
				}
			}
			text=filterHtml(text);
			list.add(text);
		}
		return list;
	}
	
	private String getExName(String ex)
	{
		int index=ex.indexOf("text: ");
		if(index>=0)
		{
			ex=ex.substring(6+index);
		}
		
		index=ex.indexOf("text:");
		if(index>=0)
		{
			ex=ex.substring(5+index);
		}
		index=ex.indexOf(",");
		if(index>0)
		{
			ex=ex.substring(0,index);
		}
		return ex;
	}
	
	/**
	 * 切分NodeList
	 * 
	 * @param length
	 *            切多少个
	 * @param index
	 *            从第几个索引开始，截取length个对象组成nodeList对象
	 * @param nodeList
	 * @return
	 */
	public NodeList cutNodeList(int index, int length, NodeList nodeList) {
		NodeList list = new NodeList();
		Node node = null;
		for (int i = index; i <length; i++) {
			node = nodeList.elementAt(i);
			list.add(node);
			if (list.size() >= length) {
				break;
			}
		}
		return list;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			params.put("gctSearchInput", value);
			params.put("Action","SEARCH");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("gctSearchInput", value);
			params.put("Action","SEARCH");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			logger.warn("KLINE的Container无法抓取,请用bl/booking no查询!");
			params.put("gctSearchInput", value);
			params.put("Action","SEARCH");
		}
		return params;
	}
}
