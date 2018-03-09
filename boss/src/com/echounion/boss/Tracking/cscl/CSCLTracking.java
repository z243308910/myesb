package com.echounion.boss.Tracking.cscl;

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

public class CSCLTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(CSCLTracking.class);
	
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
			
			info.setTitle("CSCL Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table", "border", "1");
				NodeList nodeList = parser.parse(filter);
				TrackingInfo subInfo=null;
				if (nodeList.size() > 0) {
					for(int i=0;i<nodeList.size();i++)
					{
						if(i==0)
						{
							continue;
						}
						NodeList firstNodeList= nodeList.elementAt(i).getChildren();		//获得所有的tr
						firstNodeList=filterNode(firstNodeList, TableRow.class);
						Node[] nodes=firstNodeList.toNodeArray();
						if(i==1)			//主表
						{
							info=assibleData(nodes);							
						}
						else			//子表
						{
							subInfo=assibleData(nodes);
							subList.add(subInfo);
						}
					}
					info.addSubTrackingInfo(subList);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("CSCL 数据采集出错"+e);
		}
		return info;
	}

	private TrackingInfo assibleData(Node[] nodes)
	{
		TrackingInfo info=new TrackingInfo();
		Node tempNode=null;
		NodeList tempNodeList=null;
		int size=0;
		for(int j=0;j<nodes.length;j++)
		{
			tempNode=nodes[j];
			tempNodeList=getNodeList(tempNode);
			size=tempNodeList.size();
			if(j==0)			//标题
			{
				info.setHeadTitles(getTitles(cutNodeList(0,size,tempNodeList)));							
			}else
			{
				info.addRowDataList(getDataList(cutNodeList(0, size,tempNodeList)));							
			}
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
		return nodeList;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			params.put("tf_bl_no", value);
			params.put("tr_num","bl_no");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("tf_bl_no", value);
			params.put("tr_num", "unit_no");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("tf_bl_no", value);
			params.put("tr_num", "unit_no");
		}
		return params;
	}

}
