package com.echounion.boss.Tracking.apl;

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

public class APLTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(APLTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		TrackingInfo info =null;
		try
		{
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
			info= new TrackingInfo(5); // 总信息
			info.setTitle("APL Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table", "cellpadding", "5");
				NodeList nodeList = parser.parse(filter);
				if (nodeList.size() > 0) {					
					NodeList firstNodeList= nodeList.elementAt(1).getChildren();		//获得所有的tr
					firstNodeList=filterNode(firstNodeList, TableRow.class);
					Node[] nodes=firstNodeList.toNodeArray();
					Node tempNode=null;
					NodeList tempNodeList=null;
					for(int i=0;i<nodes.length;i++)
					{
						tempNode=nodes[i];
						if(i==0)			//标题
						{
							tempNodeList=filterNode(tempNode.getChildren(),TableHeader.class);
							info.setHeadTitles(getTitles(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));							
						}else
						{
							tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
							info.addRowDataList(getDataList(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));							
						}
					}
					
					TrackingInfo subInfo=null;
					if(nodeList.elementAt(2)!=null)
					{
						subInfo=new TrackingInfo(7);
						NodeList secondNodeList=nodeList.elementAt(2).getChildren();
						secondNodeList=filterNode(secondNodeList, TableRow.class);
						nodes=secondNodeList.toNodeArray();
						tempNode=null;
						for(int i=0;i<nodes.length;i++)
						{
							tempNode=nodes[i];
							if(i==0)			//标题
							{
								tempNodeList=filterNode(tempNode.getChildren(),TableHeader.class);
								subInfo.setHeadTitles(getTitles(cutNodeList(0, subInfo.getDataColumnSize(),tempNodeList)));							
							}else
							{
								tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
								subInfo.addRowDataList(getDataList(cutNodeList(0, subInfo.getDataColumnSize(),tempNodeList)));							
							}
						}
						subList.add(subInfo);
					}
					info.addSubTrackingInfo(subList);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("APL 数据采集出错"+e);
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
			params.put("popupFlag",false);
			params.put("blNbr", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("popupFlag",false);
			params.put("blNbr", value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("popupFlag",false);
			params.put("cntrNbr", value);
		}
		return params;
	}
	
}
