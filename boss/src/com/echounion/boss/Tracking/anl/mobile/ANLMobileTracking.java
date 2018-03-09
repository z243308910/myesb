package com.echounion.boss.Tracking.anl.mobile;

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

public class ANLMobileTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(ANLMobileTracking.class);
	
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
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				info = getContainerInfo(data);		
			}
		}catch (Exception e) {
			logger.error("ANL数据采集出错"+e);
		}
		return info;
	}

	public TrackingInfo getContainerInfo(String data)throws Exception
	{
		TrackingInfo info = new TrackingInfo();
		info.setTitle("Tracking Result");
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "class", "data-table");
		NodeList nodeList = parser.parse(filter);
		if (nodeList.size() > 0) {
			nodeList = nodeList.elementAt(0).getChildren(); 				// 获得第一个table
			nodeList = filterNode(nodeList, TableRow.class);				// 获得tr类型的nodeList
			Node trNode=null;
			for(int i=0;i<nodeList.size();i++)
			{
				trNode=nodeList.elementAt(i);
				if(i==0)
				{
					NodeList thNodeList=filterNode(trNode.getChildren(), TableHeader.class);
					info.setHeadTitles(getTitles(cutNodeList(0,thNodeList)));					
				}else
				{
					NodeList tdNodeList=filterNode(trNode.getChildren(), TableColumn.class);
					info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));					
				}
			}
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) 
	{
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("Reference", value);
		params.put("SearchBy","Container");
		return params;
	}

}
