package com.echounion.boss.Tracking.cma;

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
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class CMATracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(CMATracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		String stype=params.get(TrackingAdapter.STYPE).toString();
		params=assembleSelfParams(params);
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		TrackingInfo info =null;
		try
		{
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String htmlData = EntityUtils.toString(entity);
				if(stype.equals(TrackingAdapter.STYPE_CONTAINER))
				{
					info=getContainerInfo(info, htmlData);					
				}else
				{
					info=getContainerInfo(info, htmlData);
				}
			}
		}catch (Exception e) {
			logger.error("CMA数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(TrackingInfo info,String htmlData)
	{
		info= new TrackingInfo(); // 总信息
		info.setTitle("CMA Tracking Result");
		try
		{
			List<String> headList=new ArrayList<>();
			List<String> dataList=new ArrayList<>();	
			String filerProp[]=new String[]
					{
						"items-table mini wauto txtleft small-midi",
						"items-table left w35 midi oldie-midiplus txtleft mr1 small-w45 small-midi",
						"items-table left mega w35 txtleft mr2 small-w45 small-midi",
						"items-table left maxi w25 mb0-8 txtleft small-w100 small-midi"
					};
			
			for (String prop : filerProp) {
				Parser parser = new Parser(htmlData);
				AndFilter filter = getAndFilter("table", "class", prop);
				NodeList nodeList = parser.parse(filter);
				Node tmpTrNode=null;
				Node tmpTdNode=null;
				NodeList tmpTdNodeList=null;
				if (nodeList.size() > 0) {
					nodeList =filterNode(nodeList.elementAt(0).getChildren(),TableRow.class);			//获取第一个table
					for (int i=0;i<nodeList.size();i++) {
						tmpTrNode=nodeList.elementAt(i);
						tmpTdNodeList=tmpTrNode.getChildren();
						for(int j=0;j<tmpTdNodeList.size();j++)
						{
							tmpTdNode=tmpTdNodeList.elementAt(j);
							if(tmpTdNode instanceof TableHeader)		//标题
							{
								headList.add(tmpTdNode.toPlainTextString().trim());
							}else if(tmpTdNode instanceof TableColumn)
							{
								dataList.add(tmpTdNode.toPlainTextString().trim());
							}
						}
					}
				}
			}
			info.setHeadTitles(headList);
			info.addRowDataList(dataList);
		}
		catch (Exception e) {
				e.printStackTrace();
			}
			
			try
			{
				TrackingInfo subInfo=new TrackingInfo();
				Parser parser = new Parser(htmlData);
				AndFilter filter = getAndFilter("table", "class","data-table");
				NodeList nodeList = parser.parse(filter);
				Node tmpTrNode=null;
				Node tmpTdNode=null;
				NodeList tmpTdNodeList=null;
				if(nodeList.size()>0)
				{
					List<String> headList=new ArrayList<>();
					List<String> dataList=new ArrayList<>();
					List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
					subInfo.setTitle("Container Moves");
					
					nodeList =filterNode(nodeList.elementAt(0).getChildren(),TableRow.class);			//获取第一个table
					for (int i=0;i<nodeList.size();i++) {
						tmpTrNode=nodeList.elementAt(i);
						tmpTdNodeList=tmpTrNode.getChildren();
						for(int j=0;j<tmpTdNodeList.size();j++)
						{
							tmpTdNode=tmpTdNodeList.elementAt(j);
							if(tmpTdNode instanceof TableHeader)		//标题
							{
								headList.add(tmpTdNode.toPlainTextString().trim());
							}else if(tmpTdNode instanceof TableColumn)
							{
								dataList.add(tmpTdNode.toPlainTextString().trim());
							}
						}
					}
					subInfo.setHeadTitles(headList);
					subInfo.addRowDataList(dataList);
					subList.add(subInfo);
					info.addSubTrackingInfo(subList);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		return info;
	}
	
	public List<String> getTitles(NodeList nodeList)
	{
		List<String> list = new ArrayList<String>();
		Node node = null;
		NodeList childrenNodeList=null;
		Node tempNode=null;		
		for (int i = 0; i <nodeList.size(); i++) {
			if(i%2!=0)
			{
				continue;
			}
			node=nodeList.elementAt(i);
			childrenNodeList=node.getChildren();
			tempNode=childrenNodeList.elementAt(1);			
			list.add(filterHtml(tempNode.toPlainTextString().trim()));
		}
		return list;
	}
	
	public List<String> getDataList(NodeList nodeList)
	{
		List<String> list = new ArrayList<String>();
		Node node = null;
		NodeList childrenNodeList=null;
		Node tempNode=null;
		for (int i = 0; i <nodeList.size(); i++) {
			if(i%2!=0)
			{
				continue;
			}
			node=nodeList.elementAt(i);
			childrenNodeList=node.getChildren();
			tempNode=childrenNodeList.elementAt(3);
			list.add(filterHtml(tempNode.toPlainTextString().trim()));
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
			params.put("SearchBy", "BL");
			params.put("Reference", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("SearchBy", "Booking");
			params.put("Reference", value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("SearchBy", "Container");
			params.put("Reference", value);
		}
		return params;
	}
}
