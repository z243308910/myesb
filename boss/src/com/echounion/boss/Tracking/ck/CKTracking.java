package com.echounion.boss.Tracking.ck;

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
import org.htmlparser.util.SimpleNodeIterator;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class CKTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(CKTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
			info= new TrackingInfo(); // 总信息
			info.setTitle("CK Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table", "class", "box_s");
				NodeList nodeList = parser.parse(filter);
				TrackingInfo subInfo=null;
				boolean isTitle=true;		//标识是否是标题或者数据
				boolean isData=true;
				if (nodeList.size() > 0) {
					for(int i=0;i<nodeList.size();i++)
					{
						NodeList firstNodeList= nodeList.elementAt(i).getChildren();		//获得所有的tr
						firstNodeList=filterNode(firstNodeList, TableRow.class);
						Node[] nodes=firstNodeList.toNodeArray();
						for (Node node : nodes) {
							
							if(isHeadTag(node.getChildren()))
							{
								if(isTitle)			//主表标题
								{
									info=assibleData(info,node,true);
									isTitle=false;
								}
								else			//子表标题
								{
									subInfo=new TrackingInfo();
									subInfo=assibleData(subInfo,node,true);
									subList.add(subInfo);
								}
							}else
							{
								if(isData)			//主表数据
								{
									info=assibleData(info,node,false);
									isData=false;
								}
								else			//子表数据
								{
									subInfo=assibleData(subInfo,node,false);
								}
							}
						}
					}
					info.addSubTrackingInfo(subList);
				}
			}
		}catch (Exception e) {
			logger.error("CK 数据采集出错"+e);
		}
		return info;
	}

	private TrackingInfo assibleData(TrackingInfo info,Node node,boolean isTitle)
	{
		NodeList tempNodeList=null;
		int size=0;
		tempNodeList=getNodeList(node);
		size=tempNodeList.size();
		if(isTitle)			//标题
		{
			info.setHeadTitles(getTitles(cutNodeList(0,size,tempNodeList)));							
		}else
		{
			info.addRowDataList(getDataList(cutNodeList(0, size,tempNodeList)));							
		}
		return info;
	}
	
	private NodeList getNodeList(Node tempNode)
	{
		if(tempNode==null)
		{
			return null;
		}
		NodeList nodeList=filterNode(tempNode.getChildren(),TableHeader.class);
		nodeList.add(filterNode(tempNode.getChildren(),TableColumn.class));
		return nodeList==null?new NodeList():nodeList;
	}
	
	/**
	 * 判断是否是标题
	 * @param nodeList
	 * @return
	 */
	private boolean isHeadTag(NodeList nodeList)
	{
		SimpleNodeIterator sit=nodeList.elements();
		Node node=null;
		while (sit.hasMoreNodes()) {
			node=sit.nextNode();
			if(node instanceof TableHeader)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			
			params.put("cmd","se");
			params.put("subCmd", "03");
			params.put("lastCmd", "05");
			params.put("select_Gubun","sbkg_bl");
			params.put("user_bkg_bl", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
		}
		return params;
	}

}
