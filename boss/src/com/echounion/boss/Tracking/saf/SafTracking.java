package com.echounion.boss.Tracking.saf;

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
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class SafTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(SafTracking.class);
	
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
			if(stype.equals(TrackingAdapter.STYPE_CONTAINER))
			{
				info=getContainerInfo(entity);
			}
			else
			{
				info=getBillBookingInfo(entity);
			}
			
		}catch (Exception e) {
			logger.error("SAF 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(HttpEntity entity)
	{
		TrackingInfo info= new TrackingInfo(); // 总信息
		info.setTitle("SAF Tracking Result");
		try
		{
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("table", "class", "results");
			NodeList nodeList = parser.parse(filter);
			if (nodeList!=null && nodeList.size() > 0) {
				NodeList  firstNodeList = nodeList.elementAt(0).getChildren();
				firstNodeList=filterNode(firstNodeList, TableRow.class);
				if(firstNodeList!=null)
				{
						for(int i=0;i<firstNodeList.size();i++)
						{
							TableRow trNode=(TableRow)firstNodeList.elementAt(i);
							if(i==0)			//子标题
							{
								NodeList thNodeList=filterNode(trNode.getChildren(), TableHeader.class);
								info.setHeadTitles(getTitles(cutNodeList(0,thNodeList)));		
							}
							else
							{
								if(!"results".equals(trNode.getAttribute("class")))
								{
									continue;
								}
								NodeList tdNodeList=filterNode(firstNodeList.elementAt(i).getChildren(), TableColumn.class);
								info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));							
							}
						}
				}
			}
		}
		}catch (Exception e) {
			logger.error("采集SAF信息出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getBillBookingInfo(HttpEntity entity)
	{
		TrackingInfo info= new TrackingInfo(); // 总信息
		info.setTitle("SAF Tracking Result");
		try
		{
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			System.out.println(data);
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("table", "class", "results");
			NodeList nodeList = parser.parse(filter);
			if (nodeList!=null && nodeList.size() > 0) {
				NodeList  firstNodeList = nodeList.elementAt(0).getChildren();
				firstNodeList=filterNode(firstNodeList, TableRow.class);
				if(firstNodeList!=null)
				{
						for(int i=0;i<firstNodeList.size();i++)
						{
							TableRow trNode=(TableRow)firstNodeList.elementAt(i);
							if(i==0)			//子标题
							{
								NodeList thNodeList=filterNode(trNode.getChildren(), TableHeader.class);
								info.setHeadTitles(getTitles(cutNodeList(0,thNodeList)));		
							}
							else
							{
								if(!"results".equals(trNode.getAttribute("class")))
								{
									continue;
								}
								NodeList tdNodeList=filterNode(firstNodeList.elementAt(i).getChildren(), TableColumn.class);
								info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));							
							}
						}
				}
			}
		}
		}catch (Exception e) {
			logger.error("采集SAF信息出错"+e);
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
			params.put("linktype","unreg");
			params.put("queryorigin","Header");
			params.put("queryoriginauto","HeaderNonSecure");
			params.put("searchType","Document");
			params.put("searchNumberString",value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("linktype","unreg");
			params.put("queryorigin","Header");
			params.put("queryoriginauto","HeaderNonSecure");
			params.put("searchType","Document");
			params.put("searchNumberString",value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("linktype","unreg");
			params.put("queryorigin","Header");
			params.put("queryoriginauto","HeaderNonSecure");
			params.put("searchType","Container");
			params.put("searchNumberString",value);
		}
		return params;
	}
}
