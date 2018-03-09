package com.echounion.boss.Tracking.msk;

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
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class MskTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(MskTracking.class);
	
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
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				if(stype.equals(TrackingAdapter.STYPE_CONTAINER))			//箱
				{
					return getContainerInfo(parser);
				}
				else if(stype.equals(TrackingAdapter.STYPE_BOOKING))
				{
					return getBillInfo(parser);
				}else if(stype.equals(TrackingAdapter.STYPE_BILL))
				{
					return getBillInfo(parser);
				}
			}
		}catch (Exception e) {
			logger.error("MSK数据采集出错"+e);
		}
		return info;
	}
	

	/**
	 * BillNo
	 * @author 胡礼波
	 * 2012-11-23 下午06:54:27
	 * @return
	 */
	public TrackingInfo getBillInfo(Parser parser)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(10); // 总信息
		info.setTitle("MSK Tracking Result");
		try
		{
			AndFilter filter = getAndFilter("table", "class", "lstBox");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				NodeList tdNodeList=null;
				NodeList firstTableNodeList=nodeList.elementAt(0).getChildren();	//获得第一个table
				NodeList firstTrNodeList=filterNode(firstTableNodeList,TableRow.class);	//获得tr类型的nodeList
				for(int i=0;i<firstTrNodeList.size();i++)
				{
					tdNodeList=filterNode(firstTrNodeList.elementAt(i).getChildren(),TableColumn.class);
					if(tdNodeList.size()<=2)
					{
						break;
					}
					if(i==0)
					{
						info.setHeadTitles(getDataList(cutNodeList(0,tdNodeList)));
					}else
					{
						info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));							
					}
				}
			}
		}catch (Exception e) {
			logger.error("采集MSK BILL NO 出错"+e);
		}
		return info;
	}
	
	/**
	 * Container信息
	 * @author 胡礼波
	 * 2012-11-23 下午05:14:21
	 * @param parser
	 * @return
	 */
	public TrackingInfo getContainerInfo(Parser parser)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(); // 总信息
		info.setTitle("Container details");
		try
		{
			AndFilter filter = getAndFilter("td", "class", "lstTxt");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				nodeList = nodeList.elementAt(2).getChildren(); // 获得第三个td
				nodeList = filterNode(nodeList, LinkTag.class); // 获得a标签类型的nodeList
				LinkTag linkTag=(LinkTag)nodeList.elementAt(0);
				String link=linkTag.getLink();			//箱型的URL地址
				
				HttpClient client =getHttpClient();
				HttpUriRequest httpSubmit=getRequestSubmit(link, new HashMap<String,Object>(),false);
				HttpResponse response = client.execute(httpSubmit);
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);
				parser = new Parser(data);

				filter = getAndFilter("table", "class", "dlgBoxDetails");
				nodeList = parser.parse(filter);
				Node tempNode=null;
				NodeList tdNodeList=null;
				TrackingInfo subInfo=null;
				List<TrackingInfo> subInfoList=new ArrayList<TrackingInfo>();
				try
				{
					for (int i=0;i<nodeList.size();i++) {				//遍历table
						tempNode=nodeList.elementAt(i);
						NodeList trNodes=tempNode.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class));
						subInfo=new TrackingInfo();
						for(int j=0;j<trNodes.size();j++)				//tr
						{
							
							Node trNode=trNodes.elementAt(j);
							if(!(trNode instanceof TableRow))
							{
								continue;
							}
							if(i==0)
							{
								tdNodeList=filterNode(trNode.getChildren(),TableColumn.class);
								if(j==0)		//column列名
								{
									info.setHeadTitles(getTitles(cutNodeList(0,tdNodeList)));		
								}
								else if(j>0)
								{
									info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
								}
							}else
							{
								tdNodeList=filterNode(trNode.getChildren(),TableColumn.class);
								if(j==0)		//column列名
								{
									subInfo.setHeadTitles(getTitles(cutNodeList(0,tdNodeList)));
									subInfoList.add(subInfo);
								}
								else if(j>0)
								{
									subInfo.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
								}
							}
						}
					}
					
					
					/***********************Routing info*************************/
					parser = new Parser(data);
					filter = getAndFilter("table", "class", "lstBox");
					nodeList = parser.parse(filter);
					tempNode=nodeList.elementAt(1);					//获取第二个table
					if(tempNode!=null)
					{
						NodeList trNodes=tempNode.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class));
						if(trNodes!=null && trNodes.size()>0)
						{
							subInfo=new TrackingInfo();
							subInfo.setTitle("Container is empty - Current move");
							for (int i=0;i<trNodes.size();i++) {
								Node trNode=trNodes.elementAt(i);
								tdNodeList=filterNode(trNode.getChildren(),TableColumn.class);
								if(i==0)
								{
									continue;
								}
								if(i==1)
								{
									subInfo.setHeadTitles(getTitles(cutNodeList(0,tdNodeList)));
								}else
								{
									subInfo.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
								}
							}
							subInfoList.add(subInfo);
						}
					}
					info.addSubTrackingInfo(subInfoList);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			logger.error("采集MEC Continer信息异常"+e);
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
			params.put("portlet_trackSimple_1wlw-select_key:{pageFlow.trackSimpleForm.type}", "BLNUMBER");
			params.put("portlet_trackSimple_1{pageFlow.trackSimpleForm.numbers}", value);
			params.put("portlet_trackSimple_1wlw-select_key:{pageFlow.trackSimpleForm.type}OldValue","true");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("portlet_trackSimple_1wlw-select_key:{pageFlow.trackSimpleForm.type}", "BOOKINGNUMBER");
			params.put("portlet_trackSimple_1{pageFlow.trackSimpleForm.numbers}", value);
			params.put("portlet_trackSimple_1wlw-select_key:{pageFlow.trackSimpleForm.type}OldValue","true");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("portlet_trackSimple_1wlw-select_key:{pageFlow.trackSimpleForm.type}", "CONTAINERNUMBER");
			params.put("portlet_trackSimple_1{pageFlow.trackSimpleForm.numbers}", value);
			params.put("portlet_trackSimple_1wlw-select_key:{pageFlow.trackSimpleForm.type}OldValue","true");
		}else
		{
			logger.warn("MSK search type is null!");
		}
		logger.info("MSK Search value is:"+value);
		return params;
	}
}
