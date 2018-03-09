package com.echounion.boss.Tracking.vessel;

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
import com.echounion.boss.entity.dto.TrackingInfo;

public class VesselTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(VesselTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =null;
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			info =new TrackingInfo(5);     			  //总消息
			HttpEntity entity = response.getEntity();
			info.setTitle("Vessel Tracking Result");
			if (entity != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),"GBK"));
				String data = null;
				StringBuffer sb = new StringBuffer();
				while((data=reader.readLine())!=null)
				{
					sb.append(data);
				}
				Parser parser = new Parser(sb.toString());
				AndFilter filter = getAndFilter("table", "cellpadding", "4");
				NodeList nodeList = parser.parse(filter);
				if (nodeList.size() > 1) {		
					
					NodeList firstNodeList= nodeList.elementAt(0).getChildren();		//获取第一个table下子标签
					firstNodeList=filterNode(firstNodeList, TableRow.class);			//使用filter（）方法过滤nodelist中除了指定类型的tag之外的tag
					Node[] nodes=firstNodeList.toNodeArray();
					Node tempNode=null;
					NodeList tempNodeList=null;
					for(int i=0;i<nodes.length;i++)                                     //组装标题
					{
						tempNode=nodes[i];
						tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);          
						info.setHeadTitles(getTitles(cutNodeList(2, info.getDataColumnSize(),tempNodeList)));							
					}
					
					Node[] nextNodes = nodeList.toNodeArray();
					for(int j = 1; j<nextNodes.length ; j++)							//组装数据
					{
						
						NodeList secondNodeList=nextNodes[j].getChildren();
						secondNodeList=filterNode(secondNodeList, TableRow.class);
						nodes=secondNodeList.toNodeArray();
						tempNode=null;
						for(int i=0;i<nodes.length;i++)
						{
							tempNode=nodes[i];
							tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
							info.addRowDataList(getDataList(cutNodeList(2, info.getDataColumnSize(),tempNodeList)));							
						}
					}
				}
				
			}
		}catch (Exception e) {
			logger.error("Vessel 数据采集出错",e);
		}
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		
		Map<String,Object> params=new HashMap<String, Object>();
		
		String is_export = String.valueOf(map.get("is_export"));
		String from_port_id=String.valueOf(map.get(VesselAdapter.POL));		//获得起运港
		String to_port_id=String.valueOf(map.get(VesselAdapter.POD));		//获取目的港
		String range=String.valueOf(map.get("range"));                      //获得显示数据的范围
		String encode=String.valueOf(map.get("encode"));					//页面编码
		
		params.put("from_port_id", from_port_id);
		params.put("to_port_id", to_port_id);
		params.put("range", range);
		params.put("is_export", is_export);
		params.put("encode", encode);
		return params;
	}
}
