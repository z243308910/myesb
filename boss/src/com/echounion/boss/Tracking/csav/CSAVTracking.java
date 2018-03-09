package com.echounion.boss.Tracking.csav;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.entity.dto.TrackingInfo;

public class CSAVTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(CSAVTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost)
	{
 		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =null;
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			info =new TrackingInfo(5);     			  //总消息
			HttpEntity entity = response.getEntity();
			info.setTitle("CSAV Tracking Result");
			if (entity != null) 
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),Constant.ENCODEING_GBK));
				String data = null;
				StringBuffer sb = new StringBuffer();
				while((data=reader.readLine())!=null)
				{
					sb.append(data);
				}
				Parser parser = new Parser(sb.toString());
				AndFilter filter = getAndFilter("table", "class", "form");
				NodeList nodeList = parser.parse(filter);
				if(nodeList.size() > 1)
				{
					NodeList firstNodeList = nodeList.elementAt(1).getChildren();//获取第二个table下的子标签
					firstNodeList = filterNode(firstNodeList, TableRow.class);
					Node[] nodes = firstNodeList.toNodeArray();
					
					if(nodes.length > 1)				
					{
						Node tempNode=nodes[0];
						NodeList tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
						info.setHeadTitles(getTitles(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));//组装标题
						
						for(int i = 1; i<nodes.length ; i++)							//组装数据
						{
							tempNode=nodes[i];
							tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
							info.addRowDataList(getDataList(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));				
						}
					}
				}
			}
		}
		catch (Exception e) 
		{
			logger.error("CSAC 数据采集出错",e);
		}
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map)
	{
		Map<String,Object> params=new HashMap<String, Object>();
		String openagent = String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("openagent", openagent);
		return params;
	}
}
