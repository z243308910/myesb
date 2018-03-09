package com.echounion.boss.Tracking.pil;

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
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class PILTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(PILTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client =getHttpsClient();
		params=assembleSelfParams(params);
		TrackingInfo info = new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(params.get("searchby").equals("cn"))
			{
				info=getContainerInfo(entity);
			}
			else
			{
				info=getBillBookingInfo(entity);
			}
			
		}catch (Exception e) {
			logger.error("PIL 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(HttpEntity entity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(6); // 总信息
		info.setTitle("PIL Tracking Result");
		try
		{
		if (entity != null) {
			JSONObject obj = JSON.parseObject(EntityUtils.toString(entity), JSONObject.class);
			JSONObject data = obj.getJSONObject("data");
			if(!(null==data||data.isEmpty()))
			{
				String containers = data.getString("containers");
				Parser parser = new Parser(containers);
				NodeList nodeList = parser.parse(getAndFilter("tr","class","titles"));
				Node[] columns = nodeList.elementAt(0).getChildren().toNodeArray();
				for(Node column:columns)
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
				}
				parser = new Parser(containers);
				nodeList = parser.parse(new NodeClassFilter(TableTag.class));
				Node[] containerTabs = nodeList.toNodeArray();
				Node[] rows = null;
				for(Node contrainer:containerTabs)
				{
					rows = contrainer.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
					for(Node row : rows)
					{
						columns = row.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
						for(Node column:columns)
						{
							info.addRowData(filterHtml(column.toPlainTextString()));
						}
					}
				}
			}
		}
		}catch (Exception e) {
			logger.error("采集PIL信息出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getBillBookingInfo(HttpEntity entity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(4); // 总信息
		info.setTitle("PIL Tracking Result");
		try
		{
		if (entity != null) {
			JSONObject obj = JSON.parseObject(EntityUtils.toString(entity), JSONObject.class);
			JSONObject data = obj.getJSONObject("data");
			if(!(null==data||data.isEmpty()))
			{
				String schedule_table = data.getString("schedule_table");
				schedule_table = schedule_table.replace("\\", "").replaceAll("<br />", " ");
				Parser parser = new Parser(schedule_table);
				NodeList nodeList = parser.parse(new NodeClassFilter(TableRow.class));
				Node[] rows = nodeList.toNodeArray();
				Node[] columns = null;
				for(int i=0;i<rows.length;i++)
				{
					columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
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
				info.addSubTrackingInfo(getBLSubTrackingInfo(data));
			}
		}
		}catch (Exception e) {
			logger.error("采集PIL信息出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getBLSubTrackingInfo(JSONObject data) throws Exception
	{
		TrackingInfo info = new TrackingInfo(6);
		String containers = data.getString("containers");
		containers = containers.replace("\\", "");
		Parser parser = new Parser(containers);
		NodeList nodeList = parser.parse(getAndFilter("tr","class","titles"));
		Node[] columns = nodeList.elementAt(0).getChildren().toNodeArray();
		for(Node column:columns)
		{
			info.addHeadTitle(filterHtml(column.toPlainTextString()));
		}
		parser = new Parser(containers);
		nodeList = parser.parse(new NodeClassFilter(TableTag.class));
		Node[] containerTabs = nodeList.toNodeArray();
		Node[] rows = null;
		for(Node contrainer:containerTabs)
		{
			rows = contrainer.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
			for(Node row : rows)
			{
				columns = row.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
				for(Node column:columns)
				{
					info.addRowData(filterHtml(column.toPlainTextString()));
				}
			}
		}
		return info;
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
			params.put("ref_num", value);
			params.put("fn","get_tracktrace_bl");
			params.put("searchby","bl");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("refno", value);
			params.put("searchby","br");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("ref_num", value);
			params.put("fn","get_tracktrace_container");
			params.put("searchby","cn");
		}
		return params;
	}
}
