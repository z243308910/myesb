package com.echounion.boss.Tracking.zim;

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
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class ZIMTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(ZIMTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		String stype=params.get(TrackingAdapter.STYPE).toString();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(params.containsKey("searchtype"))
			{
				if(stype.equals(TrackingAdapter.STYPE_CONTAINER))
				{
				info=getContainerInfo(entity);
				}
				else
				{
					info=getBillBookingInfo(entity);
				}
			}
			
			
		}catch (Exception e) {
			logger.error("ZIM 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(HttpEntity entity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(4); // 总信息
		info.setTitle("ZIM Tracking Result");
		try
		{
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("table", "id", "ctl00_SPWebPartManager1_g_0728e18d_b0eb_4993_ae27_14cdcc027dc9_ctl00_TraceShipment1_ContainerSearchRoutingDetails_gridContainerRoutingDetails");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				NodeList  firstNodeList = nodeList.elementAt(0).getChildren();
				firstNodeList=filterNode(firstNodeList, TableRow.class);
				Node[] tempNodes=firstNodeList.toNodeArray();
				if(tempNodes!=null)
				{
					for(int i=0;i<tempNodes.length;i++)
					{
						NodeList tempNodeList=tempNodes[i].getChildren();	//行的列
						if(i==0)			//子标题
						{
							tempNodeList=filterNode(tempNodeList, TableHeader.class);
							info.setHeadTitles(getTitles(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));		
						}
						else
						{
							tempNodeList=filterNode(tempNodeList, TableColumn.class);
							info.addRowDataList(getDataList(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));							
						}							
					}	
				}
			}
		}
		}catch (Exception e) {
			logger.error("采集ZIM信息出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getBillBookingInfo(HttpEntity entity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(3); // 总信息
		info.setTitle("PIL Tracking Result");
		try
		{
		List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
		TrackingInfo subTracking=null;						//子单
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			System.out.println(data);
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("div", "class", "generatl_data");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				NodeList  firstNodeList = nodeList.elementAt(0).getChildren();
				firstNodeList=filterNode(firstNodeList, BulletList.class);
				for (Node node:firstNodeList.toNodeArray()) {		//遍历每行
					NodeList tempNodeList=node.getChildren();	//行的列
					tempNodeList=filterNode(tempNodeList, Bullet.class);
					SimpleNodeIterator itNode=tempNodeList.elements();
					Node tempNode=null;
					NodeList spanNodeList=null;
					while(itNode.hasMoreNodes())
					{
						tempNode=itNode.nextNode();
						spanNodeList=tempNode.getChildren();
						spanNodeList=filterNode(spanNodeList,Span.class);
						tempNode=spanNodeList.elementAt(1);
						info.addHeadTitle(filterHtml(tempNode.toPlainTextString()));	//主标题
						tempNode=spanNodeList.elementAt(0);						
						info.addRowData(filterHtml(tempNode.toPlainTextString()));	//数据
					}
				}
				
				parser = new Parser(data);
				filter = getAndFilter("table", "id", "ctl00_SPWebPartManager1_g_0728e18d_b0eb_4993_ae27_14cdcc027dc9_ctl00_TraceShipment1_gridContainers");
				nodeList = parser.parse(filter);
				if(nodeList.size() > 0)
				{
					NodeList secondNodeList=nodeList.elementAt(0).getChildren();	//子单
					secondNodeList=filterNode(secondNodeList, TableRow.class);
					subTracking=new TrackingInfo(6);
					for (int i=0;i<secondNodeList.size();i++) {		//遍历每行
						NodeList tempNodeList=secondNodeList.elementAt(i).getChildren();	//行的列
						if(i==0)			//子标题
						{
							tempNodeList=filterNode(tempNodeList, TableHeader.class);
							subTracking.setHeadTitles(getTitles(cutNodeList(0, subTracking.getDataColumnSize(),tempNodeList)));		
						}
						else
						{
							tempNodeList=filterNode(tempNodeList, TableColumn.class);
							subTracking.addRowDataList(getDataList(cutNodeList(0, subTracking.getDataColumnSize(),tempNodeList)));							
						}
					}
					subList.add(subTracking);
					info.addSubTrackingInfo(subList);
				}
			}
		}
		}catch (Exception e) {
			logger.error("采集ZIM 信息出错"+e);
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
			params.put("searchvalue1", value);
			params.put("searchtype", "1");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("searchvalue1", value);
			params.put("searchtype", "2");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("searchvalue1", value);
			params.put("searchtype","3");
		}
		return params;
	}
}
