package com.echounion.boss.Tracking.uasc;

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
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class UASCTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(UASCTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost) {
		HttpClient client =getHttpClient();
		String stype=params.get(TrackingAdapter.STYPE).toString();
		params=assembleSelfParams(params);
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		TrackingInfo info =null;
		try
		{
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(8); 									// 总信息
			info.setTitle("Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				if(stype.equals(TrackingAdapter.STYPE_CONTAINER))
				{
					info=getContainer(data, info);
				}else
				{
					info=getBill(data, info);
				}
			}
		}catch (Exception e) {
			logger.error("UASC数据采集出错"+e);
		}
		return info;
	}

	public TrackingInfo getContainer(String data,TrackingInfo info)throws Exception
	{
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "id", "table-trackContainer");
		NodeList nodeList = parser.parse(filter);
		if (nodeList.size() > 0) {
			nodeList = nodeList.elementAt(0).getChildren(); 				// 获得子节点
			nodeList = filterNode(nodeList, TableRow.class); 					// 获得tr类型的nodeList
			Node[] nodes=nodeList.toNodeArray();
			Node tempNode=null;
			NodeList tempNodeList=null;
			for(int i=0;i<nodes.length;i++)
			{
				tempNode=nodes[i];
				if(i==0)			//标题
				{
					tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
					info.setHeadTitles(getTitles(cutNodeList(0, tempNodeList)));							
				}else
				{
					tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
					info.addRowDataList(getDataList(cutNodeList(0, tempNodeList)));							
				}
			}
		}		
		return info;
	}
	
	public TrackingInfo getBill(String data,TrackingInfo info)throws Exception
	{
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "id", "table-trackSummary");
		NodeList nodeList = parser.parse(filter);
		if (nodeList.size() > 0) {
			nodeList = nodeList.elementAt(0).getChildren(); 				// 获得子节点
			nodeList = filterNode(nodeList, TableRow.class); 					// 获得tr类型的nodeList
			Node[] nodes=nodeList.toNodeArray();
			Node tempNode=null;
			NodeList tempNodeList=null;
			for(int i=0;i<nodes.length;i++)
			{
				tempNode=nodes[i];
				if(i==0)			//标题
				{
					tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
					info.setHeadTitles(getTitles(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));							
				}else
				{
					tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
					info.addRowDataList(getDataList(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));							
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
			params.put("Sequence", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("Sequence", value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("ContainerNo", value);
		}
		return params;
	}

}
