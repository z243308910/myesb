package com.echounion.boss.Tracking.nyk;

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
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class NYKTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(NYKTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info=getTrackingInfo(entity);
			
		}catch (Exception e) {
			logger.error("NYK 数据采集出错"+e);
		}
		return info;
	}
	
	
	private TrackingInfo getTrackingInfo(HttpEntity entity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(2); // 总信息
		info.setTitle("NYK Tracking Result");
		try
		{
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("table", "cellpadding", "2");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				NodeList  firstNodeList =nodeList.elementAt(1).getChildren();
				firstNodeList=filterNode(firstNodeList, TableRow.class);
				SimpleNodeIterator itNode=firstNodeList.elements();
				Node tempNodeTr=null;
				Node[] columnNodes=null;
				while(itNode.hasMoreNodes())
				{
					tempNodeTr=itNode.nextNode();
					columnNodes=tempNodeTr.getChildren().toNodeArray();
					columnNodes=filterNode(tempNodeTr.getChildren(),TableColumn.class).toNodeArray();
					for(int i=0;i<columnNodes.length;i++)
					{
							if(i%2==0)		//标题
							{
								info.addHeadTitle(filterHtml(columnNodes[i].toPlainTextString()));
							}else
							{
								info.addRowData(filterHtml(columnNodes[i].toPlainTextString()));
							}							
					}
				}
				
				firstNodeList=nodeList.elementAt(2).getChildren();
				firstNodeList=filterNode(firstNodeList, TableRow.class);
				itNode=firstNodeList.elements();
				tempNodeTr=null;
				List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
				TrackingInfo subInfo=null;
				while(itNode.hasMoreNodes())		//遍历行
				{
					tempNodeTr=itNode.nextNode();
					if(tempNodeTr instanceof TableRow)
					{
						TableRow tempTr=(TableRow)tempNodeTr;
						if(tempTr.getAttribute("class")!=null)	//表头
						{
							if(tempTr.getAttribute("class").equals("ctTableHeader"))
							{
								subInfo=new TrackingInfo(4);
								subInfo.setHeadTitles(getTitles(filterNode(tempTr.getChildren(),TableColumn.class)));
								subList.add(subInfo);
								continue;
							}
							else
							{
								subInfo.addRowDataList(getDataList(filterNode(tempTr.getChildren(),TableColumn.class)));
							}
						}
					}
				}
				info.addSubTrackingInfo(subList);
			}
		}
		}catch (Exception e) {
			logger.error("采集 NYK 信息出错"+e);
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
			params.put("searchText", value);
			params.put("fromCT", true);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("searchText", value);
			params.put("fromCT", true);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("searchText", value);
			params.put("fromCT", true);
		}
		return params;
	}
}
