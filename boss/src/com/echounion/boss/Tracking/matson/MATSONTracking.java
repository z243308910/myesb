package com.echounion.boss.Tracking.matson;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class MATSONTracking extends AbstractTrack {

	private Logger logger = Logger.getLogger(MATSONTracking.class);

	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		HttpClient client = getHttpsClient();
		params = assembleSelfParams(params);
		TrackingInfo info = new TrackingInfo();
		try {
			HttpUriRequest httpSubmit = getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info = new TrackingInfo(); // 总信息
			info.setTitle("MATSON Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				NodeFilter[] filters = new NodeFilter[]{new TagNameFilter("table"),
						new HasAttributeFilter("width", "98%"), 
						new HasAttributeFilter("cellpadding", "5")};
				NodeList nodeList = parser.parse(new AndFilter(filters));
				info = getTrackingInfo(nodeList);
				info.addSubTrackingInfo(getShipmentInfo(nodeList));
				info.addSubTrackingInfo(getContainerInfo(nodeList));				
			}
		} catch (Exception e) {
			logger.error("MATSON 数据采集出错" + e);
		}
		return info;
	}
	
	private TrackingInfo getTrackingInfo(NodeList nodeList) throws Exception 
	{
		TrackingInfo info = new TrackingInfo(3); // 总信息
		info.setTitle("MATSON Tracking Result");
		Node[] rows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
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
		return info;
	}
	
	private TrackingInfo getShipmentInfo(NodeList nodeList) throws Exception
	{
		TrackingInfo info = new TrackingInfo(1); // 总信息
		info.setTitle("Shipment Information");
		Node[] rows = nodeList.elementAt(1).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
		Node[] columns = null;
		info.addHeadTitle("Shipment Information");
		for(Node row:rows)
		{
			columns = row.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
			for(Node column:columns)
			{		
				info.addRowData(filterHtml(column.toPlainTextString()));
			}
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(NodeList nodeList) throws Exception
	{
		TrackingInfo info = new TrackingInfo(1); // 总信息
		info.setTitle("Container Information");
		Node[] rows = nodeList.elementAt(2).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
		Node[] columns = null;
		info.addHeadTitle("Container Information");
		for(Node row:rows)
		{
			columns = row.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
			for(Node column:columns)
			{		
				info.addRowData(filterHtml(column.toPlainTextString()));
			}
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String, Object> params = new HashMap<String, Object>();
		String type = String.valueOf(map.get(TrackingAdapter.STYPE)); // 获得类型
		String value = String.valueOf(map.get(TrackingAdapter.SVALUE));
		if (type.equals(TrackingAdapter.STYPE_BILL)) // bill
		{
			params.put("billNumber", value);
		} else if (type.equals(TrackingAdapter.STYPE_BOOKING)) // 订餐
		{
			params.put("bookingNumber", value);
		} else if (type.equals(TrackingAdapter.STYPE_CONTAINER)) // container
		{
			params.put("containerNumber", value);
		}
		return params;
	}

}
